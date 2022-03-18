/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1015.server;

import jakarta.jws.HandlerChain;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

/**
 * Simple service for testing non-defined soapAction with Soap1.2; starting from wsdl
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
@WebService(endpointInterface = "bugs.jaxws1015.server.AddNumbersPortType")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@HandlerChain(name="", file="handlers.xml")
public class AddNumbersImpl implements bugs.jaxws1015.server.AddNumbersPortType {

    @Override
    public int addNumbers(@WebParam(name = "arg0", targetNamespace = "http://duke.example.org") int arg0, @WebParam(name = "arg1", targetNamespace = "http://duke.example.org") int arg1) throws AddNumbersFault_Exception {
        return arg0 + arg1;
    }
}
