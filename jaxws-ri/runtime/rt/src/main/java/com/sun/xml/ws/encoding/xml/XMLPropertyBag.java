/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding.xml;

import com.oracle.webservices.api.message.BasePropertySet;

public class XMLPropertyBag extends BasePropertySet {

    private String contentType;
    @Override
    protected PropertyMap getPropertyMap() {
        return model;
    }

    @Property(XMLConstants.OUTPUT_XML_CHARACTER_ENCODING)
    public String getXMLContentType(){
        return contentType;
    }
    
    public void setXMLContentType(String content){
        contentType = content;
    }
    
    private static final PropertyMap model;

    static {
        model = parse(XMLPropertyBag.class);
    }

}
