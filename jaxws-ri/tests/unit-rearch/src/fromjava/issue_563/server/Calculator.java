/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.issue_563.server;
import calculator.Query;
import calculator.Result;
import java.math.BigInteger;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;

@WebService
public class Calculator {
    @WebMethod
    public Result add(Query query) {
        BigInteger sum = query.getArg0().add(query.getArg1());
        Result result = new Result();

        result.setSum(sum);
        return result;
    }

    /*
    public static void main(String[] args){
        // create and publish an endpoint
        Calculator calculator = new Calculator();
        Endpoint endpoint = Endpoint.publish("http://localhost:8080/calculator", calculator);        
    }
    */
}
