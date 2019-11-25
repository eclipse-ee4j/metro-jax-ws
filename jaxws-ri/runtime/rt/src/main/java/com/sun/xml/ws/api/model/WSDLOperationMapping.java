/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.model;

import javax.xml.namespace.QName;

import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;

/**
 * WSDLOperationMapping represents the mapping between a WSDL operation and a 
 * JavaMethod. This is intended to be the output of resolving a Packet to the 
 * targeting WSDL operation.
 * 
 * @author shih-chang.chen@oracle.com
 */
public interface WSDLOperationMapping {
	
    WSDLBoundOperation getWSDLBoundOperation();
    
    JavaMethod getJavaMethod();
    
    /**
     * WSDL1.1 allows operation overloading on the operation name; the operation name should
     * NOT be used as identifier of the operation.
     */
    QName getOperationName();
}
