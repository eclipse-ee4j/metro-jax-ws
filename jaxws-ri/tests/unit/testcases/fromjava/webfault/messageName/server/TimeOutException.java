/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.webfault.messageName.server;

import jakarta.xml.ws.WebFault;

/**
 * @author Rama Pulavarthi
 */
@WebFault(messageName="TimedOutException")
public class TimeOutException extends Exception {
    int timeInSeconds;
    public TimeOutException(String message,int timeInSeconds) {
        super(message);
        this.timeInSeconds = timeInSeconds;

    }
    public TimeOutFault getFaultInfo() {
        TimeOutFault f = new TimeOutFault();
        f.message = getMessage();
        f.timeinSeconds = timeInSeconds; 
        return f;
    }
}
