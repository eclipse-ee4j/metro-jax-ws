/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.parser;


import com.sun.xml.ws.streaming.Attributes;
import com.sun.xml.ws.streaming.XMLReaderException;
import com.sun.xml.ws.util.xml.XmlUtil;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;


/**
 *
 * TODO: made public just for now
 * @author WS Development Team
 */
public class ParserUtil {
    public static String getAttribute(XMLStreamReader reader, String name) {
        return reader.getAttributeValue(null, name);
    }

    public static String getAttribute(XMLStreamReader reader, String nsUri, String name) {
        return reader.getAttributeValue(nsUri, name);
    }

    public static String getAttribute(XMLStreamReader reader, QName name) {
        return reader.getAttributeValue(name.getNamespaceURI(), name.getLocalPart());
    }

    public static QName getQName(XMLStreamReader reader, String tag){
        String localName = XmlUtil.getLocalPart(tag);
        String pfix = XmlUtil.getPrefix(tag);
        String uri = reader.getNamespaceURI(fixNull(pfix));
        return new QName(uri, localName);
    }

    public static String getMandatoryNonEmptyAttribute(XMLStreamReader reader,
        String name) {
//        String value = getAttribute(reader, name);
        String value = reader.getAttributeValue(null, name);

        if (value == null) {
            failWithLocalName("client.missing.attribute", reader, name);
        } else if (value.equals("")) {
            failWithLocalName("client.invalidAttributeValue", reader, name);
        }

        return value;
    }

    public static void failWithFullName(String key, XMLStreamReader reader) {
//        throw new WebServicesClientException(key,
//        new Object[]{
//          Integer.toString(reader.getLineNumber()),
//          reader.getName().toString()});
    }

    public static void failWithLocalName(String key, XMLStreamReader reader) {
        //throw new WebServicesClientException(key,
        //        new Object[]{
        //           Integer.toString(reader.getLineNumber()),
        //          reader.getLocalName()});
    }

    public static void failWithLocalName(String key, XMLStreamReader reader,
        String arg) {
        //throw new WebServicesClientException(key,
        //      new Object[]{
        //          Integer.toString(reader.getLineNumber()),
        //          reader.getLocalName(),
        //          arg});
    }

    private static @NotNull String fixNull(@Nullable String s) {
        if (s == null) return "";
        else return s;
    }
}
