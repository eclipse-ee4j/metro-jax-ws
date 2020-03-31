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

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Currency;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Jitendra Kotamraju
 */
public class CurrencyAdapter extends XmlAdapter<String,Currency> {

    public Currency unmarshal(String val) throws Exception {
        return Currency.getInstance(val);
    }

    public String marshal(Currency val) throws Exception {
        return val.toString();
    }

}
