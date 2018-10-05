/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.addressing.v200408;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.WebServiceException;

import com.sun.xml.ws.addressing.WsaTubeHelper;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.model.SEIModel;
import org.w3c.dom.Element;

/**
 * @author Arun Gupta
 */
public class WsaTubeHelperImpl extends WsaTubeHelper {
    static final JAXBContext jc;

    static {
        try {
            jc = JAXBContext.newInstance(ProblemAction.class,
                                         ProblemHeaderQName.class);
        } catch (JAXBException e) {
            throw new WebServiceException(e);
        }
    }

    public WsaTubeHelperImpl(WSDLPort wsdlPort, SEIModel seiModel, WSBinding binding) {
        super(binding,seiModel,wsdlPort);
    }

    private Marshaller createMarshaller() throws JAXBException {
        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
        return marshaller;
    }

    @Override
    public final void getProblemActionDetail(String action, Element element) {
        ProblemAction pa = new ProblemAction(action);
        try {
            createMarshaller().marshal(pa, element);
        } catch (JAXBException e) {
            throw new WebServiceException(e);
        }
    }

    @Override
    public final void getInvalidMapDetail(QName name, Element element) {
        ProblemHeaderQName phq = new ProblemHeaderQName(name);
        try {
            createMarshaller().marshal(phq, element);
        } catch (JAXBException e) {
            throw new WebServiceException(e);
        }
    }

    @Override
    public final void getMapRequiredDetail(QName name, Element element) {
        getInvalidMapDetail(name, element);
    }
}
