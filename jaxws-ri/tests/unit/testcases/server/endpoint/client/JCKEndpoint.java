/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint.client;

import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.xml.ws.WebServiceException;

/**
 * @author Vivek Pandey
 */
@WebService(portName = "GetTrackerDataPort", targetNamespace = "http://dlhandlerservice.org/wsdl", serviceName = "DLHandlerService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public class JCKEndpoint {
    public String getTrackerData(String param){return param;}
}
