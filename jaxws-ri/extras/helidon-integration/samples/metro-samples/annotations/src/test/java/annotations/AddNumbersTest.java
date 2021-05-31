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

package annotations;

import annotations.client.AddNumbers;
import annotations.client.AddNumbersException_Exception;
import annotations.client.AddNumbersImplService;
import io.helidon.webserver.WebServer;
import java.util.concurrent.TimeUnit;
import jakarta.xml.ws.BindingProvider;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AddNumbersTest {

    private static WebServer webServer;

    @BeforeAll
    public static void startTheServer() throws Exception {
        webServer = Main.startServer();

        long timeout = 2000; // 2 seconds should be enough to start the server
        long now = System.currentTimeMillis();

        while (!webServer.isRunning()) {
            Thread.sleep(100);
            if ((System.currentTimeMillis() - now) > timeout) {
                Assertions.fail("Failed to start webserver");
            }
        }
    }

    @AfterAll
    public static void stopServer() throws Exception {
        if (webServer != null) {
            webServer.shutdown()
                    .toCompletableFuture()
                    .get(10, TimeUnit.SECONDS);
        }
    }

    @Test
    public void testSuccess() throws AddNumbersException_Exception {
        AddNumbers port = new AddNumbersImplService().getAddNumbersImplPort();
        Map<String, Object> ctxt = ((BindingProvider)port).getRequestContext();
        int number1 = 10;
        int number2 = 20;

        System.out.printf("Invoking addNumbers(%d, %d)\n", number1, number2);
        int result = port.add(number1, number2);
        System.out.printf("The result of adding %d and %d is %d.\n\n", number1, number2, result);
        Assertions.assertEquals(30, result);
    }

    @Test
    public void testFault() throws AddNumbersException_Exception {
        AddNumbers port = new AddNumbersImplService().getAddNumbersImplPort();
        try {
            int number1 = -10;
            int number2 = 20;

            System.out.printf("Invoking addNumbers(%d, %d)\n", number1, number2);
            int result = port.add(number1, number2);
            System.out.printf("The result of adding %d and %d is %d.\n", number1, number2, result);
            Assertions.fail();
        } catch (AddNumbersException_Exception ex) {
            System.out.printf("Caught AddNumbersException_Exception: %s\n", ex.getFaultInfo().getFaultInfo());
        }
    }

}
