/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.anonymous.client;

import javax.xml.ws.soap.SOAPFaultException;
import javax.xml.ws.WebServiceException;

import junit.framework.TestCase;
import com.sun.xml.ws.addressing.W3CAddressingConstants;
import com.sun.xml.ws.resources.AddressingMessages;
import testutil.WsaUtils;

/**
 * @author Arun Gupta
 */
public class ProhibitedAnonymousClient extends TestCase {
    public ProhibitedAnonymousClient(String name) {
        super(name);
    }

    public void testAnonymousReplyToDefault() throws Exception {
        try {
            BindingProviderUtil.createProhibitedAnonymousStub().addNumbers(40, 40, "ProhibitedAnonymous.testAnonymousReplyTo");
            fail("MUST throw SOAPFaultException with ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED fault code");
        } catch (WebServiceException e) {
            assertEquals(AddressingMessages.WSAW_ANONYMOUS_PROHIBITED(), e.getMessage());
        }
    }

    public void testAnonymousReplyTo() throws Exception {
        try {
        WsaUtils.invoke(BindingProviderUtil.createProhibitedAnonymousDispatchWithoutAddressing(),
                        BindingProviderUtil.ANONYMOUS_REPLY_TO_COMPLETE_MESSAGE,
                        WsaUtils.S11_NS,
                        BindingProviderUtil.PROHIBITED_IN_ACTION,
                        BindingProviderUtil.getProhibitedAnonymousAddress(),
                        "ProhibitedAnonymous.testNonAnonymousFaultTo");
            fail("MUST throw SOAPFaultException with ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED fault code");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertEquals(W3CAddressingConstants.ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED,
                         e.getFault().getFaultCodeAsQName());
        }
    }

    public void testAnonymousFaultTo() throws Exception {
        try {
            WsaUtils.invoke(BindingProviderUtil.createProhibitedAnonymousDispatchWithoutAddressing(),
                            BindingProviderUtil.ANONYMOUS_FAULT_TO_COMPLETE_MESSAGE,
                            WsaUtils.S11_NS,
                            BindingProviderUtil.PROHIBITED_IN_ACTION,
                            BindingProviderUtil.getProhibitedAnonymousAddress(),
                            "ProhibitedAnonymous.testAnonymousFaultTo");
            fail("MUST throw SOAPFaultException with ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED fault code");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertEquals(W3CAddressingConstants.ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED,
                         e.getFault().getFaultCodeAsQName());
        }
    }

    public void testNonAnonymousFaultTo() throws Exception {
        try {
        WsaUtils.invoke(BindingProviderUtil.createProhibitedAnonymousDispatchWithoutAddressing(),
                        BindingProviderUtil.NON_ANONYMOUS_FAULT_TO_COMPLETE_MESSAGE,
                        WsaUtils.S11_NS,
                        BindingProviderUtil.getProhibitedAnonymousAddress(),
                        BindingProviderUtil.PROHIBITED_IN_ACTION,
                        BindingProviderUtil.getProhibitedAnonymousAddress(),
                        "ProhibitedAnonymous.testNonAnonymousFaultTo");
            fail("WebServiceException must be thrown");
        } catch (WebServiceException e) {
            assertEquals("No response returned.", e.getMessage());
        }

    }

    public void testNonAnonymousReplyTo() throws Exception {
        try {
            WsaUtils.invoke(BindingProviderUtil.createProhibitedAnonymousDispatchWithoutAddressing(),
                            BindingProviderUtil.NON_ANONYMOUS_REPLY_TO_COMPLETE_MESSAGE,
                            WsaUtils.S11_NS,
                            BindingProviderUtil.getProhibitedAnonymousAddress(),
                            BindingProviderUtil.PROHIBITED_IN_ACTION,
                            BindingProviderUtil.getProhibitedAnonymousAddress(),
                            "ProhibitedAnonymous.testNonAnonymousReplyTo");
            fail("WebServiceException must be thrown");
        } catch (WebServiceException e) {
            assertEquals("No response returned.", e.getMessage());
        }
    }
}
