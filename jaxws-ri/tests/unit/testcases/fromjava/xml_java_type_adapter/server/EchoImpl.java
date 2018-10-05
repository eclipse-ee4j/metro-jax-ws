/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.xml_java_type_adapter.server;

import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.ws.WebServiceException;

@WebService(name="Echo", serviceName="echoService", targetNamespace="http://echo.org/")
public class EchoImpl {

    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    public byte[] echo(String str,
                       @XmlJavaTypeAdapter(HexBinaryAdapter.class)
                       byte[] bin) {
        if (!str.equals("binary")) {
            throw new WebServiceException("Expecting=binary, but got="+str);
        }
        if (bin == null || bin.length != 4 || bin[0] != (byte)0xCA
                || bin[1] != (byte)0xFE || bin[2] != (byte)0xBA
                || bin[3] != (byte)0xBE) {
            throw new WebServiceException("Didn't receive CAFEBABE.");
        }
        return bin;
    }
    
}
