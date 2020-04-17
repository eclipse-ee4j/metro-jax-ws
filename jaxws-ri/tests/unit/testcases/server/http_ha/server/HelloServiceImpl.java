/*
 * Copyright (c) 2005, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.http_ha.server;

import java.util.*;

import jakarta.annotation.Resource;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceContext;

import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.ha.HaInfo;

/**
 * Tests if Packet.HA_INFO is populated or not
 *
 * @author Jitendra Kotamraju
 */
@WebService(name="Hello", serviceName="HelloService", targetNamespace="urn:test")
public class HelloServiceImpl {
    @Resource
    private WebServiceContext wsc;

    public void testHa() {
        MessageContext ctxt = wsc.getMessageContext();
        HaInfo haInfo = (HaInfo)ctxt.get(Packet.HA_INFO);
        if (haInfo == null) {
            throw new WebServiceException("Packet.HA_INFO is not populated.");
        }
        String replica = (String)haInfo.getReplicaInstance();
        if (replica == null || !replica.equals("replica1")) {
            throw new WebServiceException("Expecting getReplicaInstance()=replica1 but got="+replica);
        }
        String key = (String)haInfo.getKey();
        if (key == null || !key.equals("key1")) {
            throw new WebServiceException("Expecting getKey()=key1 but got="+key);
        }
        Boolean failOver = (Boolean)haInfo.isFailOver();
        if (failOver == null || !failOver) {
            throw new WebServiceException("Expecting isFailOver()=true but got="+failOver);
        }
    }
}
