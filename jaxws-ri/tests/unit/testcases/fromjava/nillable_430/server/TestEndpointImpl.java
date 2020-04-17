/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nillable_430.server;

import jakarta.xml.ws.WebServiceException;
import java.util.Date;

/**
 * @author Jitendra Kotamraju
 */
@jakarta.jws.WebService

public class TestEndpointImpl {

    public String test(Integer intVal, String strVal, Long longVal, Date dateVal) {
        if (intVal != null) {
            throw new WebServiceException("intVal Expected: null Got:"+intVal);
        }
        if (strVal != null) {
            throw new WebServiceException("strVal Expected: null Got:"+strVal);
        }
        if (longVal != null) {
            throw new WebServiceException("longVal Expected: null Got:"+longVal);
        }
        if (dateVal != null) {
            throw new WebServiceException("dateVal Expected: null Got:"+dateVal);
        }
        return null;
    }

}
