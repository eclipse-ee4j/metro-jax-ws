/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package externalmetadata.fromjava.server;

/**
 * Simple service to test external definition of metadata (instead of
 * annotations there are xml descriptors);
 * This would be typically some implementation where we can't change/add java annotations
 * - for example we have no source code for this class
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class BlackboxService {

    public void doSomething() {
        // doesn't need to do anything ...
        System.out.println("method [externalmetadata.fromjava.server.BlackboxServiceImpl.doSomething] invoked. Did something ...");
    }

}
