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

package provider.no_content_type_657.server;

import jakarta.activation.DataSource;
import javax.annotation.Resource;
import jakarta.xml.ws.*;
import jakarta.xml.ws.http.HTTPBinding;
import jakarta.xml.ws.handler.MessageContext;

/**
 * Issue 657
 *
 * HTTP PUT operation sends a response with no content-type
 *
 * @author Jitendra Kotamraju
 */
@WebServiceProvider
@ServiceMode(value=Service.Mode.MESSAGE)
@BindingType(HTTPBinding.HTTP_BINDING)
public class HelloImpl implements Provider<DataSource> {

    @Resource
    WebServiceContext wsCtxt;

    public DataSource invoke(DataSource msg) {
        MessageContext msgCtxt = wsCtxt.getMessageContext();        
        msgCtxt.put(MessageContext.HTTP_RESPONSE_CODE, 200);
        return null;
    }
}
