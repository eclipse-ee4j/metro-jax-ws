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

import java.util.Collections;
import java.util.List;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

/**
 * @author lingling guo
 */
@WebService(serviceName = "GenericWithEnumsService",portName = "GenericWithEnumsPort",name = "GenericWithEnums",targetNamespace = "http://GenericWithEnumsservice.org/wsdl")
public class GenericWithEnums {    
    public GenericWithEnums() {}
    public enum AnEnum { VAL1, VAL2 }  
    @WebMethod
    public List<String> getListOfStringFromEnumArray(AnEnum[] anEnums) {
        return Collections.emptyList();
    }
}
