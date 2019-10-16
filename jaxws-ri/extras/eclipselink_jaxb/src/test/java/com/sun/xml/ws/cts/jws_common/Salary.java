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

public class Salary {

  private int salary = 0;
  private int bonusPercentage = 0;
  private String currency = "USD";

  public Salary(int salary, int bonusPercentage, String currency) {
    this.salary = salary;
    this.bonusPercentage = bonusPercentage;
    this.currency = currency;
  }

  public Salary(int salary, int bonusPercentage) {
    this.salary = salary;
    this.bonusPercentage = bonusPercentage;
  }

  public Salary() {}

  public int getSalary() { return salary; }
  public void setSalary(int salary) { this.salary = salary; }

  public int getBonusPercentage() { return bonusPercentage; }
  public void setBonusPercentage(int bonusPercentage) { this.bonusPercentage = bonusPercentage; }

  public String getCurrency() { return currency; }
  public void setCurrency(String currency) { this.currency = currency; }

  public String toString() {
    return "Salary:$" + salary + " Bonus Percentage:%" + bonusPercentage + " Currency:" + currency;
  }

}

