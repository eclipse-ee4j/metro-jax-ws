/*
 * Copyright (c) 2004, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

/*
 * Hello_Impl.java
 *
 * Created on July 25, 2003, 10:37 AM
 */

package provider.http_status_code_oneway_607.server;

import java.rmi.Remote;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.Service;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.Map;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.Service.Mode;
import jakarta.xml.soap.SOAPBody;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.MimeHeader;
import jakarta.xml.ws.ServiceMode;
import jakarta.xml.ws.WebServiceProvider;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import javax.annotation.Resource;
import java.io.StringReader;

import org.w3c.dom.Node;

/**
 * Service returns null to indicate oneway, also sets a http status
 * code. Client should make sure it doesn't receive 202
 *
 * @author Jitendra Kotamraju
 */
@WebServiceProvider
@ServiceMode(value=Service.Mode.MESSAGE)
public class HelloImpl implements Provider<SOAPMessage> {

    @Resource
    WebServiceContext wsCtxt;

    public SOAPMessage invoke(SOAPMessage msg) {
        MessageContext msgCtxt = wsCtxt.getMessageContext();        
        msgCtxt.put(MessageContext.HTTP_RESPONSE_CODE, 502);
        return null;
    }
}
