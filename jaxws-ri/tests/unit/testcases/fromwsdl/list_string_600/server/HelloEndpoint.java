/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.list_string_600.server;

import jakarta.jws.WebService;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Jitendra Kotamraju
 */
@WebService(endpointInterface = "fromwsdl.list_string_600.server.HelloServicePortType")
public class HelloEndpoint {
    public List<String> getAllGuestNames() {
        List<String> list = new ArrayList<String>();
        list.add("sun");
        return list;
    }
}
