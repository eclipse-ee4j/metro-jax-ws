/*
 * Copyright (c) 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.util;

import java.util.Iterator;

/**
 * Utility class used as a JEP 238 multi release jar versioned class.
 *
 * Version for {@code runtime < 9}.
 */
public class MrJarUtil {

    static <S> Iterator<S> getIterator(Class<S> service, ClassLoader loader) {
        return new ServiceFinder.LazyIterator<>(service, loader);
    }

}
