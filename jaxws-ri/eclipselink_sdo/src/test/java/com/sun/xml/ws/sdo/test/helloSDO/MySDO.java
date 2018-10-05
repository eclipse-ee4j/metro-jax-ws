/*
 * Copyright (c) 2012, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.sdo.test.helloSDO;

import commonj.sdo.DataObject;

public interface MySDO extends DataObject {

   public java.lang.String getStringPart();

   public void setStringPart(java.lang.String value);

   public int getIntPart();

   public void setIntPart(int value);

}
