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
public class CreateEmpElementImpl extends SDODataObject implements CreateEmpElement {

   public static String SDO_URI = "http://sdo.sample.service/types/";

   public CreateEmpElementImpl() {}

//   public Type getType() {
//      if(type == null){
//         Type lookupType = HelperProvider.getTypeHelper().getType(SDO_URI, "CreateEmpElement");
//         setType(lookupType);
//      }
//      return type;
//   }

   public com.sun.xml.ws.sdo.sample.service.types.Emp getEmp() {
      return (com.sun.xml.ws.sdo.sample.service.types.Emp)get("emp");
   }

   public void setEmp(com.sun.xml.ws.sdo.sample.service.types.Emp value) {
      set("emp" , value);
   }


}

