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
public class GetEmpElementImpl extends SDODataObject implements GetEmpElement {

   public static String SDO_URI = "http://sdo.sample.service/types/";

   public GetEmpElementImpl() {}

//   public Type getType() {
//      if(type == null){
//         Type lookupType = HelperProvider.getTypeHelper().getType(SDO_URI, "GetEmpElement");
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

