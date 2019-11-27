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

public interface FindClerksElement extends DataObject {

   public com.sun.xml.ws.sdo.sample.service.types.FindCriteria getFindCriteria();

   public void setFindCriteria(com.sun.xml.ws.sdo.sample.service.types.FindCriteria value);

   public com.sun.xml.ws.sdo.sample.service.types.FindControl getFindControl();

   public void setFindControl(com.sun.xml.ws.sdo.sample.service.types.FindControl value);


}

