/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.jaxb_annotation;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.adapters.NormalizedStringAdapter;

/**
 * @author Rama Pulavarthi
 */
public class NormalizedString {
//    public String getNormalized() {
//        return normalized;
//    }
//
//    public void setNormalized(String normalized) {
//        this.normalized = normalized;
//    }

    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    private String normalized;
}
