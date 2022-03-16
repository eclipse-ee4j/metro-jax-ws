/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.client;

import com.sun.xml.ws.util.JAXWSUtils;

import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;
import jakarta.xml.ws.WebServiceException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;

/**
 * Represents parsed {@link WebServiceClient} and {@link WebEndpoint}
 * annotations on a {@link Service}-derived class.
 *
 * @author Kohsuke Kawaguchi
 */
final class SCAnnotations {
    SCAnnotations(final Class<?> sc) {
        AccessController.doPrivileged(new PrivilegedAction<Void>() {
            @Override
            public Void run() {
                WebServiceClient wsc =sc.getAnnotation(WebServiceClient.class);
                if(wsc==null) {
                    throw new WebServiceException("Service Interface Annotations required, exiting...");
                }

                String tns = wsc.targetNamespace();
                try {
                    JAXWSUtils.getFileOrURL(wsc.wsdlLocation());
                } catch (IOException e) {
                    // TODO: report a reasonable error message
                    throw new WebServiceException(e);
                }

                for (Method method : sc.getDeclaredMethods()) {
                    WebEndpoint webEndpoint = method.getAnnotation(WebEndpoint.class);
                    if (webEndpoint != null) {
                        String endpointName = webEndpoint.name();
                        QName portQName = new QName(tns, endpointName);
                        portQNames.add(portQName);
                    }
                    Class<?> seiClazz = method.getReturnType();
                    if (seiClazz!=void.class) {
                        classes.add(seiClazz);
                    }
                }

                return null;
            }
        });
    }

    final ArrayList<QName> portQNames = new ArrayList<>();
    final ArrayList<Class> classes = new ArrayList<>();
}
