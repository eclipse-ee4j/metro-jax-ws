/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.fault_detail_676.server;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.WebServiceProvider;
import java.io.ByteArrayInputStream;

/**
 * @author Jitendra Kotamraju
 */
@WebServiceProvider(targetNamespace="urn:test", serviceName="Hello",
    portName="HelloPort")
public class HelloImpl implements Provider<Source> {

    // sends multiple detail entries in soap fault
    public Source invoke(Source source) {
        String body  =
            "<soap:Fault xmlns:soap='http://schemas.xmlsoap.org/soap/envelope/'>"+
                    "<faultcode>soap:Server</faultcode>"+
                    "<faultstring>fault message</faultstring>"+
                    "<faultactor>http://example.org/actor</faultactor>"+
                    "<detail>"+
                    "<entry1>entry1</entry1>"+
                    "<entry2>entry2</entry2>"+
                    "</detail>" +
                    "</soap:Fault>";
        return new StreamSource(new ByteArrayInputStream(body.getBytes()));
    }

}
