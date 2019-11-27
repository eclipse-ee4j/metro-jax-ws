/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.sdo.sample.service.types;

import commonj.sdo.Type;
import commonj.sdo.impl.HelperProvider;
import org.eclipse.persistence.sdo.SDODataObject;

public class FindControlImpl extends SDODataObject implements FindControl {

   public static String SDO_URI = "http://sdo.sample.service/types/";

   public FindControlImpl() {}

//   public Type getType() {
//      if(type == null){
//         Type lookupType = HelperProvider.getTypeHelper().getType(SDO_URI, "FindControl");
//         setType(lookupType);
//      }
//      return type;
//   }

   public boolean getRetrieveAllTranslations() {
      return getBoolean("retrieveAllTranslations");
   }

   public void setRetrieveAllTranslations(boolean value) {
      set("retrieveAllTranslations" , value);
   }


}

