/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws236.server;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceException;


@WebService
public class TestEndpoint
{
    @WebMethod()
    public String[] testStringArray( String[] s )
    {
        if(!s[0].equals("one") || !s[1].equals("") || (s[2] != null))
            throw new WebServiceException("One of the value was incorrect!");
        return new String[] {"two", "", null};
    }
}
