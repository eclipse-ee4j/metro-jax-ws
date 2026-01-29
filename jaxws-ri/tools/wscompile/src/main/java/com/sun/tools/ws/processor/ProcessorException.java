/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor;

import com.sun.xml.ws.util.exception.JAXWSExceptionBase;

/**
 * ProcessorException represents an exception that occurred while processing
 * a web service.
 * 
 * @see JAXWSExceptionBase
 * 
 * @author WS Development Team
 */
public class ProcessorException extends JAXWSExceptionBase {

    private static final long serialVersionUID = -3521729994453882680L;

    @SuppressWarnings({"deprecation"})
    public ProcessorException(String key, Object... args) {
        super(key, args);
    }

    public ProcessorException(String msg){
        super(msg);        
    }

    public ProcessorException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getDefaultResourceBundleName() {
        return "com.sun.tools.ws.resources.processor";
    }
}
