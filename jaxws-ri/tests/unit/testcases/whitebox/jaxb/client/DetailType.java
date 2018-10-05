/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.jaxb.client;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * &lt;env:Detail>
 *     &lt;m:MaxTime>P5M</m:MaxTime>
 * &lt;/env:Detail>
 */
@XmlRootElement(name = "detail",namespace = "")
class DetailType {
    /**
     * The detail entry could be 0 or more elements. Perhaps some elements may be
     * known to JAXB while others can be handled using DOMHandler.
     *
     * Even though the jaxbContext is aware of the detail jaxbBean but we get the list of
     * {@link org.w3c.dom.Node}s.
     *
     * this is because since we unmarshall using {@link com.sun.xml.bind.api.Bridge} all we're
     * going to get during unmarshalling is {@link org.w3c.dom.Node} and not the jaxb bean instances.
     *
     * TODO: For now detailEntry would be List of Node isntead of Object and it needs to be changed to
     * {@link Object} once we have better solution that working thru {@link com.sun.xml.bind.api.Bridge}
     */
    @XmlAnyElement
    private final List<Element> detailEntry = new ArrayList<Element>();

    @NotNull
    List<Element> getDetails() {
        return detailEntry;
    }

    /**
     * Gets the n-th detail object, or null if no such item exists.
     */
    @Nullable
    Node getDetail(int n) {
        if(n < detailEntry.size())
            return detailEntry.get(n);
        else
            return null;
    }

    DetailType(Element detailObject) {
        if(detailObject != null)
            detailEntry.add(detailObject);
    }

    DetailType() {
    }
}
