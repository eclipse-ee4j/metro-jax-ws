/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.assembler;

import com.sun.xml.ws.api.*;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.api.client.WSPortInfo;
import com.sun.xml.ws.api.message.Header;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubelineAssembler;
import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.binding.BindingImpl;
import com.sun.xml.ws.client.WSServiceDelegate;
import com.sun.xml.ws.developer.WSBindingProvider;
import com.sun.xml.ws.policy.PolicyException;
import junit.framework.TestCase;
import org.glassfish.gmbal.ManagedObjectManager;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Binding;
import jakarta.xml.ws.EndpointReference;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.soap.AddressingFeature;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
public class TubelineAssemblerFactoryImplTest extends TestCase {

    private static final String NAMESPACE = "http://service1.test.ws.xml.sun.com/";
    private static final URI ADDRESS_URL;
    

    static {
        try {
            ADDRESS_URL = new URI("http://localhost:8080/dispatch/Service1Service");
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Failed to initialize address URI", e);
        }
    }

    public TubelineAssemblerFactoryImplTest(String testName) {
        super(testName);
    }

    public void testCreateClientNull() {
        try {
            getAssembler(BindingID.SOAP11_HTTP).createClient(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
        }
    }

    public void testCreateServerNull() {
        try {
            getAssembler(BindingID.SOAP11_HTTP).createServer(null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
        }
    }

    private class TestResourceLoader extends ResourceLoader {

        @Override
        public URL getResource(String resourceName) throws MalformedURLException {
            return Thread.currentThread().getContextClassLoader().getResource(resourceName);
        }
        
    }
    public void testAlternateConfigFileName() throws Exception {
        final BindingID bindingId = BindingID.SOAP11_HTTP;
        final  String ALTERNATE_FILE_NAME = "tubes-config/jaxws-tubes-alternate.xml";
        final Container container = new Container() {
            public <S> S getSPI(Class<S> spiType) {
                if (spiType.isAssignableFrom(MetroConfigName.class)) {
                    return spiType.cast( new MetroConfigName()  {

                        @Override
                        public String getDefaultFileName() {
                            return ALTERNATE_FILE_NAME;
                        }

                        @Override
                        public String getAppFileName() {
                            return ALTERNATE_FILE_NAME;
                        }
                        
                    });
                } else if (spiType.isAssignableFrom(ResourceLoader.class)) {
                    return spiType.cast( new TestResourceLoader());
                }
                return null;
            }   
        };
        final ClientTubeAssemblerContext jaxwsContext = getClientContext(bindingId, container);
        MetroTubelineAssembler assembler = (MetroTubelineAssembler)getAssembler(bindingId);
        TubelineAssemblyController tubelineAssemblyController = assembler.getTubelineAssemblyController();
        DefaultClientTubelineAssemblyContext context = new DefaultClientTubelineAssemblyContext(jaxwsContext);
        Collection<TubeCreator> tubeCreators = tubelineAssemblyController.getTubeCreators(context);
        assertEquals(2, tubeCreators.size());
        
    }
    /**
     * Test client creation with parameters that correspond to a dispatch client
     * with no wsit-client.xml and with no WSDL.
     */
    public void testCreateDispatchClientNoConfig() throws Exception {
        final BindingID bindingId = BindingID.SOAP11_HTTP;
        final Container container = MockupMetroConfigLoader.createMockupContainer("tubes-config/jaxws-tubes-default.xml");

        final ClientTubeAssemblerContext context = getClientContext(bindingId, container);
        final Tube tubeline = getAssembler(bindingId).createClient(context);
        assertNotNull(tubeline);
    }

    private ClientTubeAssemblerContext getClientContext(
            final BindingID bindingId,
            final Container container) {
        final WSBinding binding = bindingId.createBinding();
        final EndpointAddress address = new EndpointAddress(ADDRESS_URL);
        final WSDLPort port = null;
        final QName serviceName = new QName(NAMESPACE, "Service1Service");
        WSService service =  WSService.create(serviceName);
        final QName portName = new QName(NAMESPACE, "Service1Port");
        // Corresponds to Service.addPort(portName, bindingId, address)
        service.addPort(portName, bindingId.toString(), ADDRESS_URL.toString());
        final WSPortInfo portInfo = ((WSServiceDelegate) service).safeGetPort(portName);

        WSBindingProvider wsbp = new WSBindingProvider() {

            public void setOutboundHeaders(List<Header> headers) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void setOutboundHeaders(Header... headers) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void setOutboundHeaders(Object... headers) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public List<Header> getInboundHeaders() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void setAddress(String address) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public WSEndpointReference getWSEndpointReference() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public WSPortInfo getPortInfo() {
                return portInfo;
            }

            public Map<String, Object> getRequestContext() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Map<String, Object> getResponseContext() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Binding getBinding() {
                return binding;
            }

            public EndpointReference getEndpointReference() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public <T extends EndpointReference> T getEndpointReference(Class<T> clazz) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void close() throws IOException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public ManagedObjectManager getManagedObjectManager() {
                return null;
            }

            public Set<Component> getComponents() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public <S> S getSPI(Class<S> type) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        final ClientTubeAssemblerContext context = new ClientTubeAssemblerContext(
                address,
                port,
                wsbp,
                binding,
                container,
                ((BindingImpl)binding).createCodec(),
                null, null);
        return context;
    }

    /**
     * Test client creation with parameters that correspond to a dispatch client
     * with wsit-client.xml.
     */
    public void testCreateDispatchClientNoPoliciesConfig() throws PolicyException {
        Tube tubeline = testDispatch("nopolicies.xml");
        assertNotNull(tubeline);
    }

    /**
     * Test client creation with parameters that correspond to a dispatch client
     * with wsit-client.xml.
     */
    public void testCreateDispatchClientAllFeaturesConfig() throws PolicyException {
        Tube tubeline = testDispatch("allfeatures.xml");
        assertNotNull(tubeline);
    }

    /**
     * Test client creation with parameters that correspond to a dispatch client
     * with wsit-client.xml.
     */
    public void testCreateDispatchClientNoServiceMatchConfig() throws PolicyException {
        Tube tubeline = testDispatch("noservicematch.xml");
        assertNotNull(tubeline);
    }

    /**
     * Execute a sequence that corresponds to:
     * <pre>
     *   Service.createService(null, serviceName);
     *   Service.addPort(portName, bindingId, address);
     * </pre>
     */
    private Tube testDispatch(String configFileName) throws PolicyException {
        final URL wsdlLocation = null;
        final QName serviceName = new QName(NAMESPACE, "Service1Service");
        // Corresponds to Service.createService(wsdlLocation, serviceName)
        final WSServiceDelegate serviceDelegate = new WSServiceDelegate(wsdlLocation, serviceName, Service.class);

        final QName portName = new QName(NAMESPACE, "Service1Port");
        final BindingID bindingId = BindingID.SOAP11_HTTP;
        // Corresponds to Service.addPort(portName, bindingId, address)
        serviceDelegate.addPort(portName, bindingId.toString(), ADDRESS_URL.toString());

        final EndpointAddress address = new EndpointAddress(ADDRESS_URL);
        final WSDLPort port = null;
        final WSPortInfo portInfo = serviceDelegate.safeGetPort(portName);
        final WSBinding binding = bindingId.createBinding(new AddressingFeature(true));
        final Container container = MockupMetroConfigLoader.createMockupContainer("tubes-config/" + configFileName);

        WSBindingProvider wsbp = new WSBindingProvider() {

            public void setOutboundHeaders(List<Header> headers) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void setOutboundHeaders(Header... headers) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void setOutboundHeaders(Object... headers) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public List<Header> getInboundHeaders() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void setAddress(String address) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public WSEndpointReference getWSEndpointReference() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public WSPortInfo getPortInfo() {
                return portInfo;
            }

            public Map<String, Object> getRequestContext() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Map<String, Object> getResponseContext() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Binding getBinding() {
                return binding;
            }

            public EndpointReference getEndpointReference() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public <T extends EndpointReference> T getEndpointReference(Class<T> clazz) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void close() throws IOException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public ManagedObjectManager getManagedObjectManager() {
                return null;
            }

            public Set<Component> getComponents() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public <S> S getSPI(Class<S> type) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };

        final ClientTubeAssemblerContext context = new ClientTubeAssemblerContext(
                address, 
                port,
                wsbp,
                binding,
                container,
                ((BindingImpl)binding).createCodec(),
                null, null);

        return getAssembler(bindingId).createClient(context);
    }

    private TubelineAssembler getAssembler(BindingID bindingId) {
        return new MetroTubelineAssembler(bindingId, MetroTubelineAssembler.JAXWS_TUBES_CONFIG_NAMES);
    }
}
