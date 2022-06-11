/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.dl_swa;

import java.awt.Image;
import java.awt.Rectangle;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.sun.xml.ws.db.toplink.JAXBContextFactory;
import jakarta.activation.CommandMap;
import jakarta.activation.DataHandler;
import jakarta.activation.MailcapCommandMap;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.WebServiceFeature;
import jakarta.xml.ws.handler.MessageContext;

import com.oracle.webservices.api.databinding.DatabindingModeFeature;
import com.oracle.webservices.api.databinding.JavaCallInfo;

import org.junit.Assert;
import org.xml.sax.EntityResolver;

import com.sun.xml.ws.base.WsDatabindingTestBase;
import com.sun.xml.ws.api.databinding.Databinding;
import com.sun.xml.ws.api.databinding.DatabindingConfig;
import com.sun.xml.ws.api.message.Attachment;
import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.wsdl.WSDLModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension;
import com.sun.xml.ws.handler.SOAPMessageContextImpl;
import com.sun.xml.ws.message.DataHandlerAttachment;
import com.sun.xml.ws.util.ServiceFinder;
import com.sun.xml.ws.util.xml.XmlUtil;
import com.sun.xml.ws.wsdl.parser.RuntimeWSDLParser;

public class SwaMimeAttachmentTest extends WsDatabindingTestBase  {

    protected DatabindingModeFeature databindingMode() {
        return new DatabindingModeFeature(JAXBContextFactory.ECLIPSELINK_JAXB);
    }

    public void testAttachmentContentId() throws Exception {
        WSDLPort wsdlPort = getWSDLPort(getResource("WSW2JDLSwaTestService.wsdl"));
        Class<SwaTest1> proxySEIClass = SwaTest1.class;
        WebServiceFeature[] f = { databindingMode() };
        
        DatabindingConfig cliConfig = new DatabindingConfig();
        cliConfig.setContractClass(proxySEIClass);
        cliConfig.setFeatures(f);
        cliConfig.setWsdlPort(wsdlPort);

        cliConfig.setWsdlPort(wsdlPort);
        cliConfig.getMappingInfo().setServiceName(new QName("http://SwaTestService.org/wsdl", "WSIDLSwaTestService"));
        Databinding cli = (Databinding) factory.createRuntime(cliConfig); 

        URL url1 = getResource("attach.text");
        URL url2 = getResource("attach.html");
        URL url3 = getResource("attach.xml");
        URL url4 = getResource("attach.jpeg1");
        URL url5 = getResource("attach.jpeg2");
        DataHandler dh1 = new DataHandler(url1);
        DataHandler dh2 = new DataHandler(url2);
        DataHandler dh3 = new DataHandler(url3);
//        DataHandler dh4 = new DataHandler(url4);
//        DataHandler dh5 = new DataHandler(url5);
        jakarta.xml.ws.Holder<jakarta.activation.DataHandler> attach1 = new jakarta.xml.ws.Holder<jakarta.activation.DataHandler>();
        attach1.value = dh1;
        jakarta.xml.ws.Holder<jakarta.activation.DataHandler> attach2 = new jakarta.xml.ws.Holder<jakarta.activation.DataHandler>();
        attach2.value = dh2;
        jakarta.xml.ws.Holder<javax.xml.transform.Source> attach3 = new jakarta.xml.ws.Holder<javax.xml.transform.Source>();
        attach3.value = new StreamSource(dh3.getInputStream());
        jakarta.xml.ws.Holder<java.awt.Image> attach4 = new jakarta.xml.ws.Holder<java.awt.Image>();
        jakarta.xml.ws.Holder<java.awt.Image> attach5 = new jakarta.xml.ws.Holder<java.awt.Image>();
        attach4.value = javax.imageio.ImageIO.read(url4);
        attach5.value = javax.imageio.ImageIO.read(url5);
        VoidRequest request = new VoidRequest();
        Object[] args = { request, attach1, attach2, attach3, attach4, attach5 };
        Method method = findMethod(proxySEIClass, "echoAllAttachmentTypes");
        JavaCallInfo cliCall = cli.createJavaCallInfo(method, args);
        Packet cliSoapReq = (Packet)cli.serializeRequest(cliCall);

        SOAPMessageContextImpl smc = new SOAPMessageContextImpl(null, cliSoapReq, null);
        @SuppressWarnings({"unchecked"})
        Map<String, DataHandler> smcAtts1 = (Map<String, DataHandler>) smc.get(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS);
        smc.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, smcAtts1);
        Assert.assertEquals( 5, smcAtts1.size() );
        for (String cid : smcAtts1.keySet()) 
            Assert.assertTrue(cid.charAt(0)!='<');
        
        for (com.sun.xml.ws.api.message.Attachment a : cliSoapReq.getMessage().getAttachments()) 
            Assert.assertTrue(a.getContentId().charAt(0)!='<');
        
        Object s1 = cliSoapReq.getAsSOAPMessage();
        Object s2 = smc.getMessage();
        Assert.assertSame(s1, s2);
        
        for (com.sun.xml.ws.api.message.Attachment a : cliSoapReq.getMessage().getAttachments()) 
            Assert.assertTrue(a.getContentId().charAt(0)!='<');
//        {
//        Map<String, DataHandler> atts = (Map<String, DataHandler>) smc.get(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS);
//        AttachmentSet attSet = cliSoapReq.getMessage().getAttachments();
//        for(String cid : atts.keySet()){
//            if (attSet.get(cid) == null) {  // Otherwise we would be adding attachments twice
//                Attachment att = new DataHandlerAttachment(cid, atts.get(cid));
//                attSet.add(att);
//            }
//        }
//        }


        @SuppressWarnings({"unchecked"})
        Map<String, DataHandler> smcAtts2 = (Map<String, DataHandler>) smc.get(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS);
        Assert.assertEquals( 5, smcAtts1.size() );
        for (String cid : smcAtts2.keySet()) Assert.assertTrue(cid.charAt(0)!='<');
    }


    public void testCustomAttachmentContentId() throws Exception {
        WSDLPort wsdlPort = getWSDLPort(getResource("WSW2JDLSwaTestService.wsdl"));
        Class<SwaTest1> proxySEIClass = SwaTest1.class;
        WebServiceFeature[] f = { databindingMode() };
        
        DatabindingConfig cliConfig = new DatabindingConfig();
        cliConfig.setContractClass(proxySEIClass);
        cliConfig.setFeatures(f);
        cliConfig.setWsdlPort(wsdlPort);

        cliConfig.setWsdlPort(wsdlPort);
        cliConfig.getMappingInfo().setServiceName(new QName("http://SwaTestService.org/wsdl", "WSIDLSwaTestService"));
        Databinding cli = (Databinding) factory.createRuntime(cliConfig); 

        URL url1 = getResource("attach.text");
        URL url2 = getResource("attach.html");
        URL url3 = getResource("attach.xml");
        URL url4 = getResource("attach.jpeg1");
        URL url5 = getResource("attach.jpeg2");
        DataHandler dh1 = new DataHandler(url1);
        DataHandler dh2 = new DataHandler(url2);
        DataHandler dh3 = new DataHandler(url3);
        DataHandler dh4 = new DataHandler(url4);
//        DataHandler dh5 = new DataHandler(url5);
        jakarta.xml.ws.Holder<jakarta.activation.DataHandler> attach1 = new jakarta.xml.ws.Holder<jakarta.activation.DataHandler>();
        attach1.value = dh1;
        jakarta.xml.ws.Holder<jakarta.activation.DataHandler> attach2 = new jakarta.xml.ws.Holder<jakarta.activation.DataHandler>();
        attach2.value = dh2;
        jakarta.xml.ws.Holder<javax.xml.transform.Source> attach3 = new jakarta.xml.ws.Holder<javax.xml.transform.Source>();
        attach3.value = new StreamSource(dh3.getInputStream());
        jakarta.xml.ws.Holder<java.awt.Image> attach4 = new jakarta.xml.ws.Holder<java.awt.Image>();
        jakarta.xml.ws.Holder<java.awt.Image> attach5 = new jakarta.xml.ws.Holder<java.awt.Image>();
        attach4.value = javax.imageio.ImageIO.read(url4);
        attach5.value = javax.imageio.ImageIO.read(url5);
        VoidRequest request = new VoidRequest();
        Object[] args = { request, attach1, attach2, attach3, attach4, attach5 };
        Method method = findMethod(proxySEIClass, "echoAllAttachmentTypes");
        JavaCallInfo cliCall = cli.createJavaCallInfo(method, args);
        Packet cliSoapReq = (Packet)cli.serializeRequest(cliCall);

        String customContentId = "<abcd@example.org>";
        Map<String, DataHandler> attMap = new HashMap<String, DataHandler>();
        attMap.put(customContentId, dh4);
        cliSoapReq.invocationProperties.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, attMap);

        SOAPMessageContextImpl smc = new SOAPMessageContextImpl(null, cliSoapReq, null);
        @SuppressWarnings({"unchecked"})
        Map<String, DataHandler> smcAtts1 = (Map<String, DataHandler>) smc.get(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS);

        Assert.assertEquals( 6, smcAtts1.size() );
        Assert.assertNotNull(smcAtts1.get(customContentId));
        
        {//ClientSOAPHandlerTube.callHandlersOnRequest
            @SuppressWarnings({"unchecked"})
            Map<String, DataHandler> atts = (Map<String, DataHandler>) smc.get(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS);
            AttachmentSet attSet = cliSoapReq.getMessage().getAttachments();
            for(String cid : atts.keySet()){
                if (attSet.get(cid) == null) {  // Otherwise we would be adding attachments twice
                    Attachment att = new DataHandlerAttachment(cid, atts.get(cid));
                    attSet.add(att);
                }
            }
        }

        int attCount = 0;
        for (com.sun.xml.ws.api.message.Attachment a : cliSoapReq.getMessage().getAttachments()) {
//            Assert.assertTrue(a.getContentId().charAt(0)!='<'); 
            attCount++;
        }
        Assert.assertEquals( 6, attCount);
        Object s1 = cliSoapReq.getAsSOAPMessage();
        Object s2 = smc.getMessage();
        Assert.assertSame(s1, s2);

        int attCountSaaj = 0;
        for (com.sun.xml.ws.api.message.Attachment a : cliSoapReq.getMessage().getAttachments()) {
            Assert.assertTrue(a.getContentId().charAt(0)!='<');
            attCountSaaj++;
        }
        Assert.assertEquals( 6, attCountSaaj);
        @SuppressWarnings({"unchecked"})
        Map<String, DataHandler> smcAtts2 = (Map<String, DataHandler>) smc.get(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS);
        Assert.assertEquals( 6, smcAtts2.size() );
//        System.out.println(smcAtts2.size() + " " + smcAtts2);
        Assert.assertNotNull(smcAtts2.get(customContentId));
    }
    
    public void testCTS_WsiDocLitSwaTest() throws Exception {
        WSDLPort wsdlPort = getWSDLPort(getResource("WSW2JDLSwaTestService.wsdl"));


        Class<SwaTestImpl1> endpointClass = SwaTestImpl1.class;
        Class<SwaTest1> proxySEIClass = SwaTest1.class;
        DatabindingConfig srvConfig = new DatabindingConfig();
        srvConfig.setEndpointClass(endpointClass);
//        srvConfig.setMetadataReader(new DummyAnnotations());
        WebServiceFeature[] f = { databindingMode() };
        srvConfig.setFeatures(f);
        srvConfig.setWsdlPort(wsdlPort);
        
        DatabindingConfig cliConfig = new DatabindingConfig();
//        cliConfig.setMetadataReader(new DummyAnnotations());
        cliConfig.setContractClass(proxySEIClass);
        cliConfig.setFeatures(f);
        cliConfig.setWsdlPort(wsdlPort);

        CommandMap map = CommandMap.getDefaultCommandMap();
        ((MailcapCommandMap)map).addMailcap("image/*;;x-java-content-handler=com.sun.xml.messaging.saaj.soap.ImageDataContentHandler");

        SwaTest1 port = createProxy(SwaTest1.class, srvConfig, cliConfig, false);        
        {
            URL url1 = getResource("attach.text");
            URL url2 = getResource("attach.html");
            URL url3 = getResource("attach.xml");
            URL url4 = getResource("attach.jpeg1");
            URL url5 = getResource("attach.jpeg2");
            DataHandler dh1 = new DataHandler(url1);
            DataHandler dh2 = new DataHandler(url2);
            DataHandler dh3 = new DataHandler(url3);
            DataHandler dh4 = new DataHandler(url4);
            DataHandler dh5 = new DataHandler(url5);
            jakarta.xml.ws.Holder<jakarta.activation.DataHandler> attach1 = new jakarta.xml.ws.Holder<jakarta.activation.DataHandler>();
            attach1.value = dh1;
            jakarta.xml.ws.Holder<jakarta.activation.DataHandler> attach2 = new jakarta.xml.ws.Holder<jakarta.activation.DataHandler>();
            attach2.value = dh2;
            jakarta.xml.ws.Holder<javax.xml.transform.Source> attach3 = new jakarta.xml.ws.Holder<javax.xml.transform.Source>();
            attach3.value = new StreamSource(dh3.getInputStream());
            jakarta.xml.ws.Holder<java.awt.Image> attach4 = new jakarta.xml.ws.Holder<java.awt.Image>();
            jakarta.xml.ws.Holder<java.awt.Image> attach5 = new jakarta.xml.ws.Holder<java.awt.Image>();
            attach4.value = javax.imageio.ImageIO.read(url4);
            attach5.value = javax.imageio.ImageIO.read(url5);
            VoidRequest request = new VoidRequest();
            OutputResponseAll response = port.echoAllAttachmentTypes(request,
                    attach1, attach2, attach3, attach4, attach5);
            Assert.assertTrue(ValidateRequestResponseAttachmentsEchoAllTestCase(
                    request, response, attach1, attach2, attach3, attach4,
                    attach5));
        }
        {
            InputRequestGet request = new InputRequestGet();
            URL url1 = getResource("attach.text");
            URL url2 = getResource("attach.html");
            request.setMimeType1("text/plain");
            request.setMimeType2("text/html");
            request.setUrl1(url1.toString());
            request.setUrl2(url2.toString());
            jakarta.xml.ws.Holder<DataHandler> attach1 = new jakarta.xml.ws.Holder<DataHandler>();
            jakarta.xml.ws.Holder<DataHandler> attach2 = new jakarta.xml.ws.Holder<DataHandler>();
            jakarta.xml.ws.Holder<OutputResponse> response = new jakarta.xml.ws.Holder<OutputResponse>();
            port.getMultipleAttachments(request, response, attach1, attach2);
            Assert.assertTrue(ValidateRequestResponseAttachmentsGetTestCase(request,
                    response.value, attach1, attach2));
        }
        {
            jakarta.xml.ws.Holder<byte[]> data = new jakarta.xml.ws.Holder<byte[]>();
//            InputStream in = getSwaAttachmentURL("attach.jpeg1").openStream();
//            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
//            byte[] bytes = new byte[4096];
//            int read = in.read(bytes);
//            while (read != -1) {
//                baos.write(bytes, 0, read);
//                read = in.read(bytes);
//            }

            java.awt.Image image =  javax.imageio.ImageIO.read(getResource("attach.jpeg1"));
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            javax.imageio.ImageIO.write((java.awt.image.RenderedImage) image, "jpeg", baos);
            data.value = baos.toByteArray();
            byte[] bytes = baos.toByteArray();
            port.echoData("EnableMIMEContent = false", data);   
//            for ( int i = 0; i < data.value.length; i++ ) {
//                Assert.assertTrue(bytes[i] == data.value[i]);
//            }
        }
    }

    /***************************************************************************
     * Validate request, response and attachments (echoAllAttachmentTypes)
     **************************************************************************/
    private boolean ValidateRequestResponseAttachmentsEchoAllTestCase(
            VoidRequest request, OutputResponseAll response,
            jakarta.xml.ws.Holder<DataHandler> attach1,
            jakarta.xml.ws.Holder<DataHandler> attach2,
            jakarta.xml.ws.Holder<Source> attach3,
            jakarta.xml.ws.Holder<Image> attach4,
            jakarta.xml.ws.Holder<Image> attach5) throws Exception {
        boolean result = true;
        URL url1 = getResource("attach.text");
        URL url2 = getResource("attach.html");
        URL url3 = getResource("attach.xml");
        URL url4 = getResource("attach.jpeg1");
        URL url5 = getResource("attach.jpeg2");
        DataHandler dh1 = new DataHandler(url1);
        byte data1[] = new byte[4096];
        byte data2[] = new byte[4096];
        int count1 = dh1.getInputStream().read(data1, 0, 4096);
        int count2 = attach1.value.getInputStream().read(data2, 0, 4096);
        if (!ValidateAttachmentData(count1, data1, count2, data2, "Attachment1"))
            result = false;

        data2 = new byte[4096];
        dh1 = new DataHandler(url2);
        count1 = dh1.getInputStream().read(data1, 0, 4096);
        count2 = attach2.value.getInputStream().read(data2, 0, 4096);
        if (!ValidateAttachmentData(count1, data1, count2, data2, "Attachment2"))
            result = false;

        data2 = new byte[4096];
        dh1 = new DataHandler(url3);
        count1 = dh1.getInputStream().read(data1, 0, 4096);
        count2 = ((StreamSource) attach3.value).getInputStream().read(data2, 0,
                4096);
//        System.out.println("------------------------------ " + count1);
//        System.out.println(new String(data1, 0, count1));
//        System.out.println("------------------------------ " + count2);
//        System.out.println(new String(data2, 0, count2));
////        if (!ValidateAttachmentData(count1, data1, count2, data2, "Attachment3"))
////            result = false;

        dh1 = new DataHandler(url4);
        count1 = dh1.getInputStream().read(data1, 0, 4096);
        Image image1 = javax.imageio.ImageIO.read(url4);
        Image image2 = attach4.value;
        AttachmentHelper helper = new AttachmentHelper();
        if (!helper.compareImages(image1, image2,
                new Rectangle(0, 0, 100, 120), "Attachment4"))
            result = false;

        dh1 = new DataHandler(url5);
        count1 = dh1.getInputStream().read(data1, 0, 4096);
        image1 = javax.imageio.ImageIO.read(url5);
        image2 = attach5.value;
        helper = new AttachmentHelper();
        if (!helper.compareImages(image1, image2,
                new Rectangle(0, 0, 100, 120), "Attachment5"))
            result = false;
        return result;
    }

    private boolean ValidateAttachmentData(int count1, byte[] data1,
            int count2, byte[] data2, String attach) {
        int max = 0;
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        java.io.PrintStream ps = new java.io.PrintStream(baos);
        if (count2 > count1) {
            for (int i = count1; i < count2; i++) {
                if ((char) data2[i] != '\r')
                    break;
            }
            count2 = count1;
        }
        if (count1 != count2) {
            if (count2 > count1)
                max = count1;
            else
                max = count2;
            ps.printf("data1[%d]=0x%x  data2[%d]=0x%x", max - 1,
                    data1[max - 1], max - 1, data2[max - 1]);
            baos.reset();
            if (count2 > count1) {
                for (int i = count1; i < count2; i++) {
                    ps.printf("Extra data was: data2[%d]=0x%x|0%o", i,
                            data2[i], data2[i]);
                    baos.reset();
                }
            } else {
                for (int i = count2; i < count1; i++) {
                    ps.printf("Extra data was: data1[%d]=0x%x|0%o", i,
                            data1[i], data1[i]);
                    baos.reset();
                }
            }
            return false;
        }
        for (int i = 0; i < count1; i++) {
            if (data1[i] != data2[i]) {
                return false;
            }
        }
        return true;
    }

    /***************************************************************************
     * Validate request, response and attachments (getMultipleAttachments)
     **************************************************************************/
    private boolean ValidateRequestResponseAttachmentsGetTestCase(
            InputRequestGet request, OutputResponse response,
            jakarta.xml.ws.Holder<DataHandler> attach1,
            jakarta.xml.ws.Holder<DataHandler> attach2) {
        boolean result = true;
        if (!response.getMimeType1().equals(request.getMimeType1())) {
            result = false;
        }
        if (!response.getMimeType2().equals(request.getMimeType2())) {
            result = false;
        } else {
        }
        if (!response.getResult().equals("ok")) {
            result = false;
        } else {
        }
        try {
            DataHandler dh1 = new DataHandler(new URL(request.getUrl1()));
            DataHandler dh2 = new DataHandler(new URL(request.getUrl2()));
            byte data1[] = new byte[4096];
            byte data2[] = new byte[4096];
            int count1 = dh1.getInputStream().read(data1, 0, 4096);
            int count2 = attach1.value.getInputStream().read(data2, 0, 4096);
            if (!ValidateAttachmentData(count1, data1, count2, data2,
                    "Attachment1"))
                result = false;
            count1 = dh2.getInputStream().read(data1, 0, 4096);
            data2 = new byte[4096];
            count2 = attach2.value.getInputStream().read(data2, 0, 4096);
            if (!ValidateAttachmentData(count1, data1, count2, data2,
                    "Attachment2"))
                result = false;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
    
    static WSDLPort getWSDLPort(URL wsdlLoc) throws Exception {
        EntityResolver er = XmlUtil.createDefaultCatalogResolver();
        WSDLModel wsdl = RuntimeWSDLParser.parse(wsdlLoc, new StreamSource(wsdlLoc.toExternalForm()), er,
                true, null, ServiceFinder.find(WSDLParserExtension.class).toArray());
        QName serviceName = wsdl.getFirstServiceName();
        return wsdl.getService(serviceName).getFirstPort();
    }
    private URL getResource(String str) throws Exception {
//        return new File("D:/oc4j/webservices/devtest/data/cts15/DLSwaTest/" + str).toURL();
        return Thread.currentThread().getContextClassLoader().getResource("etc/"+str);
    }
    
}
