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

import junit.framework.TestCase;
import org.junit.Assert;

/**
 *
 * @author Marek Potociar (marek.potociar@sun.com)
 */
public abstract class AbstractPolicyApiClassTestBase extends TestCase {
    
    /**
     * Creates a new instance of AbstractPolicyApiClassTestBase
     */
    public AbstractPolicyApiClassTestBase(String name) {
        super(name);
    }
    
    protected abstract Object[][] getEqualInstanceRows() throws Exception;
    
    public final void testEqualsWithNull() throws Exception{
        for (Object[] instanceRow : getEqualInstanceRows()) {
            for (Object instance : instanceRow) {
                Assert.assertNotNull("Instance of class '" + instance.getClass().getName() + "' must not return true when comparing for equality with 'null' value.", instance);
            }
        }
    }
    
    public final void testEqualsOnManyEqualPolicies() throws Exception {
        Object[][] testbed = getEqualInstanceRows();
        String className = testbed[0][0].getClass().getName();
        int index = 0;
        for (Object[] instanceRow : testbed) {
            for (int i = 0; i < instanceRow.length; i++) {                
                Assert.assertEquals("'" + index + "' array of equal '" + className + "' instances comparison failed on comparing instance '" + i + "' with itself", instanceRow[i], instanceRow[i]);
                for (int j = i + 1; j < instanceRow.length; j++) {
//                    System.out.println( instanceRow[i].toString() + "\n");
//                    System.out.println( instanceRow[j].toString() + "\n");
                    Assert.assertEquals("'" + index + "' row of equal '" + className + "' instances comparison failed on comparing instance '" + i + "' to instance '" + j + "'", instanceRow[i], instanceRow[j]);
                    Assert.assertEquals("'" + index + "' row of equal '" + className + "' instances comparison failed on comparing instance '" + j + "' to instance '" + i + "'", instanceRow[j], instanceRow[i]);
                }
            }
            index++;
        }
    }    
}
