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

public class Address {

  private String email = "";
  private String phone = "";
  private String street = "";
  private String city = "San Francisco";
  private String state = "CA";
  private String zipcode = "94104";
  private String country = "U.S.";

  public Address(String email, String phone, String street, String city, String state, String zipcode, String country) {
    this.email = email;
    this.phone = phone;
    this.street = street;
    this.city = city;
    this.state= state;
    this.zipcode = zipcode;
    this.country = country;
  }

  public Address() {}

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getPhone() { return phone; }
  public void setPhone(String phone) { this.phone = phone; }

  public String getStreet() { return street; }
  public void setStreet(String street) { this.street = street; }

  public String getCity() { return city; }
  public void setCity(String city) { this.city = city; }

  public String getState() { return state; }
  public void setState(String state) { this.state = state; }

  public String getZipcode() { return zipcode; }
  public void setZipcode(String zipcode) { this.zipcode = zipcode; }

  public String getCountry() { return country; }
  public void setCountry(String country) { this.country = country; }

  public String toString() {
    return "email:" + email + " phone:" + phone + " street:" + street + " city:" + city + " state:" + state + " zipcode:" + zipcode + 
      " country:" + country;
  }
}



