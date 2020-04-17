/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package xop.hello_handler.server;


import com.sun.xml.ws.developer.JAXWSProperties;

import jakarta.xml.ws.WebServiceException;
import jakarta.activation.DataHandler;
import jakarta.annotation.Resource;
import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import java.awt.*;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;
import javax.xml.transform.Source;



@WebService(endpointInterface = "xop.hello_handler.server.Hello")
@BindingType(value=SOAPBinding.SOAP11HTTP_MTOM_BINDING)

public class HelloImpl {
    
    public void detail(Holder<byte[]> photo, Holder<Image> image){
        verifyHandlerExecution();
        if (image.value == null) {
            throw new WebServiceException("Received Image is null");
        }
        MessageContext mc = wsContext.getMessageContext();
        //Mtom attachments are not visible to the applications
//        String conneg = (String)mc.get(JAXWSProperties.CONTENT_NEGOTIATION_PROPERTY);
//        if (conneg == null || conneg != "optimistic" ) {
//            Map<String, DataHandler> attachments = (Map<String, DataHandler>)mc.get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);
//            if(attachments.size() != 2)
//                throw new WebServiceException("Expected 2 attachments in MessageContext, received: "+attachments.size()+"!");
//        }
    }

    public DataHandler claimForm(DataHandler data) throws IOException {
        verifyHandlerExecution();
//        Source src = null;
//        try {
//            src = (Source) data.getContent();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        if(data.getContentType().equals("image/jpeg")){
            Image image = (Image) data.getContent();
        }else if(data.getContentType().equals("text/xml")){
            Source src = (Source)data.getContent();
        }else{
            throw new WebServiceException("Expected image/jpeg or text/xml contentType, received: "+data.getContentType());
        }
        return data;
    }

    public void echoData(Holder<byte[]> data){
        verifyHandlerExecution();
        if(wsContext != null){
            MessageContext mc = wsContext.getMessageContext();
            mc.put(JAXWSProperties.MTOM_THRESHOLOD_VALUE, 2000);
        }
    }
    
    private void verifyHandlerExecution(){
        MessageContext mc = wsContext.getMessageContext();
        String value = (String)mc.get("MyHandler_Property");
        if(value != "foo"){
            throw new RuntimeException("Server-side Handler not called");
        }        
    }
    @Resource
    private WebServiceContext wsContext;
}
