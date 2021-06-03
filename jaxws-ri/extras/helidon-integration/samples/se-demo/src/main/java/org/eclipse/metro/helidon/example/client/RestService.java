/*
 * Copyright (c) 2021 Oracle and/or its affiliates. All rights reserved.
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

package org.eclipse.metro.helidon.example.client;

import com.dataaccess.webservicesserver.NumberConversion;
import com.dataaccess.webservicesserver.NumberConversionSoapType;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author lukas
 */
public class RestService implements Service {

    private NumberConversionSoapType port = new NumberConversion().getNumberConversionSoap();

    @Override
    public void update(Routing.Rules rules) {
        rules
                .get("/toWords", this::toWords)
                .get("/toDollars", this::toDollars);
    }

    private void toDollars(ServerRequest request, ServerResponse response) {
        String number = request.queryParams().first("number").orElse("321");
        String dollars = port.numberToDollars(new BigDecimal(number));
        sendResponse(response, number + " == " + dollars);
    }

    private void toWords(ServerRequest request, ServerResponse response) {
        String number = request.queryParams().first("number").orElse("321");
        String dollars = port.numberToWords(new BigInteger(number));
        sendResponse(response, number + " == " + dollars);
    }

    private void sendResponse(ServerResponse response, String rsp) {
        response.send(rsp);
    }

}
