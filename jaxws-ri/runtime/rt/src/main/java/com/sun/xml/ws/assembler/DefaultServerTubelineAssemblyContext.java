/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.xml.ws.assembler;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
import com.sun.xml.ws.policy.PolicyMap;

/**
 * The context is a wrapper around the existing JAX-WS {@link ServerTubeAssemblerContext} with additional features
 *
 * @author Marek Potociar (marek.potociar at sun.com)
 */
class DefaultServerTubelineAssemblyContext extends TubelineAssemblyContextImpl implements ServerTubelineAssemblyContext  {

    private final @NotNull ServerTubeAssemblerContext wrappedContext;
    private final PolicyMap policyMap;
    // TODO: add next tube getter/package-private setter
    // TODO: replace the PipeConfiguration

    public DefaultServerTubelineAssemblyContext(@NotNull ServerTubeAssemblerContext context) {
        this.wrappedContext = context;
        this.policyMap = context.getEndpoint().getPolicyMap();
    }
    
    public PolicyMap getPolicyMap() {
        return policyMap;
    }
    
    public boolean isPolicyAvailable() {
        return policyMap != null && !policyMap.isEmpty();
    }

    /**
     * The created pipeline will use seiModel to get java concepts for the endpoint
     *
     * @return Null if the service doesn't have SEI model e.g. Provider endpoints,
     *         and otherwise non-null.
     */
    public @Nullable SEIModel getSEIModel() {
        return wrappedContext.getSEIModel();
    }

    /**
     * The created pipeline will be used to serve this port.
     *
     * @return Null if the service isn't associated with any port definition in WSDL,
     *         and otherwise non-null.
     */
    public @Nullable WSDLPort getWsdlPort() {
        return wrappedContext.getWsdlModel();
    }

    /**
     *
     * The created pipeline is used to serve this {@link com.sun.xml.ws.api.server.WSEndpoint}.
     * Specifically, its {@link com.sun.xml.ws.api.WSBinding} should be of interest to  many
     * {@link com.sun.xml.ws.api.pipe.Pipe}s.
     *  @return Always non-null.
     */
    public @NotNull WSEndpoint getEndpoint() {
        return wrappedContext.getEndpoint();
    }

    /**
     * The last {@link com.sun.xml.ws.api.pipe.Pipe} in the pipeline. The assembler is expected to put
     * additional {@link com.sun.xml.ws.api.pipe.Pipe}s in front of it.
     *
     * <p>
     * (Just to give you the idea how this is used, normally the terminal pipe
     * is the one that invokes the user application or {@link javax.xml.ws.Provider}.)
     *
     * @return always non-null terminal pipe
     */
     public @NotNull Tube getTerminalTube() {
         return wrappedContext.getTerminalTube();
    }

    /**
     * If this server pipeline is known to be used for serving synchronous transport,
     * then this method returns true. This can be potentially use as an optimization
     * hint, since often synchronous versions are cheaper to execute than asycnhronous
     * versions.
     */
    public boolean isSynchronous() {
        return wrappedContext.isSynchronous();
    }

    /**
     * Gets the {@link Codec} that is set by {@link #setCodec} or the default codec
     * based on the binding. The codec is a full codec that is responsible for
     * encoding/decoding entire protocol message(for e.g: it is responsible to
     * encode/decode entire MIME messages in SOAP binding)
     *
     * @return codec to be used for web service requests
     * @see {@link com.sun.xml.ws.api.pipe.Codecs}
     */
    public @NotNull Codec getCodec() {
        return wrappedContext.getCodec();
    }

    /**
     * Interception point to change {@link Codec} during {@link Tube}line assembly. The
     * new codec will be used by jax-ws server runtime for encoding/decoding web service
     * request/response messages. {@link WSEndpoint#createCodec()} will return a copy
     * of this new codec and will be used in the server runtime.
     *
     * <p>
     * The codec is a full codec that is responsible for
     * encoding/decoding entire protocol message(for e.g: it is responsible to
     * encode/decode entire MIME messages in SOAP binding)
     *
     * <p>
     * the codec should correctly implement {@link Codec#copy} since it is used while
     * serving requests concurrently.
     *
     * @param codec codec to be used for web service requests
     * @see {@link com.sun.xml.ws.api.pipe.Codecs}
     */
    public void setCodec(@NotNull Codec codec) {
        wrappedContext.setCodec(codec);
    }
        
    public ServerTubeAssemblerContext getWrappedContext() {
        return wrappedContext;
    }    
}
