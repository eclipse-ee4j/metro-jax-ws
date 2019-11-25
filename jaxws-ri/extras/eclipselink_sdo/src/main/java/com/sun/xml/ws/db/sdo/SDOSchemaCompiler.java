/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.db.sdo;

import org.eclipse.persistence.sdo.SDOType;
import org.eclipse.persistence.sdo.helper.SDOHelperContext;
import org.eclipse.persistence.sdo.helper.SDOXSDHelper;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
//import javax.xml.transform.stax.StAXSource;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for generating sdo java types from a set of given schemas.
 * Schemas can be passed in using SAX events, DOM or a set of closure source.
 * Created by IntelliJ IDEA.
 * User: giglee
 * Date: May 14, 2009
 * Time: 9:47:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class SDOSchemaCompiler {

    private List<Source> schemas = null;
    private EntityResolver entityResolver = null;

    public SDOSchemaCompiler() {
        resetSchema();
    }

    /*public ContentHandler getParserHandler(String systemId) {
        Document doc = SDOUtils.newDocument();
        SAX2DOMContentHandler handler = new SAX2DOMContentHandler(doc);
        DOMSource src = new DOMSource(doc, systemId);
        schemas.add(src);
        return handler;
    }*/

    public void parseSchema(Source schema) {
        schemas.add(schema);
    }

    public void parseSchema(String systemId, Element element) {
        Source src = new DOMSource(element);
        src.setSystemId(systemId);
        schemas.add(src);
    }

    public void parseSchema(InputSource source) {
        schemas.add(new SAXSource(source));
    }

    public void parseSchemas(List<Source> inputSchemas) {
        this.schemas = inputSchemas;
    }

    public void parseSchema(String systemId, XMLStreamReader xmlStreamReader) throws XMLStreamException {
        //StAXSource src = new StAXSource(xmlStreamReader);
        SAXSource src = new com.sun.xml.ws.util.xml.StAXSource(xmlStreamReader, false);
        src.setSystemId(systemId);
        schemas.add(src);
    }


    public void setEntityResolver(EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    }

    public void forcePackageName(String packageName) {
        throw new SDODatabindingException("package change name not supported");
    }

    public void resetSchema() {
        if (schemas == null) {
            schemas = new ArrayList<Source>();
        } else {
            schemas.clear();
        }
    }

    public Xsd2JavaSDOModel bind() {
        SDOHelperContext hc = new SDOHelperContext();
        List<SDOType> types = new ArrayList<SDOType>();
        SDODatabindingSchemaResolver schemaResolver = new SDODatabindingSchemaResolver(schemas, entityResolver);
        for (Source schema : schemas) {
            List<SDOType> list = ((SDOXSDHelper) hc.getXSDHelper()).define(schema, schemaResolver);
            types.addAll(list);
        }
        return new Xsd2JavaSDOModel(hc, types);
    }

}
