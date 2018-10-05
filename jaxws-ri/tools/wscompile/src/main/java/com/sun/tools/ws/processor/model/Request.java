/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model;

import com.sun.tools.ws.wsdl.framework.Entity;
import com.sun.tools.ws.wscompile.ErrorReceiver;

/**
 *
 * @author WS Development Team
 */
public class Request extends Message {

    public Request(com.sun.tools.ws.wsdl.document.Message entity, ErrorReceiver receiver) {
        super(entity, receiver);
    }

    public void accept(ModelVisitor visitor) throws Exception {
        visitor.visit(this);
    }
}
