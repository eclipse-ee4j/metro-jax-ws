/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testutil;

import java.net.ServerSocket;
import java.io.IOException;

/**
 * Finds the free socket port so that an endpoint can be published without
 * any conflicts
 *
 * @author Jitendra Kotamraju
 */
public class PortAllocator {

    public static int getFreePort() {
        ServerSocket soc = null;
        try {
            soc = new ServerSocket(0);
            soc.setReuseAddress(true);
            return soc.getLocalPort();
        } catch(IOException ioe) {
            throw new RuntimeException(ioe);
        } finally {
            if (soc != null) {
                try {
                    soc.close();
                } catch(IOException ioe) {
                    // cannot do much
                }
            }
        }
    }

}
