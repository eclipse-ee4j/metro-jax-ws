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

public interface Dept extends DataObject {

   public java.math.BigInteger getDeptno();

   public void setDeptno(java.math.BigInteger value);

   public java.lang.String getDname();

   public void setDname(java.lang.String value);

   public java.lang.String getLoc();

   public void setLoc(java.lang.String value);

   public java.util.List getEmp();

   public void setEmp(java.util.List value);


}

