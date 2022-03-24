/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.jws_common;

@SuppressWarnings({"serial"})
public class Name implements java.io.Serializable {

  private String firstName = "";
  private String lastName = "";

  public Name(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Name() {}

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

  public String toString() {
    return "First Name:" + firstName + " Last Name:" + lastName;
  }
}
