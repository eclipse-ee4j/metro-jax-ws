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

import commonj.sdo.DataObject;

public interface ViewCriteriaRow extends DataObject {

   public java.lang.String getConjunction();

   public void setConjunction(java.lang.String value);

   public boolean getUpperCaseCompare();

   public void setUpperCaseCompare(boolean value);

   public java.util.List getItem();

   public void setItem(java.util.List value);


}

