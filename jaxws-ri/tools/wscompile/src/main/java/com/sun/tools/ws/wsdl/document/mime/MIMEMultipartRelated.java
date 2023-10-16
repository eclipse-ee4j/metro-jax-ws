/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document.mime;

import com.sun.tools.ws.wsdl.framework.EntityAction;
import com.sun.tools.ws.wsdl.framework.ExtensionImpl;
import com.sun.tools.ws.wsdl.framework.ExtensionVisitor;
import org.xml.sax.Locator;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A MIME multipartRelated extension.
 *
 * @author WS Development Team
 */
public class MIMEMultipartRelated extends ExtensionImpl {

    public MIMEMultipartRelated(Locator locator) {
        super(locator);
        _parts = new ArrayList<>();
    }

    @Override
    public QName getElementName() {
        return MIMEConstants.QNAME_MULTIPART_RELATED;
    }

    public void add(MIMEPart part) {
        _parts.add(part);
    }

    public Iterable<MIMEPart> getParts() {
        return _parts;
    }

    @Override
    public void withAllSubEntitiesDo(EntityAction action) {
        super.withAllSubEntitiesDo(action);

        for (MIMEPart part : _parts) {
            action.perform(part);
        }
    }

    @Override
    public void accept(ExtensionVisitor visitor) throws Exception {
        visitor.preVisit(this);
        visitor.postVisit(this);
    }

    @Override
    public void validateThis() {
    }

    private List<MIMEPart> _parts;
}
