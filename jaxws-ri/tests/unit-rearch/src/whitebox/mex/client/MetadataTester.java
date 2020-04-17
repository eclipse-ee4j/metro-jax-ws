/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.mex.client;

import com.sun.xml.ws.api.server.Container;
import junit.framework.TestCase;
import com.sun.xml.ws.wsdl.parser.RuntimeWSDLParser;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
import com.sun.xml.ws.api.model.wsdl.WSDLService;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.util.JAXWSUtils;
import com.sun.org.apache.xml.internal.resolver.CatalogManager;
import com.sun.org.apache.xml.internal.resolver.tools.CatalogResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLStreamException;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Dispatch;
import jakarta.xml.bind.JAXBContext;
import javax.xml.transform.stream.StreamSource;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Enumeration;
import java.io.IOException;

public class MetadataTester extends TestCase {

    private static final String  mexURL = "src/whitebox/mex/resolver/OnlyOne.xml";
    private static final String  msURL = "src/whitebox/mex/resolver/ms-mex.xml";
    private static final String stsWSDL = "src/whitebox/mex/resolver/sts.wsdl";
    private static final String  abstractFirstURL = "src/whitebox/mex/resolver/abstract-first-mex.xml";

    public void testMSMexResponse() throws IOException, SAXException, XMLStreamException {
        URL url = JAXWSUtils.getFileOrURL(escapeSpace(msURL));
        com.sun.xml.ws.api.model.wsdl.WSDLModel doc = RuntimeWSDLParser.parse(url, new StreamSource(url.toExternalForm()), getResolver(), true, Container.NONE);
        WSDLBoundPortType binding = doc.getBinding(new QName("http://InteropBaseAddress/interop", "PingService10"), new QName("http://InteropBaseAddress/interop", "X10_IPingService"));
        if(binding == null){
            assertTrue(false);
        }

        //test WSDLBoundPortType.getOperation()
        WSDLBoundOperation op = binding.getOperation("http://InteropBaseAddress/interop", "PingRequest");
        assertTrue(op.getName().equals(new QName("http://InteropBaseAddress/interop", "Ping")));
        assertTrue(op.getOperation().getInput().getName().equals("PingRequest"));
        assertTrue(op.getOperation().getOutput().getName().equals("PingResponse"));
    }
    
    public void testAbstractFirstMexResponse() throws IOException, SAXException, XMLStreamException {
        URL url = JAXWSUtils.getFileOrURL(abstractFirstURL);
        com.sun.xml.ws.api.model.wsdl.WSDLModel doc = RuntimeWSDLParser.parse(url, new StreamSource(url.toExternalForm()), getResolver(), true, Container.NONE);
        WSDLBoundPortType binding = doc.getBinding(new QName("http://tempuri.org/", "PingService10"), new QName("http://tempuri.org/", "X10_IPingService"));
        if(binding == null){
            assertTrue(false);
        }
        //test WSDLBoundPortType.getOperation()
        WSDLBoundOperation op = binding.getOperation("http://InteropBaseAddress/interop", "PingRequest");
        assertTrue(op.getName().equals(new QName("http://InteropBaseAddress/interop", "Ping")));
        assertTrue(op.getOperation().getInput().getName().equals("PingRequest"));
        assertTrue(op.getOperation().getOutput().getName().equals("PingResponse"));
    }

    public void testDispatchWithMultiplePort() throws IOException, SAXException, XMLStreamException {
        Service service = Service.create(JAXWSUtils.getFileOrURL(stsWSDL), new QName("http://tempuri.org/", "SecurityTokenService"));
        Dispatch disp = service.createDispatch(new QName("http://tempuri.org/", "WSHttpBinding_ISecurityTokenService"), (JAXBContext) null, Service.Mode.PAYLOAD);
        assertTrue(disp != null);
        disp = service.createDispatch(new QName("http://tempuri.org/", "WSHttpBinding_ISecurityTokenService1"), (JAXBContext) null, Service.Mode.PAYLOAD);
        assertTrue(disp != null);
        disp = service.createDispatch(new QName("http://tempuri.org/", "CustomBinding_ISecurityTokenService1"), (JAXBContext) null, Service.Mode.PAYLOAD);
        assertTrue(disp != null);
        disp = service.createDispatch(new QName("http://tempuri.org/", "CustomBinding_ISecurityTokenService"), (JAXBContext) null, Service.Mode.PAYLOAD);
        assertTrue(disp != null);
    }

    public void testMultiplePort() throws IOException, SAXException, XMLStreamException {
        URL url = JAXWSUtils.getFileOrURL(msURL);
        com.sun.xml.ws.api.model.wsdl.WSDLModel doc = RuntimeWSDLParser.parse(url, new StreamSource(url.toExternalForm()), getResolver(), true, Container.NONE);
        WSDLService service = doc.getService(new QName("http://InteropBaseAddress/interop", "PingService10"));
        WSDLPort port1 = service.get(new QName("http://InteropBaseAddress/interop", "X10_IPingService"));
        assertTrue(port1 != null);
        WSDLPort port2 = service.get(new QName("http://InteropBaseAddress/interop", "X10-NoTimestamp_IPingService"));
        assertTrue(port2 != null);
        WSDLPort port3 = service.get(new QName("http://InteropBaseAddress/interop", "K10_IPingService"));
        assertTrue(port3 != null);
        WSDLPort port4 = service.get(new QName("http://InteropBaseAddress/interop", "KD10_IPingService"));
        assertTrue(port4 != null);
    }

    public void testWSDLModel() throws IOException, SAXException, XMLStreamException {
        URL url = JAXWSUtils.getFileOrURL(mexURL);
        com.sun.xml.ws.api.model.wsdl.WSDLModel doc = RuntimeWSDLParser.parse(url, new StreamSource(url.toExternalForm()), getResolver(), true, Container.NONE);
        WSDLBoundPortType binding = doc.getBinding(new QName("urn:test", "Hello"), new QName("urn:test", "HelloPort"));
        if(binding == null){
            assertTrue(false);
        }

        //test WSDLBoundPortType.getOperation()
        WSDLBoundOperation op = binding.getOperation("urn:test:types", "Hello");
        assertTrue(op.getName().equals(new QName("urn:test", "hello")));
        assertTrue(op.getOperation().getInput().getName().equals("helloRequest"));
        assertTrue(op.getOperation().getOutput().getName().equals("helloResponse"));
        assertTrue(op.getOperation().getFaults().iterator().next().getName().equals("helloFault"));

    }

    private static EntityResolver getResolver() {
        EntityResolver resolver = null;
        if(resolver==null) {
            // set up a manager
            CatalogManager manager = new CatalogManager();
            manager.setIgnoreMissingProperties(true);
            try {
                    manager.setVerbosity(0);
            } catch (SecurityException e) {
                // recover by not setting the debug flag.
            }

            // parse the catalog
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> catalogEnum;
            try {
                if(cl==null)
                    catalogEnum = ClassLoader.getSystemResources("/META-INF/jaxws-catalog.xml");
                else
                    catalogEnum = cl.getResources("/META-INF/jaxws-catalog.xml");

                while(catalogEnum.hasMoreElements()) {
                    URL url = catalogEnum.nextElement();
                    manager.getCatalog().parseCatalog(url);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            resolver = new CatalogResolver(manager);
        }

        return resolver;
    }

    private String escapeSpace( String url ) {
        // URLEncoder didn't work.
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < url.length(); i++) {
            // TODO: not sure if this is the only character that needs to be escaped.
            if (url.charAt(i) == ' ')
                buf.append("%20");
            else
                buf.append(url.charAt(i));
        }
        return buf.toString();
    }

}
