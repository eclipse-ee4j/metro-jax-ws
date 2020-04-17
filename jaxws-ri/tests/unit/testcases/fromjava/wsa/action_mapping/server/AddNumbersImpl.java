/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.wsa.action_mapping.server;

import jakarta.jws.WebService;
import jakarta.jws.Oneway;
import jakarta.jws.WebMethod;
import jakarta.xml.ws.Action;
import jakarta.xml.ws.FaultAction;

/**
 * @author Rama Pulavarthi
 */

@WebService(name="AddNumbers",
    portName="AddNumbersPort",
    targetNamespace="http://foobar.org/",
    serviceName="AddNumbersService")
public class AddNumbersImpl {

    @Oneway
    public void notify(String s) {
       //
    }
    public int addNumbersNoAction(int number1, int number2) throws AddNumbersException {
        return doStuff(number1, number2);
    }

    @Action(
        input="",
        output="")
    public int addNumbersEmptyAction(int number1, int number2) throws AddNumbersException {
        return doStuff(number1, number2);
    }

    @WebMethod(action="http://example.com/soapaction")
    public int addNumbersSOAPAction(int number1, int number2) throws AddNumbersException {
        return doStuff(number1, number2);
    }

    @Action(
        input="http://example.com/input",
        output="http://example.com/output")
    public int addNumbers(int number1, int number2) throws AddNumbersException {
        return doStuff(number1, number2);
    }

    @Action(
        input="http://example.com/input2",
        output="http://example.com/output2")
    public int addNumbers2(int number1, int number2) throws AddNumbersException {
        return doStuff(number1, number2);
    }

    @Action(input="http://example.com/input3")
    public int addNumbers3(int number1, int number2) throws AddNumbersException {
        return doStuff(number1, number2);
    }

    @Action(input="finput1",
            output="foutput1",
            fault={
                @FaultAction(className=AddNumbersException.class, value="http://fault1")
            })
    public int addNumbersFault1(int number1, int number2) throws AddNumbersException {
        return doStuff(number1, number2);
    }

    @Action(input="finput2",
            output="foutput2",
            fault={
                @FaultAction(className=AddNumbersException.class, value="http://fault2/addnumbers"),
                @FaultAction(className=TooBigNumbersException.class, value="http://fault2/toobignumbers")
            })
    public int addNumbersFault2(int number1, int number2) throws AddNumbersException, TooBigNumbersException {
        throwTooBigException(number1, number2);

        return doStuff(number1, number2);
    }

    @Action(input="finput3",
            output="foutput3",
            fault={
                @FaultAction(className=AddNumbersException.class, value="http://fault3/addnumbers")
            })
    public int addNumbersFault3(int number1, int number2) throws AddNumbersException, TooBigNumbersException {
        throwTooBigException(number1, number2);

        return doStuff(number1, number2);
    }

    @Action(
        fault={
            @FaultAction(className=AddNumbersException.class, value="http://fault4/addnumbers")
        })
    public int addNumbersFault4(int number1, int number2) throws AddNumbersException, TooBigNumbersException {
        throwTooBigException(number1, number2);

        return doStuff(number1, number2);
    }

    @Action(
        fault={
            @FaultAction(className=TooBigNumbersException.class, value="http://fault5/toobignumbers")
        })
    public int addNumbersFault5(int number1, int number2) throws AddNumbersException, TooBigNumbersException {
        throwTooBigException(number1, number2);

        return doStuff(number1, number2);
    }

    @Action(
        fault={
            @FaultAction(className=AddNumbersException.class, value="http://fault6/addnumbers"),
            @FaultAction(className=TooBigNumbersException.class, value="http://fault6/toobignumbers")
        })
    public int addNumbersFault6(int number1, int number2) throws AddNumbersException, TooBigNumbersException {
        throwTooBigException(number1, number2);

        return doStuff(number1, number2);
    }

    @Action(
        fault={
            @FaultAction(className=AddNumbersException.class, value=""),
            @FaultAction(className=TooBigNumbersException.class, value="")
        })
    public int addNumbersFault7(int number1, int number2) throws AddNumbersException, TooBigNumbersException {
        throwTooBigException(number1, number2);

        return doStuff(number1, number2);
    }

    int doStuff(int number1, int number2) throws AddNumbersException {
        if (number1 < 0 || number2 < 0) {
            throw new AddNumbersException("Negative numbers can't be added!",
                                          "Numbers: " + number1 + ", " + number2);
                }
        return number1 + number2;
    }

    void throwTooBigException(int number1, int number2) throws TooBigNumbersException {
        if (number1 > 10 || number2 > 10)
            throw new TooBigNumbersException("Too bug numbers can't be added!",
                                             "Numbers: " + number1 + ", " + number2);
    }

}
