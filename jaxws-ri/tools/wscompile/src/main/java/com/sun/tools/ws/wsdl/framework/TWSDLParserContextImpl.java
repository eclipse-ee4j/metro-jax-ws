/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wsdl.framework;

import com.sun.tools.ws.api.wsdl.TWSDLParserContext;
import com.sun.tools.ws.wsdl.parser.DOMForest;
import com.sun.tools.ws.wscompile.ErrorReceiver;
import com.sun.tools.ws.resources.WsdlMessages;
import com.sun.xml.ws.util.NamespaceSupport;
import com.sun.xml.ws.util.xml.XmlUtil;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.xml.sax.Locator;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The context used by parser classes.
 *
 * @author WS Development Team
 */
@SuppressWarnings({"deprecation"})
public class TWSDLParserContextImpl implements TWSDLParserContext {

    private final static String PREFIX_XMLNS = "xmlns";
    private boolean _followImports;
    private final AbstractDocument _document;
    private final NamespaceSupport _nsSupport;
    private final ArrayList<ParserListener> _listeners;
    private final WSDLLocation _wsdlLocation;
    private final DOMForest forest;
    private final ErrorReceiver errorReceiver;

    public TWSDLParserContextImpl(DOMForest forest, AbstractDocument doc, ArrayList<ParserListener> listeners, ErrorReceiver errReceiver) {
        this._document = doc;
        this._listeners = listeners;
        this._nsSupport = new NamespaceSupport();
        this._wsdlLocation = new WSDLLocation();
        this.forest = forest;
        this.errorReceiver = errReceiver;
    }

    public AbstractDocument getDocument() {
        return _document;
    }

    public boolean getFollowImports() {
        return _followImports;
    }

    public void setFollowImports(boolean b) {
        _followImports = b;
    }

    @Override
    public void push() {
        _nsSupport.pushContext();
    }

    @Override
    public void pop() {
        _nsSupport.popContext();
    }

    @Override
    public String getNamespaceURI(String prefix) {
        return _nsSupport.getURI(prefix);
    }

    @Override
    public Iterable<String> getPrefixes() {
        return _nsSupport.getPrefixes();
    }

    @Override
    public String getDefaultNamespaceURI() {
        return getNamespaceURI("");
    }

    @Override
    public void registerNamespaces(Element e) {
        for (Iterator iter = XmlUtil.getAllAttributes(e); iter.hasNext();) {
            Attr a = (Attr) iter.next();
            if (a.getName().equals(PREFIX_XMLNS)) {
                // default namespace declaration
                _nsSupport.declarePrefix("", a.getValue());
            } else {
                String prefix = XmlUtil.getPrefix(a.getName());
                if (prefix != null && prefix.equals(PREFIX_XMLNS)) {
                    String nsPrefix = XmlUtil.getLocalPart(a.getName());
                    String uri = a.getValue();
                    _nsSupport.declarePrefix(nsPrefix, uri);
                }
            }
        }
    }

    @Override
    public Locator getLocation(Element e) {
        return forest.locatorTable.getStartLocation(e);
    }

    public QName translateQualifiedName(Locator locator, String s) {
        if (s == null)
            return null;

        String prefix = XmlUtil.getPrefix(s);
        String uri = null;

        if (prefix == null) {
            uri = getDefaultNamespaceURI();
        } else {
            uri = getNamespaceURI(prefix);
            if (uri == null) {
                errorReceiver.error(locator, WsdlMessages.PARSING_UNKNOWN_NAMESPACE_PREFIX(prefix));
            }
        }

        return new QName(uri, XmlUtil.getLocalPart(s));
    }

    public void fireIgnoringExtension(Element e, Entity entity) {
        QName name = new QName(e.getNamespaceURI(), e.getLocalName());
        QName parent = entity.getElementName();
        List _targets = null;

        synchronized (this) {
            if (_listeners != null) {
                _targets = (List) _listeners.clone();
            }
        }

        if (_targets != null) {
            for (Iterator iter = _targets.iterator(); iter.hasNext();) {
                ParserListener l = (ParserListener) iter.next();
                l.ignoringExtension(entity, name, parent);
            }
        }
    }

    public void fireDoneParsingEntity(QName element, Entity entity) {
        List _targets = null;

        synchronized (this) {
            if (_listeners != null) {
                _targets = (List) _listeners.clone();
            }
        }

        if (_targets != null) {
            for (Iterator iter = _targets.iterator(); iter.hasNext();) {
                ParserListener l = (ParserListener) iter.next();
                l.doneParsingEntity(element, entity);
            }
        }
    }

    //bug fix: 4856674, WSDLLocation context maintainence
    //and utility funcitons
    public void pushWSDLLocation() {
        _wsdlLocation.push();
    }

    public void popWSDLLocation() {
        _wsdlLocation.pop();
    }

    public void setWSDLLocation(String loc) {
        _wsdlLocation.setLocation(loc);
    }

    public String getWSDLLocation() {
        return _wsdlLocation.getLocation();
    }
}
