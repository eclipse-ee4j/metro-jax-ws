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

public class GetTotalCompImpl extends SDODataObject implements GetTotalComp {

   public static String SDO_URI = "http://sdo.sample.service/types/";

   public GetTotalCompImpl() {}

//   public Type getType() {
//      if(type == null){
//         Type lookupType = HelperProvider.getTypeHelper().getType(SDO_URI, "GetTotalCompElement");
//         setType(lookupType);
//      }
//      return type;
//   }

   public java.math.BigInteger getEmpno() {
      return getBigInteger("empno");
   }

   public void setEmpno(java.math.BigInteger value) {
      set("empno" , value);
   }


}

