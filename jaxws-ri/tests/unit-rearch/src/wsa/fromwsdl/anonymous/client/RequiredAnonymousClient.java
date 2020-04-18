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

import jakarta.xml.ws.soap.SOAPFaultException;

import junit.framework.TestCase;
import testutil.WsaUtils;
import com.sun.xml.ws.addressing.W3CAddressingConstants;

/**
 * @author Arun Gupta
 */
public class RequiredAnonymousClient extends TestCase {
    public RequiredAnonymousClient(String name) {
        super(name);
    }

    public void testAnonymousReplyTo() throws Exception {
        BindingProviderUtil.createRequiredAnonymousStub().addNumbers(30, 30, "RequiredAnonymous.testAnonymousReplyTo");
    }

    public void testAnonymousFaultTo() throws Exception {
        WsaUtils.invoke(BindingProviderUtil.createRequiredAnonymousDispatch(),
                        BindingProviderUtil.ANONYMOUS_FAULT_TO_MESSAGE,
                        WsaUtils.S11_NS,
                        "RequiredAnonymous.testAnonymousFaultTo");
    }

    public void testNonAnonymousFaultTo() throws Exception {
        try {
            WsaUtils.invoke(BindingProviderUtil.createRequiredAnonymousDispatch(),
                            BindingProviderUtil.NON_ANONYMOUS_FAULT_TO_MESSAGE,
                            WsaUtils.S11_NS,
                            BindingProviderUtil.getRequiredAnonymousAddress(),
                            "RequiredAnonymous.testNonAnonymousFaultTo");
            fail("MUST throw SOAPFaultException with ONLY_ANONYMOUS_ADDRESS_SUPPORTED fault code");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertEquals(W3CAddressingConstants.ONLY_ANONYMOUS_ADDRESS_SUPPORTED, e.getFault().getFaultCodeAsQName());
        }
    }

    public void testNonAnonymousReplyTo() throws Exception {
        try {
            WsaUtils.invoke(BindingProviderUtil.createRequiredAnonymousDispatchWithoutAddressing(),
                            BindingProviderUtil.NON_ANONYMOUS_REPLY_TO_ANONYMOUS_FAULT_TO_COMPLETE_MESSAGE,
                            WsaUtils.S11_NS,
                            BindingProviderUtil.getRequiredAnonymousAddress(),
                            BindingProviderUtil.REQUIRED_IN_ACTION,
                            BindingProviderUtil.getRequiredAnonymousAddress(),
                            "RequiredAnonymous.testNonAnonymousReplyTo");
            fail("MUST throw SOAPFaultException due to non-anon ReplyTo with required wsaw:Anonymous");
        } catch (SOAPFaultException e) {
            assertNotNull(e.getFault());
            assertEquals(W3CAddressingConstants.ONLY_ANONYMOUS_ADDRESS_SUPPORTED, e.getFault().getFaultCodeAsQName());
        }
    }
}
