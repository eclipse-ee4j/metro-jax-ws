/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.wrapper_xmltype_601.server;

import javax.jws.WebParam;
import javax.xml.ws.Holder;
import java.util.List;
import java.math.BigInteger;

/**
 * xml type is used for deciding whether a wrapper parameter is INOUT
 *
 * @author Jitendra Kotamraju
 */
@javax.jws.WebService(endpointInterface = "fromwsdl.wrapper_xmltype_601.server.Hello")
public class TestEndpointImpl implements Hello {
    public int testApl(BigInteger inParam1, Holder<BigInteger> outParam1) {
        outParam1.value = inParam1;
        return 10;
    }
}
