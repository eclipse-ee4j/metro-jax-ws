/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package xop.hellodirect.server;

import javax.xml.ws.WebServiceException;
import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.xml.ws.Holder;
import java.awt.*;

@WebService(endpointInterface = "xop.hellodirect.server.Hello")
public class HelloImpl {

    public void detail(Holder<byte[]> photo, Holder<Image> image) {
        if (image.value == null) {
            throw new WebServiceException("Received Image is null");
        }
    }

    public DataHandler claimForm(DataHandler data) {
        return data;
    }

    public void echoData(Holder<byte[]> data) {
        if (data.value == null) {
            throw new WebServiceException("Received Image is null");
        }
    }
}
