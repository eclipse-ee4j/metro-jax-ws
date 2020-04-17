/*
 * Copyright (c) 2018, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bsh;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPConstants;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.Binding;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.handler.Handler;
import java.io.File;
import java.io.FileInputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import static junit.framework.TestCase.*;

/**
 * Created by miran on 02/03/15.
 */
public class Util {

    // utility methods to be made available to the test clients

    /**
     * Method used to add a Handler to a stub or dispatch object.
     */
    public static void addHandler(Handler handler, Object provider) {
        BindingProvider bindingProvider = (BindingProvider) provider;
        Binding binding = bindingProvider.getBinding();
        List<Handler> handlers = binding.getHandlerChain();
        handlers.add(handler);
        binding.setHandlerChain(handlers);
    }

    /**
     * Method used to clear any handlers from a stub or dispatch object.
     */
    public static void clearHandlers(Object provider) {
        BindingProvider bindingProvider = (BindingProvider) provider;
        Binding binding = bindingProvider.getBinding();
        binding.setHandlerChain(new java.util.ArrayList());
    }

    /**
     * Reads Source into a SOAP Message
     */
    public static SOAPMessage getSOAPMessage(Source msg) {
        try {
            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage message = factory.createMessage();
            message.getSOAPPart().setContent(msg);
            message.saveChanges();
            return message;
        } catch (SOAPException e) {
            e.printStackTrace();
            fail("Can't get SOAPMessage");
            return null;
        }
    }

    /**
     * Reads Source into a SOAP Message
     */
    public static SOAPMessage getSOAPMessage12(Source msg) {
        try {
            MessageFactory factory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
            SOAPMessage message = factory.createMessage();
            message.getSOAPPart().setContent(msg);
            message.saveChanges();
            return message;
        } catch (SOAPException e) {
            e.printStackTrace();
            fail("Error getting SOAP12 Message");
            return null;
        }
    }

    /**
     * Creates JAXBContext for the current compiled artifacts
     */
    public static JAXBContext createJAXBContext(Class<?> objectFactory) {
        try {
            return JAXBContext.newInstance(new Class[]{objectFactory});
        } catch (JAXBException e) {
            e.printStackTrace();
            fail("Error creating JAXBContext");
            return null;
        }
    }

    public static java.io.File resource(String path) {
        URL resource = Util.class.getResource("/" + path);
        if (resource != null) {
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else {
            String pwd = System.getenv("PWD");
            File f =  new File(pwd + "/client-classes/client/" + path);
            if (f.exists()) {
                return f;
            } else {
                throw new RuntimeException("No resource found! resource: " + path + ", path = " + pwd);
            }
        }
    }

    public static javax.xml.transform.stream.StreamSource streamSource(File file) throws java.io.FileNotFoundException {
        return new StreamSource(new FileInputStream(file));
    }

}
