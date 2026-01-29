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
public class DeptImpl extends SDODataObject implements Dept {

   public static String SDO_URI = "http://sdo.sample.service/types/";

   public DeptImpl() {}

//   public Type getType() {
//      if(type == null){
//         Type lookupType = HelperProvider.getTypeHelper().getType(SDO_URI, "Dept");
//         setType(lookupType);
//      }
//      return type;
//   }

   public java.math.BigInteger getDeptno() {
      return getBigInteger("Deptno");
   }

   public void setDeptno(java.math.BigInteger value) {
      set("Deptno" , value);
   }

   public java.lang.String getDname() {
      return getString("Dname");
   }

   public void setDname(java.lang.String value) {
      set("Dname" , value);
   }

   public java.lang.String getLoc() {
      return getString("Loc");
   }

   public void setLoc(java.lang.String value) {
      set("Loc" , value);
   }

   public java.util.List getEmp() {
      return getList("Emp");
   }

   public void setEmp(java.util.List value) {
      set("Emp" , value);
   }


}

