/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.endpoint_features.client;

import java.net.ServerSocket;
import java.io.IOException;

/**
 * @author Jitendra Kotamraju
 */
public class Util {

    public static int getFreePort() {
        int port = -1;
        try {
               ServerSocket soc = new ServerSocket(0);
            port = soc.getLocalPort();
               soc.close();
        } catch (IOException e) {
        }
        return port;
    }

}
