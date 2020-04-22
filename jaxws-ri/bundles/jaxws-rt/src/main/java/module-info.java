/*
 * Copyright (c) 2018, 2020 Oracle and/or its affiliates. All rights reserved.
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
module com.sun.xml.ws {

    requires java.desktop;
    requires java.logging;
    requires java.management;
    requires jdk.httpserver;

    requires transitive jakarta.activation;
    requires jakarta.annotation;
    requires transitive jakarta.jws;
    requires transitive java.xml;
    requires transitive jakarta.xml.bind;
    requires transitive jakarta.xml.soap;
    requires transitive jakarta.xml.ws;
    requires static java.servlet;

    requires org.jvnet.mimepull;
    requires transitive org.jvnet.staxex;
    requires transitive com.sun.xml.bind;
    requires com.sun.xml.fastinfoset;
    requires transitive com.sun.xml.streambuffer;

    exports com.sun.xml.ws.policy;
    exports com.sun.xml.ws.policy.sourcemodel;
    exports com.sun.xml.ws.policy.sourcemodel.attach /* TODO: to metro-wsit only ? */;
    exports com.sun.xml.ws.policy.sourcemodel.wspolicy;
    exports com.sun.xml.ws.policy.spi;
    exports com.sun.xml.ws.policy.subject;
    exports com.sun.xml.ws.policy.privateutil /* TODO: to metro-wsit only ! */;

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
    exports com.sun.xml.ws.api.model.wsdl;
    exports com.sun.xml.ws.api.pipe;
    exports com.sun.xml.ws.api.pipe.helper;
    exports com.sun.xml.ws.api.policy; //wsit
    exports com.sun.xml.ws.api.server;
    exports com.sun.xml.ws.api.streaming;
    exports com.sun.xml.ws.api.wsdl.parser;
    exports com.sun.xml.ws.api.wsdl.writer;
    exports com.sun.xml.ws.addressing;
    exports com.sun.xml.ws.addressing.policy; //wsit-impl
    exports com.sun.xml.ws.addressing.v200408;
    exports com.sun.xml.ws.assembler to org.glassfish.metro.wsit.impl.module; //wsit-api
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
    exports com.sun.xml.ws.spi.db;
    exports com.sun.xml.ws.streaming;
    exports com.sun.xml.ws.transport;
    exports com.sun.xml.ws.transport.http;
    exports com.sun.xml.ws.util;
    exports com.sun.xml.ws.util.exception;
    exports com.sun.xml.ws.util.pipe;
    exports com.sun.xml.ws.util.xml;
    exports com.sun.xml.ws.wsdl; // wsit-api
    exports com.sun.xml.ws.wsdl.parser;
    exports com.sun.xml.ws.wsdl.writer;

    exports com.sun.xml.ws.encoding.fastinfoset; //wsit

    exports com.sun.xml.ws.transport.httpspi.servlet;

    exports com.sun.xml.ws.developer.servlet;
    exports com.sun.xml.ws.server.servlet;
    exports com.sun.xml.ws.transport.http.servlet;

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


    uses jakarta.xml.ws.spi.Provider;
    uses jakarta.xml.soap.MessageFactory;
    uses jakarta.xml.soap.SAAJMetaFactory;
    uses jakarta.xml.soap.SOAPConnectionFactory;
    uses jakarta.xml.soap.SOAPFactory;

    uses com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
    uses com.sun.xml.ws.policy.jaxws.spi.PolicyMapConfigurator;

    uses com.sun.xml.ws.policy.spi.LoggingProvider;
    uses com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
    uses com.sun.xml.ws.policy.spi.PolicyAssertionCreator;
    uses com.sun.xml.ws.policy.spi.PrefixMapper;

    provides jakarta.xml.ws.spi.Provider with
            com.sun.xml.ws.spi.ProviderImpl;

    provides com.sun.xml.ws.policy.spi.LoggingProvider with
            com.sun.xml.ws.policy.jaxws.XmlWsLoggingProvider;

}
