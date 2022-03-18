/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.endpoint.client;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import jakarta.xml.ws.Endpoint;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.net.URL;
import java.io.*;
import jakarta.xml.ws.Provider;
import javax.xml.transform.Source;
import jakarta.xml.ws.WebServiceProvider;
import java.io.StringReader;
import jakarta.annotation.Resource;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.http.HTTPBinding;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Iterator;
import jakarta.xml.ws.WebServiceException;

import com.sun.xml.ws.developer.JAXWSProperties;
import com.sun.net.httpserver.HttpExchange;


/**
 * @author Jitendra Kotamraju
 */
public class MessageContextTester extends TestCase {

    @WebServiceProvider
    static class MyProvider implements Provider<Source> {
        @Resource
        WebServiceContext ctxt;

        public Source invoke(Source source) {
            MessageContext msgCtxt = ctxt.getMessageContext();

            // toString()
            System.out.println("MessageContext="+msgCtxt);

            // Test Map.get()
            String method = (String)msgCtxt.get(MessageContext.HTTP_REQUEST_METHOD);
            if (method == null || !method.equals("GET")) {
                throw new WebServiceException("Expected method=GET but got="+method);
            }

            // Test iterator
            Iterator<Map.Entry<String, Object>> i = msgCtxt.entrySet().iterator();
            while(i.hasNext()) {
                Map.Entry<String, Object> e = i.next();
            }

            // Test keySet() iterator
            Iterator<String> i1 = msgCtxt.keySet().iterator();
            while(i1.hasNext()) {
                i1.next();
            }

            // Test Map.size()
            int no = msgCtxt.size();

            // Test Map.put()
            msgCtxt.put("key", "value");

            // Test Map.get() for the new addition
            String value = (String)msgCtxt.get("key");
            if (value == null || !value.equals("value")) {
                throw new WebServiceException("Expected=value but got="+value);
            }

            // Test Map.size() for the new addition
            int num = msgCtxt.size();
            if (num != no+1) {
                throw new WebServiceException("Expected no="+(no+1)+" but got="+num);
            }

            // Test Map.remove() for the new addition
            value = (String)msgCtxt.remove("key");
            if (value == null || !value.equals("value")) {
                throw new WebServiceException("Expected=value but got="+value);
            }

            // Test Map.size() for the removal
            num = msgCtxt.size();
            if (num != no) {
                throw new WebServiceException("Expected="+no+" but got="+num);
            }

            // Test iterator.remove()
            msgCtxt.put("key", "value");
            i = msgCtxt.entrySet().iterator();
            while(i.hasNext()) {
                Map.Entry<String, Object> e = i.next();
                if (e.getKey().equals("key")) {
                    i.remove();
                }
            }
            num = msgCtxt.size();
            if (num != no) {
                throw new WebServiceException("Expected="+no+" but got="+num);
            }
            value = (String)msgCtxt.get("key");
            if (value != null) {
                throw new WebServiceException("Expected=null"+" but got="+value);
            }

            // Test Map.putAll()
            Map<String, Object> two = new HashMap<String, Object>();
            two.put("key1", "value1");
            two.put("key2", "value2");
            msgCtxt.putAll(two);
            num = msgCtxt.size();
            if (num != no+2) {
                throw new WebServiceException("Expected="+(no+2)+" but got="+num);
            }
            value = (String)msgCtxt.get("key1");
            if (value == null || !value.equals("value1")) {
                throw new WebServiceException("Expected=value1"+" but got="+value);
            }
            value = (String)msgCtxt.get("key2");
            if (value == null || !value.equals("value2")) {
                throw new WebServiceException("Expected=value2"+" but got="+value);
            }

            String replyElement = new String("<p>hello world</p>");
            StreamSource reply = new StreamSource(new StringReader (replyElement));
            return reply;
        }
    }

    public void testMessageContext() throws Exception {
        int port = Util.getFreePort();
        String address = "http://127.0.0.1:"+port+"/";
        Endpoint e = Endpoint.create(HTTPBinding.HTTP_BINDING, new MyProvider());
        e.publish(address);

        URL url = new URL(address);
        InputStream in = url.openStream();
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        String str;
        while ((str=rdr.readLine()) != null);

        e.stop();
    }

    @WebServiceProvider
    static class MessageContextProvider implements Provider<Source> {
        @Resource
        WebServiceContext ctxt;

        public Source invoke(Source source) {
            MessageContext msgCtxt = ctxt.getMessageContext();
            
            String qs = (String)msgCtxt.get(MessageContext.QUERY_STRING);
            if (qs == null || !qs.equals("a=b")) {
                throw new WebServiceException("Unexpected QUERY_STRING. Expected: "+"a=b"+" Got: "+qs);
            }

            String pathInfo = (String)msgCtxt.get(MessageContext.PATH_INFO);
            if (pathInfo == null || !pathInfo.equals("/a/b")) {
                throw new WebServiceException("Unexpected PATH_INFO. Expected: "+"/a/b"+" Got: "+pathInfo);
            }

            HttpExchange exchange = (HttpExchange)msgCtxt.get(JAXWSProperties.HTTP_EXCHANGE);
            if (exchange == null ) {
                throw new WebServiceException("HttpExchange object is not populated");
            }

            String replyElement = new String("<p>hello world</p>");
            StreamSource reply = new StreamSource(new StringReader (replyElement));
            return reply;
        }
    }

    public void testHttpProperties() throws Exception {
        int port = Util.getFreePort();
        String address = "http://127.0.0.1:"+port+"/hello";
        Endpoint e = Endpoint.create(HTTPBinding.HTTP_BINDING, new MessageContextProvider());
        e.publish(address);

        URL url = new URL(address+"/a/b?a=b");
        InputStream in = url.openStream();
        BufferedReader rdr = new BufferedReader(new InputStreamReader(in));
        String str;
        while ((str=rdr.readLine()) != null);

        e.stop();
    }

}

