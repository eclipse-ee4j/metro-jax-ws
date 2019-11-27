/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.cts.jws_common;

public class Employee {

  private Name name;
  private Department dept;
  private Salary salary;
  private Address address;
  private String title;
  private int type;

  public Employee(Name name, Department dept, Salary salary, Address address, String title, int type) {
    this.name = name;
    this.dept = dept;
    this.salary = salary;
    this.address = address;
    this.title = title;
    this.type = type;
  }

  public Employee() {
    name = new Name();
    dept = new Department();
    salary = new Salary();
    address = new Address();
    title = "";
    type = EmployeeType.PERMANENT;
  }

  public Name getName() throws NameException { return name; }
  public void setName(Name name) throws NameException { this.name = name; }

  public Department getDept() throws DepartmentException { return dept; }
  public void setDept(Department dept) throws DepartmentException { this.dept = dept; }

  public Salary getSalary() throws SalaryException { return salary; }
  public void setSalary(Salary salary) throws SalaryException { this.salary = salary; }

  public Address getAddress() throws AddressException { return address; }
  public void setAddress(Address address) throws AddressException { this.address = address; }

  public String getTitle() throws TitleException { return title; }
  public void setTitle(String title) throws TitleException { this.title = title; }

  public int getType() throws TypeException { return type; }
  public void setType(int type) throws TypeException { this.type = type; }

  public String toString() {
    return name + "::" + dept + "::" + salary + "::" + address + "::" + title + "::" + type;
  }

}

