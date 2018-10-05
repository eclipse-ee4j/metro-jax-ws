/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package provider.http_status_code_200_oneway.server;

import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.xml.ws.*;
import javax.xml.ws.http.HTTPBinding;
import javax.xml.ws.handler.MessageContext;
import javax.xml.transform.Source;

/**
 * Issue WSIT:1354
 *
 * Oneway returns 200 status code
 *
 * @author Jitendra Kotamraju
 */
@WebServiceProvider
@ServiceMode(value=Service.Mode.MESSAGE)
public class HelloImpl implements Provider<Source> {

    @Resource
    WebServiceContext wsCtxt;

    public Source invoke(Source msg) {
        MessageContext msgCtxt = wsCtxt.getMessageContext();        
        msgCtxt.put(MessageContext.HTTP_RESPONSE_CODE, 200);
        return null;
    }
}
