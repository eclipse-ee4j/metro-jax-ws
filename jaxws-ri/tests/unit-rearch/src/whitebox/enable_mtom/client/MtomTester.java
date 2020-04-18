/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.enable_mtom.client;

import junit.framework.TestCase;
import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.WSBinding;

import jakarta.xml.ws.soap.MTOMFeature;
import jakarta.xml.ws.soap.SOAPBinding;

import whitebox.enable_mtom.config.WebServiceImpl1;

/**
 * @author Rama Pulavarthi
 */

public class MtomTester extends TestCase {
    public MtomTester(String name) {
        super(name);
    }

    public void testMTOM1() throws Exception {
        WSBinding binding = BindingID.parse( WebServiceImpl1.class).createBinding();
        assert(!binding.isFeatureEnabled(MTOMFeature.class));
    }

    public void testMTOM2() throws Exception {
        WSBinding binding = BindingID.parse(MtomWebServiceImpl.class).createBinding(new MTOMFeature());
        assert(binding.isFeatureEnabled(MTOMFeature.class));
    }

    public void testMTOM3() throws Exception {
        WSBinding binding = BindingID.parse(SOAPBinding.SOAP11HTTP_MTOM_BINDING).createBinding(
                BindingID.parse(WebServiceImpl1.class).createBinding().getFeatures());
        assert(binding.isFeatureEnabled(MTOMFeature.class));
    }
}
