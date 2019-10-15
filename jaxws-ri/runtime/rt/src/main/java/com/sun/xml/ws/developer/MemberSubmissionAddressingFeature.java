/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.developer;

import com.sun.xml.ws.api.FeatureConstructor;

import javax.xml.ws.WebServiceFeature;

import org.glassfish.gmbal.ManagedAttribute;
import org.glassfish.gmbal.ManagedData;


/**
 * Addressing Feature representing MemberSubmission Version.
 *
 * @author Rama Pulavarthi
 */

@ManagedData
public class MemberSubmissionAddressingFeature extends WebServiceFeature {
    /**
     * Constant value identifying the MemberSubmissionAddressingFeature
     */
    public static final String ID = "http://java.sun.com/xml/ns/jaxws/2004/08/addressing";

    /**
     * Constant ID for the <code>required</code> feature parameter
     */
    public static final String IS_REQUIRED = "ADDRESSING_IS_REQUIRED";
    
    private boolean required;

    /**
     * Create an MemberSubmissionAddressingFeature
     * The instance created will be enabled.
     */
    public MemberSubmissionAddressingFeature() {
        this.enabled = true;
    }

    /**
     * Create an MemberSubmissionAddressingFeature
     *
     * @param enabled specifies whether this feature should
     *                be enabled or not.
     */
    public MemberSubmissionAddressingFeature(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Create an <code>MemberSubmissionAddressingFeature</code>
     *
     * @param enabled specifies whether this feature should
     * be enabled or not.
     * @param required specifies the value that will be used
     * for the <code>required</code> attribute on the
     * <code>wsaw:UsingAddressing</code> element.
     */
    public MemberSubmissionAddressingFeature(boolean enabled, boolean required) {
        this.enabled = enabled;
        this.required = required;
    }

    /**
     * Create an <code>MemberSubmissionAddressingFeature</code>
     *
     * @param enabled specifies whether this feature should
     * be enabled or not.
     * @param required specifies the value that will be used
     * for the <code>required</code> attribute on the
     * <code>wsaw:UsingAddressing</code> element.
     * @param validation specifies the value that will be used
     * for validation for the incoming messages. If LAX, messages are not strictly checked for conformance with  the spec.
     */
    @FeatureConstructor({"enabled","required","validation"})
    public MemberSubmissionAddressingFeature(boolean enabled, boolean required, MemberSubmissionAddressing.Validation validation) {
        this.enabled = enabled;
        this.required = required;
        this.validation = validation;
    }


    @ManagedAttribute
    public String getID() {
        return ID;
    }

    @ManagedAttribute
    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    private MemberSubmissionAddressing.Validation validation = MemberSubmissionAddressing.Validation.LAX;
    public void setValidation(MemberSubmissionAddressing.Validation validation) {
        this.validation = validation;
        
    }

    @ManagedAttribute
    public MemberSubmissionAddressing.Validation getValidation() {
        return validation;
    }
}
