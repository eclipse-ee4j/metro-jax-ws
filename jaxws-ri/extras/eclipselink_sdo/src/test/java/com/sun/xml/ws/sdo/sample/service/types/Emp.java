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

public interface Emp extends DataObject {

   public java.math.BigInteger getEmpno();

   public void setEmpno(java.math.BigInteger value);

   public java.lang.String getEname();

   public void setEname(java.lang.String value);

   public java.lang.String getJob();

   public void setJob(java.lang.String value);

   public java.math.BigInteger getMgr();

   public void setMgr(java.math.BigInteger value);

   public java.lang.String getHiredate();

   public void setHiredate(java.lang.String value);

   public java.math.BigDecimal getSal();

   public void setSal(java.math.BigDecimal value);

   public java.math.BigDecimal getComm();

   public void setComm(java.math.BigDecimal value);

   public java.math.BigInteger getDeptno();

   public void setDeptno(java.math.BigInteger value);


}

