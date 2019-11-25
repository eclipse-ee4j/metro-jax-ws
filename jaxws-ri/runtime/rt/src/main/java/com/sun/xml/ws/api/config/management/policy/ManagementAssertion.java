/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.config.management.policy;

import com.sun.istack.logging.Logger;
import com.sun.xml.ws.policy.AssertionSet;
import com.sun.xml.ws.policy.Policy;
import com.sun.xml.ws.policy.PolicyAssertion;
import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.PolicyMap;
import com.sun.xml.ws.policy.PolicyMapKey;
import com.sun.xml.ws.policy.SimpleAssertion;
import com.sun.xml.ws.policy.sourcemodel.AssertionData;
import com.sun.xml.ws.policy.spi.AssertionCreationException;
import com.sun.xml.ws.resources.ManagementMessages;

import java.util.Collection;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;

/**
 * Base class for the #ManagedClientAssertion and #ManagedServiceAssertion. Provides
 * convenience methods to directly access the policy assertion parameters.
 *
 * @author Fabian Ritzmann
 */
public abstract class ManagementAssertion extends SimpleAssertion {

    /**
     * To be able to distinguish between explicit settings and no setting.
     */
    public static enum Setting { NOT_SET, OFF, ON }

    /**
     * The name of the management attribute.
     */
    protected static final QName MANAGEMENT_ATTRIBUTE_QNAME = new QName("management");
    /**
     * The name of the monitoring attribute.
     */
    protected static final QName MONITORING_ATTRIBUTE_QNAME = new QName("monitoring");

    /**
     * The name of the id attribute.
     */
    private static final QName ID_ATTRIBUTE_QNAME = new QName("id");
    /**
     * The name of the start attribute.
     */
    private static final QName START_ATTRIBUTE_QNAME = new QName("start");

    private static final Logger LOGGER = Logger.getLogger(ManagementAssertion.class);

    /**
     * Return ManagementAssertion if one can be found in the policy map under
     * the given service and port name.
     *
     * @param <T> The implementation class of the assertion.
     * @param name The fully qualified name of the server or client assertion.
     * @param policyMap The policy map. May be null.
     * @param serviceName The WSDL service name. May not be null.
     * @param portName The WSDL port name. May not be null.
     * @param type The implementation class of the assertion.
     * @return An instance of ManagementAssertion or null.
     * @throws WebServiceException If computing the effective policy of the endpoint scope failed.
     */
    protected static <T extends ManagementAssertion> T getAssertion(final QName name,
            final PolicyMap policyMap, QName serviceName, QName portName, Class<T> type)
            throws WebServiceException {
        try {
            PolicyAssertion assertion = null;
            if (policyMap != null) {
                final PolicyMapKey key = PolicyMap.createWsdlEndpointScopeKey(serviceName, portName);
                final Policy policy = policyMap.getEndpointEffectivePolicy(key);
                if (policy != null) {
                    final Iterator<AssertionSet> assertionSets = policy.iterator();
                    if (assertionSets.hasNext()) {
                        final AssertionSet assertionSet = assertionSets.next();
                        final Iterator<PolicyAssertion> assertions = assertionSet.get(name).iterator();
                        if (assertions.hasNext()) {
                            assertion = assertions.next();
                        }
                    }
                }
            }
            return assertion == null ? null : assertion.getImplementation(type);
        } catch (PolicyException ex) {
            throw LOGGER.logSevereException(new WebServiceException(
                    ManagementMessages.WSM_1001_FAILED_ASSERTION(name), ex));
        }
    }

    /**
     * Create a new ManagementAssertion instance.
     *
     * @param name The fully qualified name of the server or client assertion. Must
     *   not be null.
     * @param data The assertion data. Must not be null.
     * @param assertionParameters Parameters of the assertion. May be null.
     * @throws AssertionCreationException Thrown if the creation of the assertion failed.
     */
    protected ManagementAssertion(final QName name, AssertionData data, Collection<PolicyAssertion> assertionParameters)
            throws AssertionCreationException {
        super(data, assertionParameters);
        if (!name.equals(data.getName())) {
            throw LOGGER.logSevereException(new AssertionCreationException(data,
                    ManagementMessages.WSM_1002_EXPECTED_MANAGEMENT_ASSERTION(name)));
        }
        if (isManagementEnabled() && !data.containsAttribute(ID_ATTRIBUTE_QNAME)) {
            throw LOGGER.logSevereException(new AssertionCreationException(data,
                    ManagementMessages.WSM_1003_MANAGEMENT_ASSERTION_MISSING_ID(name)));
        }
    }

    /**
     * Returns the value of the id attribute. May not be null.
     *
     * @return The value of the id attribute.
     */
    public String getId() {
        return this.getAttributeValue((ID_ATTRIBUTE_QNAME));
    }

    /**
     * Returns the value of the start attribute. May be null.
     *
     * @return The value of the start attribute.
     */
    public String getStart() {
        return this.getAttributeValue((START_ATTRIBUTE_QNAME));
    }

    /**
     * Returns the value of the managment attribute depending on whether this is
     * a client-side or server-side assertion.
     *
     * @return The value of the managment attribute.
     */
    public abstract boolean isManagementEnabled();

    /**
     * Returns the value of the monitoring attribute.
     *
     * @return The value of the monitoring attribute.
     */
    public Setting monitoringAttribute() {
        final String monitoring = this.getAttributeValue(MONITORING_ATTRIBUTE_QNAME);
        Setting result = Setting.NOT_SET;
        if (monitoring != null) {
            if (monitoring.trim().toLowerCase().equals("on")
                || Boolean.parseBoolean(monitoring)) {
                result = Setting.ON;
            }
            else {
                result = Setting.OFF;
            }
        }
        return result;
    }

}
