/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.local;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.server.ServiceDefinition;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.api.server.WebServiceContextDelegate;
import com.sun.xml.ws.transport.http.WSHTTPConnection;
import com.sun.xml.ws.util.ByteArrayBuffer;

import jakarta.xml.ws.handler.MessageContext;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * {@link WSHTTPConnection} implemented for the local transport.
 *
 * @author WS Development Team
 */
final class LocalConnectionImpl extends WSHTTPConnection implements WebServiceContextDelegate {

    private final Map<String, List<String>> reqHeaders;
    private Map<String, List<String>> rspHeaders = null;
    private int statusCode;
    private ByteArrayBuffer baos;
    /**
     * The address of the endpoint to which this message is sent.
     */
    private final URI baseURI;
    private final ClosedCallback callback;

    LocalConnectionImpl(URI baseURI, @NotNull Map<String, List<String>> reqHeaders) {
        this(baseURI, reqHeaders, null);
    }

    LocalConnectionImpl(URI baseURI, @NotNull Map<String, List<String>> reqHeaders,
                        @Nullable ClosedCallback callback) {
        this.baseURI = baseURI;
        this.reqHeaders = reqHeaders;
        this.callback = callback;
    }

    public @NotNull InputStream getInput() {
        return baos.newInputStream();
    }

    public @NotNull OutputStream getOutput() {
        baos = new ByteArrayBuffer();
        return baos;
    }

    public String toString() {
        return baos.toString();
    }

    public @NotNull WebServiceContextDelegate getWebServiceContextDelegate() {
        return this;
    }

    public Principal getUserPrincipal(Packet request) {
        return null;   // not really supported
    }

    public boolean isUserInRole(Packet request, String role) {
        return false;   // not really supported
    }

    public @NotNull String getEPRAddress(Packet request, WSEndpoint endpoint) {
        return baseURI.resolve("?"+endpoint.getPortName().getLocalPart()).toString();
    }

    public String getWSDLAddress(@NotNull Packet request, @NotNull WSEndpoint endpoint) {
        ServiceDefinition sd = endpoint.getServiceDefinition();
        if(sd != null) {
            return sd.getPrimary().getURL().toString();
        } else
            return null;
    }

    @Property(MessageContext.HTTP_REQUEST_METHOD)
    public @NotNull String getRequestMethod() {
        return "POST";   // not really supported
    }

    @Override
    public boolean isSecure() {
        return false;   // not really supported
    }

    @Property(MessageContext.QUERY_STRING)
    public String getQueryString() {
        return null;   // not really supported
    }

    @Property(MessageContext.PATH_INFO)
    public String getPathInfo() {
        return null;   // not really supported
    }

    @Override @NotNull
    public String getBaseAddress() {
        return null;    // not really supported
    }

    @Property(MessageContext.HTTP_RESPONSE_CODE)
    public int getStatus () {
        return statusCode;
    }

    public void setStatus (int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    @Property({MessageContext.HTTP_RESPONSE_HEADERS, Packet.OUTBOUND_TRANSPORT_HEADERS})
    public @Nullable Map<String, List<String>> getResponseHeaders() {
        if(rspHeaders==null)
            rspHeaders = new HashMap<>();

        return rspHeaders;
    }

    @Property({MessageContext.HTTP_REQUEST_HEADERS,Packet.INBOUND_TRANSPORT_HEADERS})
    public @NotNull Map<String, List<String>> getRequestHeaders () {
        return reqHeaders;
    }

    public String getRequestHeader(String headerName) {
        List<String> values = getRequestHeaders().get(headerName);
        if(values==null || values.isEmpty())
            return null;
        else
            return values.get(0);
    }

    @Override
	public void setResponseHeader(String key, List<String> value) {
        if(rspHeaders==null)
            rspHeaders = new HashMap<>();

        rspHeaders.put(key, value);
	}

	@Override
	public Set<String> getRequestHeaderNames() {
        return getRequestHeaders().keySet();
	}

	@Override
	public List<String> getRequestHeaderValues(String headerName) {
		return getRequestHeaders().get(headerName);
	}

    public void setResponseHeaders(Map<String,List<String>> headers) {
        if(headers==null)
            // be defensive
            this.rspHeaders = new HashMap<>();
        else {
            this.rspHeaders = new HashMap<>(headers);

            rspHeaders.keySet().removeIf(key -> key.equalsIgnoreCase("Content-Type") || key.equalsIgnoreCase("Content-Length"));
        }
    }

    public void setContentTypeResponseHeader(@NotNull String value) {
        if(rspHeaders==null)
            rspHeaders = new HashMap<>();

        rspHeaders.put("Content-Type", Collections.singletonList(value));
    }

	@Override
	public String getRequestURI() {
		return null;
	}

	@Override
	public String getRequestScheme() {
		return null;
	}

	@Override
	public String getServerName() {
		return null;
	}

	@Override
	public int getServerPort() {
		return -1;
	}
	
    @Override
    public void close() {
        if (!isClosed()) {
            super.close();
            if (callback != null) {
                callback.onClosed();
            }
        }
    }

    protected PropertyMap getPropertyMap() {
        return model;
    }

    private static final PropertyMap model;

    static {
        model = parse(LocalConnectionImpl.class);
    }
}

