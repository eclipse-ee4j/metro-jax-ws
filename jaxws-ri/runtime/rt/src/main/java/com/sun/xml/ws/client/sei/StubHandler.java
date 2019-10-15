/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client.sei;

import com.oracle.webservices.api.databinding.JavaCallInfo;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.databinding.ClientCallBridge;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.MessageContextFactory;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.JavaMethod;
import com.sun.xml.ws.fault.SOAPFaultBuilder;
import com.sun.xml.ws.model.CheckedExceptionImpl;
import com.sun.xml.ws.model.JavaMethodImpl;
import com.sun.xml.ws.model.ParameterImpl;
import com.sun.xml.ws.model.WrapperParameter;

import javax.xml.namespace.QName;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@link com.sun.xml.ws.client.sei.MethodHandler} that handles synchronous method invocations.
 *  This is refactored from SEIMethodHandler.
 * <p>
 * This class mainly performs the following two tasks:
 * <ol>
 *  <li>Accepts Object[] that represents arguments for a Java method,
 *      and creates {@link com.sun.xml.ws.message.jaxb.JAXBMessage} that represents a request message.
 *  <li>Takes a {@link com.sun.xml.ws.api.message.Message} that represents a response,
 *      and extracts the return value (and updates {@link javax.xml.ws.Holder }s.)
 * </ol>
 *
 * <h2>Creating {@link com.sun.xml.ws.message.jaxb.JAXBMessage }</h2>
 * <p>
 * At the construction time, we prepare {@link com.sun.xml.ws.client.sei.BodyBuilder} and {@link com.sun.xml.ws.client.sei.MessageFiller}s
 * that know how to move arguments into a {@link com.sun.xml.ws.api.message.Message }.
 * Some arguments go to the payload, some go to headers, still others go to attachments.
 *
 * @author Kohsuke Kawaguchi
 * @author Jitendra Kotamraju
 * @author shih-chang.chen@oracle.com
 */
public class StubHandler implements ClientCallBridge {

    // these objects together create a message from method parameters
    private final BodyBuilder bodyBuilder;
    private final MessageFiller[] inFillers;
    protected final String soapAction;
    protected final boolean isOneWay;
    protected final JavaMethodImpl javaMethod;
    protected final Map<QName, CheckedExceptionImpl> checkedExceptions;
    protected SOAPVersion soapVersion = SOAPVersion.SOAP_11;
    protected ResponseBuilder responseBuilder;
    protected MessageContextFactory packetFactory;
    
    public StubHandler(JavaMethodImpl method, MessageContextFactory mcf) {
        //keep all the CheckedException model for the detail qname
        this.checkedExceptions = new HashMap<QName, CheckedExceptionImpl>();
        for(CheckedExceptionImpl ce : method.getCheckedExceptions()){
            checkedExceptions.put(ce.getBond().getTypeInfo().tagName, ce);
        }
        //If a non-"" soapAction is specified, wsa:action the SOAPAction
        String soapActionFromBinding = method.getBinding().getSOAPAction();
        if(method.getInputAction() != null && soapActionFromBinding != null && !soapActionFromBinding.equals("") ) {
            this.soapAction = method.getInputAction();
        } else {
            this.soapAction = soapActionFromBinding;
        }
        this.javaMethod = method;
        packetFactory = mcf;
        
        soapVersion = javaMethod.getBinding().getSOAPVersion();

        {// prepare objects for creating messages
            List<ParameterImpl> rp = method.getRequestParameters();

            BodyBuilder bodyBuilder = null;
            List<MessageFiller> fillers = new ArrayList<MessageFiller>();

            for (ParameterImpl param : rp) {
                ValueGetter getter = getValueGetterFactory().get(param);

                switch(param.getInBinding().kind) {
                case BODY:
                    if(param.isWrapperStyle()) {
                        if(param.getParent().getBinding().isRpcLit())
                            bodyBuilder = new BodyBuilder.RpcLit((WrapperParameter)param, soapVersion, getValueGetterFactory());
                        else
                            bodyBuilder = new BodyBuilder.DocLit((WrapperParameter)param, soapVersion, getValueGetterFactory());
                    } else {
                        bodyBuilder = new BodyBuilder.Bare(param, soapVersion, getter);
                    }
                    break;
                case HEADER:
                    fillers.add(new MessageFiller.Header(
                        param.getIndex(),
                        param.getXMLBridge(),
                        getter ));
                    break;
                case ATTACHMENT:
                    fillers.add(MessageFiller.AttachmentFiller.createAttachmentFiller(param, getter));
                    break;
                case UNBOUND:
                    break;
                default:
                    throw new AssertionError(); // impossible
                }
            }

            if(bodyBuilder==null) {
                // no parameter binds to body. we create an empty message
                switch(soapVersion) {
                case SOAP_11:
                    bodyBuilder = BodyBuilder.EMPTY_SOAP11;
                    break;
                case SOAP_12:
                    bodyBuilder = BodyBuilder.EMPTY_SOAP12;
                    break;
                default:
                    throw new AssertionError();
                }
            }

            this.bodyBuilder = bodyBuilder;
            this.inFillers = fillers.toArray(new MessageFiller[fillers.size()]);
        }

        this.isOneWay = method.getMEP().isOneWay();
        responseBuilder = buildResponseBuilder(method, ValueSetterFactory.SYNC);
    }

    ResponseBuilder buildResponseBuilder(JavaMethodImpl method, ValueSetterFactory setterFactory) {
        // prepare objects for processing response
        List<ParameterImpl> rp = method.getResponseParameters();
        List<ResponseBuilder> builders = new ArrayList<ResponseBuilder>();

        for( ParameterImpl param : rp ) {
            ValueSetter setter;
            switch(param.getOutBinding().kind) {
            case BODY:
                if(param.isWrapperStyle()) {
                    if(param.getParent().getBinding().isRpcLit())
                        builders.add(new ResponseBuilder.RpcLit((WrapperParameter)param, setterFactory));
                    else
                        builders.add(new ResponseBuilder.DocLit((WrapperParameter)param, setterFactory));
                } else {
                    setter = setterFactory.get(param);
                    builders.add(new ResponseBuilder.Body(param.getXMLBridge(),setter));
                }
                break;
            case HEADER:
                setter = setterFactory.get(param);
                builders.add(new ResponseBuilder.Header(soapVersion, param, setter));
                break;
            case ATTACHMENT:
                setter = setterFactory.get(param);
                builders.add(ResponseBuilder.AttachmentBuilder.createAttachmentBuilder(param, setter));
                break;
            case UNBOUND:
                setter = setterFactory.get(param);
                builders.add(new ResponseBuilder.NullSetter(setter,
                    ResponseBuilder.getVMUninitializedValue(param.getTypeInfo().type)));
                break;
            default:
                throw new AssertionError();
            }
        }
        ResponseBuilder rb;
        switch(builders.size()) {
        case 0:
            rb = ResponseBuilder.NONE;
            break;
        case 1:
            rb = builders.get(0);
            break;
        default:
            rb = new ResponseBuilder.Composite(builders);
        }
        return rb;
    }


    /**
     * Creates a request {@link com.sun.xml.ws.message.jaxb.JAXBMessage} from method arguments.
     * @param args proxy invocation arguments
     * @return Message for the arguments
     */
    public Packet createRequestPacket(JavaCallInfo args) {
        Message msg = bodyBuilder.createMessage(args.getParameters());

        for (MessageFiller filler : inFillers) filler.fillIn(args.getParameters(),msg);

        Packet req = (Packet)packetFactory.createContext(msg);
        req.setState(Packet.State.ClientRequest);
        req.soapAction = soapAction;
        req.expectReply = !isOneWay;
        req.getMessage().assertOneWay(isOneWay);
        req.setWSDLOperation(getOperationName());
        return req;
    }

    ValueGetterFactory getValueGetterFactory() {
        return ValueGetterFactory.SYNC;
    }

	public JavaCallInfo readResponse(Packet p, JavaCallInfo call) throws Throwable {
		Message msg = p.getMessage();
        if(msg.isFault()) {
            SOAPFaultBuilder faultBuilder = SOAPFaultBuilder.create(msg);
            Throwable t = faultBuilder.createException(checkedExceptions);
            call.setException(t);
            throw t;
        } else {
        	initArgs(call.getParameters());
            Object ret = responseBuilder.readResponse(msg, call.getParameters());
            call.setReturnValue(ret);
            return call;
        }
	}

	public QName getOperationName() {
		//TODO javaMethod.getOperation()
		return javaMethod.getOperationQName();
	}

	public String getSoapAction() {
		return soapAction;
	}

	public boolean isOneWay() {
		return isOneWay;
	}

	protected void initArgs(Object[] args) throws Exception {
	}

	public Method getMethod() {
		return javaMethod.getMethod();
	}
    
    public JavaMethod getOperationModel() {
        return javaMethod;
    }
}
