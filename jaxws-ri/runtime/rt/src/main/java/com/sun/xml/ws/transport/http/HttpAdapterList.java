/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http;

import com.sun.xml.ws.transport.http.DeploymentDescriptorParser.AdapterFactory;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.api.server.PortAddressResolver;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.istack.NotNull;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.AbstractList;
import java.util.Map.Entry;

/**
 * List of {@link HttpAdapter}s created together.
 *
 * <p>
 * Some cases WAR file may contain multiple endpoints for ports in a WSDL.
 * If the runtime knows these ports, their port addresses can be patched.
 * This class keeps a list of {@link HttpAdapter}s and use that information to patch
 * multiple port addresses.
 *
 * <p>
 * Concrete implementations of this class need to override {@link #createHttpAdapter}
 * method to create implementations of {@link HttpAdapter}.
 *
 * @author Jitendra Kotamraju
 */
public abstract class HttpAdapterList<T extends HttpAdapter> extends AbstractList<T> implements AdapterFactory<T> {
    private final List<T> adapters = new ArrayList<T>();
    private final Map<PortInfo, String> addressMap = new HashMap<PortInfo, String>();

    // TODO: documented because it's used by AS
    @Override
    public T createAdapter(String name, String urlPattern, WSEndpoint<?> endpoint) {
        T t = createHttpAdapter(name, urlPattern, endpoint);
        adapters.add(t);
        WSDLPort port = endpoint.getPort();
        if (port != null) {
            PortInfo portInfo = new PortInfo(port.getOwner().getName(),port.getName().getLocalPart(), endpoint.getImplementationClass());
            addressMap.put(portInfo, getValidPath(urlPattern));
        }
        return t;
    }

    /**
     * Implementations need to override this one to create a concrete class
     * of HttpAdapter
     */
    protected abstract T createHttpAdapter(String name, String urlPattern, WSEndpoint<?> endpoint);

    /**
     * @return urlPattern without "/*"
     */
    private String getValidPath(@NotNull String urlPattern) {
        if (urlPattern.endsWith("/*")) {
            return urlPattern.substring(0, urlPattern.length() - 2);
        } else {
            return urlPattern;
        }
    }

    /**
     * Creates a PortAddressResolver that maps portname to its address
     *
     * @param endpointImpl application endpoint Class that eventually serves the request.
     */
    public PortAddressResolver createPortAddressResolver(final String baseAddress, final Class<?> endpointImpl) {
        return new PortAddressResolver() {
            @Override
            public String getAddressFor(@NotNull QName serviceName, @NotNull String portName) {
                String urlPattern = addressMap.get(new PortInfo(serviceName,portName, endpointImpl));
                if (urlPattern == null) {
                    //if a WSDL defines more ports, urlpattern is null (portName does not match endpointImpl)
                    //so fallback to the default behaviour where only serviceName/portName is checked
                    for (Entry<PortInfo, String> e : addressMap.entrySet()) {
                        if (serviceName.equals(e.getKey().serviceName) && portName.equals(e.getKey().portName)) {
                                urlPattern = e.getValue();
                                break;
                        }
                    }
                }
                return (urlPattern == null) ? null : baseAddress+urlPattern;
            }
        };
    }


    @Override
    public T get(int index) {
        return adapters.get(index);
    }

    @Override
    public int size() {
        return adapters.size();
    }

    private static class PortInfo {
        private final QName serviceName;
        private final String portName;
        private final Class<?> implClass;

        PortInfo(@NotNull QName serviceName, @NotNull String portName, Class<?> implClass) {
            this.serviceName = serviceName;
            this.portName = portName;
            this.implClass = implClass;
        }

        @Override
        public boolean equals(Object portInfo) {
            if (portInfo instanceof PortInfo) {
                PortInfo that = (PortInfo)portInfo;
                if (this.implClass == null) {
                    return this.serviceName.equals(that.serviceName) && this.portName.equals(that.portName) && that.implClass == null;
                }
                return this.serviceName.equals(that.serviceName) && this.portName.equals(that.portName) && this.implClass.equals(that.implClass);
            }
            return false;
        }

        @Override
        public int hashCode() {
            int retVal = serviceName.hashCode()+portName.hashCode();
            return implClass != null ? retVal + implClass.hashCode() : retVal;
        }
    }
}
