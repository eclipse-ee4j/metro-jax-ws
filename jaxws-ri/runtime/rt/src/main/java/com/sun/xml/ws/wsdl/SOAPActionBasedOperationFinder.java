/*
 * Copyright (c) 2007, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl;

import com.sun.istack.Nullable;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.WSDLOperationMapping;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.model.AbstractSEIModelImpl;
import com.sun.xml.ws.model.JavaMethodImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * An {@link WSDLOperationFinder} that uses SOAPAction as the key for dispatching.
 * <br>
 * A map of all SOAPAction on the port and the corresponding WSDL operation QName
 * is initialized in the constructor. The SOAPAction from the
 * request {@link com.sun.xml.ws.api.message.Packet} is used as the key to identify the associated wsdl operation.
 *
 * @author Jitendra Kotamraju
 */
final class SOAPActionBasedOperationFinder extends WSDLOperationFinder {
    private final Map<String, WSDLOperationMapping> methodHandlers;

    public SOAPActionBasedOperationFinder(WSDLPort wsdlModel, WSBinding binding, @Nullable SEIModel seiModel) {
        super(wsdlModel,binding,seiModel);
        methodHandlers = new HashMap<>();

        // Find if any SOAPAction repeat for operations
        Map<String, Integer> unique = new HashMap<>();
        if (seiModel != null) {
            for (JavaMethodImpl m : ((AbstractSEIModelImpl) seiModel).getJavaMethods()) {
                String soapAction = m.getSOAPAction();
                Integer count = unique.get(soapAction);
                if (count == null) {
                    unique.put(soapAction, 1);
                } else {
                    unique.put(soapAction, ++count);
                }
            }

            for (JavaMethodImpl m : ((AbstractSEIModelImpl) seiModel).getJavaMethods()) {
                String soapAction = m.getSOAPAction();
                // Set up method handlers only for unique SOAPAction values so
                // that dispatching happens consistently for a method
                if (unique.get(soapAction) == 1) {
                    methodHandlers.put('"' + soapAction + '"', wsdlOperationMapping(m));
                }
            }
        } else {
            for(WSDLBoundOperation wsdlOp: wsdlModel.getBinding().getBindingOperations()) {
                methodHandlers.put(wsdlOp.getSOAPAction(), wsdlOperationMapping(wsdlOp));
            }
        }

    }

    @Override
    public WSDLOperationMapping getWSDLOperationMapping(Packet request) throws DispatchException {
        return request.soapAction == null ? null : methodHandlers.get(request.soapAction);
    }
}
