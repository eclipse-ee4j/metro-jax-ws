/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.binding;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.client.HandlerConfiguration;
import com.sun.xml.ws.encoding.soap.streaming.SOAP12NamespaceConstants;
import com.sun.xml.ws.resources.ClientMessages;

import javax.xml.namespace.QName;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPFactory;

import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceFeature;
import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.soap.MTOMFeature;
import jakarta.xml.ws.soap.SOAPBinding;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author WS Development Team
 */
public final class SOAPBindingImpl extends BindingImpl implements SOAPBinding {

    public static final String X_SOAP12HTTP_BINDING =
        "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/";

    private static final String ROLE_NONE = SOAP12NamespaceConstants.ROLE_NONE;
    //protected boolean enableMtom;
    protected final SOAPVersion soapVersion;

    private Set<QName> portKnownHeaders = Collections.emptySet();
    private Set<QName> bindingUnderstoodHeaders = new HashSet<QName>();
    private final Lock lock = new ReentrantLock();

    /**
     * Use {@link BindingImpl#create(BindingID)} to create this.
     *
     * @param bindingId SOAP binding ID
     */
    SOAPBindingImpl(BindingID bindingId) {
        this(bindingId,EMPTY_FEATURES);
    }

    /**
     * Use {@link BindingImpl#create(BindingID)} to create this.
     *
     * @param bindingId binding id
     * @param features
     *      These features have a precedence over
     *      {@link BindingID#createBuiltinFeatureList() the implicit features}
     *      associated with the {@link BindingID}. 
     */
    SOAPBindingImpl(BindingID bindingId, WebServiceFeature... features) {
        super(bindingId, features);
        this.soapVersion = bindingId.getSOAPVersion();
        //populates with required roles and updates handlerConfig
        setRoles(new HashSet<String>());
        //Is this still required? comment out for now
        //setupSystemHandlerDelegate(serviceName);

        this.features.addAll(bindingId.createBuiltinFeatureList());
    }

    /**
     *  This method should be called if the binding has SOAPSEIModel
     *  The Headers understood by the Port are set, so that they can be used for MU
     *  processing.
     *
     * @param headers SOAP header names
     */
    public void setPortKnownHeaders(@NotNull Set<QName> headers) {
     
    	try{
    	  lock.lock();
          this.portKnownHeaders = headers;
		} finally {
    		lock.unlock();
    	}
    }

    /**
     * TODO A feature should be created to configure processing of MU headers.
     */
    public boolean understandsHeader(QName header) {
        return serviceMode == jakarta.xml.ws.Service.Mode.MESSAGE
                || portKnownHeaders.contains(header)
                || bindingUnderstoodHeaders.contains(header);

    }

    /**
     * Sets the handlers on the binding and then sorts the handlers in to logical and protocol handlers.
     * Creates a new HandlerConfiguration object and sets it on the BindingImpl. Also parses Headers understood by
     * Protocol Handlers and sets the HandlerConfiguration.
     */
    public void setHandlerChain(List<Handler> chain) {
        setHandlerConfig(new HandlerConfiguration(getHandlerConfig().getRoles(), chain));
    }

    protected void addRequiredRoles(Set<String> roles) {
        roles.addAll(soapVersion.requiredRoles);
    }

    public Set<String> getRoles() {
        return getHandlerConfig().getRoles();
    }

    /**
     * Adds the next and other roles in case this has
     * been called by a user without them.
     * Creates a new HandlerConfiguration object and sets it on the BindingImpl.
     */
    public void setRoles(Set<String> roles) {
        if (roles == null) {
            roles = new HashSet<String>();
        }
        if (roles.contains(ROLE_NONE)) {
            throw new WebServiceException(ClientMessages.INVALID_SOAP_ROLE_NONE());
        }
        addRequiredRoles(roles);
        setHandlerConfig(new HandlerConfiguration(roles, getHandlerConfig()));
    }


    /**
     * Used typically by the runtime to enable/disable Mtom optimization
     */
    public boolean isMTOMEnabled() {
        return isFeatureEnabled(MTOMFeature.class);
    }

    /**
     * Client application can override if the MTOM optimization should be enabled
     */
    public void setMTOMEnabled(boolean b) {
        features.setMTOMEnabled(b);
    }

    public SOAPFactory getSOAPFactory() {
        return soapVersion.getSOAPFactory();
    }

    public MessageFactory getMessageFactory() {
        return soapVersion.getMessageFactory();
    }

}
