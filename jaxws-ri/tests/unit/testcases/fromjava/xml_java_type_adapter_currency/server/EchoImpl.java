/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.xml_java_type_adapter_currency.server;

import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.adapters.HexBinaryAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.ws.WebServiceException;
import java.util.Currency;
import java.util.Locale;

/**
 * @author Jitendra Kotamraju
 */
@WebService(name="Echo", serviceName="echoService", targetNamespace="http://echo.org/")
public class EchoImpl {

    public Currency getCurrency() {
        return Currency.getInstance(Locale.US);
    }
    
}
