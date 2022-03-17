/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.config.management.policy;

import com.sun.xml.ws.api.config.management.policy.ManagedClientAssertion;
import com.sun.xml.ws.api.config.management.policy.ManagedServiceAssertion;
import com.sun.xml.ws.policy.PolicyAssertion;
import com.sun.xml.ws.policy.PolicyConstants;
import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
import com.sun.xml.ws.policy.spi.PolicyAssertionValidator.Fitness;

import javax.xml.namespace.QName;

/**
 * Validate the ManagedService and ManagedClient policy assertions.
 *
 * @author Fabian Ritzmann
 */
public class ManagementPolicyValidator implements PolicyAssertionValidator {

    @Override
    public Fitness validateClientSide(PolicyAssertion assertion) {
        final QName assertionName = assertion.getName();
        if (ManagedClientAssertion.MANAGED_CLIENT_QNAME.equals(assertionName)) {
            return Fitness.SUPPORTED;
        }
        else if (ManagedServiceAssertion.MANAGED_SERVICE_QNAME.equals(assertionName)) {
            return Fitness.UNSUPPORTED;
        }
        else {
            return Fitness.UNKNOWN;
        }
    }

    @Override
    public Fitness validateServerSide(PolicyAssertion assertion) {
        final QName assertionName = assertion.getName();
        if (ManagedServiceAssertion.MANAGED_SERVICE_QNAME.equals(assertionName)) {
            return Fitness.SUPPORTED;
        }
        else if (ManagedClientAssertion.MANAGED_CLIENT_QNAME.equals(assertionName)) {
            return Fitness.UNSUPPORTED;
        }
        else {
            return Fitness.UNKNOWN;
        }
    }

    @Override
    public String[] declareSupportedDomains() {
        return new String[] { PolicyConstants.SUN_MANAGEMENT_NAMESPACE };
    }

}
