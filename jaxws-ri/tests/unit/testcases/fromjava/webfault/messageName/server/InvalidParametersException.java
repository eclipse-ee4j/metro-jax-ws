/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.webfault.messageName.server;

import javax.xml.ws.WebFault;

/**
 * @author Rama Pulavarthi
 */
@WebFault(messageName = "InvalidInputException")
public class InvalidParametersException extends Exception {
    String message;
    String operation;
    public InvalidParametersException(String operation, String message ) {
        this.message = message;
        this.operation = operation;
    }

    public String getMessage() {
        return message;
    }

    public String getOperation() {
        return operation;
    }

}
