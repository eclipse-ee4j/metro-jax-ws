/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model;

import junit.framework.TestCase;

import java.util.List;
import java.util.HashMap;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * VM signature test for wrapper bean fields
 * 
 * @author Jitendra Kotamraju
 */
public class FieldSignatureTest<K,V> extends TestCase {

    public void test() throws Exception {
        assertEquals("B", FieldSignature.vms(byte.class));
        assertEquals("[B", FieldSignature.vms(byte[].class));
        assertEquals("Ljava/lang/String;", FieldSignature.vms(String.class));
        assertEquals("[Ljava/lang/Object;", FieldSignature.vms((new Object[3]).getClass()));
        assertEquals("[[[[[[[I", FieldSignature.vms((new int[3][4][5][6][7][8][9]).getClass()));
    }

    public List<List<String>[]> type1;
    public void test1() throws Exception {
        Field f = FieldSignatureTest.class.getField("type1");
        assertEquals("Ljava/util/List<[Ljava/util/List<Ljava/lang/String;>;>;", FieldSignature.vms(f.getGenericType()));
    }

    public List<?> type2;
    public void test2() throws Exception { 
        Field f = FieldSignatureTest.class.getField("type2");
        assertEquals("Ljava/util/List<*>;", FieldSignature.vms(f.getGenericType()));
    }

    public List<? super Integer> type3;
    public void test3() throws Exception {
        Field f = FieldSignatureTest.class.getField("type3");
        assertEquals("Ljava/util/List<-Ljava/lang/Integer;>;", FieldSignature.vms(f.getGenericType()));
    }

    public List<? extends Number> type4;
    public void test4() throws Exception {
        Field f = FieldSignatureTest.class.getField("type4");
        assertEquals("Ljava/util/List<+Ljava/lang/Number;>;", FieldSignature.vms(f.getGenericType()));
    }

    public byte[] type5;
    public void test5() throws Exception {
        Field f = FieldSignatureTest.class.getField("type5");
        assertEquals("[B", FieldSignature.vms(f.getGenericType()));
    }

    // Reflection API gives "GenericArrayType" for byte[] in this method
    // see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5041784
    public void mytest(byte[] a, List<String> list) {
    }
    public void test6() throws Exception {
        Method[] ms = FieldSignatureTest.class.getMethods();
        Method m = FieldSignatureTest.class.getMethod("mytest", byte[].class, List.class);
        assertEquals("[B", FieldSignature.vms(m.getGenericParameterTypes()[0]));
    }

// While creating wrapper bean fields, it doesn't create with TypeVariables
// Otherwise, the type variable need to be declared in the wrapper bean class
//
//    public HashMap<K,V> type5;
//    public void test5() throws Exception {
//        Field f = FieldSignatureTest.class.getField("type5");
//        assertEquals("Ljava/util/HashMap<TK;TV;>;", FieldSignature.vms(f.getGenericType()));
//    }

}
