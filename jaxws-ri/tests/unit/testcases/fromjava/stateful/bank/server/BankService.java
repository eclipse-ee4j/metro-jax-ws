/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.stateful.bank.server;

import javax.jws.WebService;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

/**
 * @author Kohsuke Kawaguchi
 */
@WebService(portName = "BankServicePort")
public class BankService {
    public W3CEndpointReference getAccount(int id) {
        Account a = new Account(id);
        return Account.manager.export(a);
    }
}
