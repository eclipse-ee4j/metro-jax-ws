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
public class ViewCriteriaItemImpl extends SDODataObject implements ViewCriteriaItem {

   public static String SDO_URI = "http://sdo.sample.service/types/";

   public ViewCriteriaItemImpl() {}

//   public Type getType() {
//      if(type == null){
//         Type lookupType = HelperProvider.getTypeHelper().getType(SDO_URI, "ViewCriteriaItem");
//         setType(lookupType);
//      }
//      return type;
//   }

   public java.lang.String getConjunction() {
      return getString("conjunction");
   }

   public void setConjunction(java.lang.String value) {
      set("conjunction" , value);
   }

   public boolean getUpperCaseCompare() {
      return getBoolean("upperCaseCompare");
   }

   public void setUpperCaseCompare(boolean value) {
      set("upperCaseCompare" , value);
   }

   public java.lang.String getAttribute() {
      return getString("attribute");
   }

   public void setAttribute(java.lang.String value) {
      set("attribute" , value);
   }

   public java.lang.String getOperator() {
      return getString("operator");
   }

   public void setOperator(java.lang.String value) {
      set("operator" , value);
   }

   public java.util.List getValue() {
      return getList("value");
   }

   public void setValue(java.util.List value) {
      set("value" , value);
   }

   public com.sun.xml.ws.sdo.sample.service.types.ViewCriteria getNested() {
      return (com.sun.xml.ws.sdo.sample.service.types.ViewCriteria)get("nested");
   }

   public void setNested(com.sun.xml.ws.sdo.sample.service.types.ViewCriteria value) {
      set("nested" , value);
   }


}

