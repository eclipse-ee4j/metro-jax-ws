/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.submission.fromwsdl.crinterop.s12.client;

import junit.framework.TestCase;
import testutil.WsaUtils;
import static wsa.submission.fromwsdl.crinterop.s12.common.TestConstants.*;

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
    public void test1200() throws Exception {
        BindingProviderUtil.createStub().notify("test1200");
    }

    /**
     * SOAP 1.1 one-way defaulted with a MessageID value.
     */
    public void test1201() throws Exception {
        BindingProviderUtil.createStub().notify("test1201");
    }

    /**
     * SOAP 1.1 one-way message with a ReplyTo address of none.
     */
//    public void test1202() throws Exception {
//        WsaUtils.invokeOneWay12(BindingProviderUtil.createDispatchWithWSDLWithoutAddressing(),
//                              MESSAGES.getNoneReplyToMessage(),
//                              WsaUtils.S12_NS,
//                              BindingProviderUtil.getAddress(),
//                              NOTIFY_ACTION,
//                              "test1202");
//    }

    /**
     * SOAP 1.1 one-way message with a FaultTo address of none.
     */
//    public void test1203() throws Exception {
//        WsaUtils.invokeOneWay12(BindingProviderUtil.createDispatchWithWSDLWithoutAddressing(),
//                              MESSAGES.getNoneFaultToMessage(),
//                              WsaUtils.S12_NS,
//                              MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME,
//                              BindingProviderUtil.getAddress(),
//                              NOTIFY_ACTION,
//                              "test1203");
//    }

    /**
     * SOAP 1.1 one-way message with a ReplyTo and FaultTo address of none.
     */
//    public void test1204() throws Exception {
//        WsaUtils.invokeOneWay12(BindingProviderUtil.createDispatchWithWSDLWithoutAddressing(),
//                              MESSAGES.getNoneReplyToFaultToMessage(),
//                              WsaUtils.S12_NS,
//                              MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME,
//                              BindingProviderUtil.getAddress(),
//                              NOTIFY_ACTION,
//                              "test1204");
//    }

    /**
     * SOAP 1.1 one-way message with ReplyTo containing a Reference Parameter.
     */
    public void test1206() throws Exception {
        WsaUtils.invokeOneWay12(BindingProviderUtil.createDispatchWithWSDLWithoutAddressing(),
                              MESSAGES.getReplyToRefpsNotifyMessage(),
                              WsaUtils.S12_NS,
                              BindingProviderUtil.getAddress(),
                              NOTIFY_ACTION,
                              "test1206");
    }

    /**
     * SOAP 1.1 one-way message with ReplyTo containing WSDL Metadata.
     */
//    public void test1207() throws Exception {
//        WsaUtils.invokeOneWay12(BindingProviderUtil.createDispatchWithWSDLWithoutAddressing(),
//                              MESSAGES.getReplyToMetadataMessage(),
//                              WsaUtils.S12_NS,
//                              MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME,
//                              BindingProviderUtil.getAddress(),
//                              NOTIFY_ACTION,
//                              "test1207");
//    }

    /**
     * SOAP 1.1 one-way message with ReplyTo containing EPR extensions.
     */
    public void test1208() throws Exception {
        WsaUtils.invokeOneWay12(BindingProviderUtil.createDispatchWithWSDLWithoutAddressing(),
                              MESSAGES.getReplyToExtensionsMessage(),
                              WsaUtils.S12_NS,
                              BindingProviderUtil.getAddress(),
                              NOTIFY_ACTION,
                              "test1208");
    }
}
