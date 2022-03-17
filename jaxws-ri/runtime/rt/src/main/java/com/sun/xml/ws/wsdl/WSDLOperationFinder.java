/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl;

import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.model.JavaMethod;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.WSDLOperationMapping;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.model.JavaMethodImpl;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import javax.xml.namespace.QName;

/**
 * Extensions if this class will be used for dispatching the request message to the correct endpoint method by
 * identifying the wsdl operation associated with the request.
 *
 * @see OperationDispatcher
 * 
 * @author Rama Pulavarthi
 */
public abstract class WSDLOperationFinder {
    protected final WSDLPort wsdlModel;
    protected final WSBinding binding;
    protected final SEIModel seiModel;

    public WSDLOperationFinder(@NotNull WSDLPort wsdlModel, @NotNull WSBinding binding, @Nullable SEIModel seiModel) {
        this.wsdlModel = wsdlModel;
        this.binding = binding;
        this.seiModel= seiModel;
    }

    /**
     * This methods returns the QName of the WSDL operation corresponding to a request Packet.
     * An implementation should return null when it cannot dispatch to a unique method based on the information it processes.
     * In such case, other OperationFinders are queried to resolve a WSDL operation.
     * It should throw an instance of DispatchException if it finds incorrect information in the packet.
     *
     * @param request  Request Packet that is used to find the associated WSDLOperation
     * @return QName of the WSDL Operation that this request correponds to.
     *          null when it cannot find a unique operation to dispatch to.
     * @throws DispatchException When the information in the Packet is invalid
     */
    public WSDLOperationMapping getWSDLOperationMapping(Packet request) throws DispatchException {
        return null;
    }
    
    protected WSDLOperationMapping wsdlOperationMapping(JavaMethodImpl j) {
        return new WSDLOperationMappingImpl(j.getOperation(), j);
    }
    
    protected WSDLOperationMapping wsdlOperationMapping(WSDLBoundOperation o) {
        return new WSDLOperationMappingImpl(o, null);
    }
    
    static class WSDLOperationMappingImpl implements WSDLOperationMapping {
        private WSDLBoundOperation wsdlOperation;
        private JavaMethod javaMethod;
        private QName operationName;
        
        WSDLOperationMappingImpl(WSDLBoundOperation wsdlOperation, JavaMethodImpl javaMethod) {
            this.wsdlOperation = wsdlOperation;
            this.javaMethod = javaMethod;
            operationName = (javaMethod != null) ? javaMethod.getOperationQName() : wsdlOperation.getName();
        }

        @Override
        public WSDLBoundOperation getWSDLBoundOperation() {
            return wsdlOperation;
        }
        
        @Override
        public JavaMethod getJavaMethod() {
            return javaMethod;
        }
        
        @Override
        public QName getOperationName() {
            return operationName;
        }
    }
}
