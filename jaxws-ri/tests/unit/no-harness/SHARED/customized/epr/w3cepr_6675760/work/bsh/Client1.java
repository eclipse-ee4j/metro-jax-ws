/*
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bsh;

import jakarta.activation.*;
import jakarta.xml.ws.*;
import jakarta.xml.ws.soap.*;
import jakarta.xml.ws.handler.*;
import jakarta.xml.ws.handler.soap.*;
import jakarta.xml.bind.*;
import jakarta.xml.soap.*;
import javax.xml.namespace.*;
import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.util.*;
import java.util.concurrent.*;
//import epr.w3cepr_6675760.client.*;
import java.net.URL;
import java.net.URI;

import static junit.framework.TestCase.*;
import static bsh.Util.*;


public class Client1 {

    private static int DEPLOY_PORT = Integer.valueOf(System.getProperty("deployPort"));

    static String home = "/Users/miran/dev/clean/jaxws-ri/jaxws-ri/tests/unit/testcases/epr/w3cepr_6675760";

    // TODO
    static List<URL> wsdlUrls = new ArrayList<URL>();


    // injected from bsh scripts

        static Object getField(Object instance, Class cls, String name) throws Exception {
            java.lang.reflect.Field f = cls.getDeclaredField(name);
            f.setAccessible(true);
            return f.get(instance);
        }
    


    static URI createUri(String s) {
        try {
            return new URI(s);
        } catch(Throwable t){
            throw new RuntimeException(t);
        }
    }

    public static void main(String[] args) throws Throwable {
        // bsh script START
        
        javax.xml.transform.Source source = new javax.xml.transform.stream.StreamSource(
        new java.io.FileInputStream(resource("w3cepr.xml")));
        jakarta.xml.ws.wsaddressing.W3CEndpointReference jaxwsEPR = new jakarta.xml.ws.wsaddressing.W3CEndpointReference(source);
        String str = jaxwsEPR.toString();

        Object address = getField(jaxwsEPR, jakarta.xml.ws.wsaddressing.W3CEndpointReference.class, "address");
        assertTrue(address != null);

        Object referenceParameters = getField(jaxwsEPR, jakarta.xml.ws.wsaddressing.W3CEndpointReference.class, "referenceParameters");
        assertTrue(referenceParameters != null);

        Object metadata = getField(jaxwsEPR, jakarta.xml.ws.wsaddressing.W3CEndpointReference.class, "metadata");
        assertTrue(metadata != null);

        Object elements = getField(jaxwsEPR, jakarta.xml.ws.wsaddressing.W3CEndpointReference.class, "elements");
        assertTrue(elements != null);

        Object attributes = getField(jaxwsEPR, jakarta.xml.ws.wsaddressing.W3CEndpointReference.class, "attributes");
        assertTrue(attributes != null);

        assertTrue(str.indexOf("eprattr") != -1);
        assertTrue(str.indexOf("epr-attribute") != -1);

        assertTrue(str.indexOf("addrattr") != -1);
        assertTrue(str.indexOf("address-attribute") != -1);

        assertTrue(str.indexOf("refattr") != -1);
        assertTrue(str.indexOf("ref-attribute") != -1);
        assertTrue(str.indexOf("refelem1") != -1);
        assertTrue(str.indexOf("ref-element-1-text") != -1);
        assertTrue(str.indexOf("refelem2") != -1);
        assertTrue(str.indexOf("ref-element-2-text") != -1);

        assertTrue(str.indexOf("metaattr") != -1);
        assertTrue(str.indexOf("meta-attribute") != -1);
        assertTrue(str.indexOf("metaelem") != -1);
        assertTrue(str.indexOf("metaelemattr") != -1);
        assertTrue(str.indexOf("meta-element-attribute") != -1);
        assertTrue(str.indexOf("meta-element-text") != -1);

        assertTrue(str.indexOf("eprelem") != -1);
        assertTrue(str.indexOf("eprelemattr") != -1);
        assertTrue(str.indexOf("epr-element-attribute") != -1);
        assertTrue(str.indexOf("epr-element-text") != -1);
    
        // bsh script END

        System.out.println("= TEST PASSED: Client1");
    }

}
