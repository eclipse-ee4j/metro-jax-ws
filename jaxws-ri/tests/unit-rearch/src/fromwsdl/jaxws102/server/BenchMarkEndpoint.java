/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.jaxws102.server;

import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceException;

/**
 * @author Vivek Pandey
 */
@WebService(endpointInterface = "fromwsdl.jaxws102.server.BenchMarkPortType")
public class BenchMarkEndpoint {
    public void benchMarkOperation1(Holder<BenchMarkType> benchMarkPart) {
        if (!benchMarkPart.value.getString().equals("Type1")) {
            throw new WebServiceException("Invalid type, expected 'Type1', received: " + benchMarkPart.value.getString());
        }
        benchMarkPart.value.setString("Type2");
    }

    public void benchMarkOperation2(BenchMarkType benchMarkPart) {
        if(!benchMarkPart.getString().equals("Type2")){
            throw new WebServiceException("Invalid type, expected 'Type2', received: " + benchMarkPart.getString());
        }
    }

}
