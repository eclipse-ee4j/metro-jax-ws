/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.schema_inline.server;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;

/**
 * @author Jitendra Kotamraju
 */
@XmlType(name = "FooException", namespace = "urn:test:types")
public class FooException {

    @XmlElement(name = "varInt", namespace = "", type = Integer.class)
    protected int varInt;

    @XmlTransient
    public int getVarInt() {
        return varInt;
    }

    public void setVarInt(int value) {
        this.varInt = value;
    }

}
