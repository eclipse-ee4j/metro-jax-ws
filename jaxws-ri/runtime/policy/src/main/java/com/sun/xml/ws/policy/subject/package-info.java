/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/**
 * Provides utility classes and objects that can serve as policy subjects for
 * {@link com.sun.xml.ws.policy.PolicySubject}. The current implementation provides
 * subjects for WSDL 1.0/1.1 binding elements.
 *
 * We are not trying to provide an exact model of WSDL elements. The JAX-WS
 * WSDLModel does that already. Instead, we are aiming at providing a light-weight
 * and easy to use representation of WSDL elements.
 * 
 * At the same time, this implementation is providing a simple way of mapping the
 * subjects to WSDL scopes. That limits how the WsdlSubjects can be used. Ultimately,
 * each subject is always linked to one service, port and binding element. That
 * means that WsdlSubjects cannot accurately represent e.g. a WSDL message element
 * that is referenced by multiple WSDL binding operations.
 */
package com.sun.xml.ws.policy.subject;
