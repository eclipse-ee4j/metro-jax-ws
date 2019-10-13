/*
 * Copyright (c) 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws;

import com.sun.tools.ws.wscompile.WsgenOptions;
import java.io.PrintStream;
import javax.annotation.processing.Processor;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;

/**
 * WebServiceAP is an {@link Processor AnnotationProcessor} for processing javax.jws.* and
 * javax.xml.ws.* annotations. This class is used either by the WsGen (CompileTool) tool or
 * indirectly when invoked by javac.
 *
 * @author lukas
 */
@SupportedAnnotationTypes({
        "javax.jws.HandlerChain",
        "javax.jws.Oneway",
        "javax.jws.WebMethod",
        "javax.jws.WebParam",
        "javax.jws.WebResult",
        "javax.jws.WebService",
        "javax.jws.soap.InitParam",
        "javax.jws.soap.SOAPBinding",
        "javax.jws.soap.SOAPMessageHandler",
        "javax.jws.soap.SOAPMessageHandlers",
        "javax.xml.ws.BindingType",
        "javax.xml.ws.RequestWrapper",
        "javax.xml.ws.ResponseWrapper",
        "javax.xml.ws.ServiceMode",
        "javax.xml.ws.WebEndpoint",
        "javax.xml.ws.WebFault",
        "javax.xml.ws.WebServiceClient",
        "javax.xml.ws.WebServiceProvider",
        "javax.xml.ws.WebServiceRef"
})
@SupportedOptions({WebServiceAP.DO_NOT_OVERWRITE, WebServiceAP.IGNORE_NO_WEB_SERVICE_FOUND_WARNING, WebServiceAP.VERBOSE})
public class WebServiceAP extends com.sun.tools.ws.processor.modeler.annotation.WebServiceAp {

    public WebServiceAP() {
        super();
    }

    public WebServiceAP(WsgenOptions options, PrintStream out) {
        super(options, out);
    }

}
