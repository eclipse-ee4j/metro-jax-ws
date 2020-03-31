/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.w3c.fromwsdl.crinterop_s11.client;
import static wsa.w3c.fromwsdl.crinterop_s11.client.TestConstants.*;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.SOAPException;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.io.IOException;


import jakarta.xml.ws.*;

/**
 * This class handles the non-anonymous responses
 * @author Rama Pulavarthi
 */
@WebServiceProvider
@ServiceMode(value = Service.Mode.MESSAGE)
public class NonAnonymousRespProcessor implements Provider<SOAPMessage> {
    Exchanger<SOAPMessage> msgExchanger;
    public NonAnonymousRespProcessor( Exchanger<SOAPMessage> msgExchanger) {
        this.msgExchanger = msgExchanger;
    }

    public SOAPMessage invoke(SOAPMessage request) {
        /*
        System.out.printf("====%s[start:%tc]====\n", getClass().getName(), Calendar.getInstance());
        try {
            request.writeTo(System.out);
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("====%s[end:%tc]====\n", getClass().getName(), Calendar.getInstance());
        */
        try {
            msgExchanger.exchange(request, PROVIDER_MAX_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (TimeoutException e) {
            e.printStackTrace();
        }

        return null;
    }  
}
