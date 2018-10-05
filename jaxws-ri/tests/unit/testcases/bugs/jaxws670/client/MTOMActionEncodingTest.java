/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws670.client;

import com.sun.xml.ws.developer.JAXWSProperties;
import junit.framework.TestCase;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.MTOMFeature;
import java.io.UnsupportedEncodingException;

/**
 * TODO: Write some description here ...
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class MTOMActionEncodingTest extends TestCase {

    private static final String ALMOST_BINARY_CONTENT = "ALMOST BINARY CONTENT";

    public void test() throws UnsupportedEncodingException {
        Echo echoPort = new EchoService().getEchoPort(new MTOMFeature());
        String echoed = echoPort.echo(ALMOST_BINARY_CONTENT.getBytes("UTF-8"));
        assertEquals(echoed, ALMOST_BINARY_CONTENT);
    }

}
