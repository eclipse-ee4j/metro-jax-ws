/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package async.client;

import javax.xml.ws.AsyncHandler;
import javax.xml.ws.Response;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.xml.ws.BindingProvider;
import com.sun.xml.ws.developer.JAXWSProperties;
public class AddNumbersClient {
    private AddNumbersImpl port;

    public AddNumbersClient() {
        port = new AddNumbersService().getAddNumbersImplPort();
    }

    public static void main(String[] args) {
        try {
            AddNumbersClient client = new AddNumbersClient();
            //invoke synchronous method
            client.invokeSynchronous();

            //invoke async polling method
            client.invokeAsyncPoll();

            //invoke async callback method
            client.invokeAsyncCallback();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void invokeSynchronous() throws RemoteException {
        int number1 = 10;
        int number2 = 20;

        System.out.println("\nInvoking synchronous addNumber():");
        int result = port.addNumbers(number1, number2);
        System.out.printf("The result of adding %d and %d is %d.\n", number1, number2, result);
    }

    private void invokeAsyncPoll() throws InterruptedException, ExecutionException {
        int number1 = 10;
        int number2 = 20;

        //set request timeout to 30 sec, so that the client does n't wait forever
        int timeout = 30000;
        ((BindingProvider)port).getRequestContext().put(JAXWSProperties.REQUEST_TIMEOUT, timeout);

        System.out.println("\nInvoking Asynchronous Polling addNumbersAsync():");
        Response<AddNumbersResponse> resp = port.addNumbersAsync(number1, number2);
        while (!resp.isDone()) {
            System.out.println("No Response yet, Sleeping for 1 sec");
            Thread.sleep(1000);
        }
        AddNumbersResponse output = resp.get();
        System.out.printf("The result of adding %d and %d is %d.\n", number1, number2, output.getReturn());
    }

    private void invokeAsyncCallback() throws InterruptedException {
        int number1 = 10;
        int number2 = 20;

        System.out.println("\nInvoking Asynchronous Callback addNumbersAsync():");
        AddNumbersCallbackHandler callbackHandler = new AddNumbersCallbackHandler();
        Future<?> response = port.addNumbersAsync(number1, number2, callbackHandler);
        Thread.sleep(8000);
        if(response.isDone()) {
            AddNumbersResponse output = callbackHandler.getResponse();
            System.out.printf("The result of adding %d and %d is %d.\n", number1, number2, output.getReturn());
        } else {
            System.out.println("Waited 8 sec, no response yet, something wrong");
        }        
    }

    /**
     * Async callback handler
     */
    private class AddNumbersCallbackHandler implements AsyncHandler<AddNumbersResponse> {
        private AddNumbersResponse output;

        /*
        * @see javax.xml.ws.AsyncHandler#handleResponse(javax.xml.ws.Response)
        */
        public void handleResponse(Response<AddNumbersResponse> response) {
            System.out.println("AddNumbersCallbackHandler: Received Response from the service");
            try {
                output = response.get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        AddNumbersResponse getResponse() {
            return output;
        }
    }

}
