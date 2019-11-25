/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/**
 * {@link com.sun.xml.ws.api.message.Message} implementation for JAXB.
 *
 * <pre>
 * TODO:
 *      Because a producer of a message doesn't generally know
 *      when a message is consumed, it's difficult for
 *      the caller to do a proper instance caching. Perhaps
 *      there should be a layer around JAXBContext that does that?
 * </pre>
 */
package com.sun.xml.ws.message.jaxb;

import com.sun.xml.ws.api.message.Message;
