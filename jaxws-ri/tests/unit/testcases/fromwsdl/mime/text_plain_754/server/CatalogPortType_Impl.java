/*
 * Copyright (c) 2004, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.mime.text_plain_754.server;

import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceException;


/**
 * Test case for issue: 754 - tests text/plain in mime binding
 *
 * @author Jitendra Kotamraju
 */
@WebService(endpointInterface = "fromwsdl.mime.text_plain_754.server.CatalogPortType")
public class CatalogPortType_Impl {

    public void echoString(String input, String attInput, Holder<String> output, Holder<String> att) {
        if (!input.equals("input")) {
            throw new WebServiceException("Expected input=input, got="+input);
        }
        if (!attInput.equals("attInput")) {
            throw new WebServiceException("Expected attInput=attInput, got="+attInput);
        }
        output.value = "output";
        att.value = "att";
    }

}
