/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.wsit1533.server;

import jakarta.activation.DataHandler;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlMimeType;

/**
 * TODO: Write some description here ...
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
@WebService(name = "UploadServicePortType", wsdlLocation = "WEB-INF/wsdl/UploadService.wsdl")
public interface Upload {
        /**
         * test the metro data upload
         * @param data the data to upload
         * @return the first line of the provided data
         */
        @WebMethod
        public String uploadDataTest(
                          @WebParam(name = "data")
                          @XmlMimeType("application/octet-stream")
                          DataHandler data );

        //other methods
}
