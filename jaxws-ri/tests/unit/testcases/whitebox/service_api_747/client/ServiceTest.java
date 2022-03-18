/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.service_api_747.client;

import java.util.Iterator;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.soap.SOAPBinding;

import junit.framework.TestCase;

/**
 * @author Jitendra Kotamraju
 */
public class ServiceTest extends TestCase {

    public void test() throws Exception {
        Service service = Service.create(new QName("", ""));
        Iterator<QName> ports = service.getPorts();
        assertFalse(ports.hasNext());
        
        QName newPort = new QName("urn:test", "newPort");
        service.addPort(newPort, SOAPBinding.SOAP11HTTP_BINDING, "http://localhost/service");
        ports = service.getPorts();
        assertEquals(newPort, ports.next());
    }

}
