/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.submission.fromwsdl.crinterop.s11.server;

import java.util.Calendar;
import java.io.IOException;

import jakarta.xml.ws.WebServiceProvider;
import jakarta.xml.ws.ServiceMode;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Provider;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPException;

/**
 * @author Arun Gupta
 */
@WebServiceProvider
@ServiceMode(value = Service.Mode.MESSAGE)
public class NonAnonymousProvider implements Provider<SOAPMessage> {
    public SOAPMessage invoke(SOAPMessage request) {
        System.out.printf("====%s[start:%tc]====\n", getClass().getName(), Calendar.getInstance());
        try {
            request.writeTo(System.out);
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("====%s[end:%tc]====\n", getClass().getName(), Calendar.getInstance());
        return null;
    }
}
