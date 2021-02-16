/*
 * Copyright (c) 2019, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

open module com.sun.xml.ws.policy {

    requires transitive java.xml;
    requires java.logging;

    requires org.glassfish.jaxb.runtime;

    exports com.sun.xml.ws.policy;
    exports com.sun.xml.ws.policy.util;
    exports com.sun.xml.ws.policy.sourcemodel;
    exports com.sun.xml.ws.policy.sourcemodel.attach /* TODO: to metro-wsit only ? */;
    exports com.sun.xml.ws.policy.sourcemodel.wspolicy;
    exports com.sun.xml.ws.policy.spi;
    exports com.sun.xml.ws.policy.subject;
    exports com.sun.xml.ws.policy.privateutil /* TODO: to metro-wsit only ! */;

    uses com.sun.xml.ws.policy.spi.LoggingProvider;
    uses com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
    uses com.sun.xml.ws.policy.spi.PolicyAssertionCreator;
    uses com.sun.xml.ws.policy.spi.PrefixMapper;

    provides com.sun.xml.ws.policy.spi.PolicyAssertionValidator with com.sun.xml.ws.policy.spi.MockPolicyAssertionValidator;
    provides com.sun.xml.ws.policy.spi.PolicyAssertionCreator with com.sun.xml.ws.policy.privateutil.MockPolicyAssertionCreator;

}
