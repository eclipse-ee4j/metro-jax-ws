/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1045.server;

import jakarta.jws.WebService;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.Addressing;
import jakarta.xml.ws.soap.SOAPBinding;

/**
 * Simple test for JAX_WS-1045 http://java.net/jira/browse/JAX_WS-1045
 * - NPE at com.sun.xml.internal.ws.wsdl.writer.W3CAddressingWSDLGeneratorExtension:72 (Soap12/no Action)
 * - without this fix WSDL generation fails, because there was missing NPE check in W3CAddressingWSDLGeneratorExtension
 * which is valid for SOAP 1.2
 * No test client necessary, without fix it's impossible even generate and deploy server
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
@WebService
@Addressing(required = true)
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class SimpleService {

    public void emptyMethod() {
        // even the one doing nothing can mess up things ;)
    }

}
