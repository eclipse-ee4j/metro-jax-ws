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

public interface FindCriteria extends DataObject {

   public int getFetchStart();

   public void setFetchStart(int value);

   public int getFetchSize();

   public void setFetchSize(int value);

   public com.sun.xml.ws.sdo.sample.service.types.ViewCriteria getFilter();

   public void setFilter(com.sun.xml.ws.sdo.sample.service.types.ViewCriteria value);

   public com.sun.xml.ws.sdo.sample.service.types.SortOrder getSortOrder();

   public void setSortOrder(com.sun.xml.ws.sdo.sample.service.types.SortOrder value);

   public java.util.List getFindAttribute();

   public void setFindAttribute(java.util.List value);

   public boolean getExcludeAttribute();

   public void setExcludeAttribute(boolean value);

   public java.util.List getChildFindCriteria();

   public void setChildFindCriteria(java.util.List value);


}

