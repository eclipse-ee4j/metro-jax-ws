/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.soap12.mtom_optional.server;

import jakarta.jws.WebService;
import javax.xml.transform.Source;
import jakarta.xml.bind.annotation.XmlMimeType;
import jakarta.xml.ws.soap.MTOM;
import jakarta.xml.ws.BindingType;

/**
 * @author Rama Pulavarthi
 */
@MTOM
@BindingType("http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
@WebService
public class MtomSample {

    public @XmlMimeType("text/xml; charset=iso-8859-1")Source echo(@XmlMimeType("text/xml; charset=iso-8859-1") Source input) {
        return input;    
    }


}
