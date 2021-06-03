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

package org.eclipse.metro.helidon.example.fromwsdl;

import jakarta.annotation.Resource;
import jakarta.jws.WebService;
import jakarta.xml.ws.WebServiceContext;
import java.util.Objects;
import java.util.TreeMap;
import org.example.duke.AddNumbersFault;
import org.example.duke.AddNumbersFault_Exception;

/*
 * Normally the web service implementation class would implement the endpointInterface class.
 * However, it is not necessary as this sample demonstrates.  It is useful to implement the
 * endpointInteface as the compiler will catch errors in the methods signatures of the 
 * implementation class.
 */
@WebService(
        wsdlLocation="WEB-INF/wsdl/AddNumbers.wsdl",
        endpointInterface = "org.example.duke.AddNumbersPortType"
)
public class AddNumbersImpl {

    @Resource
    private WebServiceContext ctx;

    /**
     * @param number1
     * @param number2
     * @return The sum
     * @throws AddNumbersFault_Exception if any of the numbers to be added is
     * negative.
     */
    public int addNumbers(int number1, int number2) throws AddNumbersFault_Exception {
        Objects.nonNull(ctx);
        if (number1 < 0 || number2 < 0) {
            String message = "Negative number cant be added!";
            String detail = "Numbers: " + number1 + ", " + number2;
            AddNumbersFault fault = new AddNumbersFault();
            fault.setMessage(message);
            fault.setFaultInfo(detail);
            throw new AddNumbersFault_Exception(message, fault);
        }
        return number1 + number2;
    }

    /*
     * Simple one-way method that takes an integer.
     */
    public void oneWayInt(int number) {
        Objects.nonNull(ctx);
        System.out.println("");
        System.out.println("Service received: " + number);
        System.out.println("MessageContext: ");
        new TreeMap<>(ctx.getMessageContext()).entrySet()
                .forEach((entry) ->
                        System.out.println(entry.getKey() + "\n\t" + entry.getValue()));
        System.out.println("");
    }

}
