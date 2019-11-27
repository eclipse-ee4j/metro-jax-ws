/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.parser;

import javax.xml.ws.WebServiceException;


/**
 * listen to static errors found during building a the WSDL Model.
 *
 * @author Vivek Pandey
 */
public interface ErrorHandler {
    /**
     * Receives a notification for an error in the annotated code.
     */
    void error( Throwable e );
}
