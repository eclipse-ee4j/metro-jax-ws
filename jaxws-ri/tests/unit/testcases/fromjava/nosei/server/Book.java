/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nosei.server;


public enum Book {
    HTPJ("How to Program Java", "2005"),
    JWSDP("Java Web Services Developers Pack", "2005");

    private final String title;
    private final String yearPublished;

    Book(String title, String year) {
        this.title = title;
        this.yearPublished = year;
    }

    public String getTitle() {
        return title;
    }
  
    public String getYearPublished() {
        return yearPublished;
    }
}
