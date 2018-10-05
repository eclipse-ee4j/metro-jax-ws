/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.exception_mapping.client;


import javax.jws.WebService;
import javax.jws.Oneway;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.Holder;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Deployemnt of this endpoint should fail as Oneway method throws checked exception.
 * @author Rama Pulavarthi
 */
@WebService
public class NegativeEchoImpl {

    @Oneway
    public void notify(String str) throws WebServiceException, RemoteException, EchoException {
        if(str.contains("fault")) {
            throw new RuntimeException("You asked it, you got it");
        } else if(str.contains("remote")) {
            throw new RemoteException("As asked here is the Remote Exception");
        }
        //do nothing
    }

}
