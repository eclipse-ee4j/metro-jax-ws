/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.document;

import com.sun.tools.ws.api.wsdl.TWSDLExtensible;
import com.sun.tools.ws.api.wsdl.TWSDLExtension;
import com.sun.tools.ws.wsdl.framework.*;
import com.sun.tools.ws.wscompile.ErrorReceiver;
import org.xml.sax.Locator;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * Entity corresponding to the "portType" WSDL element.
 *
 * @author WS Development Team
 */
@SuppressWarnings({"deprecation"})
public class PortType extends GlobalEntity implements TWSDLExtensible {

    public PortType(Defining defining, Locator locator, ErrorReceiver errReceiver) {
        super(defining, locator, errReceiver);
        _operations = new ArrayList<>();
        _operationKeys = new HashSet<>();
        _helper = new ExtensibilityHelper();
    }

    public void add(Operation operation) {
        String key = operation.getUniqueKey();
        if (_operationKeys.contains(key))
            throw new ValidationException(
                "validation.ambiguousName",
                operation.getName());
        _operationKeys.add(key);
        _operations.add(operation);
    }

    public Iterator<Operation> operations() {
        return _operations.iterator();
    }

    public Set<Operation> getOperationsNamed(String s) {
        Set<Operation> result = new HashSet<>();
        for (Operation operation : _operations) {
            if (operation.getName().equals(s)) {
                result.add(operation);
            }
        }
        return result;
    }

    @Override
    public Kind getKind() {
        return Kinds.PORT_TYPE;
    }

    @Override
    public QName getElementName() {
        return WSDLConstants.QNAME_PORT_TYPE;
    }

    public Documentation getDocumentation() {
        return _documentation;
    }

    public void setDocumentation(Documentation d) {
        _documentation = d;
    }

    @Override
    public void withAllSubEntitiesDo(EntityAction action) {
        super.withAllSubEntitiesDo(action);

        for (Operation operation : _operations) {
            action.perform(operation);
        }
        _helper.withAllSubEntitiesDo(action);
    }

    public void accept(WSDLDocumentVisitor visitor) throws Exception {
        visitor.preVisit(this);
        _helper.accept(visitor);
        for (Operation operation : _operations) {
            operation.accept(visitor);
        }
        visitor.postVisit(this);
    }

    @Override
    public void validateThis() {
        if (getName() == null) {
            failValidation("validation.missingRequiredAttribute", "name");
        }
    }

    @Override
    public String getNameValue() {
        return getName();
    }

    @Override
    public String getNamespaceURI() {
        return getDefining().getTargetNamespaceURI();
    }

    @Override
    public QName getWSDLElementName() {
        return getElementName();
    }

    /* (non-Javadoc)
    * @see TWSDLExtensible#addExtension(ExtensionImpl)
    */
    @Override
    public void addExtension(TWSDLExtension e) {
        _helper.addExtension(e);

    }

    /* (non-Javadoc)
     * @see TWSDLExtensible#extensions()
     */
    @Override
    public Iterable<TWSDLExtension> extensions() {
        return _helper.extensions();
    }

    @Override
    public TWSDLExtensible getParent() {
        return parent;
    }

    public void setParent(TWSDLExtensible parent) {
        this.parent = parent;
    }

    private TWSDLExtensible parent;
    private Documentation _documentation;
    private List<Operation> _operations;
    private Set<String> _operationKeys;
    private ExtensibilityHelper _helper;
}
