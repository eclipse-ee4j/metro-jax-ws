/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.pipe;

/**
 * A Content-Type transport header that will be returned by {@link Codec#encode(com.sun.xml.ws.api.message.Packet, java.io.OutputStream)}.
 * It will provide the Content-Type header and also take care of SOAP 1.1 SOAPAction header.
 *
 * @see com.oracle.webservices.api.message.ContentType
 * TODO: rename to ContentMetadata?
 *
 * @author Vivek Pandey
 */
public interface ContentType extends com.oracle.webservices.api.message.ContentType {
}
