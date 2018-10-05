/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document;

import com.sun.tools.ws.wsdl.framework.*;
import com.sun.tools.ws.wscompile.ErrorReceiver;
import com.sun.tools.ws.wscompile.AbortException;
import com.sun.tools.ws.resources.WsdlMessages;
import org.xml.sax.Locator;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * Entity corresponding to the "message" WSDL element.
 *
 * @author WS Development Team
 */
public class Message extends GlobalEntity {

    public Message(Defining defining, Locator locator, ErrorReceiver errReceiver) {
        super(defining, locator, errReceiver);
        _parts = new ArrayList<MessagePart>();
        _partsByName = new HashMap<String, MessagePart>();
    }

    public void add(MessagePart part) {
        if (_partsByName.get(part.getName()) != null){
            errorReceiver.error(part.getLocator(), WsdlMessages.VALIDATION_DUPLICATE_PART_NAME(getName(), part.getName()));
            throw new AbortException();
        }
        
        if(part.getDescriptor() != null && part.getDescriptorKind() != null) {
            _partsByName.put(part.getName(), part);
            _parts.add(part);
        } else
            errorReceiver.warning(part.getLocator(), WsdlMessages.PARSING_ELEMENT_OR_TYPE_REQUIRED(part.getName()));
    }

    public Iterator<MessagePart> parts() {
        return _parts.iterator();
    }

    public List<MessagePart> getParts(){
        return _parts;
    }

    public MessagePart getPart(String name) {
        return _partsByName.get(name);
    }

    public int numParts() {
        return _parts.size();
    }

    public Kind getKind() {
        return Kinds.MESSAGE;
    }

    public QName getElementName() {
        return WSDLConstants.QNAME_MESSAGE;
    }

    public Documentation getDocumentation() {
        return _documentation;
    }

    public void setDocumentation(Documentation d) {
        _documentation = d;
    }

    public void withAllSubEntitiesDo(EntityAction action) {
        super.withAllSubEntitiesDo(action);

        for (Iterator iter = _parts.iterator(); iter.hasNext();) {
            action.perform((Entity) iter.next());
        }
    }

    public void accept(WSDLDocumentVisitor visitor) throws Exception {
        visitor.preVisit(this);
        for (Iterator<MessagePart> iter = _parts.iterator(); iter.hasNext();) {
            iter.next().accept(visitor);
        }
        visitor.postVisit(this);
    }

    public void validateThis() {
        if (getName() == null) {
            errorReceiver.error(getLocator(), WsdlMessages.VALIDATION_MISSING_REQUIRED_ATTRIBUTE("name", "wsdl:message"));
            throw new AbortException();
        }
    }

    private Documentation _documentation;
    private List<MessagePart> _parts;
    private Map<String, MessagePart> _partsByName;
}
