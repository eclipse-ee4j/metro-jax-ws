/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.policy;

import com.sun.xml.ws.policy.Policy;
import com.sun.xml.ws.policy.sourcemodel.PolicyModelGenerator;
import com.sun.xml.ws.policy.sourcemodel.PolicySourceModel;

/**
 *
 * @author Fabian Ritzmann
 */
public abstract class ModelGenerator extends PolicyModelGenerator {

    private static final SourceModelCreator CREATOR = new SourceModelCreator();

    /**
     * This private constructor avoids direct instantiation from outside the class.
     */
    private ModelGenerator() {
        super();
    }

    /**
     * Factory method that returns a ModelGenerator instance.
     *
     * @return A ModelGenerator instance.
     */
    public static PolicyModelGenerator getGenerator() {
        return PolicyModelGenerator.getCompactGenerator(CREATOR);
    }


    protected static class SourceModelCreator extends PolicySourceModelCreator {

        @Override
        protected PolicySourceModel create(Policy policy) {
            return SourceModel.createPolicySourceModel(policy.getNamespaceVersion(),
                                                       policy.getId(), policy.getName());

        }

    }

}
