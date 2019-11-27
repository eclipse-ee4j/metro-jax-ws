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

public class EmpImpl extends SDODataObject implements Emp {

   public static String SDO_URI = "http://sdo.sample.service/types/";

   public EmpImpl() {}

//   public Type getType() {
//      if(type == null){
//         Type lookupType = HelperProvider.getTypeHelper().getType(SDO_URI, "Emp");
//         setType(lookupType);
//      }
//      return type;
//   }

   public java.math.BigInteger getEmpno() {
      return getBigInteger("Empno");
   }

   public void setEmpno(java.math.BigInteger value) {
      set("Empno" , value);
   }

   public java.lang.String getEname() {
      return getString("Ename");
   }

   public void setEname(java.lang.String value) {
      set("Ename" , value);
   }

   public java.lang.String getJob() {
      return getString("Job");
   }

   public void setJob(java.lang.String value) {
      set("Job" , value);
   }

   public java.math.BigInteger getMgr() {
      return getBigInteger("Mgr");
   }

   public void setMgr(java.math.BigInteger value) {
      set("Mgr" , value);
   }

   public java.lang.String getHiredate() {
      return getString("Hiredate");
   }

   public void setHiredate(java.lang.String value) {
      set("Hiredate" , value);
   }

   public java.math.BigDecimal getSal() {
      return (java.math.BigDecimal)get("Sal");
   }

   public void setSal(java.math.BigDecimal value) {
      set("Sal" , value);
   }

   public java.math.BigDecimal getComm() {
      return (java.math.BigDecimal)get("Comm");
   }

   public void setComm(java.math.BigDecimal value) {
      set("Comm" , value);
   }

   public java.math.BigInteger getDeptno() {
      return getBigInteger("Deptno");
   }

   public void setDeptno(java.math.BigInteger value) {
      set("Deptno" , value);
   }


}

