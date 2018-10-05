/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.attachment_595.client;

import junit.framework.TestCase;

/**
 * Client SOAP handler adds an attachment
 *
 * @author Jitendra Kotamraju
 */
public class AttachmentTest extends TestCase {

    public AttachmentTest(String name) throws Exception {
        super(name);
    }

    public void testProxy() {
        Hello hello = new Hello_Service().getHelloPort();
        hello.voidTest(new VoidType());
    }

}
