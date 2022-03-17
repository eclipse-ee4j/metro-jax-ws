/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model;

import com.sun.tools.ws.wscompile.ErrorReceiver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author WS Development Team
 */
public class Response extends Message {

    public Response(com.sun.tools.ws.wsdl.document.Message entity, ErrorReceiver receiver) {
        super(entity, receiver);
    }

    public void addFaultBlock(Block b) {
        if (_faultBlocks.containsKey(b.getName())) {
            throw new ModelException("model.uniqueness");
        }
        _faultBlocks.put(b.getName(), b);
    }

    public Iterator getFaultBlocks() {
        return _faultBlocks.values().iterator();
    }

    public int getFaultBlockCount () {
        return _faultBlocks.size();
    }

    /* serialization */
    public Map getFaultBlocksMap() {
        return _faultBlocks;
    }

    public void setFaultBlocksMap(Map m) {
        _faultBlocks = m;
    }

    @Override
    public void accept(ModelVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    private Map _faultBlocks = new HashMap();
}
