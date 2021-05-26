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

package annotations.server;

import javax.jws.WebService;

@WebService(endpointInterface="annotations.server.AddNumbersIF")
public class AddNumbersImpl {
	
    /**
     * @param number1 an int > 0
     * @param number2 an int > 0
     * @return The sum
     * @throws AddNumbersException
     *             if any of the numbers to be added is negative.
     */
    public int addNumbers(int number1, int number2) throws AddNumbersException {
        if (number1 < 0 || number2 < 0) {
                throw new AddNumbersException("Negative number cant be added!",
                                "Numbers: " + number1 + ", " + number2);
        }
        return number1 + number2;
    }
}
