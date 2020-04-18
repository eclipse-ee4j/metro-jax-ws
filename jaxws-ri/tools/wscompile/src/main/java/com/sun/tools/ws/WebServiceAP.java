/*
 * Copyright (c) 2019, 2020 Oracle and/or its affiliates. All rights reserved.
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
 * WebServiceAP is an {@link Processor AnnotationProcessor} for processing jakarta.jws.* and
 * jakarta.xml.ws.* annotations. This class is used either by the WsGen (CompileTool) tool or
 * indirectly when invoked by javac.
 *
 * @author lukas
 */
@SupportedAnnotationTypes({
        "jakarta.jws.HandlerChain",
        "jakarta.jws.Oneway",
        "jakarta.jws.WebMethod",
        "jakarta.jws.WebParam",
        "jakarta.jws.WebResult",
        "jakarta.jws.WebService",
        "jakarta.jws.soap.InitParam",
        "jakarta.jws.soap.SOAPBinding",
        "jakarta.jws.soap.SOAPMessageHandler",
        "jakarta.jws.soap.SOAPMessageHandlers",
        "jakarta.xml.ws.BindingType",
        "jakarta.xml.ws.RequestWrapper",
        "jakarta.xml.ws.ResponseWrapper",
        "jakarta.xml.ws.ServiceMode",
        "jakarta.xml.ws.WebEndpoint",
        "jakarta.xml.ws.WebFault",
        "jakarta.xml.ws.WebServiceClient",
        "jakarta.xml.ws.WebServiceProvider",
        "jakarta.xml.ws.WebServiceRef"
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
