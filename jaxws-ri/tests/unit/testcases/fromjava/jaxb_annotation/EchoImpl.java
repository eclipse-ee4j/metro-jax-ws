/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.jaxb_annotation;

import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;


@WebService(name = "Echo", serviceName = "EchoService", targetNamespace = "http://echo.org/")
public class EchoImpl {
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public String normalize(@XmlJavaTypeAdapter(NormalizedStringAdapter.class) String value) {
        return value;
    }

    public NormalizedString echo(NormalizedString str) {
        return str;
    }
}
