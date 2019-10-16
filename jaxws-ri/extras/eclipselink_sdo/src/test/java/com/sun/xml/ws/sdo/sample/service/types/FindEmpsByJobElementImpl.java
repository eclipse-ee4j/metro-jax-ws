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

public class FindEmpsByJobElementImpl extends SDODataObject implements FindEmpsByJobElement {

   public static String SDO_URI = "http://sdo.sample.service/types/";

   public FindEmpsByJobElementImpl() {}

//   public Type getType() {
//      if(type == null){
//         Type lookupType = HelperProvider.getTypeHelper().getType(SDO_URI, "FindEmpsByJobElement");
//         setType(lookupType);
//      }
//      return type;
//   }

   public com.sun.xml.ws.sdo.sample.service.types.FindCriteria getFindCriteria() {
      return (com.sun.xml.ws.sdo.sample.service.types.FindCriteria)get("findCriteria");
   }

   public void setFindCriteria(com.sun.xml.ws.sdo.sample.service.types.FindCriteria value) {
      set("findCriteria" , value);
   }

   public java.lang.String getJob() {
      return getString("job");
   }

   public void setJob(java.lang.String value) {
      set("job" , value);
   }

   public com.sun.xml.ws.sdo.sample.service.types.FindControl getFindControl() {
      return (com.sun.xml.ws.sdo.sample.service.types.FindControl)get("findControl");
   }

   public void setFindControl(com.sun.xml.ws.sdo.sample.service.types.FindControl value) {
      set("findControl" , value);
   }


}

