/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package wsa.fromjava.default_action.server;

import jakarta.jws.HandlerChain;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.Oneway;
import jakarta.xml.ws.Action;
import jakarta.xml.ws.FaultAction;
import jakarta.xml.ws.BindingType;

import jakarta.xml.ws.soap.SOAPBinding;
import jakarta.xml.ws.soap.AddressingFeature;
import jakarta.xml.ws.soap.Addressing;

/**
 * @author Rama Pulavarthi
 */

@Addressing
@WebService(name = "AddNumbers",
        portName = "AddNumbersPort",
        targetNamespace = "http://foobar.org/",
        serviceName = "AddNumbersService")
public class AddNumbersImpl {
    @WebMethod
    public int addNumbersNoAction(int number1, int number2) throws AddNumbersException {
        return doStuff(number1, number2);
    }

    @WebMethod
    @Action(
            input = "",
            output = "")
    public int addNumbersEmptyAction(int number1, int number2) throws AddNumbersException {
        return doStuff(number1, number2);
    }

    @WebMethod(action = "http://example.com/input")
    @Action(
            output = "http://example.com/output")
    public int addNumbers(int number1, int number2) throws AddNumbersException {
        return doStuff(number1, number2);
    }

    @WebMethod
    @Action(
            input = "http://example.com/input2",
            output = "http://example.com/output2")
    public int addNumbers2(int number1, int number2) throws AddNumbersException {
        return doStuff(number1, number2);
    }

    @WebMethod(action="")
    public int addNumbers3(int number1, int number2) throws AddNumbersException {
        return doStuff(number1, number2);
    }

    @Oneway
    @WebMethod
    public void onewayNumbers(int i) {

    }
    int doStuff(int number1, int number2) throws AddNumbersException {
        if (number1 < 0 || number2 < 0) {
            throw new AddNumbersException("Negative numbers can't be added!",
                    "Numbers: " + number1 + ", " + number2);
        }
        return number1 + number2;
    }
    
}
