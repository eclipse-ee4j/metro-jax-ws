/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.epr_subtypes.server;

import javax.jws.WebService;
import javax.jws.WebParam;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import javax.xml.ws.Holder;


/**
 * Tests wsa:EndpointReferenceType's subtypes mapping
 *
 * @author Jitendra Kotamraju
 */
@WebService(endpointInterface = "epr.epr_subtypes.server.Hello")
public class TestEndpointImpl implements Hello {

    public void hello(W3CEndpointReference epr,
                      W3CEndpointReference subepr,
                      W3CEndpointReference subsubepr,
                      Holder<W3CEndpointReference> importedepr,
                      Holder<W3CEndpointReference> importedsubepr,
                      Holder<W3CEndpointReference> importedsubsubepr) {

    }

}
