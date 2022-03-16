/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.model;

/**
 *
 * @author WS Development Team
 */
public interface ModelProperties {

    //to set WSDL_MODELER_NAME from inside WSDLModeler
    String WSDL_MODELER_NAME =
        "com.sun.xml.ws.processor.modeler.wsdl.WSDLModeler";
    String PROPERTY_PARAM_MESSAGE_PART_NAME =
        "com.sun.xml.ws.processor.model.ParamMessagePartName";
    String PROPERTY_ANONYMOUS_TYPE_NAME =
        "com.sun.xml.ws.processor.model.AnonymousTypeName";
    String PROPERTY_ANONYMOUS_ARRAY_TYPE_NAME =
        "com.sun.xml.ws.processor.model.AnonymousArrayTypeName";
    String PROPERTY_ANONYMOUS_ARRAY_JAVA_TYPE =
        "com.sun.xml.ws.processor.model.AnonymousArrayJavaType";

    String PROPERTY_PTIE_CLASS_NAME =
        "com.sun.xml.ws.processor.model.PtieClassName";
    String PROPERTY_EPTFF_CLASS_NAME =
        "com.sun.xml.ws.processor.model.EPTFFClassName";
    String PROPERTY_SED_CLASS_NAME =
        "com.sun.xml.ws.processor.model.SEDClassName";
        String PROPERTY_WSDL_PORT_NAME =
        "com.sun.xml.ws.processor.model.WSDLPortName";
    String PROPERTY_WSDL_PORT_TYPE_NAME =
        "com.sun.xml.ws.processor.model.WSDLPortTypeName";
    String PROPERTY_WSDL_BINDING_NAME =
        "com.sun.xml.ws.processor.model.WSDLBindingName";
    String PROPERTY_WSDL_MESSAGE_NAME =
        "com.sun.xml.ws.processor.model.WSDLMessageName";
    String PROPERTY_MODELER_NAME =
        "com.sun.xml.ws.processor.model.ModelerName";
    String PROPERTY_STUB_CLASS_NAME =
        "com.sun.xml.ws.processor.model.StubClassName";
    String PROPERTY_STUB_OLD_CLASS_NAME =
        "com.sun.xml.ws.processor.model.StubOldClassName";
    String PROPERTY_DELEGATE_CLASS_NAME =
        "com.sun.xml.ws.processor.model.DelegateClassName";
    String PROPERTY_CLIENT_ENCODER_DECODER_CLASS_NAME =
        "com.sun.xml.ws.processor.model.ClientEncoderClassName";
    String PROPERTY_CLIENT_CONTACTINFOLIST_CLASS_NAME =
        "com.sun.xml.ws.processor.model.ClientContactInfoListClassName";
    String PROPERTY_TIE_CLASS_NAME =
        "com.sun.xml.ws.processor.model.TieClassName";
    String PROPERTY_JAVA_PORT_NAME =
        "com.sun.xml.ws.processor.model.JavaPortName";
}
