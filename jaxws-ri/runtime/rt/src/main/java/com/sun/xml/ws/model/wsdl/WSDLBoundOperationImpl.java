/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model.wsdl;

import com.sun.istack.Nullable;
import com.sun.istack.NotNull;
import com.sun.xml.ws.api.model.ParameterBinding;
import com.sun.xml.ws.api.model.wsdl.*;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLBoundFault;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLBoundOperation;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLBoundPortType;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLMessage;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLModel;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLOperation;
import com.sun.xml.ws.api.model.wsdl.editable.EditableWSDLPart;
import com.sun.xml.ws.model.RuntimeModeler;

import jakarta.jws.WebParam.Mode;
import jakarta.jws.soap.SOAPBinding.Style;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

import java.util.*;

/**
 * Implementation of {@link WSDLBoundOperation}
 *
 * @author Vivek Pandey
 */
public final class WSDLBoundOperationImpl extends AbstractExtensibleImpl implements EditableWSDLBoundOperation {
    private final QName name;

    // map of wsdl:part to the binding
    private final Map<String, ParameterBinding> inputParts;
    private final Map<String, ParameterBinding> outputParts;
    private final Map<String, ParameterBinding> faultParts;
    private final Map<String, String> inputMimeTypes;
    private final Map<String, String> outputMimeTypes;
    private final Map<String, String> faultMimeTypes;

    private boolean explicitInputSOAPBodyParts = false;
    private boolean explicitOutputSOAPBodyParts = false;
    private boolean explicitFaultSOAPBodyParts = false;

    private Boolean emptyInputBody;
    private Boolean emptyOutputBody;
    private Boolean emptyFaultBody;

    private final Map<String, EditableWSDLPart> inParts;
    private final Map<String, EditableWSDLPart> outParts;
    private final List<EditableWSDLBoundFault> wsdlBoundFaults;
    private EditableWSDLOperation operation;
    private String soapAction;
    private ANONYMOUS anonymous;

    private final EditableWSDLBoundPortType owner;

    /**
     *
     * @param name wsdl:operation name qualified value
     */
    public WSDLBoundOperationImpl(XMLStreamReader xsr, EditableWSDLBoundPortType owner, QName name) {
        super(xsr);
        this.name = name;
        inputParts = new HashMap<>();
        outputParts = new HashMap<>();
        faultParts = new HashMap<>();
        inputMimeTypes = new HashMap<>();
        outputMimeTypes = new HashMap<>();
        faultMimeTypes = new HashMap<>();
        inParts = new HashMap<>();
        outParts = new HashMap<>();
        wsdlBoundFaults = new ArrayList<>();
        this.owner = owner;
    }

    @Override
    public QName getName(){
        return name;
    }

    @Override
    public String getSOAPAction() {
        return soapAction;
    }

    @Override
    public void setSoapAction(String soapAction) {
        this.soapAction = soapAction!=null?soapAction:"";
    }

    @Override
    public EditableWSDLPart getPart(String partName, Mode mode) {
        if(mode==Mode.IN){
            return inParts.get(partName);
        }else if(mode==Mode.OUT){
            return outParts.get(partName);
        }
        return null;
    }

    @Override
    public void addPart(EditableWSDLPart part, Mode mode){
        if(mode==Mode.IN)
            inParts.put(part.getName(), part);
        else if(mode==Mode.OUT)
            outParts.put(part.getName(), part);
    }

    /**
     * Map of wsdl:input part name and the binding as {@link ParameterBinding}
     *
     * @return empty Map if there is no parts
     */
    @Override
    public Map<String, ParameterBinding> getInputParts() {
        return inputParts;
    }

    /**
     * Map of wsdl:output part name and the binding as {@link ParameterBinding}
     *
     * @return empty Map if there is no parts
     */
    @Override
    public Map<String, ParameterBinding> getOutputParts() {
        return outputParts;
    }

    /**
     * Map of wsdl:fault part name and the binding as {@link ParameterBinding}
     *
     * @return empty Map if there is no parts
     */
    @Override
    public Map<String, ParameterBinding> getFaultParts() {
        return faultParts;
    }

    // TODO: what's the difference between this and inputParts/outputParts?
    @Override
    public Map<String, ? extends EditableWSDLPart> getInParts() {
        return Collections.<String, EditableWSDLPart>unmodifiableMap(inParts);
    }

    @Override
    public Map<String, ? extends EditableWSDLPart> getOutParts() {
        return Collections.<String, EditableWSDLPart>unmodifiableMap(outParts);
    }

    @NotNull
    @Override
    public List<? extends EditableWSDLBoundFault> getFaults() {
        return wsdlBoundFaults;
    }

    @Override
    public void addFault(@NotNull EditableWSDLBoundFault fault){
        wsdlBoundFaults.add(fault);
    }


    /**
     * Gets {@link ParameterBinding} for a given wsdl part in wsdl:input
     *
     * @param part Name of wsdl:part, must be non-null
     * @return null if the part is not found.
     */
    @Override
    public ParameterBinding getInputBinding(String part){
        if(emptyInputBody == null){
            emptyInputBody = inputParts.get(" ") != null;
        }
        ParameterBinding block = inputParts.get(part);
        if(block == null){
            if(explicitInputSOAPBodyParts || emptyInputBody)
                return ParameterBinding.UNBOUND;
            return ParameterBinding.BODY;
        }

        return block;
    }

    /**
     * Gets {@link ParameterBinding} for a given wsdl part in wsdl:output
     *
     * @param part Name of wsdl:part, must be non-null
     * @return null if the part is not found.
     */
    @Override
    public ParameterBinding getOutputBinding(String part){
        if(emptyOutputBody == null){
            emptyOutputBody = outputParts.get(" ") != null;
        }
        ParameterBinding block = outputParts.get(part);
        if(block == null){
            if(explicitOutputSOAPBodyParts || emptyOutputBody)
                return ParameterBinding.UNBOUND;
            return ParameterBinding.BODY;
        }

        return block;
    }

    /**
     * Gets {@link ParameterBinding} for a given wsdl part in wsdl:fault
     *
     * @param part Name of wsdl:part, must be non-null
     * @return null if the part is not found.
     */
    @Override
    public ParameterBinding getFaultBinding(String part){
        if(emptyFaultBody == null){
            emptyFaultBody = faultParts.get(" ") != null;
        }
        ParameterBinding block = faultParts.get(part);
        if(block == null){
            if(explicitFaultSOAPBodyParts || emptyFaultBody)
                return ParameterBinding.UNBOUND;
            return ParameterBinding.BODY;
        }

        return block;
    }

    /**
     * Gets the MIME type for a given wsdl part in wsdl:input
     *
     * @param part Name of wsdl:part, must be non-null
     * @return null if the part is not found.
     */
    @Override
    public String getMimeTypeForInputPart(String part){
        return inputMimeTypes.get(part);
    }

    /**
     * Gets the MIME type for a given wsdl part in wsdl:output
     *
     * @param part Name of wsdl:part, must be non-null
     * @return null if the part is not found.
     */
    @Override
    public String getMimeTypeForOutputPart(String part){
        return outputMimeTypes.get(part);
    }

    /**
     * Gets the MIME type for a given wsdl part in wsdl:fault
     *
     * @param part Name of wsdl:part, must be non-null
     * @return null if the part is not found.
     */
    @Override
    public String getMimeTypeForFaultPart(String part){
        return faultMimeTypes.get(part);
    }

    @Override
    public EditableWSDLOperation getOperation() {
        return operation;
    }


    @Override
    public EditableWSDLBoundPortType getBoundPortType() {
        return owner;
    }

    @Override
    public void setInputExplicitBodyParts(boolean b) {
        explicitInputSOAPBodyParts = b;
    }

    @Override
    public void setOutputExplicitBodyParts(boolean b) {
        explicitOutputSOAPBodyParts = b;
    }

    @Override
    public void setFaultExplicitBodyParts(boolean b) {
        explicitFaultSOAPBodyParts = b;
    }

    private Style style = Style.DOCUMENT;
    @Override
    public void setStyle(Style style){
        this.style = style;
    }

    @Override
    public @Nullable QName getRequestPayloadName() {
        if (emptyRequestPayload)
            return null;

        if (requestPayloadName != null)
            return requestPayloadName;

        if(style.equals(Style.RPC)){
            String ns = getRequestNamespace() != null ? getRequestNamespace() : name.getNamespaceURI();
            requestPayloadName = new QName(ns, name.getLocalPart());
            return requestPayloadName;
        }else{
            QName inMsgName = operation.getInput().getMessage().getName();
            EditableWSDLMessage message = messages.get(inMsgName);
            for(EditableWSDLPart part:message.parts()){
                ParameterBinding binding = getInputBinding(part.getName());
                if(binding.isBody()){
                    requestPayloadName = part.getDescriptor().name();
                    return requestPayloadName;
                }
            }

            //Its empty payload
            emptyRequestPayload = true;
        }
        //empty body
        return null;
    }

    @Override
    public @Nullable QName getResponsePayloadName() {
        if (emptyResponsePayload)
            return null;

        if (responsePayloadName != null)
            return responsePayloadName;

        if(style.equals(Style.RPC)){
            String ns = getResponseNamespace() != null ? getResponseNamespace() : name.getNamespaceURI();
            responsePayloadName = new QName(ns, name.getLocalPart()+"Response");
            return responsePayloadName;
        }else{
            QName outMsgName = operation.getOutput().getMessage().getName();
            EditableWSDLMessage message = messages.get(outMsgName);
            for(EditableWSDLPart part:message.parts()){
                ParameterBinding binding = getOutputBinding(part.getName());
                if(binding.isBody()){
                    responsePayloadName = part.getDescriptor().name();
                    return responsePayloadName;
                }
            }

            //Its empty payload
            emptyResponsePayload = true;
        }
        //empty body
        return null;
    }


    private String reqNamespace;
    private String respNamespace;

    /**
     * For rpclit gives namespace value on soapbinding:body@namespace
     *
     * @return   non-null for rpclit and null for doclit
     * @see RuntimeModeler#processRpcMethod(JavaMethodImpl, String, String, Method)
     */
    @Override
    public String getRequestNamespace(){
        return (reqNamespace != null)?reqNamespace:name.getNamespaceURI();
    }

    @Override
    public void setRequestNamespace(String ns){
        reqNamespace = ns;
    }

    /**
     * For rpclit gives namespace value on soapbinding:body@namespace
     *
     * @return   non-null for rpclit and null for doclit
     * @see RuntimeModeler#processRpcMethod(JavaMethodImpl, String, String, Method)
     */
    @Override
    public String getResponseNamespace(){
        return (respNamespace!=null)?respNamespace:name.getNamespaceURI();
    }

    @Override
    public void setResponseNamespace(String ns){
        respNamespace = ns;
    }

    EditableWSDLBoundPortType getOwner(){
        return owner;
    }

    private QName requestPayloadName;
    private QName responsePayloadName;
    private boolean emptyRequestPayload;
    private boolean emptyResponsePayload;
    private Map<QName, ? extends EditableWSDLMessage> messages;

    @Override
    public void freeze(EditableWSDLModel parent) {
        messages = parent.getMessages();
        operation = owner.getPortType().get(name.getLocalPart());
        for(EditableWSDLBoundFault bf : wsdlBoundFaults){
            bf.freeze(this);
        }
    }

    @Override
    public void setAnonymous(ANONYMOUS anonymous) {
        this.anonymous = anonymous;
    }

    @Override
    public ANONYMOUS getAnonymous() {
        return anonymous;
    }
}
