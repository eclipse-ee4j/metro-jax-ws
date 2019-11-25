/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.server;

import javax.xml.ws.Endpoint;

public class AddWebservice {
    
    public static void main (String[] args) throws Exception {
        Endpoint endpoint = Endpoint.publish (
            "http://localhost:8080/jaxws-fromjava/addnumbers",
            new AddNumbersImpl ());

        // Stops the endpoint if it receives request http://localhost:9090/stop
        new EndpointStopper(9090, endpoint);
    }
}
