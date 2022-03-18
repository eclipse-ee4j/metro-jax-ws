/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.misc.server;

import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;

@jakarta.jws.WebService(endpointInterface="server.misc.server.HelloPortType")
public class HelloPortTypeImpl implements HelloPortType {
	private WebServiceContext wsContext;

    public EchoResponseType echo(EchoType reqBody,
                                              EchoType reqHeader,
                                              Echo2Type req2Header) {
        EchoResponseType response = null;
        try {
            ObjectFactory of = new ObjectFactory();
            response = of.createEchoResponseType();
            response.setRespInfo(reqBody.getReqInfo() + reqHeader.getReqInfo() +
                                                      req2Header.getReqInfo());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

    public String echo2(String info) {
		MessageContext msgCtxt = wsContext.getMessageContext();
/*
		if (msgCtxt.get("jakarta.xml.ws.servlet.context") == null
        	|| msgCtxt.get("jakarta.xml.ws.servlet.session") == null
        	|| msgCtxt.get("jakarta.xml.ws.servlet.request") == null
        	|| msgCtxt.get("jakarta.xml.ws.servlet.response") == null) {
			throw new WebServiceException("MessageContext is not populated.");
		}
*/
        return info;
    }

    public void echo3(Holder<java.lang.String> reqInfo) {
    }

    public void echo4(Echo4Type reqBody,
                      Echo4Type reqHeader,
                      String req2Header,
                      Holder<String> respBody,
                      Holder<String> respHeader) {
    }

    @Resource
    private void setContext(WebServiceContext wsContext) {
		System.out.println("Setting WebServiceContext");
		this.wsContext = wsContext;
    }

    @PostConstruct
    private void onPostConstruct() {
		System.out.println("Called onPostConstruct");
    }

    @PreDestroy
    private void onPreDestroy() {
		System.out.println("Called onPreDestroy");
    }

}
