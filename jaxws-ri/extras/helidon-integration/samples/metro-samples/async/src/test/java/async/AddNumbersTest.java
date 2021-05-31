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

package async;

import async.client.AddNumbersImpl;
import async.client.AddNumbersResponse;
import async.client.AddNumbersService;
import jakarta.xml.ws.AsyncHandler;
import jakarta.xml.ws.Response;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import jakarta.xml.ws.BindingProvider;
import com.sun.xml.ws.developer.JAXWSProperties;
import io.helidon.webserver.WebServer;
import java.util.concurrent.TimeUnit;
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
    public void testSynchronous() throws RemoteException {
        AddNumbersImpl port = new AddNumbersService().getAddNumbersImplPort();
        int number1 = 10;
        int number2 = 20;

        System.out.println("\nInvoking synchronous addNumber():");
        int result = port.addNumbers(number1, number2);
        System.out.printf("The result of adding %d and %d is %d.\n", number1, number2, result);
        Assertions.assertEquals(30, result);
    }

    @Test
    public void testAsyncPoll() throws InterruptedException, ExecutionException {
        AddNumbersImpl port = new AddNumbersService().getAddNumbersImplPort();
        int number1 = 10;
        int number2 = 20;

        //set request timeout to 30 sec, so that the client does n't wait forever
        int timeout = 30000;
        ((BindingProvider) port).getRequestContext().put(JAXWSProperties.REQUEST_TIMEOUT, timeout);

        System.out.println("\nInvoking Asynchronous Polling addNumbersAsync():");
        Response<AddNumbersResponse> resp = port.addNumbersAsync(number1, number2);
        while (!resp.isDone()) {
            System.out.println("No Response yet, Sleeping for 1 sec");
            Thread.sleep(1000);
        }
        AddNumbersResponse output = resp.get();
        System.out.printf("The result of adding %d and %d is %d.\n", number1, number2, output.getReturn());
        Assertions.assertEquals(30, output.getReturn());
    }

    @Test
    public void testAsyncCallback() throws InterruptedException {
        AddNumbersImpl port = new AddNumbersService().getAddNumbersImplPort();
        int number1 = 10;
        int number2 = 20;

        System.out.println("\nInvoking Asynchronous Callback addNumbersAsync():");
        AddNumbersCallbackHandler callbackHandler = new AddNumbersCallbackHandler();
        Future<?> response = port.addNumbersAsync(number1, number2, callbackHandler);
        Thread.sleep(8000);
        if (response.isDone()) {
            AddNumbersResponse output = callbackHandler.getResponse();
            System.out.printf("The result of adding %d and %d is %d.\n", number1, number2, output.getReturn());
            Assertions.assertEquals(30, output.getReturn());
        } else {
            System.out.println("Waited 8 sec, no response yet, something wrong");
            Assertions.fail("Waited 8 sec, no response yet, something wrong");
        }
    }

    /**
     * Async callback handler
     */
    private class AddNumbersCallbackHandler implements AsyncHandler<AddNumbersResponse> {

        private AddNumbersResponse output;

        /*
        * @see jakarta.xml.ws.AsyncHandler#handleResponse(jakarta.xml.ws.Response)
         */
        public void handleResponse(Response<AddNumbersResponse> response) {
            System.out.println("AddNumbersCallbackHandler: Received Response from the service");
            try {
                output = response.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        AddNumbersResponse getResponse() {
            return output;
        }
    }

}
