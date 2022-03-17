/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.base;

import com.oracle.webservices.api.databinding.DatabindingModeFeature;
import com.oracle.webservices.api.databinding.JavaCallInfo;
import com.oracle.webservices.api.message.ContentType;
import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.databinding.Databinding;
import com.sun.xml.ws.api.databinding.DatabindingConfig;
import com.sun.xml.ws.api.databinding.DatabindingFactory;
import com.sun.xml.ws.api.databinding.WSDLGenInfo;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.wsdl.WSDLModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension;
import com.sun.xml.ws.api.wsdl.writer.WSDLGeneratorExtension;
import com.sun.xml.ws.binding.WebServiceFeatureList;
import com.sun.xml.ws.util.ServiceFinder;
import com.sun.xml.ws.wsdl.parser.RuntimeWSDLParser;
import jakarta.xml.ws.WebServiceFeature;
import junit.framework.TestCase;
import org.junit.Assert;

import javax.xml.namespace.QName;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * WsDatabindingTestBase
 *
 * @author shih-chang.chen@oracle.com
 */
abstract public class WsDatabindingTestBase extends TestCase {
    static public DatabindingFactory factory = DatabindingFactory.newInstance();

    static public <T> T createProxy(Class<T> proxySEI, Class<?> endpointClass, String db, boolean debug) throws Exception {
        DatabindingConfig srvConfig = new DatabindingConfig();
        srvConfig.setEndpointClass(endpointClass);
        DatabindingModeFeature dbf = new DatabindingModeFeature(db);
        WebServiceFeatureList wsfeatures = new WebServiceFeatureList(endpointClass);
        WebServiceFeature[] f = {dbf};
//      config.setFeatures(wsfeatures.toArray());
        srvConfig.setFeatures(f);

        DatabindingConfig cliConfig = new DatabindingConfig();
        cliConfig.setContractClass(proxySEI);
        cliConfig.setFeatures(f);
        return createProxy(proxySEI, srvConfig, cliConfig, debug);
    }

    static public <T> T createProxy(Class<T> proxySEI, DatabindingConfig srvConfig, DatabindingConfig cliConfig, boolean debug) throws Exception {
        Class<?> endpointClass = srvConfig.getEndpointClass();
        //TODO default BindingID
        if (srvConfig.getMappingInfo().getBindingID() == null)
            srvConfig.getMappingInfo().setBindingID(BindingID.parse(endpointClass));
        Databinding srvDb = (Databinding) factory.createRuntime(srvConfig);
        InVmWSDLResolver result = new InVmWSDLResolver();
        WSDLGenInfo wsdlGenInfo = new WSDLGenInfo();
        wsdlGenInfo.setWsdlResolver(result);
//        wsdlGenInfo.setContainer(container);
        wsdlGenInfo.setExtensions(ServiceFinder.find(WSDLGeneratorExtension.class).toArray());
        wsdlGenInfo.setInlineSchemas(true);
        srvDb.generateWSDL(wsdlGenInfo);
        if (debug) result.print();
        WSDLModel wsdl = RuntimeWSDLParser.parse(
                result.getWsdlSource(), result.getEntityResolver(), false, null, new WSDLParserExtension[0]);
        QName serviceName = wsdl.getFirstServiceName();
        WSDLPort wsdlPort = wsdl.getService(serviceName).getFirstPort();
//        ((AbstractSEIModelImpl)((DatabindingImpl)srvDb).getModel()).freeze((WSDLPortImpl)wsdlPort);

        if (cliConfig.getWsdlPort() == null) cliConfig.setWsdlPort(wsdlPort);
        cliConfig.getMappingInfo().setServiceName(serviceName);
        Databinding cliDb = (Databinding) factory.createRuntime(cliConfig);

        Class<?>[] intf = {proxySEI};
        WsDatabindingTestFacade h = new WsDatabindingTestFacade(cliDb, srvDb, endpointClass);
        h.wireLog = debug;
        Object proxy = Proxy.newProxyInstance(proxySEI.getClassLoader(), intf, h);
        return proxySEI.cast(proxy);
    }

    static public void assertEqualList(List<?> list1, List<?> list2) {
        Assert.assertEquals(list1.size(), list2.size());
        for (int i = 0; i < list1.size(); i++) {
            Assert.assertEquals(list1.get(i), list2.get(i));
        }
    }

    static public void assertEqualCollection(Collection<?> c1, Collection<?> c2) {
        Assert.assertEquals(c1.size(), c2.size());
        for (Iterator i = c1.iterator(); i.hasNext(); ) {
            Assert.assertTrue(c2.contains(i.next()));
        }
    }

    static public void assertEqualArray(Object a1, Object a2) {
        Assert.assertEquals(Array.getLength(a1), Array.getLength(a2));
        for (int i = 0; i < Array.getLength(a1); i++) {
            Assert.assertEquals(Array.get(a1, i), Array.get(a2, i));
        }
    }

    static public boolean equalsMap(Map<?, ?> req, Map<?, ?> res) {
        if (req.size() != res.size()) return false;
        for (Object k : req.keySet()) if (!req.get(k).equals(res.get(k))) return false;
        return true;
    }

    static public Method findMethod(Class<?> c, String n) {
        for (Method m : c.getMethods()) if (m.getName().equals(n)) return m;
        return null;
    }

    static public class WsDatabindingTestFacade implements InvocationHandler {
        public Object serviceBeanInstance;
        public boolean wireLog = false;
        public boolean keepWireMessage = false;
        public boolean keepMessageObject = false;
        public String request;
        public String response;
        public Packet requestMessage;
        public Packet responseMessage;
        Databinding cli;
        Databinding srv;
        Class<?> serviceBeanType;

        WsDatabindingTestFacade(Databinding client, Databinding server, Class endpoint) {
            cli = client;
            srv = server;
            serviceBeanType = endpoint;
            try {
                serviceBeanInstance = serviceBeanType.getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//			JavaCallInfo cliCall = new JavaCallInfo();
//			cliCall.setMethod(method);
//			cliCall.setParameters(args);
            JavaCallInfo cliCall = cli.createJavaCallInfo(method, args);
//			ClientCallBridge clientBridge = cli.getClientBridge(method);
            Packet cliSoapReq = (Packet) cli.serializeRequest(cliCall);
            //Transmit to Server
            ByteArrayOutputStream cliBo = new ByteArrayOutputStream();
//			ContentType cliCt = cli.encode(cliSoapReq, cliBo);
            ContentType cliCt = cliSoapReq.writeTo(cliBo);
            if (wireLog) {
                System.out.println("Request Message: " + cliCt.getContentType() + " " + cliCt.getSOAPActionHeader());
                System.out.println(new String(cliBo.toByteArray()));
            }
            if (keepMessageObject) {
                requestMessage = cliSoapReq;
            }
            if (keepWireMessage) {
                request = new String(cliBo.toByteArray());
            }

            ByteArrayInputStream srvBi = new ByteArrayInputStream(cliBo.toByteArray());
            Packet srvSoapReq = (Packet) srv.getMessageContextFactory().createContext(srvBi, cliCt.getContentType());
//			Packet srvSoapReq = new Packet();
//	        packet.soapAction = fixQuotesAroundSoapAction(con.getRequestHeader("SOAPAction"));
//			srv.decode(srvBi, cliCt.getContentType(), srvSoapReq);
//			Message srvSoapReq = tie.getMessageFactory().createMessage(srcReq, cliSoapReq.transportHeaders(), null);
//			EndpointCallBridge endpointBridge = srv.getEndpointBridge(srvSoapReq);
            JavaCallInfo srcCall = srv.deserializeRequest(srvSoapReq);
            Method intfMethod = srcCall.getMethod();
            Method implMethod = serviceBeanType.getMethod(intfMethod.getName(), intfMethod.getParameterTypes());
            try {
                Object ret = implMethod.invoke(serviceBeanInstance, srcCall.getParameters());
                srcCall.setReturnValue(ret);
            } catch (Exception e) {
                srcCall.setException(e);
            }
            Packet srvSoapRes = (Packet) srv.serializeResponse(srcCall);
//			Packet srvSoapRes = srvSoapReq.createResponse(srvSoapResMsg);
            //Transmit to Client
            ByteArrayOutputStream srvBo = new ByteArrayOutputStream();
            ContentType srvCt = srvSoapRes.writeTo(srvBo);
            if (wireLog) {
                System.out.println("Response Message: " + srvCt.getContentType());
                System.out.println(new String(srvBo.toByteArray()));
            }
            if (keepMessageObject) {
                responseMessage = srvSoapRes;
            }
            if (keepWireMessage) {
                response = new String(srvBo.toByteArray());
            }
            ByteArrayInputStream cliBi = new ByteArrayInputStream(srvBo.toByteArray());
//			Packet cliSoapRes = new Packet();
//			cli.decode(cliBi, srvCt.getContentType(), cliSoapRes);
            Packet cliSoapRes = (Packet) cli.getMessageContextFactory().createContext(cliBi, srvCt.getContentType());
            cliCall = cli.deserializeResponse(cliSoapRes, cliCall);
            if (cliCall.getException() != null) {
                throw cliCall.getException();
            }
            return cliCall.getReturnValue();
        }
    }
}
