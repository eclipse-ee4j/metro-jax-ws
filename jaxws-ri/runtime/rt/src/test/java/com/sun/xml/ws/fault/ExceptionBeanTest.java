/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.fault;

import com.sun.xml.ws.developer.ServerSideException;
import com.sun.xml.ws.util.DOMUtil;
import com.sun.xml.ws.util.xml.XmlUtil;
import junit.framework.TestCase;
import org.w3c.dom.Document;

import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.net.URL;

/**
 * @author Kohsuke Kawaguchi
 */
public class ExceptionBeanTest extends TestCase {
    /**
     * Makes sure it round-trips.
     */
    public void test1() throws Exception {
        NullPointerException y = new NullPointerException();
        y.fillInStackTrace();

        Exception e = new Exception(y);
        e.fillInStackTrace();

        Document dom = DOMUtil.createDom();
        ExceptionBean.marshal(e,dom);

        XmlUtil.newTransformer().transform(new DOMSource(dom),new StreamResult(System.out));

        ServerSideException z = ExceptionBean.unmarshal(dom.getDocumentElement());
        z.printStackTrace(System.out);
    }

    /**
     * Unmarshal test to verify the on-the-wire format compatibility.
     */
    public void test2() throws Exception {
        URL res = getClass().getResource("exception.xml");

        Document d = DOMUtil.createDom();
        XmlUtil.newTransformer().transform(new StreamSource(res.toExternalForm()),new DOMResult(d));

        ServerSideException se = ExceptionBean.unmarshal(d.getDocumentElement());
        assertTrue(se!=null);
        assertTrue(se.getMessage().contains("Client received an exception from server"));
    }
}
