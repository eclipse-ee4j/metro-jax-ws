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

public class GetDeptElementImpl extends SDODataObject implements GetDeptElement {

   public static String SDO_URI = "http://sdo.sample.service/types/";

   public GetDeptElementImpl() {}

//   public Type getType() {
//      if(type == null){
//         Type lookupType = HelperProvider.getTypeHelper().getType(SDO_URI, "GetDeptElement");
//         setType(lookupType);
//      }
//      return type;
//   }

   public java.math.BigInteger getDeptno() {
      return getBigInteger("deptno");
   }

   public void setDeptno(java.math.BigInteger value) {
      set("deptno" , value);
   }


}

