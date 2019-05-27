/*
 * Copyright (c) 2016, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.test.processor.modeler.annotation;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@WebService()
public class NillableTest {

        private java.lang.String[] entitlements;

        @XmlElementWrapper(name="titles")
        @XmlElement(name="title01", nillable=false)
        public java.lang.String[] getEntitlements() {
                return this.entitlements;
        }

        public void setEntitlements(String[] entitlements) {
                this.entitlements = entitlements;
        }
}
