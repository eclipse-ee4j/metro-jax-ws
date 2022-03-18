/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.exception_mapping.server;

import jakarta.jws.WebService;
import jakarta.jws.Oneway;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceException;
import java.util.List;
import java.rmi.RemoteException;

@WebService
public class EchoImpl {

    public Bar echoBar(Bar bar) throws EchoException, EchoRuntimeException {
        if(bar.getAge() < 0) {
            throw new EchoException("Age cannot be less than 0","Age cannot be less than 0");
        } else if(bar.getAge() >1000) {
            throw new EchoRuntimeException("Too much for me to process","Too much for me to process");
        }
        return bar;
    }
    
    public String echoString(String str) throws RuntimeException, RemoteException {
        if(str.contains("fault")) {
            throw new RuntimeException("You asked it, you got it");
        } else if(str.contains("remote")) {
            throw new RemoteException("As asked here is the Remote Exception");
        }
        return str;
    }

    public String echoStringHolder(Holder<String> str) throws WebServiceException, Error  {
        if(str.value.contains("fault")) {
            throw new WebServiceException("You asked it, you got it");    
        } else if(str.value.contains("error")) {
            throw new Error("You asked it, you got it");    
        }
        return str.value;
    }

    public List<Bar> echoBarList(List<Bar> list) throws Throwable {
        if(list.size() == 0) {
            throw new Throwable("List cannot be empty");
        }
        return list;
    }

    @Oneway
    public void notify(String str) throws WebServiceException, RemoteException {
        if(str.contains("fault")) {
            throw new RuntimeException("You asked it, you got it");
        } else if(str.contains("remote")) {
            throw new RemoteException("As asked here is the Remote Exception");
        }
        //do nothing
    }
        
}
