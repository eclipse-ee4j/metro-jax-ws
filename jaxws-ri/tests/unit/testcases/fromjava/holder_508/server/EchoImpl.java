/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.holder_508.server;

import javax.jws.*;

import javax.xml.ws.Holder;
import javax.xml.ws.*;
import javax.xml.soap.*;
import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.rmi.RemoteException;

@WebService(endpointInterface="fromjava.holder_508.server.AddNumbersPortType")
public class EchoImpl {

    @WebMethod
    public int addNumbers(Holder<Integer> holder, int val) {
        if (holder.value == null) {
            return val;
        }
        holder.value = holder.value+val;
        return holder.value;
    }
}
