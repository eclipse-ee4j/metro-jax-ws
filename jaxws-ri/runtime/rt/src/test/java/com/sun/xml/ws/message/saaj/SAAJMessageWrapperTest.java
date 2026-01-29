/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message.saaj;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.soap.MTOMFeature;

import com.oracle.webservices.api.databinding.Databinding;
import com.oracle.webservices.api.databinding.DatabindingFactory;
import com.oracle.webservices.api.databinding.JavaCallInfo;
import com.oracle.webservices.api.message.ContentType;
import com.oracle.webservices.api.message.MessageContext;
import com.oracle.webservices.api.message.MessageContextFactory;

import com.sun.xml.ws.api.message.Packet;

import junit.framework.TestCase;

public class SAAJMessageWrapperTest extends TestCase {
    
    private Databinding databinding;
    private MTOMFeature mtomf = new MTOMFeature(true);
    private MessageContextFactory mcf;
    private String reqStr = "jaxws-ri MTOM with orasaaj test";
    private JavaCallInfo clientCall;
    
    public void setUp() {
        Class<?> proxySEI = MtomSEI.class;
        DatabindingFactory dbfac = DatabindingFactory.newInstance();
        Databinding.Builder builder = dbfac.createBuilder(proxySEI, null);
        builder.serviceName(new QName("http://example.org", "MtomTestService"));
        builder.portName(new QName("http://example.org", "MtomTestPort"));
        builder.feature( mtomf
//                , new DatabindingModeFeature(DatabindingModeFeature.ECLIPSELINK_JAXB)
        );
        databinding = builder.build();
        mcf = MessageContextFactory.createFactory(mtomf);
        
        
        Method method = findMethod(proxySEI, "echoByteArray");
        byte[] req = reqStr.getBytes();
        Object[] args = { req };
        clientCall = databinding.createJavaCallInfo(method, args);
    }

    public void testMTOM_riSaaj() throws Exception {
//client creates request
        MessageContext clientRequest = databinding.serializeRequest(clientCall);
        
//client writes request to OutputStream    
        ByteArrayOutputStream clientOut = new ByteArrayOutputStream();
        ContentType cliCt = clientRequest.writeTo(clientOut);
//        com.sun.xml.ws.api.pipe.ContentType cliCt = ((com.sun.xml.ws.api.databinding.Databinding)databinding).
//            encode((Packet)clientRequest, clientOut);
        
//        System.out.println("==== Client Request " + cliCt.getContentType() + " " + cliCt.getSOAPActionHeader() );
//        System.out.println(new String(clientOut.toByteArray()) + "\r\n\r\n");
        
//client sends request to server
        ByteArrayInputStream serverIn = new ByteArrayInputStream(clientOut.toByteArray());
//server decodes request to Packet
        MessageContext serverRequest = mcf.createContext(serverIn, cliCt.getContentType());
//deprecated way to create/decode MC/Packet from inputStream        
//        com.sun.xml.ws.api.message.Packet serverRequest = new com.sun.xml.ws.api.message.Packet();
//        ((com.sun.xml.ws.api.databinding.Databinding)databinding).decode(serverIn, cliCt.getContentType(), serverRequest);
        
//setup JRF env ...
//        ContainerResolver.setInstance(new JRFContainerResolver());
        
//server coverts request to saaj SOAPMessage
        SOAPMessage saajReq1 = serverRequest.getAsSOAPMessage();
        assertEquals("wrong attachment count", 1, saajReq1.countAttachments());
//        System.out.println("==== serverRequest.getAsSOAPMessage() 1 " + saajReq1);
//Write the SOAPMessage multiple times result in different message boundary -        
//        {
//            saajReq1 = serverRequest.getAsSOAPMessage();
//            System.out.println("\r\n==== serverRequest.getAsSOAPMessage() 1 " + saajReq1);
//            ByteArrayOutputStream saajReq1Out = new ByteArrayOutputStream();
//            saajReq1.writeTo(saajReq1Out);
//            System.out.println("\r\n==== serverRequest.getAsSOAPMessage() 1 " + new String(saajReq1Out.toByteArray()));
//        }
//        {
//            saajReq1 = serverRequest.getAsSOAPMessage();
//            System.out.println("\r\n==== serverRequest.getAsSOAPMessage() 1 " + saajReq1);
//            ByteArrayOutputStream saajReq1Out = new ByteArrayOutputStream();
//            saajReq1.writeTo(saajReq1Out);
//            System.out.println("\r\n==== serverRequest.getAsSOAPMessage() 1 " + new String(saajReq1Out.toByteArray()));
//        }
//        {
//            saajReq1 = serverRequest.getAsSOAPMessage();
//            System.out.println("\r\n==== serverRequest.getAsSOAPMessage() 1 " + saajReq1);
//            ByteArrayOutputStream saajReq1Out = new ByteArrayOutputStream();
//            saajReq1.writeTo(saajReq1Out);
//            System.out.println("\r\n==== serverRequest.getAsSOAPMessage() 1 " + new String(saajReq1Out.toByteArray()));
//        }
        
   
//!! NPE if the following line of serverRequest.setMessage(...) is removed
//        ((Packet)serverRequest).setMessage(new com.sun.xml.ws.message.saaj.SAAJMessage(saajReq1));

        SOAPMessage saajReq2 = ((Packet)serverRequest).getMessage().readAsSOAPMessage();
        SOAPMessage saajReq3 = serverRequest.getAsSOAPMessage();
        SOAPMessage saajReq4 = ((Packet)serverRequest).getMessage().readAsSOAPMessage();
//      System.out.println("==== serverRequest.getAsSOAPMessage() 2 " + saajReq2);
        assertSame(saajReq1, saajReq2);
        assertSame(saajReq3, saajReq2);
        assertSame(saajReq4, saajReq3);

//!! Do we need orassaj-XMLStreamReaderEx if the databinding can descerialize orasaaj SOAPMessagae as inline?
        JavaCallInfo serverCall = databinding.deserializeRequest(serverRequest);
        serverCall.setReturnValue(serverCall.getParameters()[0]);
        
//server creates response
        MessageContext serverResponse = databinding.serializeResponse(serverCall);
//server coverts response to saaj SOAPMessage
        SOAPMessage saajRes1 = serverResponse.getAsSOAPMessage();
//        System.out.println("==== serverResponse.getAsSOAPMessage() 1 " + saajRes1);
//!! server coverts response to saaj SOAPMessage again but get another new saaj SOAPMessage 

        SOAPMessage saajRes2 = ((Packet)serverResponse).getMessage().readAsSOAPMessage();
        SOAPMessage saajRes3 = serverResponse.getAsSOAPMessage();
        SOAPMessage saajRes4 = ((Packet)serverResponse).getMessage().readAsSOAPMessage();
//        System.out.println("==== serverResponse.getAsSOAPMessage() 2 " + saajRes2);

        assertSame(saajRes1, saajRes2);
      //!! Following line keeps the same saaj message but removes mtom effect on response
//        ((Packet)serverResponse).setMessage(new com.sun.xml.ws.message.saaj.SAAJMessage(saajRes1));

        SOAPMessage saajRes5 = serverResponse.getAsSOAPMessage();
        assertSame(saajRes3, saajRes2);
        assertSame(saajRes3, saajRes4);
        assertSame(saajRes4, saajRes5);
//        System.out.println("==== serverResponse.getAsSOAPMessage() 3 " + saajRes3);
        
//        {
//            ByteArrayOutputStream serverOut = new ByteArrayOutputStream();
//            //TODO serverResponse.writeTo(serverOut);
//            com.sun.xml.ws.api.pipe.ContentType resCt = ((com.sun.xml.ws.api.databinding.Databinding)databinding).
//                encode((com.sun.xml.ws.api.message.Packet)serverResponse, serverOut);
//            System.out.println("==== Server Response 1 " + resCt.getContentType() + " " + resCt.getSOAPActionHeader() );
//            System.out.println(new String(serverOut.toByteArray()) + "\r\n\r\n");
//        }
//        {
//            ByteArrayOutputStream serverOut = new ByteArrayOutputStream();
//            //TODO serverResponse.writeTo(serverOut);
//            com.sun.xml.ws.api.pipe.ContentType resCt = ((com.sun.xml.ws.api.databinding.Databinding)databinding).
//                encode((com.sun.xml.ws.api.message.Packet)serverResponse, serverOut);
//            System.out.println("==== Server Response 2 " + resCt.getContentType() + " " + resCt.getSOAPActionHeader() );
//            System.out.println(new String(serverOut.toByteArray()) + "\r\n\r\n");
//        }        

            ByteArrayOutputStream serverOut = new ByteArrayOutputStream();
            ContentType resCt = serverResponse.writeTo(serverOut);
//            com.sun.xml.ws.api.pipe.ContentType resCt = ((com.sun.xml.ws.api.databinding.Databinding)databinding).
//                encode((com.sun.xml.ws.api.message.Packet)serverResponse, serverOut);
//            System.out.println("==== Server Response x " + resCt.getContentType() + " " + resCt.getSOAPActionHeader() );
//            System.out.println(new String(serverOut.toByteArray()) + "\r\n\r\n");
        
        
//server sends response to client
        ByteArrayInputStream clientIn = new ByteArrayInputStream(serverOut.toByteArray());
        MessageContext clientResponse = mcf.createContext(clientIn, resCt.getContentType());
//        com.sun.xml.ws.api.message.Packet clientResponse = new com.sun.xml.ws.api.message.Packet();
//        ((com.sun.xml.ws.api.databinding.Databinding)databinding).decode(clientIn, resCt.getContentType(), clientResponse);
        clientCall = databinding.deserializeResponse(clientResponse, clientCall);
        String res = new String((byte[])clientCall.getReturnValue());
//        System.out.println("response " + res);
        assertEquals(res, reqStr);
    }

    @SuppressWarnings({"deprecation"})
    public void XtestMTOM_oraSaaj() throws Exception {
//client creates request
        MessageContext clientRequest = databinding.serializeRequest(clientCall);
        
//client writes request to OutputStream       
//TODO databinding bug - fixed
//clientRequest.writeTo(clientOut);
        ByteArrayOutputStream clientOut = new ByteArrayOutputStream();
        com.sun.xml.ws.api.pipe.ContentType cliCt = ((com.sun.xml.ws.api.databinding.Databinding)databinding).
            encode((Packet)clientRequest, clientOut);
        
        System.out.println("==== Client Request " + cliCt.getContentType() + " " + cliCt.getSOAPActionHeader() );
        System.out.println(new String(clientOut.toByteArray()) + "\r\n\r\n");
        
//client sends request to server
        ByteArrayInputStream serverIn = new ByteArrayInputStream(clientOut.toByteArray());
//server decodes request to Packet
        MessageContext serverRequest = mcf.createContext(serverIn, cliCt.getContentType());
        
//setup JRF env ...
//        ContainerResolver.setInstance(new JRFContainerResolver());
        
//server coverts request to saaj SOAPMessage
        SOAPMessage saajReq1 = serverRequest.getAsSOAPMessage();
        System.out.println("==== serverRequest.getAsSOAPMessage() 1 " + saajReq1);
        
//!! NPE if getAsSOAPMessage() more than one time
//      SOAPMessage saajReq2 = serverRequest.getAsSOAPMessage();
//      System.out.println("==== serverRequest.getAsSOAPMessage() 2 " + saajReq2);
   
//!! NPE if the following line of serverRequest.setMessage(...) is removed
        ((Packet)serverRequest).setMessage(new com.sun.xml.ws.message.saaj.SAAJMessage(saajReq1));
      SOAPMessage saajReq2 = serverRequest.getAsSOAPMessage();
      System.out.println("==== serverRequest.getAsSOAPMessage() 2 " + saajReq2);

//!! Do we need orassaj-XMLStreamReaderEx if the databinding can descerialize orasaaj SOAPMessagae as inline?
        JavaCallInfo serverCall = databinding.deserializeRequest(serverRequest);
        serverCall.setReturnValue(serverCall.getParameters()[0]);
        
//server creates response
        MessageContext serverResponse = databinding.serializeResponse(serverCall);
//server coverts response to saaj SOAPMessage
        SOAPMessage saajRes1 = serverResponse.getAsSOAPMessage();
        System.out.println("==== serverResponse.getAsSOAPMessage() 1 " + saajRes1);
//!! server coverts response to saaj SOAPMessage again but get another new saaj SOAPMessage 
        SOAPMessage saajRes2 = serverResponse.getAsSOAPMessage();
        System.out.println("==== serverResponse.getAsSOAPMessage() 2 " + saajRes2);

//!! Following line keeps the same saaj message but removes mtom effect on response
        ((Packet)serverResponse).setMessage(new com.sun.xml.ws.message.saaj.SAAJMessage(saajRes1));

        SOAPMessage saajRes3 = serverResponse.getAsSOAPMessage();
        System.out.println("==== serverResponse.getAsSOAPMessage() 3 " + saajRes3);
        
//        {
//            ByteArrayOutputStream serverOut = new ByteArrayOutputStream();
//            //TODO serverResponse.writeTo(serverOut);
//            com.sun.xml.ws.api.pipe.ContentType resCt = ((com.sun.xml.ws.api.databinding.Databinding)databinding).
//                encode((com.sun.xml.ws.api.message.Packet)serverResponse, serverOut);
//            System.out.println("==== Server Response 1 " + resCt.getContentType() + " " + resCt.getSOAPActionHeader() );
//            System.out.println(new String(serverOut.toByteArray()) + "\r\n\r\n");
//        }
//        {
//            ByteArrayOutputStream serverOut = new ByteArrayOutputStream();
//            //TODO serverResponse.writeTo(serverOut);
//            com.sun.xml.ws.api.pipe.ContentType resCt = ((com.sun.xml.ws.api.databinding.Databinding)databinding).
//                encode((com.sun.xml.ws.api.message.Packet)serverResponse, serverOut);
//            System.out.println("==== Server Response 2 " + resCt.getContentType() + " " + resCt.getSOAPActionHeader() );
//            System.out.println(new String(serverOut.toByteArray()) + "\r\n\r\n");
//        }        

            ByteArrayOutputStream serverOut = new ByteArrayOutputStream();
            //TODO serverResponse.writeTo(serverOut);
            com.sun.xml.ws.api.pipe.ContentType resCt = ((com.sun.xml.ws.api.databinding.Databinding)databinding).
                encode((com.sun.xml.ws.api.message.Packet)serverResponse, serverOut);
            System.out.println("==== Server Response x " + resCt.getContentType() + " " + resCt.getSOAPActionHeader() );
            System.out.println(new String(serverOut.toByteArray()) + "\r\n\r\n");
        
        
//server sends response to client
        ByteArrayInputStream clientIn = new ByteArrayInputStream(serverOut.toByteArray());
        MessageContext clientResponse = mcf.createContext(clientIn, resCt.getContentType());
//        com.sun.xml.ws.api.message.Packet clientResponse = new com.sun.xml.ws.api.message.Packet();
//        ((com.sun.xml.ws.api.databinding.Databinding)databinding).decode(clientIn, resCt.getContentType(), clientResponse);
        clientCall = databinding.deserializeResponse(clientResponse, clientCall);
        String res = new String((byte[])clientCall.getReturnValue());
        System.out.println("response " + res);
        assertEquals(res, reqStr);
    }

    static public Method findMethod(Class<?> cls, String n) {
        for (Method m : cls.getMethods()) if (m.getName().equals(n)) return m;
        return null;
    }
}
