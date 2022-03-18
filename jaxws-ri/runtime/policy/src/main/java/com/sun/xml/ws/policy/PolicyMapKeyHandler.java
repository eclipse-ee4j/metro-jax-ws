/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy;


/**
 * This interface defines method that is used to handle actual equality comparison and hash code generation for PolicyMapKey object.
 * <br>
 * The different implementations of this interface may allow different strategies to be applied for operations mentioned above. This feature 
 * is used within {@link PolicyMap} to restrict set of fields to be compared when searching different policy scope maps.
 * 
 * 
 * 
 * @author Marek Potociar
 */
interface PolicyMapKeyHandler {
    boolean areEqual(PolicyMapKey locator1, PolicyMapKey locator2);
    
    int generateHashCode(PolicyMapKey locator);    
}
