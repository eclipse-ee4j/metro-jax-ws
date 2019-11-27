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

public class FindCriteriaImpl extends SDODataObject implements FindCriteria {

   public static String SDO_URI = "http://sdo.sample.service/types/";

   public FindCriteriaImpl() {}

//   public Type getType() {
//      if(type == null){
//         Type lookupType = HelperProvider.getTypeHelper().getType(SDO_URI, "FindCriteria");
//         setType(lookupType);
//      }
//      return type;
//   }

   public int getFetchStart() {
      return getInt("fetchStart");
   }

   public void setFetchStart(int value) {
      set("fetchStart" , value);
   }

   public int getFetchSize() {
      return getInt("fetchSize");
   }

   public void setFetchSize(int value) {
      set("fetchSize" , value);
   }

   public com.sun.xml.ws.sdo.sample.service.types.ViewCriteria getFilter() {
      return (com.sun.xml.ws.sdo.sample.service.types.ViewCriteria)get("filter");
   }

   public void setFilter(com.sun.xml.ws.sdo.sample.service.types.ViewCriteria value) {
      set("filter" , value);
   }

   public com.sun.xml.ws.sdo.sample.service.types.SortOrder getSortOrder() {
      return (com.sun.xml.ws.sdo.sample.service.types.SortOrder)get("sortOrder");
   }

   public void setSortOrder(com.sun.xml.ws.sdo.sample.service.types.SortOrder value) {
      set("sortOrder" , value);
   }

   public java.util.List getFindAttribute() {
      return getList("findAttribute");
   }

   public void setFindAttribute(java.util.List value) {
      set("findAttribute" , value);
   }

   public boolean getExcludeAttribute() {
      return getBoolean("excludeAttribute");
   }

   public void setExcludeAttribute(boolean value) {
      set("excludeAttribute" , value);
   }

   public java.util.List getChildFindCriteria() {
      return getList("childFindCriteria");
   }

   public void setChildFindCriteria(java.util.List value) {
      set("childFindCriteria" , value);
   }


}

