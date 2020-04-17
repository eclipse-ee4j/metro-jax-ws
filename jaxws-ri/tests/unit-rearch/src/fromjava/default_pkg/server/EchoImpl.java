/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

import jakarta.jws.WebService;

@WebService(name="Echo", serviceName="EchoService", targetNamespace="http://echo.org/")

public class EchoImpl {
  public int addNumbers(int x, int y) throws AddNumbersException {
      if(x<0 || y<0)
        throw new AddNumbersException("Can't add negative numders", x+ "&" + y);
      return x+y;
  }

}
