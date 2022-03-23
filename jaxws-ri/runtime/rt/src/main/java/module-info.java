/*
 * Copyright (c) 2018, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/**
 * Defines the Java API for XML-Based Web Services (JAX-WS), and
 * the Web Services Metadata API.
 *
 *
 * @uses jakarta.xml.soap.MessageFactory
 * @uses jakarta.xml.soap.SAAJMetaFactory
 * @uses jakarta.xml.soap.SOAPConnectionFactory
 * @uses jakarta.xml.soap.SOAPFactory
 * @uses jakarta.xml.ws.spi.Provider
 *
 * @since 2.4.0
 */
module com.sun.xml.ws.rt {

    requires java.desktop;
    requires java.logging;
    requires java.management;
    requires transitive java.xml;
    requires jdk.httpserver;
    requires jdk.unsupported;

    requires transitive jakarta.activation;
    requires transitive jakarta.annotation;
    requires transitive jakarta.mail;
    requires transitive jakarta.xml.bind;
    requires transitive jakarta.xml.soap;
    requires transitive jakarta.xml.ws;

    requires static jakarta.servlet;
    requires static com.sun.xml.fastinfoset;

    requires transitive org.jvnet.mimepull;
    requires transitive org.jvnet.staxex;
    requires transitive org.glassfish.jaxb.runtime;
    requires transitive com.sun.xml.streambuffer;
    requires transitive com.sun.xml.ws.policy;
    requires transitive gmbal;
    requires transitive org.glassfish.ha.api;

    exports com.oracle.webservices.api;
    exports com.oracle.webservices.api.databinding;
    exports com.oracle.webservices.api.message;
    exports com.sun.xml.ws;
    exports com.sun.xml.ws.api;
    exports com.sun.xml.ws.api.addressing;
    exports com.sun.xml.ws.api.client; //wsit-api
    exports com.sun.xml.ws.api.databinding;
    exports com.sun.xml.ws.api.ha;
    exports com.sun.xml.ws.api.handler;
    exports com.sun.xml.ws.api.config.management; // wsit-api
    exports com.sun.xml.ws.api.config.management.policy; // wsit-api
    exports com.sun.xml.ws.api.message;
    exports com.sun.xml.ws.api.message.saaj;
    exports com.sun.xml.ws.api.message.stream; //wsit
    exports com.sun.xml.ws.api.model;
    exports com.sun.xml.ws.api.model.soap;
    exports com.sun.xml.ws.api.model.wsdl;
    exports com.sun.xml.ws.api.model.wsdl.editable;
    exports com.sun.xml.ws.api.pipe;
    exports com.sun.xml.ws.api.pipe.helper;
    exports com.sun.xml.ws.api.policy; //wsit
    exports com.sun.xml.ws.api.server;
    exports com.sun.xml.ws.api.streaming;
    exports com.sun.xml.ws.api.wsdl.parser;
    exports com.sun.xml.ws.api.wsdl.writer;
    exports com.sun.xml.ws.addressing;
    exports com.sun.xml.ws.addressing.model;
    exports com.sun.xml.ws.addressing.policy; //wsit-impl
    exports com.sun.xml.ws.addressing.v200408;
    exports com.sun.xml.ws.assembler to org.glassfish.metro.wsit.impl; //wsit-api
    exports com.sun.xml.ws.assembler.dev; //wsit-api
    exports com.sun.xml.ws.binding;
    exports com.sun.xml.ws.client; //async transport
    exports com.sun.xml.ws.client.dispatch;
    exports com.sun.xml.ws.commons.xmlutil; //wsit
    exports com.sun.xml.ws.config.metro.dev; //wsit-api
    exports com.sun.xml.ws.config.metro.util; //wsit-api
    exports com.sun.xml.ws.db;
    exports com.sun.xml.ws.developer;
    exports com.sun.xml.ws.encoding;
    exports com.sun.xml.ws.encoding.policy; //wsit
    exports com.sun.xml.ws.fault; //wsit
    exports com.sun.xml.ws.handler;
    exports com.sun.xml.ws.message;
    exports com.sun.xml.ws.message.jaxb; //wsit
    exports com.sun.xml.ws.message.saaj;
    exports com.sun.xml.ws.message.source; //wsit
    exports com.sun.xml.ws.message.stream;
    exports com.sun.xml.ws.model;
    exports com.sun.xml.ws.model.wsdl; //wsit
    exports com.sun.xml.ws.policy.jaxws.spi; //wsit
    exports com.sun.xml.ws.protocol.soap; //wsit
    exports com.sun.xml.ws.resources;
    exports com.sun.xml.ws.server;
    exports com.sun.xml.ws.server.provider;
    exports com.sun.xml.ws.server.sei;
    exports com.sun.xml.ws.spi.db;
    exports com.sun.xml.ws.streaming;
    exports com.sun.xml.ws.transport;
    exports com.sun.xml.ws.transport.http;
    exports com.sun.xml.ws.transport.http.client;
    exports com.sun.xml.ws.util;
    exports com.sun.xml.ws.util.exception;
    exports com.sun.xml.ws.util.pipe;
    exports com.sun.xml.ws.util.xml;
    exports com.sun.xml.ws.wsdl; // wsit-api
    exports com.sun.xml.ws.wsdl.parser;
    exports com.sun.xml.ws.wsdl.writer;
    exports com.sun.xml.ws.wsdl.writer.document;
    exports com.sun.xml.ws.wsdl.writer.document.soap;
    exports com.sun.xml.ws.wsdl.writer.document.soap12;
    exports com.sun.xml.ws.wsdl.writer.document.xsd;

    exports com.sun.xml.ws.spi to jakarta.xml.ws;

    // XML document content needs to be exported
    opens com.sun.xml.ws.runtime.config to jakarta.xml.bind;

    // com.sun.xml.ws.fault.SOAPFaultBuilder uses JAXBContext.newInstance
    opens com.sun.xml.ws.fault to jakarta.xml.bind;

    // com.sun.xml.ws.addressing.WsaTubeHelperImpl uses JAXBContext.newInstance
    opens com.sun.xml.ws.addressing to jakarta.xml.bind;

    // com.sun.xml.ws.addressing.v200408.WsaTubeHelperImpl uses JAXBContext.newInstance
    opens com.sun.xml.ws.addressing.v200408 to jakarta.xml.bind;

    // com.sun.xml.ws.developer.MemberSubmissionEndpointReference uses JAXBContext.newInstance
    opens com.sun.xml.ws.developer to jakarta.xml.bind;

    // com.sun.xml.ws.model.ExternalMetadataReader uses JAXBContext.newInstance
    opens com.oracle.xmlns.webservices.jaxws_databinding to jakarta.xml.bind;

    opens com.sun.xml.ws.api.message;
    
    uses jakarta.xml.ws.spi.Provider;
    uses jakarta.xml.soap.MessageFactory;
    uses jakarta.xml.soap.SAAJMetaFactory;
    uses jakarta.xml.soap.SOAPConnectionFactory;
    uses jakarta.xml.soap.SOAPFactory;

    uses com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
    uses com.sun.xml.ws.policy.jaxws.spi.PolicyMapConfigurator;

    uses com.sun.xml.ws.api.wsdl.parser.MetadataResolverFactory;
    uses com.sun.xml.ws.spi.db.DatabindingProvider;
    uses com.sun.xml.ws.spi.db.BindingContextFactory;
    uses com.oracle.webservices.impl.internalspi.encoding.StreamDecoder;
    uses com.sun.xml.ws.api.message.saaj.SAAJFactory;
    uses com.oracle.webservices.api.message.MessageContextFactory;
    uses com.sun.xml.ws.api.BindingIDFactory;
    uses com.sun.xml.ws.api.client.ServiceInterceptorFactory;
    uses com.sun.xml.ws.api.policy.PolicyResolverFactory;
    uses com.sun.xml.ws.api.pipe.TransportTubeFactory;
    uses com.sun.xml.ws.api.pipe.TransportPipeFactory;
    uses com.sun.xml.ws.api.pipe.TubelineAssemblerFactory;
    uses com.sun.xml.ws.api.pipe.PipelineAssemblerFactory;
    uses com.sun.xml.ws.api.server.ProviderInvokerTubeFactory;
    uses com.sun.xml.ws.api.config.management.ManagedEndpointFactory;
    uses com.sun.xml.ws.assembler.dev.TubelineAssemblyDecorator;
    uses com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension;
    uses com.sun.xml.ws.api.wsdl.writer.WSDLGeneratorExtension;
    uses com.sun.xml.ws.api.server.EndpointReferenceExtensionContributor;
    uses com.sun.xml.ws.api.server.ServerPipelineHook;

    provides jakarta.xml.ws.spi.Provider with
            com.sun.xml.ws.spi.ProviderImpl;

    provides com.sun.xml.ws.policy.spi.LoggingProvider with
            com.sun.xml.ws.policy.jaxws.XmlWsLoggingProvider;

    provides com.sun.xml.ws.spi.db.DatabindingProvider with
            com.sun.xml.ws.db.DatabindingProviderImpl;

    provides com.sun.xml.ws.spi.db.BindingContextFactory with
            com.sun.xml.ws.db.glassfish.JAXBRIContextFactory;

}
