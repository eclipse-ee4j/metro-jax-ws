/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.oracle.webservices.api.databinding;

import java.lang.reflect.Method;

/**
 * On the client or service-requestor side, a JavaCallInfo object represents a
 * method call on the service proxy to be serialized as a SOAP request message
 * to be sent to the service. A SOAP response message returned to the service
 * client is deserialized as an update to the JavaCallInfo object which is used
 * to generated the request.
 * <p>
 * </p>
 * On the server or service provider side, a SOAP request message is
 * deserialized to a JavaCallInfo object which can be used to determine which
 * method to call, and get the parameter values to call the back-end service
 * implementation object. The return value or exception returned from the
 * service implementation should be set to the JavaCallInfo object which then
 * can be used to serialize to a A SOAP response or fault message to be sent
 * back to the service client.
 *  
 * @author shih-chang.chen@oracle.com
 */
public interface JavaCallInfo {

	/**
	 * Gets the method of this JavaCallInfo
	 * 
	 * @return the method
	 */
    Method getMethod();

//	/**
//	 * Sets the method of this JavaCallInfo
//	 * 
//	 * @param method The method to set
//	 */
//	public void setMethod(Method method);

	/**
	 * Gets the parameters of this JavaCallInfo
	 * 
	 * @return The parameters
	 */
    Object[] getParameters();

//	/**
//	 * Sets the parameters of this JavaCallInfo
//	 * 
//	 * @param parameters
//	 *            the parameters to set
//	 */
//	public void setParameters(Object[] parameters);

	/**
	 * Gets the returnValue of this JavaCallInfo
	 * 
	 * @return the returnValue
	 */
    Object getReturnValue();

	/**
	 * Sets the returnValue of this JavaCallInfo
	 * 
	 * @param returnValue
	 *            the returnValue to set
	 */
    void setReturnValue(Object returnValue);

	/**
	 * Gets the exception of this JavaCallInfo
	 * 
	 * @return the exception
	 */
    Throwable getException();

	/**
	 * Sets the exception of this JavaCallInfo
	 * 
	 * @param exception
	 *            the exception to set
	 */
    void setException(Throwable exception);
}
