/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.sourcemodel;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Marek Potociar, Jakub Podlesak
 */
public final class PolicySourceModelContext {
    
    Map<URI,PolicySourceModel> policyModels;
    
    /** 
     * Private constructor prevents instantiation of the instance from outside of the class
     */
    private PolicySourceModelContext() {
        // nothing to initialize
    }
    
    private Map<URI,PolicySourceModel> getModels() {
        if (null==policyModels) {
            policyModels = new HashMap<URI,PolicySourceModel>();
        }
        return policyModels;
    }
    
    public void addModel(final URI modelUri, final PolicySourceModel model) {
        getModels().put(modelUri,model);
    }
    
    public static PolicySourceModelContext createContext() {
        return new PolicySourceModelContext();
    }
    
    public boolean containsModel(final URI modelUri) {
        return getModels().containsKey(modelUri);
    }
    
    PolicySourceModel retrieveModel(final URI modelUri) {
        return getModels().get(modelUri);
    }
    
    PolicySourceModel retrieveModel(final URI modelUri, final URI digestAlgorithm, final String digest) {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "PolicySourceModelContext: policyModels = " + this.policyModels;
    }
}
