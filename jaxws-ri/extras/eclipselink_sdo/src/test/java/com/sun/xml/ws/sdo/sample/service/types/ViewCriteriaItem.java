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
import commonj.sdo.DataObject;

public interface ViewCriteriaItem extends DataObject {

   public java.lang.String getConjunction();

   public void setConjunction(java.lang.String value);

   public boolean getUpperCaseCompare();

   public void setUpperCaseCompare(boolean value);

   public java.lang.String getAttribute();

   public void setAttribute(java.lang.String value);

   public java.lang.String getOperator();

   public void setOperator(java.lang.String value);

   public java.util.List getValue();

   public void setValue(java.util.List value);

   public com.sun.xml.ws.sdo.sample.service.types.ViewCriteria getNested();

   public void setNested(com.sun.xml.ws.sdo.sample.service.types.ViewCriteria value);


}

