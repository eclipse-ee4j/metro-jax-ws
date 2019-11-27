/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.policy;

import com.sun.xml.ws.policy.sourcemodel.PolicyModelGenerator;
import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;
import com.sun.xml.ws.policy.sourcemodel.XmlPolicyModelUnmarshaller;
import com.sun.xml.ws.policy.sourcemodel.wspolicy.NamespaceVersion;

/**
 *
 * @author Fabian Ritzmann
 */
public class ModelUnmarshaller extends XmlPolicyModelUnmarshaller {

    private static final ModelUnmarshaller INSTANCE = new ModelUnmarshaller();

    /**
     * This private constructor avoids direct instantiation from outside the class.
     */
    private ModelUnmarshaller() {
        super();
    }

    /**
     * Factory method that returns a {@link ModelUnmarshaller} instance.
     *
     * @return {@link PolicyModelGenerator} instance
     */
    public static ModelUnmarshaller getUnmarshaller() {
        return INSTANCE;
    }

    @Override
    protected PolicySourceModel createSourceModel(NamespaceVersion nsVersion, String id, String name) {
        return SourceModel.createSourceModel(nsVersion, id, name);
    }

}
