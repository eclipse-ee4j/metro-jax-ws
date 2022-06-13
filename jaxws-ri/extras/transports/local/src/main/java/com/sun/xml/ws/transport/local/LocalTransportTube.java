/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.local;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.pipe.ContentType;
import com.sun.xml.ws.api.pipe.NextAction;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
import com.sun.xml.ws.api.server.Adapter;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.client.ContentNegotiation;
import com.sun.xml.ws.transport.http.HttpAdapter;
import com.sun.xml.ws.transport.http.WSHTTPConnection;
import com.sun.xml.ws.util.MessageWriter;
import com.sun.xml.ws.util.UtilException;

import javax.xml.ws.WebServiceException;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Transport {@link Tube} that routes a message to a service that runs within it.
 *
 * <p>
 * This is useful to test the whole client-server in a single VM.
 *
 * @author Jitendra Kotamraju
 */
final class LocalTransportTube extends AbstractTubeImpl {

    /**
     * Represents the service running inside the local transport.
     *
     * We use {@link HttpAdapter}, so that the local transport
     * excercise as much server code as possible. If this were
     * to be done "correctly" we should write our own {@link Adapter}
     * for the local transport.
     */
    private final HttpAdapter adapter;

    private final Codec codec;

    /**
     * The address of the endpoint deployed in this tube.
     */
    private final URI baseURI;

    // per-pipe reusable resources.
    // we don't really have to reuse anything since this isn't designed for performance,
    // but nevertheless we do it as an experiement.
    private final Map<String, List<String>> reqHeaders = new HashMap<String, List<String>>();

    public LocalTransportTube(URI baseURI, WSEndpoint endpoint, Codec codec) {
        this(baseURI,HttpAdapter.createAlone(endpoint),codec);
    }

    private LocalTransportTube(URI baseURI,HttpAdapter adapter, Codec codec) {
        this.adapter = adapter;
        this.codec = codec;
        this.baseURI = baseURI;
        assert codec !=null && adapter!=null;
    }

    /**
     * Copy constructor for {@link Tube#copy(TubeCloner)}.
     */
    private LocalTransportTube(LocalTransportTube that, TubeCloner cloner) {
        this(that.baseURI, that.adapter, that.codec.copy());
        cloner.add(that,this);
    }

    public @NotNull NextAction processException(@NotNull Throwable t) {
        return doThrow(t);
    }

    public Packet process(Packet request) {

        try {
            // Set up WSConnection with tranport headers, request content

            // get transport headers from message
            reqHeaders.clear();
            Map<String, List<String>> rh = (Map<String, List<String>>) request.invocationProperties.get(MessageContext.HTTP_REQUEST_HEADERS);
            //assign empty map if its null
            if(rh != null){
                reqHeaders.putAll(rh);
            }


            LocalConnectionImpl con = new LocalConnectionImpl(baseURI,reqHeaders);
            // Calling getStaticContentType sets some internal state in the codec
            // TODO : need to fix this properly in Codec
            ContentType contentType = codec.getStaticContentType(request);
            String requestContentType;
            if (contentType != null) {
                requestContentType = contentType.getContentType();
                codec.encode(request, con.getOutput());
            } else {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                contentType = codec.encode(request, baos);
                requestContentType = contentType.getContentType();
                baos.writeTo(con.getOutput());
            }
            reqHeaders.put("Content-Type", Collections.singletonList(requestContentType));

            String requestAccept = contentType.getAcceptHeader();
            if (contentType.getAcceptHeader() != null) {
                reqHeaders.put("Accept", Collections.singletonList(requestAccept));
            }

            writeSOAPAction(reqHeaders, contentType.getSOAPActionHeader(), request);
            
            if(dump)
                dump(con,"request",reqHeaders);

            adapter.handle(con);

            if(dump)
                dump(con,"response",con.getResponseHeaders());

            String responseContentType = getResponseContentType(con);

            if (con.getStatus() == WSHTTPConnection.ONEWAY) {
                return request.createClientResponse(null);    // one way. no response given.
            }

            // TODO: check if returned MIME type is the same as that which was sent
            // or is acceptable if an Accept header was used

            checkFIConnegIntegrity(request.contentNegotiation, requestContentType, requestAccept, responseContentType);

            Packet reply = request.createClientResponse(null);
            codec.decode(con.getInput(), responseContentType, reply);
            return reply;
        } catch (WebServiceException wex) {
            throw wex;
        } catch (IOException ex) {
            throw new WebServiceException(ex);
        }
    }

    /**
     * write SOAPAction header if the soapAction parameter is non-null or BindingProvider properties set.
     * BindingProvider properties take precedence.
     */
    private void writeSOAPAction(Map<String, List<String>> reqHeaders, String soapAction, Packet packet) {
        //request Property soapAction overrides wsdl
        if (soapAction != null)
            reqHeaders.put("SOAPAction", Collections.singletonList(soapAction));
        else
            reqHeaders.put("SOAPAction", Collections.singletonList("\"\""));
    }

    private void checkFIConnegIntegrity(ContentNegotiation conneg,
                                        String requestContentType, String requestAccept, String responseContentType) {
        requestAccept = (requestAccept == null) ? "" : requestAccept;
        if (requestContentType.contains("fastinfoset")) {
            if (!responseContentType.contains("fastinfoset")) {
                throw new RuntimeException(
                        "Request is encoded using Fast Infoset but response (" +
                        responseContentType +
                        ") is not");
            } else if (conneg == ContentNegotiation.none) {
                throw new RuntimeException(
                        "Request is encoded but Fast Infoset content negotiation is set to none");
            }
        } else if (requestAccept.contains("fastinfoset")) {
            if (!responseContentType.contains("fastinfoset")) {
                throw new RuntimeException(
                        "Fast Infoset is acceptable but response is not encoded in Fast Infoset");
            } else if (conneg == ContentNegotiation.none) {
                throw new RuntimeException(
                        "Fast Infoset is acceptable but Fast Infoset content negotiation is set to none");
            }
        } else if (conneg == ContentNegotiation.pessimistic) {
            throw new RuntimeException(
                    "Content negotitaion is set to pessimistic but Fast Infoset is not acceptable");
        } else if (conneg == ContentNegotiation.optimistic) {
            throw new RuntimeException(
                    "Content negotitaion is set to optimistic but the request (" +
                    requestContentType +
                    ") is not encoded using Fast Infoset");
        }
    }

    private String getResponseContentType(LocalConnectionImpl con) {
        Map<String, List<String>> rsph = con.getResponseHeaders();
        if(rsph!=null) {
            List<String> c = rsph.get("Content-Type");
            if(c!=null && !c.isEmpty())
                return c.get(0);
        }
        return null;
    }

    @NotNull
    public NextAction processRequest(@NotNull Packet request) {
        return doReturnWith(process(request));
    }

    @NotNull
    public NextAction processResponse(@NotNull Packet response) {
        throw new IllegalStateException("LocalTransportPipe's processResponse shouldn't be called.");
    }

    public void preDestroy() {
        // Nothing to do here. Intenionally left empty
    }

    public LocalTransportTube copy(TubeCloner cloner) {
        return new LocalTransportTube(this, cloner);
    }

    private void dump(LocalConnectionImpl con, String caption, Map<String,List<String>> headers) throws IOException {
        MessageWriter pw = new MessageWriter(new OutputStreamWriter(System.out), 32 * 1024);
        pw.println("---["+caption +"]---");
        if(headers!=null) {
            for (Entry<String,List<String>> header : headers.entrySet()) {
                if(header.getValue().isEmpty()) {
                    // I don't think this is legal, but let's just dump it,
                    // as the point of the dump is to uncover problems.
                    pw.println(header.getValue());
                } else {
                    for (String value : header.getValue()) {
                        pw.println(header.getKey() + ": " + value);
                    }
                }
            }
            pw.println("\n");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInput()));
        String line = reader.readLine();
        try {
            while (line != null) {
                pw.write(line);
                line = reader.readLine();
            }
            pw.write("--------------------");
        } catch (UtilException ue) {
            System.out.println("...large output...");
            System.out.println("--------------------");
        }
    }

    /**
     * Dumps what goes across HTTP transport.
     */
    private static final boolean dump;

    static {
        boolean b;
        try {
            b = Boolean.getBoolean(LocalTransportTube.class.getName()+".dump");
        } catch( Throwable t ) {
            b = false;
        }
        dump = b;
    }
}
