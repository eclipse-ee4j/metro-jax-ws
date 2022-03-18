/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.fault_header.server;

import jakarta.jws.WebService;

/**
 * Test verifying bugfix for JAX_WS-1040 - correct wsdl2java generation (annotation jakarta.xml.ws.Action)
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
@WebService(endpointInterface = "bugs.fault_header.server.FaultHeader")
public class SoapHeaderServiceImpl {

    // no params to simplify wsdl ...
    public void myWebMethod() throws MyWebMethodFault_Exception {
        MyWebMethodFault faultInfo = new MyWebMethodFault();
        throw new MyWebMethodFault_Exception("Message", faultInfo);
    }

}
