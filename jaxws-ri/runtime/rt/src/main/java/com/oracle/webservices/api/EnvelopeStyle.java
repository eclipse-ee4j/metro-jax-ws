/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.webservices.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import jakarta.xml.ws.http.HTTPBinding;
import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.ws.spi.WebServiceFeatureAnnotation;

/**
 * The EnvelopeStyle annotation is used to specify the message envelope style(s)
 * for a web service endpoint implementation class. To smooth the migration from
 * the BindingType annotation to this EnvelopeStyle annotation, each of the  
 * styles is mapped to a binding identifier defined in JAX-WS specification.
 * Though a binding identifier includes both the envelope style and transport,
 * an envelope style defined herein does NOT imply or mandate any transport protocol 
 * to be use together; HTTP is the default transport. An implementation may
 * chose to support other transport with any of the envelope styles.
 * 
 * This annotation may be overriden programmatically or via deployment 
 * descriptors, depending on the platform in use. 
 * 
 * @author shih-chang.chen@oracle.com
 */
@WebServiceFeatureAnnotation(id="", bean=com.oracle.webservices.api.EnvelopeStyleFeature.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnvelopeStyle {
    
    /**
     * The envelope styles. If not specified, the default is the SOAP 1.1. 
     * 
     * @return The enveloping styles
     */
    Style[] style() default { Style.SOAP11 };
    
    enum Style {

        /**
         * SOAP1.1. For JAX-WS, this is mapped from:
         * jakarta.xml.ws.soap.SOAPBinding.SOAP11HTTP_BINDING
         */
        SOAP11(SOAPBinding.SOAP11HTTP_BINDING),

        /**
         * SOAP1.2. For JAX-WS, this is mapped from: 
         * jakarta.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING
         */
        SOAP12(SOAPBinding.SOAP12HTTP_BINDING),

        /**
         * The raw XML. For JAX-WS, this is mapped from:
         * jakarta.xml.ws.http.HTTPBinding.HTTP_BINDING
         */
        XML(HTTPBinding.HTTP_BINDING);
        
        /**
         * The BindingID used by the BindingType annotation. 
         */
        public final String bindingId;
    
        private Style(String id) {
            bindingId = id;
        }
        
        /**
         * Checks if the style is SOAP 1.1.
         * 
         * @return true if the style is SOAP 1.1.
         */
        public boolean isSOAP11() { return this.equals(SOAP11); }

        /**
         * Checks if the style is SOAP 1.2.
         * 
         * @return true if the style is SOAP 1.2.
         */
        public boolean isSOAP12() { return this.equals(SOAP12); }

        /**
         * Checks if the style is XML.
         * 
         * @return true if the style is XML.
         */
        public boolean isXML() { return this.equals(XML); }
    }
}
