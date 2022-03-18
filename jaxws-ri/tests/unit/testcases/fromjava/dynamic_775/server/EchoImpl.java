/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.dynamic_775.server;

import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

/**
 * Issue 775 test case. Error in generating the wrapper bean dynamically
 * when the reflection type is GenericArrayType.
 *
 * @author Jitendra Kotamraju
 */
@WebService(name="Echo", serviceName="echoService", targetNamespace="http://echo.org/")
public class EchoImpl {

    // Reflection API gives "Class" for byte[] parameter
    public String echo(@WebParam(name="photo") byte[] photo) {
        return null;
    }

    // Reflection API gives "GenericArrayType" for byte[] parameter
    // See http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5041784
    public String echo1(@WebParam(name="photo") byte[] photo,
                        @WebParam(name="groups") List<String> groups) {
        return null;
    }

}
