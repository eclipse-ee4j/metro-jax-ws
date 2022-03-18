/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy;

import com.sun.xml.ws.policy.sourcemodel.AssertionData;
import java.util.Collection;

/**
 * Simple assertion is an abstract class that serves as a base class for any assertion
 * that <b>MAY NOT</b> contain nested policies.
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
public abstract class SimpleAssertion extends PolicyAssertion {
    protected SimpleAssertion() {
        super();
    }
    
    protected SimpleAssertion(AssertionData data, Collection<? extends PolicyAssertion> assertionParameters) {
        super(data, assertionParameters);
    }        
    
    @Override 
    public final boolean hasNestedPolicy() { // TODO: make abstract
        return false;
    }

    @Override
    public final NestedPolicy getNestedPolicy() { // TODO: make abstract
        return null;
    }    
}
