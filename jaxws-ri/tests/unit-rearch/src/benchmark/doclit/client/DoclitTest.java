/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package benchmark.doclit.client;

import junit.framework.TestCase;
import testutil.ClientServerTestUtil;
import testutil.benchmark.Benchmark;
import testutil.benchmark.Const;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;

/**
 * Base-class for the doc/lit benchmark.
 *
 * @author JAX-RPC RI Development Team
 */
public abstract class DoclitTest extends TestCase implements Const, Benchmark {

    public DoclitTest(String name) {
        super(name);
        try {
            complexType = of.createComplexType();
            complexType.setVarString(string);
            complexType.setVarInt(intNumber);
            complexType.setVarFloat(floatNumber);

            for (int i=0; i< 20; i++) {
                complexTypeArray[i] = of.createComplexType();
                complexTypeArray[i].setVarString(string);
                complexTypeArray[i].setVarInt(intNumber);
                complexTypeArray[i].setVarFloat(floatNumber);
            }

            nestedComplexType = of.createNestedComplexType();
            nestedComplexType.setVarString(string);
            nestedComplexType.setVarInt(intNumber);
            nestedComplexType.setVarFloat(floatNumber);
            nestedComplexType.setVarComplexType(complexType);

            DatatypeFactory dtf = DatatypeFactory.newInstance();
            Calendar cal = Calendar.getInstance();
            gregorianDate = dtf.newXMLGregorianCalendarDate(2005, 1, 12, DatatypeConstants.FIELD_UNDEFINED);

            // move the stub creation cost out of the benchmark
            createStub();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    protected final EchoPortType getStub() throws Exception {
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
    protected ComplexType[] complexTypeArray = new ComplexType[20];
    protected NestedComplexType nestedComplexType = null;
    protected Enum enumBitFive = Enum.BIT_FIVE;
    protected XMLGregorianCalendar gregorianDate = null;

}
