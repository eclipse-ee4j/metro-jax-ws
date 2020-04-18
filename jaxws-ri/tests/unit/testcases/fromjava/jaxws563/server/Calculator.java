/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.jaxws563.server;

import fromjava.jaxws563.server.types.Query;
import fromjava.jaxws563.server.types.Result;
import java.math.BigInteger;
import jakarta.jws.WebService;


@WebService
public class Calculator {

    public Result add(Query query) {
        BigInteger sum = query.getFoo().add(query.getBar());
        Result result = new Result();

        result.setSum(sum);
        return result;
    }
    
}
