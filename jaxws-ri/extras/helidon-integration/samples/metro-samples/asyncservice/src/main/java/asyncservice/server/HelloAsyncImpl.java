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

package asyncservice.server;

import com.sun.xml.ws.api.server.AsyncProvider;
import com.sun.xml.ws.api.server.AsyncProviderCallback;

import jakarta.xml.bind.JAXBContext;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceProvider;
import java.io.ByteArrayInputStream;

@WebServiceProvider(
        wsdlLocation = "WEB-INF/wsdl/hello_literal.wsdl",
        targetNamespace = "urn:test",
        serviceName = "Hello")
public class HelloAsyncImpl implements AsyncProvider<Source> {

    static String body = "<HelloResponse xmlns=\"urn:test:types\"><argument xmlns=\"\">%s</argument><extra xmlns=\"\">%s</extra></HelloResponse>";
    private static final JAXBContext jaxbContext = createJAXBContext();
    private int bodyIndex;

    /*
    public jakarta.xml.bind.JAXBContext getJAXBContext(){
        return jaxbContext;
    } */
    private static jakarta.xml.bind.JAXBContext createJAXBContext() {
        try {
            return jakarta.xml.bind.JAXBContext.newInstance(ObjectFactory.class);
        } catch (jakarta.xml.bind.JAXBException e) {
            throw new WebServiceException(e.getMessage(), e);
        }
    }

    private Hello_Type recvBean(Source source) throws Exception {
        System.out.println("**** recvBean ******");
        return (Hello_Type) jaxbContext.createUnmarshaller().unmarshal(source);
    }

    private Source sendSource(String arg, String extra) {
        System.out.println("**** sendSource ******");
        String response = String.format(body, arg, extra);
        return new StreamSource(
                new ByteArrayInputStream(response.getBytes()));
    }

    public void invoke(Source source, AsyncProviderCallback<Source> cbak, WebServiceContext ctxt) {
        System.out.println("**** Received in AsyncService Impl ******");
        try {
            Hello_Type hello = recvBean(source);
            String arg = hello.getArgument();
            new Thread(new RequestHandler(cbak, hello)).start();

        } catch (Exception e) {
            throw new WebServiceException("Endpoint failed", e);
        }
    }

    private class RequestHandler implements Runnable {

        final AsyncProviderCallback<Source> cbak;
        final Hello_Type hello;

        public RequestHandler(AsyncProviderCallback<Source> cbak, Hello_Type hello) {
            this.cbak = cbak;
            this.hello = hello;
        }

        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ie) {
                cbak.sendError(new WebServiceException("Interrupted..."));
                return;
            }
            try {
                cbak.send(sendSource(hello.getArgument(), hello.getExtra()));
            } catch (Exception e) {
                cbak.sendError(new WebServiceException(e));
            }
        }
    }

}
