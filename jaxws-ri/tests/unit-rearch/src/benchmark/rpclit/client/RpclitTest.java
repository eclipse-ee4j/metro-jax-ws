/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package benchmark.rpclit.client;

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;
import testutil.benchmark.Benchmark;
import testutil.benchmark.Const;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;
import java.util.List;

/**
 * Base-class for the rpc/lit benchmark.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class RpclitTest extends TestCase implements Const, Benchmark {
    public RpclitTest(String name) throws Exception {
        super(name);

        complexType = of.createComplexType();
        complexType.setVarString(string);
        complexType.setVarInt(intNumber);
        complexType.setVarFloat(floatNumber);

        complexTypeArray = of.createComplexTypeArray();
        List<ComplexType> complexTypeList = complexTypeArray.getValue();
        for (int i=0; i<20; i++) {
            ComplexType complexType = of.createComplexType();
            complexType.setVarString(string);
            complexType.setVarInt(intNumber);
            complexType.setVarFloat(floatNumber);
            complexTypeList.add(i, complexType);
        }

        nestedComplexType = of.createNestedComplexType();
        nestedComplexType.setVarString(string);
        nestedComplexType.setVarInt(intNumber);
        nestedComplexType.setVarFloat(floatNumber);
        nestedComplexType.setVarComplexType(complexType);

        rpclitStringArray = of.createStringArray();
        List<String> stringList = rpclitStringArray.getValue();
        for (int i=0; i<stringArray.length; i++)
            stringList.add(i, stringArray[i]);

        rpclitIntArray = of.createIntegerArray();
        List<Integer> intList = rpclitIntArray.getValue();
        for (int i=0; i<intArray.length; i++)
            intList.add(i, new Integer(intArray[i]));

        rpclitFloatArray = of.createFloatArray();
        List<Float> floatList = rpclitFloatArray.getValue();
        for (int i=0; i<floatArray.length; i++)
            floatList.add(i, new Float(floatArray[i]));

        DatatypeFactory dtf = DatatypeFactory.newInstance();
        Calendar cal = Calendar.getInstance();
        gregorianDate = dtf.newXMLGregorianCalendarDate(2005, 1, 12, DatatypeConstants.FIELD_UNDEFINED);

        // move the stub creation cost out of the benchmark
        createStub();
    }

    public EchoPortType getStub() {
        return stub;
    }

    private void createStub() throws Exception {
        if (stub == null) {
            EchoService service = new EchoService();
            stub = service.getEchoPort();
            ClientServerTestUtil.setTransport(stub);
        }
    }

    static EchoPortType stub = null;
    ObjectFactory of = new ObjectFactory();
    protected ComplexType complexType = null;
    protected ComplexTypeArray complexTypeArray = null;
    protected NestedComplexType nestedComplexType = null;
    protected Enum enumBitFive = Enum.BIT_FIVE;
    protected XMLGregorianCalendar gregorianDate;

    protected StringArray rpclitStringArray = null;
    protected IntegerArray rpclitIntArray = null;
    protected FloatArray rpclitFloatArray = null;
}
