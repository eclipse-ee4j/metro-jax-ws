/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package xop.hello_soap12.server;


import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.Holder;
import javax.activation.DataHandler;
import java.rmi.RemoteException;
import java.awt.*;

@WebService(endpointInterface = "xop.hello_soap12.server.Hello")

public class HelloImpl implements Hello {
    public void detail(Holder<byte[]> photo, Holder<Image> image){
    }

    public DataHandler claimForm(DataHandler data){
        return data;
    }

    public void echoData(Holder<byte[]> data){

    }
}
