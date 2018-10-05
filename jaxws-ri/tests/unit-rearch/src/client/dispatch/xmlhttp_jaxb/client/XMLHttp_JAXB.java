/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package client.dispatch.xmlhttp_jaxb.client;

import client.dispatch.xmlhttp_jaxb.client.atom.FeedType;
import junit.framework.TestCase;
import testutil.ClientServerTestUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author JAX-RPC RI Development Team
 */
public class XMLHttp_JAXB extends TestCase {

    // see google-custom-search.txt in project root ...
    private static final String KEY = "AIzaSyAuu1mWRLODp-bQPod76AXUf-ih6gZsrRQ";
    private static final String CX = "007386487642334705425:rffb5lj8r98";

    private static final QName GOOGLE_PORT_NAME = new QName("google_port", "http://google.com/");
    private static final QName GOOGLE_SERVICE_NAME = new QName("google", "http://google.com/");

    private static final Logger LOGGER = Logger.getLogger(XMLHttp_JAXB.class.getName());

    public XMLHttp_JAXB(String name) {
        super(name);
    }

    void setTransport(Dispatch dispatch) {
        try {
            // create helper class
            ClientServerTestUtil util = new ClientServerTestUtil();
            // set transport
            OutputStream log = null;
            log = System.out;
            util.setTransport(dispatch, (OutputStream) log);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    SOAPMessage getSOAPMessage(Source msg) throws Exception {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage();
        message.getSOAPPart().setContent((Source) msg);
        message.saveChanges();
        return message;
    }

    public void testXMLHttpJAXBPAYLOAD() throws JAXBException {

        if (ClientServerTestUtil.useLocal()) {
            return;
        }

        Service s = Service.create(GOOGLE_SERVICE_NAME);
        s.addPort(GOOGLE_PORT_NAME, HTTPBinding.HTTP_BINDING, getGoogleURL("JAXB"));

        // Get a Dispatch interface for the Yahoo News Search service and  configure it
        JAXBContext jbc = JAXBContext.newInstance("client.dispatch.xmlhttp_jaxb.client.atom");

        Dispatch<Object> d = s.createDispatch(GOOGLE_PORT_NAME, jbc, Service.Mode.PAYLOAD);
        d.getRequestContext().put(MessageContext.HTTP_REQUEST_METHOD, "GET");

        // Execute the search iterate through the results
        JAXBElement<FeedType> o = (JAXBElement<FeedType>) d.invoke(null);
        FeedType feed = o.getValue();
        assertNotNull(feed);
        List<Object> authorOrCategoryOrContributor = feed.getAuthorOrCategoryOrContributor();
        assertNotNull(authorOrCategoryOrContributor);
        assertTrue(authorOrCategoryOrContributor.size() > 0);
        LOGGER.finest("Google Custom Search successfully read as JAXB Object: authorOrCategoryOrContributor.size() = " + authorOrCategoryOrContributor.size());
    }

    public static String getGoogleURL(String query) {
        return "https://www.googleapis.com/customsearch/v1?key=" + KEY + "&cx=" + CX + "&q=" + query + "&alt=atom";
    }

}
