/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.seinoimpl.server;

import java.util.Date;

public class Bar {
   private int age; 
   private Date dob;
   
   public Bar() {}

   public int getAge() {
       return age;
   }

   public void setAge(int age) {
       this.age = age;
   }


   public Date getDob() {
       return dob;
   }

   public void setDob(Date dob) {
       this.dob = dob;
   }
}
