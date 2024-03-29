/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.developer;

import com.sun.xml.ws.api.message.HeaderList;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.api.addressing.WSEndpointReference;
import com.sun.xml.ws.client.ContentNegotiation;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.http.HTTPBinding;
import java.net.HttpURLConnection;

public interface JAXWSProperties {

    String MTOM_THRESHOLOD_VALUE =  "com.sun.xml.ws.common.MtomThresholdValue";
    String HTTP_EXCHANGE = "com.sun.xml.ws.http.exchange";

    /**
     * Set this property on the {@link BindingProvider#getRequestContext()} to
     * enable {@link HttpURLConnection#setConnectTimeout(int)}
     *
     *<p>
     * int timeout = ...;
     * Map&lt;String, Object&gt; ctxt = ((BindingProvider)proxy).getRequestContext();
     * ctxt.put(CONNECT_TIMEOUT, timeout);
     */
    String CONNECT_TIMEOUT =
        "com.sun.xml.ws.connect.timeout";

    /**
     * Set this property on the {@link BindingProvider#getRequestContext()} to
     * enable {@link HttpURLConnection#setReadTimeout(int)}
     *
     *<p>
     * int timeout = ...;
     * Map&lt;String, Object&gt; ctxt = ((BindingProvider)proxy).getRequestContext();
     * ctxt.put(REQUEST_TIMEOUT, timeout);
     */
    String REQUEST_TIMEOUT =
        "com.sun.xml.ws.request.timeout";

    /**
     * Set this property on the {@link BindingProvider#getRequestContext()} to
     * enable {@link HttpURLConnection#setChunkedStreamingMode(int)}
     *
     *<p>
     * int chunkSize = ...;
     * Map&lt;String, Object&gt; ctxt = ((BindingProvider)proxy).getRequestContext();
     * ctxt.put(HTTP_CLIENT_STREAMING_CHUNK_SIZE, chunkSize);
     */
    String HTTP_CLIENT_STREAMING_CHUNK_SIZE = "com.sun.xml.ws.transport.http.client.streaming.chunk.size";


    /**
     * Set this property on the {@link BindingProvider#getRequestContext()} to
     * enable {@link HttpsURLConnection#setHostnameVerifier(HostnameVerifier)}}. The property
     * is set as follows:
     *
     * <p>
     * HostNameVerifier hostNameVerifier = ...;
     * Map&lt;String, Object&gt; ctxt = ((BindingProvider)proxy).getRequestContext();
     * ctxt.put(HOSTNAME_VERIFIER, hostNameVerifier);
     *
     * <p>
     * <b>THIS PROPERTY IS EXPERIMENTAL AND IS SUBJECT TO CHANGE WITHOUT NOTICE IN FUTURE.</b>
     */
    String HOSTNAME_VERIFIER = "com.sun.xml.ws.transport.https.client.hostname.verifier";

    /**
     * Set this property on the {@link BindingProvider#getRequestContext()} to
     * enable {@link HttpsURLConnection#setSSLSocketFactory(SSLSocketFactory)}. The property is set
     * as follows:
     *
     * <p>
     * SSLSocketFactory sslFactory = ...;
     * Map&lt;String, Object&gt; ctxt = ((BindingProvider)proxy).getRequestContext();
     * ctxt.put(SSL_SOCKET_FACTORY, sslFactory);
     *
     * <p>
     * <b>THIS PROPERTY IS EXPERIMENTAL AND IS SUBJECT TO CHANGE WITHOUT NOTICE IN FUTURE.</b>
     */
    String SSL_SOCKET_FACTORY = "com.sun.xml.ws.transport.https.client.SSLSocketFactory";

    /**
     * Acccess the list of SOAP headers in the SOAP message.
     *
     * <p>
     * On {@link WebServiceContext}, this property returns a {@link HeaderList} object
     * that represents SOAP headers in the request message that was received.
     * On {@link BindingProvider#getResponseContext()}, this property returns a
     * {@link HeaderList} object that represents SOAP headers in the response message from the server.
     *
     * <p>
     * The property is read-only, and please do not modify the returned {@link HeaderList}
     * as that may break the JAX-WS RI in some unexpected way.
     *
     * <p>
     * <b>THIS PROPERTY IS EXPERIMENTAL AND IS SUBJECT TO CHANGE WITHOUT NOTICE IN FUTURE.</b>
     */
    String INBOUND_HEADER_LIST_PROPERTY = "com.sun.xml.ws.api.message.HeaderList";

    /**
     * Access the {@link WSEndpoint} object that delivered the request.
     *
     * <p>
     * {@link WSEndpoint} is the root of the objects that are together
     * responsible for delivering requests to the application SEI object.
     * One can look up this {@link WSEndpoint} from {@link WebServiceContext},
     * and from there access many parts of the JAX-WS RI runtime.
     *
     * <p>
     * <b>THIS PROPERTY IS EXPERIMENTAL AND IS SUBJECT TO CHANGE WITHOUT NOTICE IN FUTURE.</b>
     *
     * @since 2.1.2
     */
    String WSENDPOINT = "com.sun.xml.ws.api.server.WSEndpoint";

    /**
     * Gets the {@code wsa:To} header.
     *
     * The propery value is available on incoming SOAP message. The type of the value
     * is {@link WSEndpointReference}.
     * 
     * Null if the incoming SOAP message didn't have the header.
     *
     * @since 2.1.3
     */
    String ADDRESSING_TO = "com.sun.xml.ws.api.addressing.to";

    /**
     * Gets the {@code wsa:From} header.
     *
     * The propery value is available on incoming SOAP message. The type of the value
     * is {@link WSEndpointReference}.
     *
     * Null if the incoming SOAP message didn't have the header.
     *
     * @since 2.1.3
     */
    String ADDRESSING_FROM = "com.sun.xml.ws.api.addressing.from";

    /**
     * Gets the {@code wsa:Action} header value.
     *
     * The propery value is available on incoming SOAP message. The type of the value
     * is {@link String}.
     *
     * Null if the incoming SOAP message didn't have the header.
     *
     * @since 2.1.3
     */
    String ADDRESSING_ACTION = "com.sun.xml.ws.api.addressing.action";

    /**
     * Gets the {@code wsa:MessageID} header value.
     *
     * The propery value is available on incoming SOAP message. The type of the value
     * is {@link String}.
     *
     * Null if the incoming SOAP message didn't have the header.
     *
     * @since 2.1.3
     */
    String ADDRESSING_MESSAGEID = "com.sun.xml.ws.api.addressing.messageId";

    /**
     * Reconstructs the URL the client used to make the request. The returned URL
     * contains a protocol, server name, port number, and server path, but it does
     * not include query string parameters.
     * <p>
     * The property value is available on incoming SOAP message on servlet transport.
     *
     * @since 2.1.3
     */
    String HTTP_REQUEST_URL = "com.sun.xml.ws.transport.http.servlet.requestURL";

    /**
     * Binding to represent RESTful services. {@link HTTPBinding#HTTP_BINDING} works
     * only for Dispatch/Provider services, but this binding works with even SEI based
     * services. It would be XML, NOT SOAP on the wire. Hence, the SEI parameters
     * shouldn't be mapped to headers.
     *
     * <p>
     * Note that, this only solves limited RESTful usecases.
     *
     * <p>To enable restful binding on the service, specify the binding id via
     * {@link BindingType} or DD
     * <pre>
     * &#64;WebService
     * &#64;BindingType(JAXWSProperties.REST_BINDING)
     * </pre>
     *
     * <p>To enable restful binding on the client side, specify the binding id via
     * {@link BindingTypeFeature}
     * <pre>
     * proxy = echoImplService.getEchoImplPort(new BindingTypeFeature(JAXWSProperties.REST_BINDING));
     * </pre>
     *
     * @since 2.1.4
     */
    String REST_BINDING = "http://jax-ws.dev.java.net/rest";
    
	/**
	 * Set this property to enable {@link HttpURLConnection#setAuthenticator(Authenticator)}, 
	 * available in JDK9+.
	 *
	 * @since 2.3.4
	 */
    String REQUEST_AUTHENTICATOR = "com.sun.xml.ws.request.authenticator";

    
}
