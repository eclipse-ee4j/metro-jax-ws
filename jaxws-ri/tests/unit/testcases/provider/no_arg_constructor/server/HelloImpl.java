/*
 * Copyright (c) 2004, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.no_arg_constructor.server;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.WebServiceProvider;

/**
 * Service returns Source payload that is created using no-arg Source
 * constructors.
 *
 * @author Jitendra Kotamraju
 */
@WebServiceProvider
public class HelloImpl implements Provider<Source> {
    int source = 0;

    public Source invoke(Source msg) {
        int index = source++%3;
        if (index == 0)
            return new DOMSource();
        else if (index == 1)
            return new StreamSource();
        else
            return new SAXSource();
    }
}
