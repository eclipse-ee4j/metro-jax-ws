/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.submission.fromwsdl.crinterop.s11.client;

import junit.framework.TestCase;
import testutil.WsaUtils;
import static testutil.WsaUtils.*;
import static wsa.submission.fromwsdl.crinterop.s11.common.TestConstants.*;
import static wsa.submission.fromwsdl.crinterop.s11.client.BindingProviderUtil.*;

/**
 * @author Arun Gupta
 */
public class NotifyClient extends TestCase {
    public NotifyClient(String name) {
        super(name);
    }

    /**
     * SOAP 1.1 one-way message.
     */
    public void test1100() throws Exception {
        createStub().notify("test1100");
    }

    /**
     * SOAP 1.1 one-way defaulted with a MessageID value.
     */
    public void test1101() throws Exception {
        createStub().notify("test1101");
    }

    /**
     * SOAP 1.1 one-way message with a ReplyTo address of none.
     */
//    public void test1102() throws Exception {
//        WsaUtils.invokeOneWay(BindingProviderUtil.createDispatchWithWSDLWithoutAddressing(),
//                              MESSAGES.getNoneReplyToMessage(),
//                              WsaUtils.S11_NS,
//                              BindingProviderUtil.getAddress(),
//                              NOTIFY_ACTION,
//                              "test1102");
//    }

    /**
     * SOAP 1.1 one-way message with a FaultTo address of none.
     */
//    public void test1103() throws Exception {
//        WsaUtils.invokeOneWay(BindingProviderUtil.createDispatchWithWSDLWithoutAddressing(),
//                              MESSAGES.getNoneFaultToMessage(),
//                              WsaUtils.S11_NS,
//                              MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME,
//                              BindingProviderUtil.getAddress(),
//                              NOTIFY_ACTION,
//                              "test1103");
//    }

    /**
     * SOAP 1.1 one-way message with a ReplyTo and FaultTo address of none.
     */
//    public void test1104() throws Exception {
//        WsaUtils.invokeOneWay(BindingProviderUtil.createDispatchWithWSDLWithoutAddressing(),
//                              MESSAGES.getNoneReplyToFaultToMessage(),
//                              WsaUtils.S11_NS,
//                              MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME,
//                              BindingProviderUtil.getAddress(),
//                              NOTIFY_ACTION,
//                              "test1104");
//    }

    /**
     * SOAP 1.1 one-way message with ReplyTo containing a Reference Parameter.
     */
    public void test1106() throws Exception {
        invokeOneWay(createDispatchWithWSDLWithoutAddressing(),
                              MESSAGES.getReplyToRefpsNotifyMessage(),
                              S11_NS,
                              getAddress(),
                              NOTIFY_ACTION,
                              "test1106");
    }

    /**
     * SOAP 1.1 one-way message with ReplyTo containing WSDL Metadata.
     */
//    public void test1107() throws Exception {
//        WsaUtils.invokeOneWay(BindingProviderUtil.createDispatchWithWSDLWithoutAddressing(),
//                              MESSAGES.getReplyToMetadataMessage(),
//                              WsaUtils.S11_NS,
//                              MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME,
//                              BindingProviderUtil.getAddress(),
//                              NOTIFY_ACTION,
//                              "test1107");
//    }

    /**
     * SOAP 1.1 one-way message with ReplyTo containing EPR extensions.
     */
    public void test1108() throws Exception {
        invokeOneWay(createDispatchWithWSDLWithoutAddressing(),
                              MESSAGES.getReplyToExtensionsMessage(),
                              S11_NS,
                              getAddress(),
                              NOTIFY_ACTION,
                              "test1108");
    }
}
