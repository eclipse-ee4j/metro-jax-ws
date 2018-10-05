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

import javax.xml.ws.WebServiceException;

/**
 * @author Arun Gupta
 */
public class NoAnonymousClient extends TestCase {
    public NoAnonymousClient(String name) {
        super(name);
    }

    public void testAnonymousReplyTo() throws Exception {
        BindingProviderUtil.createNoAnonymousStub().addNumbers(10, 10, "NoAnonymous.testAnonymousReplyTo");
    }

    public void testAnonymousFaultTo() throws Exception {
        WsaUtils.invoke(BindingProviderUtil.createNoAnonymousDispatch(),
                        BindingProviderUtil.ANONYMOUS_FAULT_TO_MESSAGE,
                        WsaUtils.S11_NS,
                        "NoAnonymous.testAnonymousFaultTo");
    }

    public void testNonAnonymousFaultTo() throws Exception {
        WsaUtils.invoke(BindingProviderUtil.createNoAnonymousDispatch(),
                        BindingProviderUtil.NON_ANONYMOUS_FAULT_TO_MESSAGE,
                        WsaUtils.S11_NS,
                        BindingProviderUtil.getNoAnonymousAddress(),
                        "NoAnonymous.testNonAnonymousFaultTo");
    }

    public void testNonAnonymousReplyTo() throws Exception {
        try {
            WsaUtils.invoke(BindingProviderUtil.createNoAnonymousDispatchWithoutAddressing(),
                            BindingProviderUtil.NON_ANONYMOUS_REPLY_TO_COMPLETE_MESSAGE,
                            WsaUtils.S11_NS,
                            BindingProviderUtil.getNoAnonymousAddress(),
                            BindingProviderUtil.NO_IN_ACTION,
                            BindingProviderUtil.getNoAnonymousAddress(),
                            "NoAnonymous.testNonAnonymousReplyTo");
            fail("WebServiceException must be thrown");
        } catch (WebServiceException e) {
            assertEquals("No response returned.", e.getMessage());
        }
    }
}
