/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.fault;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * &lt;env:Reason>
 *     &lt;env:Text xml:lang="en">Sender Timeout</env:Text>
 * &lt;/env:Reason>
 * </pre>
 */
class ReasonType {
    ReasonType() {
    }

    ReasonType(String txt) {
        text.add(new TextType(txt));
    }



    /**
     * minOccurs=1 maxOccurs=unbounded
     */
    @XmlElements(@XmlElement(name = "Text", namespace = "http://www.w3.org/2003/05/soap-envelope", type = TextType.class))
    private final List<TextType> text = new ArrayList<TextType>();

    List<TextType> texts() {
        return text;
    }
}
