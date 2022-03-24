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
import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.TransportPipeFactory;
import com.sun.xml.ws.api.pipe.TransportTubeFactory;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.transport.http.DeploymentDescriptorParser;
import com.sun.xml.ws.transport.http.DeploymentDescriptorParser.AdapterFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * {@link TransportPipeFactory} for the local transport.
 *
 * <p>
 * The syntax of the endpoint address is:
 * <pre>
 * local:///path/to/exploded/war/image?portLocalName
 * </pre>
 *
 * <p>
 * If the service only contains one port, the <code>?portLocalName</code> portion
 * can be omitted.
 *
 * @author Kohsuke Kawaguchi
 */
public final class LocalTransportFactory extends TransportTubeFactory {

    /**
     * Default constructor.
     */
    public LocalTransportFactory() {}

    @Override
    public Tube doCreate(@NotNull ClientTubeAssemblerContext context) {
        URI adrs = context.getAddress().getURI();
        if(!(adrs.getScheme().equals("local") || adrs.getScheme().equals("local-async"))) {
            return null;
        }
        return adrs.getScheme().equals("local")
                ? new LocalTransportTube(adrs,createServerService(adrs),context.getCodec())
                : new LocalAsyncTransportTube(adrs,createServerService(adrs),context.getCodec());
    }

    /**
     * The local transport works by looking at the exploded war file image on
     * a file system.
     * TODO: Currently it expects the PortName to be appended to the endpoint address
     *       This needs to be expanded to take Service and Port QName as well.
     * @param adrs URI
     * @return deployed WSEndpoint
     */
    protected static WSEndpoint createServerService(URI adrs) {
        try {
            String outputDir = adrs.getPath();
            List<WSEndpoint> endpoints = parseEndpoints(outputDir);

            WSEndpoint endpoint = endpoints.get(0);
            if (endpoints.size() > 1) {
                for (WSEndpoint rei : endpoints) {
                    //TODO: for now just compare local part
                    if(rei.getPortName().getLocalPart().equals(adrs.getQuery())) {
                        endpoint = rei;
                        break;
                    }
                }
            }

            return endpoint;
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    protected static List<WSEndpoint> parseEndpoints(String outputDir) throws IOException {
        String riFile = outputDir+"/WEB-INF/sun-jaxws.xml";
        DeploymentDescriptorParser<WSEndpoint> parser = new DeploymentDescriptorParser<>(
                Thread.currentThread().getContextClassLoader(),
                new FileSystemResourceLoader(new File(outputDir)), null,
                new AdapterFactory<>() {
                    @Override
                    public WSEndpoint createAdapter(String name, String urlPattern, WSEndpoint<?> endpoint) {
                        return endpoint;
                    }
                });

        return parser.parse(new File(riFile));
    }

}
