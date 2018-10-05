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

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.message.Message;

import javax.xml.stream.XMLStreamReader;

/**
 *
 *
 * @see com.sun.xml.ws.api.pipe.Codecs
 * @author Jitendra Kotamraju
 */
public interface SOAPBindingCodec extends Codec {
    StreamSOAPCodec getXMLCodec();
}
