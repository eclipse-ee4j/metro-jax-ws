/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.spi.db;

/**
 * A JAXB Bean that works like a DOM.
 *
 * <p>
 * This bean is bound to XML as a sequence of elements, where each
 * element[i] is from bridges[i] (which defines the tag name and the expected type)
 * and values[i] (which defines the actual value.)
 *
 * <p>
 * This object allows you to treat multiple unrelated JAXB beans as a single tree.
 * This in turn allows you to marshal this tree in one marshal method invocation,
 * which is faster than multiple invocations of the marshal method. 
 *
 * <p>
 * The binding of this class is always known to {@link BindingContext}, so it can be
 * used without passing anything to {@link BindingContext#newWrapperInstace(Class)}.
 * This object can be only used for marshalling, not for unmarshalling.
 *
 * @author Kohsuke Kawaguchi
 */
public class WrapperComposite {
    public XMLBridge[] bridges;
    public Object[] values;
}
