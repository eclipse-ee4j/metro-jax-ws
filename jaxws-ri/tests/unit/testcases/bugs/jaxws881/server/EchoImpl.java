/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws881.server;

import jakarta.jws.WebService;

/**
 * TODO: Write some description here ...
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
@WebService
public class EchoImpl {

    public String echo(String msg) {
        return msg;
    }

}
