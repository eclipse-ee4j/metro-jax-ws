/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.epr_webparam_header.client;

import junit.framework.TestCase;

import javax.xml.ws.wsaddressing.W3CEndpointReferenceBuilder;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;

/**
 * @author Rama Pulavarthi
 */
public class EPRTest extends TestCase {
    public void test() {
        Test proxy =  new TestService().getTestPort();
        W3CEndpointReferenceBuilder eprbuilder = new W3CEndpointReferenceBuilder();
        String address= "http://example.com";
        eprbuilder.address(address);
        W3CEndpointReference epr = eprbuilder.build();
        W3CEndpointReference ret = proxy.test(epr);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ret.writeTo(new StreamResult(baos));
        String retEprStr = new String(baos.toByteArray());
        assertTrue(retEprStr.contains(address));
    }
}
