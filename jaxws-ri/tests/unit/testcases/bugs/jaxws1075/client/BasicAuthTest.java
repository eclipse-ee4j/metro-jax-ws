/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1075.client;

import java.util.Map;
import java.util.List;
import java.util.StringTokenizer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.Service;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * HTTP Basic Auth negtive test for 403 error
 *
 * @author qiang.wang@oracle.com
 */
public class BasicAuthTest extends TestCase {

    public BasicAuthTest(String name) {
        super(name);
    }

    /*
     * Tests Standard HTTP Authorization header on server side
     */
    public void testHttpMsgCtxt() throws Exception {
        final String prop = "com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump";
        String oldV = System.getProperty(prop);
        oldV = (oldV == null) ? "" : oldV;
	System.setProperty(prop,"true");

        Hello proxy = new HelloService().getHelloPort();
        BindingProvider bp = (BindingProvider)proxy;
        bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "auth-user");
        bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "auth-pass");


        PrintStream ps = System.err;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        System.setErr(new PrintStream(baos));

        try {
           proxy.testHttpProperties();
        } catch (Exception ex) {
          ex.printStackTrace();
          //exception must not be soapfault
          assertTrue("The exception can not be soapfault exception", !(ex instanceof javax.xml.ws.ProtocolException));
        } finally{
           System.setErr(ps);
           System.setProperty(prop, oldV);
        }

	String dumpStr = baos.toString();

	System.out.println(dumpStr);
        boolean has403 = false;
        StringTokenizer tokenizer = new StringTokenizer(dumpStr, "\n");
        while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                if (token.indexOf("HTTP status code") != -1) {
		  System.out.println("HTTP status code:" + token);
                  if(token.indexOf("403") > 0)
                     has403 = true;
                }
        }

        assertTrue("Server does not return 403 code", has403); 
   }
    
}
