/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding.fastinfoset;

/**
 * MIME types for Infosets encoded as fast infoset documents.
 *
 * @author Paul.Sandoz@Sun.Com
 */
public final class FastInfosetMIMETypes {
    /**
     * MIME type for a generic Infoset encoded as a fast infoset document.
     */
    static public final String INFOSET = "application/fastinfoset";
    /**
     * MIME type for a SOAP 1.1 Infoset encoded as a fast infoset document.
     */
    static public final String SOAP_11 = "application/fastinfoset";
    /**
     * MIME type for a SOAP 1.2 Infoset encoded as a fast infoset document.
     */
    static public final String SOAP_12 = "application/soap+fastinfoset";    
    
    /**
     * MIME type for a generic Infoset encoded as a stateful fast infoset document.
     */
    static public final String STATEFUL_INFOSET = "application/vnd.sun.stateful.fastinfoset";
    /**
     * MIME type for a SOAP 1.1 Infoset encoded as a stateful fast infoset document.
     */
    static public final String STATEFUL_SOAP_11 = "application/vnd.sun.stateful.fastinfoset";
    /**
     * MIME type for a SOAP 1.2 Infoset encoded as a stateful fast infoset document.
     */
    static public final String STATEFUL_SOAP_12 = "application/vnd.sun.stateful.soap+fastinfoset";    
}
