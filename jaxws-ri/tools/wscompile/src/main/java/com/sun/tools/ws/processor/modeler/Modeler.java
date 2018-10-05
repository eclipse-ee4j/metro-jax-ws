/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.modeler;

import com.sun.tools.ws.processor.model.Model;

/**
 * A Modeler is used to create a Model of a Web Service from a particular Web 
 * Web Service description such as a WSDL
 *
 * @author WS Development Team
*/
public interface Modeler {
    /**
     * Returns the top model of a Web Service. May throw a
     * ModelException if there is a problem with the model.
     *
     * @return Model - the root Node of the model of the Web Service
     *
     * @exception ModelerException
     */
    public Model buildModel();
}
