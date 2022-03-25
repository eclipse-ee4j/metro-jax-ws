/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.pipe.FiberContextSwitchInterceptor;
import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.ThrowableContainerPropertySet;
import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.api.server.ServiceDefinition;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.policy.PolicyMap;
import com.sun.xml.ws.wsdl.OperationDispatcher;
import org.glassfish.gmbal.AMXClient;
import org.glassfish.gmbal.GmbalMBean;
import org.glassfish.gmbal.ManagedObjectManager;
import org.glassfish.pfl.tf.timer.spi.ObjectRegistrationManager ;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import javax.xml.namespace.QName;
import jakarta.xml.ws.EndpointReference;
import org.w3c.dom.Element;

/**
 * {@link ManagedObjectManager} proxy class for {@link WSEndpointImpl} instances that could be used when Gmbal API calls
 * need to be deferred. The proxy tries to defer a need of a real ManagedObjectManager instance to the time when any
 * method from {@link ManagedObjectManager} is invoked on it. In this case a real instance of ManagedObjectManager is
 * obtained from WSEndpointImpl and the method is rather invoked on this object.
 */
public class WSEndpointMOMProxy<T> extends WSEndpoint<T> implements ManagedObjectManager {

    private final @NotNull
    WSEndpointImpl<T> wsEndpoint;
    private ManagedObjectManager managedObjectManager;

    WSEndpointMOMProxy(@NotNull WSEndpointImpl<T> wsEndpoint) {
        this.wsEndpoint = wsEndpoint;
    }

    /**
     * Returns a real instance of {@link ManagedObjectManager}
     *
     * @return an ManagedObjectManager instance
     */
    @Override
    public ManagedObjectManager getManagedObjectManager() {
        if (managedObjectManager == null) {
            managedObjectManager = wsEndpoint.obtainManagedObjectManager();
        }
        return managedObjectManager;
    }

    void setManagedObjectManager(ManagedObjectManager managedObjectManager) {
        this.managedObjectManager = managedObjectManager;
    }

    /**
     * Returns {@code true} if this proxy contains a reference to real ManagedObjectManager instance, {@code false}
     * otherwise.
     *
     * @return {@code true} if ManagedObjectManager has been created, {@code false} otherwise.
     */
    public boolean isInitialized() {
        return this.managedObjectManager != null;
    }

    public WSEndpointImpl<T> getWsEndpoint() {
        return wsEndpoint;
    }

    @Override
    public void suspendJMXRegistration() {
        getManagedObjectManager().suspendJMXRegistration();
    }

    @Override
    public void resumeJMXRegistration() {
        getManagedObjectManager().resumeJMXRegistration();
    }

    @Override
    public boolean isManagedObject(Object obj) {
        return getManagedObjectManager().isManagedObject(obj);
    }

    @Override
    public GmbalMBean createRoot() {
        return getManagedObjectManager().createRoot();
    }

    @Override
    public GmbalMBean createRoot(Object root) {
        return getManagedObjectManager().createRoot(root);
    }

    @Override
    public GmbalMBean createRoot(Object root, String name) {
        return getManagedObjectManager().createRoot(root, name);
    }

    @Override
    public Object getRoot() {
        return getManagedObjectManager().getRoot();
    }

    @Override
    public GmbalMBean register(Object parent, Object obj, String name) {
        return getManagedObjectManager().register(parent, obj, name);
    }

    @Override
    public GmbalMBean register(Object parent, Object obj) {
        return getManagedObjectManager().register(parent, obj);
    }

    @Override
    public GmbalMBean registerAtRoot(Object obj, String name) {
        return getManagedObjectManager().registerAtRoot(obj, name);
    }

    @Override
    public GmbalMBean registerAtRoot(Object obj) {
        return getManagedObjectManager().registerAtRoot(obj);
    }

    @Override
    public void unregister(Object obj) {
        getManagedObjectManager().unregister(obj);
    }

    @Override
    public ObjectName getObjectName(Object obj) {
        return getManagedObjectManager().getObjectName(obj);
    }

    @Override
    public AMXClient getAMXClient(Object obj) {
        return getManagedObjectManager().getAMXClient(obj);
    }

    @Override
    public Object getObject(ObjectName oname) {
        return getManagedObjectManager().getObject(oname);
    }

    @Override
    public void stripPrefix(String... str) {
        getManagedObjectManager().stripPrefix(str);
    }

    @Override
    public void stripPackagePrefix() {
        getManagedObjectManager().stripPackagePrefix();
    }

    @Override
    public String getDomain() {
        return getManagedObjectManager().getDomain();
    }

    @Override
    public void setMBeanServer(MBeanServer server) {
        getManagedObjectManager().setMBeanServer(server);
    }

    @Override
    public MBeanServer getMBeanServer() {
        return getManagedObjectManager().getMBeanServer();
    }

    @Override
    public void setResourceBundle(ResourceBundle rb) {
        getManagedObjectManager().setResourceBundle(rb);
    }

    @Override
    public ResourceBundle getResourceBundle() {
        return getManagedObjectManager().getResourceBundle();
    }

    @Override
    public void addAnnotation(AnnotatedElement element, Annotation annotation) {
        getManagedObjectManager().addAnnotation(element, annotation);
    }

    @Override
    public void addInheritedAnnotations( Class<?> cls ) {
        getManagedObjectManager().addInheritedAnnotations(cls);
    }

    @Override
    public void setRegistrationDebug(RegistrationDebugLevel level) {
        getManagedObjectManager().setRegistrationDebug(level);
    }

    @Override
    public void setRuntimeDebug(boolean flag) {
        getManagedObjectManager().setRuntimeDebug(flag);
    }

    @Override
    public void setTypelibDebug(int level) {
        getManagedObjectManager().setTypelibDebug(level);
    }

    @Override
    public void setJMXRegistrationDebug(boolean flag) {
        getManagedObjectManager().setJMXRegistrationDebug(flag);
    }

    @Override
    public String dumpSkeleton(Object obj) {
        return getManagedObjectManager().dumpSkeleton(obj);
    }

    @Override
    public void suppressDuplicateRootReport(boolean suppressReport) {
        getManagedObjectManager().suppressDuplicateRootReport(suppressReport);
    }

    @Override
    public void close() throws IOException {
        getManagedObjectManager().close();
    }

    @Override
    public boolean equalsProxiedInstance(WSEndpoint<?> endpoint) {
        if (wsEndpoint == null) {
            return (endpoint == null);
        }
        return wsEndpoint.equals(endpoint);
    }

    @Override
    public Codec createCodec() {
        return this.wsEndpoint.createCodec();
    }

    @Override
    public QName getServiceName() {
        return this.wsEndpoint.getServiceName();
    }

    @Override
    public QName getPortName() {
        return this.wsEndpoint.getPortName();
    }

    @Override
    public Class<T> getImplementationClass() {
        return this.wsEndpoint.getImplementationClass();
    }

    @Override
    public WSBinding getBinding() {
        return this.wsEndpoint.getBinding();
    }

    @Override
    public Container getContainer() {
        return this.wsEndpoint.getContainer();
    }

    @Override
    public WSDLPort getPort() {
        return this.wsEndpoint.getPort();
    }

    @Override
    public void setExecutor(Executor exec) {
        this.wsEndpoint.setExecutor(exec);
    }

    @Override
    public void schedule(Packet request, CompletionCallback callback, FiberContextSwitchInterceptor interceptor) {
        this.wsEndpoint.schedule(request, callback, interceptor);
    }

    @Override
    public PipeHead createPipeHead() {
        return this.wsEndpoint.createPipeHead();
    }

    @Override
    public void dispose() {
        if (this.wsEndpoint != null) {
            this.wsEndpoint.dispose();
        }
    }

    @Override
    public ServiceDefinition getServiceDefinition() {
        return this.wsEndpoint.getServiceDefinition();
    }

    @Override
    public SEIModel getSEIModel() {
        return this.wsEndpoint.getSEIModel();
    }

    @Override
    public PolicyMap getPolicyMap() {
        return this.wsEndpoint.getPolicyMap();
    }

    @Override
    public void closeManagedObjectManager() {
        this.wsEndpoint.closeManagedObjectManager();
    }

    @Override
    public ServerTubeAssemblerContext getAssemblerContext() {
        return this.wsEndpoint.getAssemblerContext();
    }

    @Override
    public <T extends EndpointReference> T getEndpointReference(Class<T> clazz, String address, String wsdlAddress, Element... referenceParameters) {
        return wsEndpoint.getEndpointReference(clazz, address, wsdlAddress, referenceParameters);
    }

    @Override
    public <T extends EndpointReference> T getEndpointReference(Class<T> clazz, String address, String wsdlAddress, List<Element> metadata, List<Element> referenceParameters) {
        return wsEndpoint.getEndpointReference(clazz, address, wsdlAddress, metadata, referenceParameters);
    }

    @Override
    public OperationDispatcher getOperationDispatcher() {
        return wsEndpoint.getOperationDispatcher();
    }

    @Override
    public Packet createServiceResponseForException(final ThrowableContainerPropertySet tc,
                                                    final Packet      responsePacket,
                                                    final SOAPVersion soapVersion,
                                                    final WSDLPort    wsdlPort,
                                                    final SEIModel    seiModel,
                                                    final WSBinding   binding)
    {
        return wsEndpoint.createServiceResponseForException(tc, responsePacket, soapVersion,
                                                            wsdlPort, seiModel, binding);
    }

    @Override
    public ObjectRegistrationManager getObjectRegistrationManager() {
        return getManagedObjectManager().getObjectRegistrationManager();
    }
}
