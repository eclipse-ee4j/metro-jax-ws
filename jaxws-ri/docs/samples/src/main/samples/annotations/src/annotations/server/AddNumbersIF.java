/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package annotations.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.WebResult;
import javax.jws.WebParam;

@WebService(targetNamespace = "http://duke.example.org", name="AddNumbers")
@SOAPBinding(style=SOAPBinding.Style.RPC, use=SOAPBinding.Use.LITERAL)
public interface AddNumbersIF {
    
    @WebMethod(operationName="add", action="urn:addNumbers")
    @WebResult(name="return")
    public int addNumbers(
        @WebParam(name="num1")int number1, 
        @WebParam(name="num2")int number2) throws AddNumbersException;

}
