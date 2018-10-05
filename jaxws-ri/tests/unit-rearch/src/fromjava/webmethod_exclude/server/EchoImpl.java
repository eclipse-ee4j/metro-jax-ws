/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.webmethod_exclude.server;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name="Echo", serviceName="EchoService", targetNamespace="http://echo.org/")

public class EchoImpl {
  public String echoString(String str) {
      return str;
  }

  @WebMethod(exclude = true)
  private static Handle decodeHandle(byte[] encodedHandle) {
   return null;
  }
  @WebMethod(exclude = true)  
  private static byte[] encodeHandle(Handle handle) {
   return handle.decode();
  }


}
