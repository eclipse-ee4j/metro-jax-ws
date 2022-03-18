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

import junit.framework.TestCase;
import testutil.WsaUtils;

import jakarta.xml.ws.WebServiceException;

/**
 * @author Arun Gupta
 */
public class OptionalAnonymousClient extends TestCase {
    public OptionalAnonymousClient(String name) {
        super(name);
    }

    public void testAnonymousReplyTo() throws Exception {
        BindingProviderUtil.createOptionalAnonymousStub().addNumbers(20, 20, "OptionalAnonymous.testAnonymousReplyTo");
    }

    public void testAnonymousFaultTo() throws Exception {
        WsaUtils.invoke(BindingProviderUtil.createOptionalAnonymousDispatch(),
                        BindingProviderUtil.ANONYMOUS_FAULT_TO_MESSAGE,
                        WsaUtils.S11_NS,
                        "OptionalAnonymous.testAnonymousFaultTo");
    }

    public void testNonAnonymousFaultTo() throws Exception {
        WsaUtils.invoke(BindingProviderUtil.createOptionalAnonymousDispatch(),
                        BindingProviderUtil.NON_ANONYMOUS_FAULT_TO_MESSAGE,
                        WsaUtils.S11_NS,
                        BindingProviderUtil.getOptionalAnonymousAddress(),
                        "OptionalAnonymous.testNonAnonymousFaultTo");
    }

    public void testNonAnonymousReplyTo() throws Exception {
        try {
            WsaUtils.invoke(BindingProviderUtil.createOptionalAnonymousDispatchWithoutAddressing(),
                            BindingProviderUtil.NON_ANONYMOUS_REPLY_TO_COMPLETE_MESSAGE,
                            WsaUtils.S11_NS,
                            BindingProviderUtil.getOptionalAnonymousAddress(),
                            BindingProviderUtil.OPTIONAL_IN_ACTION,
                            BindingProviderUtil.getOptionalAnonymousAddress(),
                            "OptionalAnonymous.testNonAnonymousReplyTo");
            fail("WebServiceException must be thrown");
        } catch (WebServiceException e) {
            assertEquals("No response returned.", e.getMessage());
        }
    }
}
