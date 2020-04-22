/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.spi;

import java.util.Collection;
import java.util.LinkedList;
import javax.xml.namespace.QName;

/**
 *
 * @author Fabian Ritzmann
 */
public class MockPolicyAssertionValidator extends AbstractQNameValidator {

    public static final QName SERVER_ASSERTION_NAME = new QName("http://example.test/", "ServerAssertion");
    public static final QName CLIENT_ASSERTION_NAME = new QName("http://example.test/", "ClientAssertion");

    private static final Collection<QName> serverAssertions = new LinkedList<QName>();
    private static final Collection<QName> clientAssertions = new LinkedList<QName>();
    
    static {
        serverAssertions.add(SERVER_ASSERTION_NAME);
        clientAssertions.add(CLIENT_ASSERTION_NAME);
    };

    public MockPolicyAssertionValidator() {
        super(serverAssertions, clientAssertions);
    }
}
