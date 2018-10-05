/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.jws_common;

public class AddressException extends Exception
{
  private String message = "Address Error";

  public AddressException(String message)
  {
    super(message);
    this.message = message;
  }

  public AddressException() { super(); }
}
