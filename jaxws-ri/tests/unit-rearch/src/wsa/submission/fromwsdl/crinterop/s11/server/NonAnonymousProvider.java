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

import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.Service;
import javax.xml.ws.Provider;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPException;

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
