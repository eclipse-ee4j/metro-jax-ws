/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.serializable.client;

import com.sun.xml.ws.client.ClientTransportException;
import com.sun.xml.ws.resources.ClientMessages;
import com.sun.xml.ws.util.ByteArrayBuffer;
import junit.framework.TestCase;

import jakarta.xml.ws.WebServiceException;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

/**
 * Tests serialzability of RI exceptions extending JAXWSExceptionBase
 * @author Rama Pulavarthi
 */
public class SerializableWebServiceExceptionTest extends TestCase {

    /**
     *  Tests Localizable with no args
     * @throws Exception
     */
    public void testClientTransportExSerial1() throws Exception {
        ClientTransportException cte_orig = new ClientTransportException(ClientMessages.localizableEPR_WITHOUT_ADDRESSING_ON());
        cte_orig.fillInStackTrace();

        ByteArrayBuffer buffer = new ByteArrayBuffer();
        ObjectOutputStream oos = new ObjectOutputStream(buffer);
        oos.writeObject(cte_orig);
        ObjectInputStream ois = new ObjectInputStream(buffer.newInputStream());
        ClientTransportException cte_ser = (ClientTransportException) ois.readObject();

        assertEquals(cte_orig.getLocalizedMessage(),cte_ser.getLocalizedMessage());
        assertEquals(cte_orig.getResourceBundleName(), cte_ser.getResourceBundleName());
        assertEquals(cte_orig.getKey(), cte_ser.getKey());
        assert(cte_orig.getArguments().length == cte_ser.getArguments().length);
    }

    /**
     * Tests Nested Exception
     * @throws Exception
     */
    public void testClientTransportExSerial2() throws Exception {
        ClientTransportException cte_orig = new ClientTransportException(ClientMessages.localizableEPR_WITHOUT_ADDRESSING_ON());
        cte_orig.fillInStackTrace();

        ByteArrayBuffer buffer = new ByteArrayBuffer();
        ObjectOutputStream oos = new ObjectOutputStream(buffer);
        oos.writeObject(new WebServiceException(cte_orig));
        ObjectInputStream ois = new ObjectInputStream(buffer.newInputStream());
        WebServiceException wse_ser = (WebServiceException) ois.readObject();
        ClientTransportException cte_ser = (ClientTransportException) wse_ser.getCause();

        assertEquals(cte_orig.getLocalizedMessage(),cte_ser.getLocalizedMessage());
        assertEquals(cte_orig.getResourceBundleName(), cte_ser.getResourceBundleName());
        assertEquals(cte_orig.getKey(), cte_ser.getKey());
        assert(cte_orig.getArguments().length == cte_ser.getArguments().length);

    }

    /**
     * Tests argument with null
     * @throws Exception
     */
    public void testClientTransportExSerial3() throws Exception {
        WebServiceException wse_orig = new ClientTransportException(ClientMessages.localizableFAILED_TO_PARSE(new URL("http://example.com?wsdl"), null));
        ClientTransportException cte_orig = (ClientTransportException)wse_orig;
        ByteArrayBuffer buffer = new ByteArrayBuffer();
        ObjectOutputStream oos = new ObjectOutputStream(buffer);
        oos.writeObject(wse_orig);

        ObjectInputStream ois = new ObjectInputStream(buffer.newInputStream());
        WebServiceException wse_ser = (WebServiceException) ois.readObject();
        ObjectInputStream ois1 = new ObjectInputStream(buffer.newInputStream());
        ClientTransportException cte_ser = (ClientTransportException) ois1.readObject();

        assertEquals(cte_orig.getLocalizedMessage(),cte_ser.getLocalizedMessage());
        assertEquals(cte_orig.getResourceBundleName(), cte_ser.getResourceBundleName());
        assertEquals(cte_orig.getKey(), cte_ser.getKey());
        assert(cte_orig.getArguments().length == cte_ser.getArguments().length);
        assertEquals(cte_orig.getArguments()[0], cte_ser.getArguments()[0]);
        assertEquals(cte_orig.getArguments()[1], cte_ser.getArguments()[1]);
    }

    /**
     * Tests with two serializable arguments
     * @throws Exception
     */
    public void testClientTransportExSerial4() throws Exception {
        WebServiceException wse_orig = new ClientTransportException(ClientMessages.localizableFAILED_TO_PARSE(new URL("http://example.com?wsdl"), new java.io.IOException("Can't access url")));
        ClientTransportException cte_orig = (ClientTransportException)wse_orig;
        ByteArrayBuffer buffer = new ByteArrayBuffer();
        ObjectOutputStream oos = new ObjectOutputStream(buffer);
        oos.writeObject(cte_orig);

        ObjectInputStream ois = new ObjectInputStream(buffer.newInputStream());
        WebServiceException wse_ser = (WebServiceException) ois.readObject();
        ObjectInputStream ois1 = new ObjectInputStream(buffer.newInputStream());
        ClientTransportException cte_ser = (ClientTransportException) ois1.readObject();

        assertEquals(cte_orig.getLocalizedMessage(),cte_ser.getLocalizedMessage());
        assertEquals(cte_orig.getResourceBundleName(), cte_ser.getResourceBundleName());
        assertEquals(cte_orig.getKey(), cte_ser.getKey());
        assert(cte_orig.getArguments().length == cte_ser.getArguments().length);
        assertEquals(cte_orig.getArguments()[0], cte_ser.getArguments()[0]);
        assertEquals(cte_orig.getArguments()[1].getClass(), cte_ser.getArguments()[1].getClass());
        assertEquals(cte_orig.getArguments()[1].toString(), cte_ser.getArguments()[1].toString());
    }

    /**
     * Tests non-serializable argument, should serialize as String
     * @throws Exception
     */
    public void testClientTransportExSerial5() throws Exception {
        ClientTransportException cte_orig = new ClientTransportException(ClientMessages.localizableFAILED_TO_PARSE(new URL("http://example.com?wsdl"), Thread.currentThread()));
        ByteArrayBuffer buffer = new ByteArrayBuffer();
        ObjectOutputStream oos = new ObjectOutputStream(buffer);
        oos.writeObject(cte_orig);

        ObjectInputStream ois = new ObjectInputStream(buffer.newInputStream());
        WebServiceException wse_ser = (WebServiceException) ois.readObject();
        ObjectInputStream ois1 = new ObjectInputStream(buffer.newInputStream());
        ClientTransportException cte_ser = (ClientTransportException) ois1.readObject();

        assertEquals(cte_orig.getLocalizedMessage(),cte_ser.getLocalizedMessage());
        assertEquals(cte_orig.getResourceBundleName(), cte_ser.getResourceBundleName());
        assertEquals(cte_orig.getKey(), cte_ser.getKey());
        assert(cte_orig.getArguments().length == cte_ser.getArguments().length);
        assertEquals(cte_orig.getArguments()[0], cte_ser.getArguments()[0]);
        assert(cte_ser.getArguments()[1] instanceof String);
        assertEquals(cte_orig.getArguments()[1].toString(), cte_ser.getArguments()[1].toString());
    }

}
