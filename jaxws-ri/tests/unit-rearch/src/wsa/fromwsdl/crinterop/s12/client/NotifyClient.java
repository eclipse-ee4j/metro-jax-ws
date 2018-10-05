/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromwsdl.crinterop.s12.client;

import junit.framework.TestCase;
import static testutil.WsaUtils.S12_NS;
import static testutil.WsaUtils.invokeOneWay12;
import static wsa.fromwsdl.crinterop.s12.client.BindingProviderUtil.createDispatchWithWSDLWithoutAddressing;
import static wsa.fromwsdl.crinterop.s12.client.BindingProviderUtil.createStub;
import static wsa.fromwsdl.crinterop.s12.client.BindingProviderUtil.getAddress;
import static wsa.fromwsdl.crinterop.s12.common.TestConstants.MESSAGES;
import static wsa.fromwsdl.crinterop.s12.common.TestConstants.NOTIFY_ACTION;

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
        createStub().notify("test1200");
    }

    /**
     * SOAP 1.1 one-way defaulted with a MessageID value.
     */
    public void test1201() throws Exception {
        createStub().notify("test1201");
    }

    /**
     * SOAP 1.1 one-way message with a ReplyTo address of none.
     */
    public void test1202() throws Exception {
        invokeOneWay12(createDispatchWithWSDLWithoutAddressing(),
                       MESSAGES.getNoneReplyToMessage(),
                       S12_NS,
                       getAddress(),
                       NOTIFY_ACTION,
                       "test1202");
    }

    /**
     * SOAP 1.1 one-way message with a FaultTo address of none.
     */
    public void test1203() throws Exception {
        invokeOneWay12(createDispatchWithWSDLWithoutAddressing(),
                       MESSAGES.getNoneFaultToMessage(),
                       S12_NS,
                       getAddress(),
                       NOTIFY_ACTION,
                       "test1203");
    }

    /**
     * SOAP 1.1 one-way message with a ReplyTo and FaultTo address of none.
     */
    public void test1204() throws Exception {
        invokeOneWay12(createDispatchWithWSDLWithoutAddressing(),
                       MESSAGES.getNoneReplyToFaultToMessage(),
                       S12_NS,
                       getAddress(),
                       NOTIFY_ACTION,
                       "test1204");
    }

    /**
     * SOAP 1.1 one-way message with ReplyTo containing a Reference Parameter.
     */
    public void test1206() throws Exception {
        invokeOneWay12(createDispatchWithWSDLWithoutAddressing(),
                       MESSAGES.getReplyToRefpsNotifyMessage(),
                       S12_NS,
                       getAddress(),
                       NOTIFY_ACTION,
                       "test1206");
    }

    /**
     * SOAP 1.1 one-way message with ReplyTo containing WSDL Metadata.
     */
    public void test1207() throws Exception {
        invokeOneWay12(createDispatchWithWSDLWithoutAddressing(),
                       MESSAGES.getReplyToMetadataMessage(),
                       S12_NS,
                       getAddress(),
                       NOTIFY_ACTION,
                       "test1207");
    }

    /**
     * SOAP 1.1 one-way message with ReplyTo containing EPR extensions.
     */
    public void test1208() throws Exception {
        invokeOneWay12(createDispatchWithWSDLWithoutAddressing(),
                       MESSAGES.getReplyToExtensionsMessage(),
                       S12_NS,
                       getAddress(),
                       NOTIFY_ACTION,
                       "test1208");
    }
}
