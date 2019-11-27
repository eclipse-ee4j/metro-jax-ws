/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model.wsdl;import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.model.wsdl.WSDLFeaturedObject;
import com.sun.xml.ws.binding.WebServiceFeatureList;

import javax.xml.stream.XMLStreamReader;
import javax.xml.ws.WebServiceFeature;

/**
 * @author Kohsuke Kawaguchi
 */
abstract class AbstractFeaturedObjectImpl extends AbstractExtensibleImpl implements WSDLFeaturedObject {
    protected WebServiceFeatureList features;

    protected AbstractFeaturedObjectImpl(XMLStreamReader xsr) {
        super(xsr);
    }
    protected AbstractFeaturedObjectImpl(String systemId, int lineNumber) {
        super(systemId, lineNumber);
    }

    public final void addFeature(WebServiceFeature feature) {
        if (features == null)
            features = new WebServiceFeatureList();

        features.add(feature);
    }

    public @NotNull WebServiceFeatureList getFeatures() {
        if(features == null)
            return new WebServiceFeatureList();
        return features;
    }

    public final WebServiceFeature getFeature(String id) {
        if (features != null) {
            for (WebServiceFeature f : features) {
                if (f.getID().equals(id))
                    return f;
            }
        }

        return null;
    }

    @Nullable
    public <F extends WebServiceFeature> F getFeature(@NotNull Class<F> featureType) {
        if(features==null)
            return null;
        else
            return features.get(featureType);
    }
}
