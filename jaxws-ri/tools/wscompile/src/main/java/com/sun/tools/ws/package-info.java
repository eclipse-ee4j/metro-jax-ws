/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/**
 * <h2>JAX-WS 2.1 Tools</h2>
 * This document describes the tools included with JAX-WS 2.0.1.
 *
 * <h2>ANT Tasks</h2>
   <dl>
 *  <dt>{@link com.sun.tools.ws.ant.AnnotationProcessingTask AnnotationProcessing}</dt>
 *  <dd>An ANT task to invoke <a href="http://download.oracle.com/javase/6/docs/api/javax/annotation/processing/package-summary.html">Annotation Processing</a>.</dd>

 *  <dt>{@link com.sun.tools.ws.ant.WsGen2 WsGen}</dt>
 *  <dd>
 *    An ANT task to invoke {@link com.sun.tools.ws.WsGen WsGen}</dd>

 *  <dt>{@link com.sun.tools.ws.ant.WsImport2 WsImport}</dt>
 *  <dd>
 *    An ANT task to invoke {@link com.sun.tools.ws.WsImport WsImport}</dd>
 *
 *  </dl>
 * <h2>Command-line Tools</h2>
   <dl>
 *  <dt><a href="http://download.oracle.com/javase/6/docs/api/javax/annotation/processing/package-summary.html">AP</a></dt>
 <dd>A Java SE tool and framework for processing annotations. Annotation processing will invoke a JAX-WS AnnotationProcossor for
 *   processing Java source  files with jakarta.jws.* annotations and making them web services.
 *   Annotation processing will compile the Java source files and generate any additional classes needed to make an jakarta.jws.WebService
 *   annotated class a Web service.</dd>
 *
 *  <dt>{@link com.sun.tools.ws.WsGen WsGen}</dt>
 *  <dd>Tool to process a compiled jakarta.jws.WebService annotated class and to generate the necessary classes to make
 *  it a Web service.</dd>

 *  <dt>{@link com.sun.tools.ws.ant.WsImport2 WsImport}</dt>
 *  <dd>
 *    Tool to import a WSDL and to generate an SEI (a jakarta.jws.WebService) interface that can be either implemented
 *    on the server to build a web service, or can be used on the client to invoke the web service.</dd>
 *  </dl>
 * <h2>Implementation Classes</h2>
 *  <dl>
 *    <dt>{@link com.sun.tools.ws.processor.model.Model Model}</dt>
 *    <dd>The model is used to represent the entire Web Service.  The JAX-WS ProcessorActions can process
 *    this Model to generate Java artifacts such as the service interface.</dd>
 *
 *
 *    <dt>{@link com.sun.tools.ws.processor.modeler.Modeler Modeler}</dt>
 *    <dd>A Modeler is used to create a Model of a Web Service from a particular Web
 *    Web Service description such as a WSDL
 *    file.</dd>
 *
 *    <dt>{@link com.sun.tools.ws.processor.modeler.wsdl.WSDLModeler WSDLModeler}</dt>
 *    <dd>The WSDLModeler processes a WSDL to create a Model.</dd>
 *
 *    <dt>{@link com.sun.tools.ws.processor.modeler.annotation.WebServiceAp WebServiceAp}</dt>
 *    <dd>WebServiceAp is a AnnotationProcessor for processing jakarta.jws.* and
 *    jakarta.xml.ws.* annotations. This class is used by the WsGen (CompileTool) tool.</dd>
 *   </dl>
 *
 **/
package com.sun.tools.ws;
