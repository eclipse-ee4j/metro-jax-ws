/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package externalmetadata.fromwsdl.client;

import javax.xml.soap.SOAPException;
import java.io.IOException;

/**
 * Client to invoke BlackboxService
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class BlackboxServiceClient {

    public static void main(String [] args) throws SOAPException, IOException {
        Blackbox port = new BlackboxServiceImplService().getBlackboxPort();
        port.doSomething();
    }

}
