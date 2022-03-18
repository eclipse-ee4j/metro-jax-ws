/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.server.provider;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.server.AsyncProvider;
import com.sun.xml.ws.resources.ServerMessages;
import com.sun.xml.ws.spi.db.BindingHelper;

import jakarta.activation.DataSource;
import jakarta.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.ServiceMode;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.soap.SOAPBinding;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Keeps the runtime information like Service.Mode and erasure of Provider class
 * about Provider endpoint. It proccess annotations to find about Service.Mode
 * It also finds about parameterized type(e.g. Source, SOAPMessage, DataSource)
 * of endpoint class.
 *
 * @author Jitendra Kotamraju
 * @author Kohsuke Kawaguchi
 */
final class ProviderEndpointModel<T> {
    /**
     * True if this is {@link AsyncProvider}.
     */
    final boolean isAsync;

    /**
     * In which mode does this provider operate?
     */
    @NotNull final Service.Mode mode;
    /**
     * T of {@link Provider}&lt;T>.
     */
    @NotNull final Class datatype;
    /**
     * User class that extends {@link Provider}.
     */
    @NotNull final Class implClass;

    ProviderEndpointModel(Class<T> implementorClass, WSBinding binding) {
        assert implementorClass != null;
        assert binding != null;

        implClass = implementorClass;
        mode = getServiceMode(implementorClass);
        Class otherClass = (binding instanceof SOAPBinding)
            ? SOAPMessage.class : DataSource.class;
        isAsync = AsyncProvider.class.isAssignableFrom(implementorClass);


        Class<?> baseType = isAsync ? AsyncProvider.class : Provider.class;
        Type baseParam = BindingHelper.getBaseType(implementorClass, baseType);
        if (baseParam==null)
            throw new WebServiceException(ServerMessages.NOT_IMPLEMENT_PROVIDER(implementorClass.getName()));
        if (!(baseParam instanceof ParameterizedType))
            throw new WebServiceException(ServerMessages.PROVIDER_NOT_PARAMETERIZED(implementorClass.getName()));

        ParameterizedType pt = (ParameterizedType)baseParam;
        Type[] types = pt.getActualTypeArguments();
        if(!(types[0] instanceof Class))
            throw new WebServiceException(ServerMessages.PROVIDER_INVALID_PARAMETER_TYPE(implementorClass.getName(),types[0]));
        datatype = (Class)types[0];

        if (mode == Service.Mode.PAYLOAD && datatype!=Source.class) {
            // Illegal to have PAYLOAD && SOAPMessage
            // Illegal to have PAYLOAD && DataSource
            throw new IllegalArgumentException(
                "Illeagal combination - Mode.PAYLOAD and Provider<"+otherClass.getName()+">");
        }
    }

    /**
     * Is it PAYLOAD or MESSAGE ??
     *
     * @param c endpoint class
     * @return Service.Mode.PAYLOAD or Service.Mode.MESSAGE
     */
    private static Service.Mode getServiceMode(Class<?> c) {
        ServiceMode mode = c.getAnnotation(ServiceMode.class);
        return (mode == null) ? Service.Mode.PAYLOAD : mode.value();
    }
}
