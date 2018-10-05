/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util.pipe;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.pipe.ClientPipeAssemblerContext;
import com.sun.xml.ws.api.pipe.Pipe;
import com.sun.xml.ws.api.pipe.PipelineAssembler;
import com.sun.xml.ws.api.pipe.ServerPipeAssemblerContext;

/**
 * Default Pipeline assembler for JAX-WS client and server side runtimes. It
 * assembles various pipes into a pipeline that a message needs to be passed
 * through.
 *
 * @author Kohsuke Kawaguchi
 * @author Jitendra Kotamraju
 */
public class StandalonePipeAssembler implements PipelineAssembler {

    @NotNull
    public Pipe createClient(ClientPipeAssemblerContext context) {
        Pipe head = context.createTransportPipe();
        head = context.createSecurityPipe(head);

        if (dump) {
            // for debugging inject a dump pipe. this is left in the production code,
            // as it would be very handy for a trouble-shooting at the production site.
            head = context.createDumpPipe("client", System.out, head);
        }
        head = context.createWsaPipe(head);
        head = context.createClientMUPipe(head);
        return context.createHandlerPipe(head);        
    }

    /**
     * On Server-side, HandlerChains cannot be changed after it is deployed.
     * During assembling the Pipelines, we can decide if we really need a
     * SOAPHandlerPipe and LogicalHandlerPipe for a particular Endpoint.
     */
    public Pipe createServer(ServerPipeAssemblerContext context) {
        Pipe head = context.getTerminalPipe();
        head = context.createHandlerPipe(head);
        head = context.createMonitoringPipe(head);
        head = context.createServerMUPipe(head);
        head = context.createWsaPipe(head);
        head = context.createSecurityPipe(head);
        return head;
    }

    /**
     * Are we going to dump the message to System.out?
     */
    private static final boolean dump;

    static {
        boolean b = false;
        try {
            b = Boolean.getBoolean(StandalonePipeAssembler.class.getName()+".dump");
        } catch (Throwable t) {
            // treat it as false
        }
        dump = b;
    }
}
