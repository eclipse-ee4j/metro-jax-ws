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

public class SortOrderImpl extends SDODataObject implements SortOrder {

   public static String SDO_URI = "http://sdo.sample.service/types/";

   public SortOrderImpl() {}

//   public Type getType() {
//      if(type == null){
//         Type lookupType = HelperProvider.getTypeHelper().getType(SDO_URI, "SortOrder");
//         setType(lookupType);
//      }
//      return type;
//   }

   public java.util.List getSortAttribute() {
      return getList("sortAttribute");
   }

   public void setSortAttribute(java.util.List value) {
      set("sortAttribute" , value);
   }


}

