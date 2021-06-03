/*
 * Copyright (c) 2021 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.eclipse.metro.helidon.example.addressing;

import jakarta.jws.WebService;
import jakarta.xml.ws.Action;
import jakarta.xml.ws.FaultAction;
import jakarta.xml.ws.soap.Addressing;

/**
 *
 * @author lukas
 */
@Addressing
@WebService
public class AddressingWS {

    @Action(
            input = "http://example.com/input",
            output = "http://example.com/output")
    public int addNumbers(int number1, int number2) throws AddNumbersException {
        return impl(number1, number2);
    }

    public int addNumbers2(int number1, int number2) throws AddNumbersException {
        return impl(number1, number2);
    }

    @Action(
            input = "http://example.com/input3",
            output = "http://example.com/output3",
            fault = {
                @FaultAction(className = AddNumbersException.class, value = "http://example.com/fault3")
            }
    )
    public int addNumbers3(int number1, int number2) throws AddNumbersException {
        return impl(number1, number2);
    }

    int impl(int number1, int number2) throws AddNumbersException {
        if (number1 < 0 || number2 < 0) {
            throw new AddNumbersException("Negative numbers can't be added!",
                    "Numbers: " + number1 + ", " + number2);
        }
        return number1 + number2;
    }
}
