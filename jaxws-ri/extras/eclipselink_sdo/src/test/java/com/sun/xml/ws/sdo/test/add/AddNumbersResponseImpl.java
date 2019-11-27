/*
 * Copyright (c) 2012, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.sdo.test.add;

import org.eclipse.persistence.sdo.SDODataObject;

public class AddNumbersResponseImpl extends SDODataObject implements AddNumbersResponse {

   public static final int START_PROPERTY_INDEX = 0;

   public static final int END_PROPERTY_INDEX = START_PROPERTY_INDEX + 0;

   public AddNumbersResponseImpl() {}

   public int getReturn() {
      return getInt(START_PROPERTY_INDEX + 0);
   }

   public void setReturn(int value) {
      set(START_PROPERTY_INDEX + 0 , value);
   }


}

