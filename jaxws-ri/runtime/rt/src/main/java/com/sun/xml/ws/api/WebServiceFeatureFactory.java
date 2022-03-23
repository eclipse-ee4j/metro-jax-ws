/*
 * Copyright (c) 2009, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api;

import com.sun.xml.ws.binding.WebServiceFeatureList;

import jakarta.xml.ws.WebServiceFeature;
import java.lang.annotation.Annotation;

/**
 * Factory methods to get web service features from the corresponding
 * feature annotations
 *
 * @author Jitendra Kotamraju
 */
public final class WebServiceFeatureFactory {

    private WebServiceFeatureFactory() {}

    /**
     * Returns a feature list for feature annotations(i.e which have
     * {@link jakarta.xml.ws.spi.WebServiceFeatureAnnotation} meta annotation)
     *
     * @param ann list of annotations(that can also have non-feature annotations)
     * @return non-null feature list object
     */
    public static WSFeatureList getWSFeatureList(Iterable<Annotation> ann) {
        WebServiceFeatureList list = new WebServiceFeatureList();
        list.parseAnnotations(ann);
        return list;
    }

    /**
     * Returns a corresponding feature for a feature annotation(i.e which has
     * {@link jakarta.xml.ws.spi.WebServiceFeatureAnnotation} meta annotation)
     *
     * @param ann any annotation, not required to be a feature annotation
     * @return corresponding feature for the annotation
     *         null, if the annotation is not a feature annotation
     */
    public static WebServiceFeature getWebServiceFeature(Annotation ann) {
        return WebServiceFeatureList.getFeature(ann);
    }

}
