/*
 * Copyright (c) 2004, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.soap12.nosei.client;

import jakarta.xml.ws.soap.SOAPFaultException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import testutil.ClientServerTestUtil;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Holder;
import java.util.ArrayList;
import java.util.List;


/**
 * @author JAX-RPC Development Team
 */
public class EchoClient extends TestCase {
    private QName portQName = new QName("http://echo.org/", "Echo");;
    private static Echo stub;

    public EchoClient(String name) throws Exception{
        super(name);
        if (stub == null) {
            EchoService service = new EchoService();
            stub = service.getEchoPort();      
            ClientServerTestUtil.setTransport(stub);   
	  }
    }



    public void testSimpleDynamic() throws Exception {
        doTestSimple();
    }

    private void doTestSimple() throws Exception {
        Bar bar = new Bar();
        bar.setAge(33);
        Holder<String> strHolder = new Holder<String>();
        strHolder.value = "fred";

        assertTrue(stub.echoString("test").equals("test"));
        assertTrue(stub.echoString("Mary & Paul").equals("Mary & Paul"));
        assertTrue(stub.echoBar(bar).getAge() == bar.getAge());
        assertTrue(stub.echoLong(33L) == 33L);
    }

    public void testExceptionDynamic() throws Exception {
        doTestExceptions();
    }

    private void doTestExceptions() throws Exception {
        try {
            stub.echoString("Exception1");
            assertTrue(false);
        } catch (Exception1_Exception e){
            Exception1 ex = e.getFaultInfo();
            assertTrue(ex.getFaultString().equals("my exception1"));
            assertTrue(ex.isValid());
        }
        try {
            stub.echoString("Fault1");
            assertTrue(false);
         } catch (Fault1 e){
            FooException ex = e.getFaultInfo();
            assertTrue(e.getMessage().equals("fault1"));
            assertTrue(ex.getVarFloat() == 44F);
            assertTrue(ex.getVarInt() == 33);
            assertTrue(ex.getVarString().equals("foo"));
        }
        try {
            stub.echoString("WSDLBarException");
            assertTrue(false);
        } catch (WSDLBarException e){
            Bar ex = e.getFaultInfo();
            assertTrue(e.getMessage().equals("my barException"));
            assertTrue(ex.getAge() == 33);
        }
        try {
            stub.echoString("Fault2");
            assertTrue(false);
        } catch (Fault2_Exception e){
            assertTrue(e.getMessage().equals("my fault2"));
            Fault2 fault2 = e.getFaultInfo();
            assertTrue(fault2.getAge() == 33);
        }
    }

    public void testHoldersDynamic() throws Exception {
        doTestHolders();
    }

    private void doTestHolders() throws Exception {
        Holder<String> strHolder = new Holder<String>();
        strHolder.value = "fred";


        assertTrue(stub.outString("tmp", 44, strHolder).equals("tmp"));
        assertTrue(strHolder.value.equals("tmp44"));
        strHolder.value = "fred";

        assertTrue(stub.inOutString("tmp", strHolder, 44).equals("tmp"));
        assertTrue(strHolder.value.equals("fredfred"));

        Holder<Long> longHolder = new Holder<Long>();
        assertTrue(stub.outLong(33, "tmp", longHolder) == 33);

        assertTrue(longHolder.value == 345L);
        assertTrue(stub.inOutLong(44, longHolder, "tmp") == 44);
        assertTrue(longHolder.value == 690L);
    }

    public void testHeadersDynamic() throws Exception {
        doTestHeaders();
    }

    private void doTestHeaders() throws Exception {
        EchoInHeader echoInHeader = new EchoInHeader();
        echoInHeader.setArg0(33);
        echoInHeader.setArg2("fred");
        EchoInHeaderResponse echoInHeaderResp = stub.echoInHeader(echoInHeader, 34L);
        assertTrue(echoInHeaderResp.getReturn() == 34L);


        EchoIn2Header echoIn2Header = new EchoIn2Header();
        echoIn2Header.setArg0(33);
        echoIn2Header.setArg3("fred");
        EchoIn2HeaderResponse echoIn2HeaderResp = stub.echoIn2Header(echoIn2Header, 34L, "dirk");
        assertTrue(echoIn2HeaderResp.getReturn() == 34L);


        EchoInOutHeader echoInOutHeader = new EchoInOutHeader();
        echoInOutHeader.setArg0(33);
        Holder<Long> longHolder = new Holder<Long>(Long.valueOf(44));
        echoInOutHeader.setArg2("fred");
        EchoInOutHeaderResponse echoInOutHeaderResp = stub.echoInOutHeader(echoInOutHeader, longHolder);
        assertTrue(echoInOutHeaderResp.getReturn().equals("fred88"));
        assertTrue(longHolder.value == 88L);

        EchoOutHeader echoOutHeader = new EchoOutHeader();
        echoOutHeader.setArg0(33);
        longHolder = new Holder<Long>();
        echoOutHeader.setArg2("fred");
//        Holder<EchoOutHeaderResponse> echoOutHeaderResp = new Holder<EchoOutHeaderResponse>();
//        stub.echoOutHeader(echoOutHeader, echoOutHeaderResp, longHolder);
//        assertTrue(echoOutHeaderResp.value.getReturn().equals("fred33"));
        EchoOutHeaderResponse response = stub.echoOutHeader(echoOutHeader, longHolder);
        assertTrue(response.getReturn().equals("fred33"));
        assertTrue(longHolder.value == 33L);


//        EchoOut2Header echoOut2Header = new EchoOut2Header();
//        echoOut2Header.setArg0(33);
//        longHolder = new Holder<Long>();
//        echoOut2Header.setArg3("fred");
////        Holder<EchoOutHeaderResponse> echoOutHeaderResp = new Holder<EchoOutHeaderResponse>();
////        stub.echoOutHeader(echoOutHeader, echoOutHeaderResp, longHolder);
////        assertTrue(echoOutHeaderResp.value.getReturn().equals("fred33"));
//        Holder<String> nameHolder = new Holder<String>();
//        EchoOut2HeaderResponse response2 = stub.echoOut2Header(echoOut2Header, longHolder, nameHolder);
//        assertTrue(response2.getReturn().equals("fred33"));
//        assertTrue(longHolder.value == 33L);
//        assertTrue(nameHolder.value.equals("Fred"));

    }


    public void testArray1Dynamic() throws Exception {
        doTestArray1();
    }

    private void doTestArray1() throws Exception {
        List<String> strArray = new ArrayList<String>();
        strArray.add("Mary");
        strArray.add("Paul");

        List<String> returnArray = stub.echoStringArray(strArray);
        assertTrue(returnArray.size() == strArray.size());
        assertTrue(returnArray.get(0).equals(strArray.get(0)));
        assertTrue(returnArray.get(1).equals(strArray.get(1)));
    }

    public void testArray2Dynamic() throws Exception {
        doTestArray2();
    }


    private void doTestArray2() throws Exception {
        Bar bar = new Bar();
        bar.setAge(33);
        Bar bar2 = new Bar();
        bar2.setAge(44);

        List<Bar> barArray = new ArrayList<Bar>();
        barArray.add(bar);
        barArray.add(bar2);
        List<Bar> resultArray = stub.echoBarArray(barArray);
        assertTrue(resultArray.size() == 2);
        assertTrue(resultArray.get(0).getAge() == bar.getAge());
        assertTrue(resultArray.get(1).getAge() == bar2.getAge());
    }

    public void testOnewayDynamic() throws Exception {
        doTestOneway();
    }


    private void doTestOneway() throws Exception {
        Oneway oneway = new Oneway();
        oneway.setArg0("fred");
        stub.oneway(oneway, 33.3F);
        assertTrue(true);
    }


    public void testOneway2Dynamic() throws Exception {
        doTestOneway2();
    }

    private void doTestOneway2() throws Exception {
        stub.oneway2("fred");
        assertTrue(true);
    }


    public void testVoidDynamic() throws Exception {
        doTestVoid();
    }


    private void doTestVoid() throws Exception {
        stub.voidTest();
        assertTrue(true);
    }

    public void testOverloadedDynamic() throws Exception {
        doTestOverloaded();
    }

    private void doTestOverloaded() throws Exception {
        assertTrue(stub.overloadedOperation("fred").equals("fred"));
//        assertTrue(stub.overloadedOperation2("earnie", " & bert").equals("earnie & bert"));
    }

    public void testSFE() throws Exception {
        try {
            stub.throwException("SFE");
            System.out.println("Expected Exception not Caught");
            assertTrue(false);
        } catch(SOAPFaultException ex) {
            System.out.println("Expected Exception caught");
            assertTrue(true);
        }    
    }


    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(EchoClient.class);
        return suite;
    }

    /*
     * for debugging
     */
    public static void main(String [] args) {
        try {
            System.setProperty("uselocal", "true");
            EchoClient testor = new EchoClient("TestClient");
            testor.testHeadersDynamic();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

