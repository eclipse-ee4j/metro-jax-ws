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

import javax.jws.WebService;
import javax.jws.WebParam;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceException;

/**
 * @author Vivek Pandey
 */
@WebService(endpointInterface = "fromwsdl.jaxws102.server.BenchMarkSubBPPortType")
public class BenchMarkSubBPEndpoint {
    public void benchMarkSubBPOperation1(Holder<BenchMarkType> benchMarkPart){
        if (!benchMarkPart.value.getString().equals("BPType1")) {
            throw new WebServiceException("Invalid type, expected 'BPType1', received: " + benchMarkPart.value.getString());
        }
        benchMarkPart.value.setString("BPType2");
    }
}
