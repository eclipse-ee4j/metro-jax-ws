/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.pipe;

import javax.xml.ws.WebServiceFeature;

/**
 * Feature used to request starting a fiber synchronous to the calling
 * thread but allowing it to later switch to run asynchronously to that thread.
 * 
 * @since 2.2.6
 */
public class SyncStartForAsyncFeature
  extends WebServiceFeature {

  public SyncStartForAsyncFeature() {
    enabled = true;
  }

  @Override
  public String getID() {
    return SyncStartForAsyncFeature.class.getSimpleName();
  }
}
