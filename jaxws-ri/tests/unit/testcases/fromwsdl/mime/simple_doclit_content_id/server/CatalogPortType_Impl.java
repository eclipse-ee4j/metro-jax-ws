/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.mime.simple_doclit_content_id.server;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.transform.Source;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.WebServiceException;
import java.math.BigDecimal;
import java.awt.Image;
import java.util.List;
import java.util.Map;
import javax.xml.ws.handler.MessageContext;


@WebService(endpointInterface = "fromwsdl.mime.simple_doclit_content_id.server.CatalogPortType")
public class CatalogPortType_Impl {
    @Resource
    protected WebServiceContext wsContext;

    public DataHandler testDataHandler(String body, DataHandler attachIn) {
        if (attachIn == null) {
            throw new WebServiceException("Received DataHandler should not be null!");
        }
        return attachIn;
    }


}
