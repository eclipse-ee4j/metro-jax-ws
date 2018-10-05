/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws6492398.server;

import javax.jws.WebService;

/**
 * @author Vivek Pandey
 */
@WebService(endpointInterface = "bugs.jaxws6492398.server.DateTime")
public class DateTimeServiceImpl {
    public GYearMonthTestResponse gYearMonthTest(GYearMonthTest parameters) {
        GYearMonthTestResponse resp = new GYearMonthTestResponse();
        resp.setResult(parameters.getValue());
        return resp;
    }
}
