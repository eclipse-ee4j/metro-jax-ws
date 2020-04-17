/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.binding;

import com.oracle.webservices.api.message.MessageContextFactory;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.client.HandlerConfiguration;
import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
import com.sun.xml.ws.developer.BindingTypeFeature;

import jakarta.activation.CommandInfo;
import jakarta.activation.CommandMap;
import jakarta.activation.MailcapCommandMap;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceFeature;
import jakarta.xml.ws.soap.AddressingFeature;
import jakarta.xml.ws.handler.Handler;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map;


/**
 * Instances are created by the service, which then
 * sets the handler chain on the binding impl.
 *
 * <p>
 * This class is made abstract as we don't see a situation when
 * a BindingImpl has much meaning without binding id.
 * IOW, for a specific binding there will be a class
 * extending BindingImpl, for example SOAPBindingImpl.
 *
 * <p>
 * The spi Binding interface extends Binding.
 *
 * @author WS Development Team
 */
public abstract class BindingImpl implements WSBinding {

    protected static final WebServiceFeature[] EMPTY_FEATURES = new WebServiceFeature[0];

    //This is reset when ever Binding.setHandlerChain() or SOAPBinding.setRoles() is called.
    private HandlerConfiguration handlerConfig;
    private final Set<QName> addedHeaders = new HashSet<QName>();
    private final Set<QName> knownHeaders = Collections.synchronizedSet(new HashSet<QName>());
    private final Set<QName> unmodKnownHeaders = Collections.unmodifiableSet(knownHeaders);
    private final BindingID bindingId;
    // Features that are set(enabled/disabled) on the binding
    protected final WebServiceFeatureList features;
    // Features that are set(enabled/disabled) on the binding or an operation
    protected final Map<QName, WebServiceFeatureList> operationFeatures = new HashMap<QName, WebServiceFeatureList>();
    // Features that are set(enabled/disabled) on the binding, an operation or an input message
    protected final Map<QName, WebServiceFeatureList> inputMessageFeatures = new HashMap<QName, WebServiceFeatureList>();
    // Features that are set(enabled/disabled) on the binding, an operation or an output message
    protected final Map<QName, WebServiceFeatureList> outputMessageFeatures = new HashMap<QName, WebServiceFeatureList>();
    // Features that are set(enabled/disabled) on the binding, an operation or a fault message
    protected final Map<MessageKey, WebServiceFeatureList> faultMessageFeatures = new HashMap<MessageKey, WebServiceFeatureList>();

    protected jakarta.xml.ws.Service.Mode serviceMode = jakarta.xml.ws.Service.Mode.PAYLOAD;

    protected MessageContextFactory messageContextFactory;

    protected BindingImpl(BindingID bindingId, WebServiceFeature ... features) {
        this.bindingId = bindingId;
        handlerConfig = new HandlerConfiguration(Collections.<String>emptySet(), Collections.<Handler>emptyList());
        if (handlerConfig.getHandlerKnownHeaders() != null)
            knownHeaders.addAll(handlerConfig.getHandlerKnownHeaders());
        this.features = new WebServiceFeatureList(features);
        this.features.validate();
    }

    public
    @NotNull
    List<Handler> getHandlerChain() {
        return handlerConfig.getHandlerChain();
    }

    public HandlerConfiguration getHandlerConfig() {
        return handlerConfig;
    }

    protected void setHandlerConfig(HandlerConfiguration handlerConfig) {
        this.handlerConfig = handlerConfig;
        knownHeaders.clear();
        knownHeaders.addAll(addedHeaders);
        if (handlerConfig != null && handlerConfig.getHandlerKnownHeaders() != null)
            knownHeaders.addAll(handlerConfig.getHandlerKnownHeaders());
    }

    public void setMode(@NotNull Service.Mode mode) {
        this.serviceMode = mode;
    }

    public Set<QName> getKnownHeaders() {
    	return unmodKnownHeaders;
    }
    
    public boolean addKnownHeader(QName headerQName) {
        addedHeaders.add(headerQName);
        return knownHeaders.add(headerQName);
    }
    
    public
    @NotNull
    BindingID getBindingId() {
        return bindingId;
    }

    public final SOAPVersion getSOAPVersion() {
        return bindingId.getSOAPVersion();
    }

    public AddressingVersion getAddressingVersion() {
        AddressingVersion addressingVersion;
        if (features.isEnabled(AddressingFeature.class))
            addressingVersion = AddressingVersion.W3C;
        else if (features.isEnabled(MemberSubmissionAddressingFeature.class))
            addressingVersion = AddressingVersion.MEMBER;
        else
            addressingVersion = null;
        return addressingVersion;
    }

    @NotNull
    public final Codec createCodec() {

        // initialization from here should cover most of cases;
        // if not, it would be necessary to call
        //   BindingImpl.initializeJavaActivationHandlers()
        // explicitly by programmer
        initializeJavaActivationHandlers();

        return bindingId.createEncoder(this);
    }

    public static void initializeJavaActivationHandlers() {
        // DataHandler.writeTo() may search for DCH. So adding some default ones.
        try {
            CommandMap map = CommandMap.getDefaultCommandMap();
            if (map instanceof MailcapCommandMap) {
                MailcapCommandMap mailMap = (MailcapCommandMap) map;

                // registering our DCH since javamail's DCH doesn't handle
                if (!cmdMapInitialized(mailMap)) {
                    mailMap.addMailcap("text/xml;;x-java-content-handler=com.sun.xml.ws.encoding.XmlDataContentHandler");
                    mailMap.addMailcap("application/xml;;x-java-content-handler=com.sun.xml.ws.encoding.XmlDataContentHandler");
                    mailMap.addMailcap("image/*;;x-java-content-handler=com.sun.xml.ws.encoding.ImageDataContentHandler");
                    mailMap.addMailcap("text/plain;;x-java-content-handler=com.sun.xml.ws.encoding.StringDataContentHandler");
                }
            }
        } catch (Throwable t) {
            // ignore the exception.
        }
    }

    private static boolean cmdMapInitialized(MailcapCommandMap mailMap) {
        CommandInfo[] commands = mailMap.getAllCommands("text/xml");
        if (commands == null || commands.length == 0) {
            return false;
        }

        // SAAJ RI implements it's own DataHandlers which can be used for JAX-WS too;
        // see com.sun.xml.messaging.saaj.soap.AttachmentPartImpl#initializeJavaActivationHandlers
        // so if found any of SAAJ or our own handler registered, we are ok; anyway using SAAJ directly here
        // is not good idea since we don't want standalone JAX-WS to depend on specific SAAJ impl.
        // This is also reason for duplication of Handler's code by JAX-WS
        String saajClassName = "com.sun.xml.messaging.saaj.soap.XmlDataContentHandler";
        String jaxwsClassName = "com.sun.xml.ws.encoding.XmlDataContentHandler";
        for (CommandInfo command : commands) {
            String commandClass = command.getCommandClass();
            if (saajClassName.equals(commandClass) ||
                    jaxwsClassName.equals(commandClass)) {
                return true;
            }
        }
        return false;
    }

    public static BindingImpl create(@NotNull BindingID bindingId) {
        if (bindingId.equals(BindingID.XML_HTTP))
            return new HTTPBindingImpl();
        else
            return new SOAPBindingImpl(bindingId);
    }

    public static BindingImpl create(@NotNull BindingID bindingId, WebServiceFeature[] features) {
        // Override the BindingID from the features
        for(WebServiceFeature feature : features) {
            if (feature instanceof BindingTypeFeature) {
                BindingTypeFeature f = (BindingTypeFeature)feature;
                bindingId = BindingID.parse(f.getBindingId());
            }
        }
        if (bindingId.equals(BindingID.XML_HTTP))
            return new HTTPBindingImpl(features);
        else
            return new SOAPBindingImpl(bindingId, features);
    }

    public static WSBinding getDefaultBinding() {
        return new SOAPBindingImpl(BindingID.SOAP11_HTTP);
    }

    public String getBindingID() {
        return bindingId.toString();
    }

    public @Nullable <F extends WebServiceFeature> F getFeature(@NotNull Class<F> featureType){
        return features.get(featureType);
    }

    public @Nullable <F extends WebServiceFeature> F getOperationFeature(@NotNull Class<F> featureType,
            @NotNull final QName operationName) {
        final WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
        return FeatureListUtil.mergeFeature(featureType, operationFeatureList, features);
    }

    public boolean isFeatureEnabled(@NotNull Class<? extends WebServiceFeature> feature){
        return features.isEnabled(feature);
    }

    public boolean isOperationFeatureEnabled(@NotNull Class<? extends WebServiceFeature> featureType,
            @NotNull final QName operationName) {
        final WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
        return FeatureListUtil.isFeatureEnabled(featureType, operationFeatureList, features);
    }

    @NotNull
    public WebServiceFeatureList getFeatures() {
        //TODO scchen convert BindingID  to WebServiceFeature[]
        if(!isFeatureEnabled(com.oracle.webservices.api.EnvelopeStyleFeature.class)) {
            WebServiceFeature[] f = { getSOAPVersion().toFeature() };
            features.mergeFeatures(f, false);
        }
        return features;
    }

    public @NotNull WebServiceFeatureList getOperationFeatures(@NotNull final QName operationName) {
        final WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
        return FeatureListUtil.mergeList(operationFeatureList, features);
    }
    
    public @NotNull WebServiceFeatureList getInputMessageFeatures(@NotNull final QName operationName) {
        final WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
        final WebServiceFeatureList messageFeatureList = this.inputMessageFeatures.get(operationName);
        return FeatureListUtil.mergeList(operationFeatureList, messageFeatureList, features);
        
    }
    
    public @NotNull WebServiceFeatureList getOutputMessageFeatures(@NotNull final QName operationName) {
        final WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
        final WebServiceFeatureList messageFeatureList = this.outputMessageFeatures.get(operationName);
        return FeatureListUtil.mergeList(operationFeatureList, messageFeatureList, features);
    }
    
    public @NotNull WebServiceFeatureList getFaultMessageFeatures(@NotNull final QName operationName,
            @NotNull final QName messageName) {
        final WebServiceFeatureList operationFeatureList = this.operationFeatures.get(operationName);
        final WebServiceFeatureList messageFeatureList = this.faultMessageFeatures.get(
                new MessageKey(operationName, messageName));
        return FeatureListUtil.mergeList(operationFeatureList, messageFeatureList, features);
    }

    public void setOperationFeatures(@NotNull final QName operationName, WebServiceFeature... newFeatures) {
        if (newFeatures != null) {
            WebServiceFeatureList featureList = operationFeatures.get(operationName);
            if (featureList == null) {
                featureList = new WebServiceFeatureList();
            }
            for (WebServiceFeature f : newFeatures) {
                featureList.add(f);
            }
            operationFeatures.put(operationName, featureList);
        }
    }

    public void setInputMessageFeatures(@NotNull final QName operationName, WebServiceFeature... newFeatures) {
        if (newFeatures != null) {
            WebServiceFeatureList featureList = inputMessageFeatures.get(operationName);
            if (featureList == null) {
                featureList = new WebServiceFeatureList();
            }
            for (WebServiceFeature f : newFeatures) {
                featureList.add(f);
            }
            inputMessageFeatures.put(operationName, featureList);
        }
    }

    public void setOutputMessageFeatures(@NotNull final QName operationName, WebServiceFeature... newFeatures) {
        if (newFeatures != null) {
            WebServiceFeatureList featureList = outputMessageFeatures.get(operationName);
            if (featureList == null) {
                featureList = new WebServiceFeatureList();
            }
            for (WebServiceFeature f : newFeatures) {
                featureList.add(f);
            }
            outputMessageFeatures.put(operationName, featureList);
        }
    }

    public void setFaultMessageFeatures(@NotNull final QName operationName, @NotNull final QName messageName, WebServiceFeature... newFeatures) {
        if (newFeatures != null) {
            final MessageKey key = new MessageKey(operationName, messageName);
            WebServiceFeatureList featureList = faultMessageFeatures.get(key);
            if (featureList == null) {
                featureList = new WebServiceFeatureList();
            }
            for (WebServiceFeature f : newFeatures) {
                featureList.add(f);
            }
            faultMessageFeatures.put(key, featureList);
        }
    }

    public synchronized @NotNull com.oracle.webservices.api.message.MessageContextFactory getMessageContextFactory () {
        if (messageContextFactory == null) {
            messageContextFactory = MessageContextFactory.createFactory(getFeatures().toArray());
        }
        return messageContextFactory;
    }

    /**
     * Experimental: Identify messages based on the name of the message and the
     * operation that uses this message.
     */
    protected static class MessageKey {
        
        final private QName operationName;
        final private QName messageName;
        
        public MessageKey(final QName operationName, final QName messageName) {
            this.operationName = operationName;
            this.messageName = messageName;
        }
        
        @Override
        public int hashCode() {
            final int hashFirst = this.operationName != null ? this.operationName.hashCode() : 0;
            final int hashSecond = this.messageName != null ? this.messageName.hashCode() : 0;

            return (hashFirst + hashSecond) * hashSecond + hashFirst;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final MessageKey other = (MessageKey) obj;
            if (this.operationName != other.operationName && (this.operationName == null || !this.operationName.equals(other.operationName))) {
                return false;
            }
            if (this.messageName != other.messageName && (this.messageName == null || !this.messageName.equals(other.messageName))) {
                return false;
            }
            return true;
        }
        
        @Override
        public String toString() { 
           return "(" + this.operationName + ", " + this.messageName + ")";
        }
        
    }

}
