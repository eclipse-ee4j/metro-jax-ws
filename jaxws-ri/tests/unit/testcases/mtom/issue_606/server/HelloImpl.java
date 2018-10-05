/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package mtom.issue_606.server;

import javax.xml.ws.WebServiceException;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

/**
 * @author Jitendra Kotamraju
 */
@MTOM
@WebService
public class HelloImpl {

    public byte[] echo(String doc, byte[] buf) {
        if (buf == null) {
            throw new WebServiceException("Received buf is null");
        }
        return buf;
    }

}
