/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
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

@SuppressWarnings({"serial"})
public class GetTotalCompResponseElementImpl extends SDODataObject implements GetTotalCompResponseElement {

   public static String SDO_URI = "http://sdo.sample.service/types/";

   public GetTotalCompResponseElementImpl() {}

//   public Type getType() {
//      if(type == null){
//         Type lookupType = HelperProvider.getTypeHelper().getType(SDO_URI, "GetTotalCompResponseElement");
//         setType(lookupType);
//      }
//      return type;
//   }

   public java.math.BigDecimal getResult() {
      return (java.math.BigDecimal)get("result");
   }

   public void setResult(java.math.BigDecimal value) {
      set("result" , value);
   }


}

