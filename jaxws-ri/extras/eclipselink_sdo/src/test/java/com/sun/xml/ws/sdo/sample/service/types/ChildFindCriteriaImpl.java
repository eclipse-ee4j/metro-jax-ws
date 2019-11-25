/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

public class ChildFindCriteriaImpl extends com.sun.xml.ws.sdo.sample.service.types.FindCriteriaImpl implements ChildFindCriteria {

   public static String SDO_URI = "http://sdo.sample.service/types/";

   public ChildFindCriteriaImpl() {}

//   public Type getType() {
//      if(type == null){
//         Type lookupType = HelperProvider.getTypeHelper().getType(SDO_URI, "ChildFindCriteria");
//         setType(lookupType);
//      }
//      return type;
//   }

   public java.lang.String getChildAttrName() {
      return getString("childAttrName");
   }

   public void setChildAttrName(java.lang.String value) {
      set("childAttrName" , value);
   }


}

