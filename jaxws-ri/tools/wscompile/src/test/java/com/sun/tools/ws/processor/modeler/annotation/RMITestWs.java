/*
 * Copyright (c) 2016, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.modeler.annotation;

import java.rmi.RemoteException;
import javax.jws.WebService;

/**
 *
 * @author lukas
 */
@WebService
public class RMITestWs {

    public String op() {
        return null;
    }

    public String opWithCustomEx() throws CustomEx {
        throw new CustomEx();
    }

    public String opWithRuntimeEx() throws RuntimeException {
        return null;
    }

    public String opWithRemoteEx() throws RemoteException {
        return null;
    }

    public String opWithRemoteExSub() throws RemoteSubEx {
        return null;
    }

}
