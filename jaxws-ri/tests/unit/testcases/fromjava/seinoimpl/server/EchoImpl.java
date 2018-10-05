/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.seinoimpl.server;

import javax.jws.*;
import javax.xml.ws.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.namespace.QName;
import javax.annotation.Resource;

@WebService(endpointInterface="fromjava.seinoimpl.server.EchoIF")
public class EchoImpl  {
    public Bar echoBar(Bar bar) {
        return bar;
    }

    public String echoString(String str) {
/*
        MessageContext msgContext = wsContext.getMessageContext();
        QName opName = (QName)msgContext.get(MessageContext.WSDL_OPERATION);
        QName expected = new QName("http://example.org/", "echoString");
        if (!expected.equals(opName)) {
            throw new WebServiceException("Expected="+expected+
                " didn't match with received one="+opName);
        }
*/
        return str;
    }

    public void echoIntHolder(Holder<Integer> age) {
        age.value = age.value*2;
    }

    @Resource
    private WebServiceContext wsContext;
}
