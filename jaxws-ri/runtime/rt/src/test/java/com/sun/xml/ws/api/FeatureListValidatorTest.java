/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.soap.AddressingFeature;

import com.sun.xml.ws.binding.BindingImpl;

import junit.framework.TestCase;

public class FeatureListValidatorTest extends TestCase {

    public void testValidateNoFeatures() {
        BindingImpl.create(BindingID.SOAP11_HTTP);
        // must not throw; otherwise ok
    }
    
    public void testInvalid() {
        try {
            BindingImpl.create(BindingID.SOAP11_HTTP, 
                    new WebServiceFeature[] {new InvalidFeature()});
            fail("InvalidFeature was passed, but no exception thrown");
        } catch (WebServiceException e) {
            // good, exception was thrown
        }
    }

    public void testAddsAddressing() {
        BindingImpl binding = BindingImpl.create(BindingID.SOAP11_HTTP, 
                new WebServiceFeature[] {new AddsAddressingFeature()});
        assertTrue("AddressingFeature must be enabled because AddsAddressingFeature was passed", 
                binding.isFeatureEnabled(AddressingFeature.class));
    }

    public void testAddsAddressingAlreadyPresent() {
        BindingImpl binding = BindingImpl.create(BindingID.SOAP11_HTTP, 
                new WebServiceFeature[] {new AddsAddressingFeature(), new AddressingFeature()});
        assertTrue("AddressingFeature must be enabled because AddsAddressingFeature was passed", 
                binding.isFeatureEnabled(AddressingFeature.class));
    }

    public void testAddsAddressingDisabledAddressing() {
        try {
            BindingImpl.create(BindingID.SOAP11_HTTP, 
                new WebServiceFeature[] {new AddsAddressingFeature(), new AddressingFeature(false)});
        } catch (WebServiceException e) {
            // good, exception was thrown
        }
    }
}
