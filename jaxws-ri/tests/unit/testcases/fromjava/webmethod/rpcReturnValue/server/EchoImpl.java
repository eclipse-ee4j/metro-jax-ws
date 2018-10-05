/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testcases.fromjava.webmethod.rpcReturnValue.server;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Holder;
import java.rmi.RemoteException;

/**
 * This is service to test if return value of method has been correctly parsed by client while RPC enabled.
 *
 * @author David Kral
 */
@WebService
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class EchoImpl {

    @WebMethod
    public String holderOperation(@WebParam(name="holder1", mode=WebParam.Mode.INOUT)Holder<String> holder1,
                                  @WebParam(name="holder2", mode=WebParam.Mode.INOUT)Holder<String> holder2) throws RemoteException{
        holder1.value += "1";
        holder2.value += "2";
        return "success";
    }

}
