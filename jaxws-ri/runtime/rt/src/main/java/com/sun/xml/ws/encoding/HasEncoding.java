/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding;

/**
 * A {@link javax.xml.stream.XMLStreamWriter} doesn't expose any method to
 * give encoding. An implementation of writer may implement
 * this interface to give the encoding with which the writer is created.
 *
 * @author  Jitendra Kotamraju
 * @since JAX-WS RI 2.2.6
 */
public interface HasEncoding {
    String getEncoding();
}

