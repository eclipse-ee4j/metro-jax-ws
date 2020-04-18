/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.doclit_fault.client;

import junit.framework.TestCase;

import javax.xml.namespace.QName;
import jakarta.xml.ws.ProtocolException;
import jakarta.xml.ws.soap.SOAPFaultException;
import jakarta.xml.soap.Detail;
import jakarta.xml.soap.DetailEntry;
import java.util.Iterator;


import static testutil.ClientServerTestUtil.getPort;
import testutil.ClientServerTestUtil;
import org.w3c.dom.Element;

public class TestApp extends TestCase {

    public static void main(String [] args) {
        try {
            System.setProperty("uselocal", "true");
            TestApp test = new TestApp("TestApp");
            //test.testProtocolException();
        } catch (Exception e) {
            System.err.println("exception: " + e);
        }
    }

    private static Fault stub;

    public TestApp(String name) throws Exception{
        super(name);
        FaultService service = new FaultService();
        stub = service.getFaultPort();
        ClientServerTestUtil.setTransport(stub, null);
    }

    public void testNullFaultBean() throws Exception{
        try{
            java.lang.String in = "nullBean";
            stub.echo(in);
            assertTrue(false);
        }catch(Fault2Exception e){
            assertTrue(e.getFaultInfo() == null);
            assertTrue(true);
        }
    }

    public void testFault1()
            throws Exception {
        try {
            java.lang.String in = "Fault1";
            java.lang.String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (Fault1Exception e) {
            System.out.println("Expected exception received: " + e.getMessage());
            //e.printStackTrace();
            assertTrue(true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testFault2() {
        try {
            java.lang.String in = "Fault2";
            java.lang.String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (Fault2Exception e) {
            System.out.println("Expected exception received: " + e.getMessage());
            assertTrue(true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testFault3() {
        try {
            String in = "Fault3";
            String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (Fault3Exception e) {
            System.out.println("Expected exception received: " + e.getMessage());
            assertTrue(true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testFault4() {
        try {
            String in = "Fault4";
            String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (Fault4Exception e) {
            System.out.println("Expected exception received: " + e.getMessage());
            assertTrue(true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testNullPointerException() {
        try {
            String in = "NullPointerException";
            String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (ProtocolException e) {
            System.out.println("Expected exception received: " + e.getMessage());
            assertTrue(true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testSOAPFaultException() {
        try {
            String in = "SOAPFaultException";
            String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (ProtocolException e) {
            if (e instanceof SOAPFaultException) {
                System.out.println("Expected exception received: " + e.getMessage());
                Detail detail = ((SOAPFaultException)e).getFault().getDetail();
                assertNotNull(detail);
                Iterator i = detail.getDetailEntries();
                assertTrue(i.hasNext());
                assertEquals(((DetailEntry)i.next()).getElementQName(),
                   new QName("http://faultservice.org/wsdl", "BasicFault"));
            } else
                assertTrue(false);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }


    public void testFault1WithCause()
            throws Exception {
        try {
            String in = "Fault1-SOAPFaultException";
            String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (Fault1Exception e) {
            System.out.println("Expected exception received: " + e.getMessage());
            assertTrue(true);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testSaajBug() throws Fault2Exception, Fault4Exception, Fault3Exception, Fault1Exception {
        try {
            stub.echo("multipleDetails");
        } catch (SOAPFaultException e) {
            System.out.println("Expected exception received: " + e.getMessage());
            Detail detail = e.getFault().getDetail();
            assertNotNull(detail);
            Iterator i = detail.getDetailEntries();
            assertTrue(i.hasNext());
            DetailEntry detail1 = (DetailEntry) i.next();
            assertEquals(detail1.getElementQName(),
                    new QName(
                            "http://www.example.com/faults", "myFirstDetail"));

            String val1 = detail1.getAttribute("msg");
            assertNotNull(val1);

            assertTrue(val1.equals("This is the first detail message."));

            assertTrue(i.hasNext());
            DetailEntry detail2 = (DetailEntry) i.next();
            assertEquals(detail2.getElementQName(),
                    new QName(
                            "http://www.example.com/faults", "mySecondDetail"));
            String val2 = detail2.getAttribute("msg");
            assertNotNull(val2);
            assertTrue(val2.equals("This is the second detail message."));
        }
    }

    public void testProtocolException() throws Exception {
        try {
            String in = "ProtocolException";
            String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (ProtocolException e) {
            System.out.println("Expected exception received: " + e.getMessage());
            if (e.getMessage().indexOf("jakarta.xml.ws.ProtocolException") != -1)
                assertTrue(true);
            else
                assertTrue(false);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testProtocolExceptionWithCause() throws Exception {
        try {
            String in = "ProtocolException2";
            String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (ProtocolException e) {
            System.out.println("Expected exception received: " + e.getMessage());
            if (e.getMessage().indexOf("FaultImpl") != -1)
                assertTrue(true);
            else
                assertTrue(false);
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void COMMENTEDtestRemoteExceptionWithSFECause() throws Exception {
        try {
            String in = "RemoteExceptionWithSFECause";
            String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (ProtocolException e) {
            if (e.getCause() instanceof SOAPFaultException) {
                System.out.println("Expected exception received: " + e.getMessage());
                assertTrue(true);
            } else {
                System.out.println("Expected cause: SOAPFaultException, Got: " + e.getCause());
                assertFalse(false);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void COMMENTEDtestRemoteExceptionWithSFECause2() throws Exception {
        try {
            String in = "RemoteExceptionWithSFECause2";
            String ret = stub.echo(in);
            fail("did not receive an exception");
        } catch (ProtocolException e) {
            if (e.getCause() instanceof SOAPFaultException) {
                System.out.println("Expected exception received: " + e.getMessage());
                assertTrue(true);
            } else {
                System.out.println("Expected cause: SOAPFaultException, Got: " + e.getCause());
                assertFalse(false);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }


}
