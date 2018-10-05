/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.enable_mtom.config;

import javax.xml.ws.soap.MTOM;
import javax.jws.WebService;

/**
 * @author Rama Pulavarthi
 */
@MTOM(enabled=false)
@WebService
public class WebServiceImpl2 {
    public String echoString(String str) {
        return str;
    }
}
