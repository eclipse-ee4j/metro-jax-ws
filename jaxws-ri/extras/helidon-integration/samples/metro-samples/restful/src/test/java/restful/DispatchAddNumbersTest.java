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

package restful;

import io.helidon.webserver.WebServer;
import javax.xml.transform.Source;
import java.net.URI;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.Dispatch;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.http.HTTPBinding;
import java.io.ByteArrayOutputStream;
import java.util.Properties;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class DispatchAddNumbersTest {

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

    private static final QName serviceQName = new QName("http://duke.example.org", "AddNumbersService");
    private static final QName portQName = new QName("http://duke.example.org", "AddNumbersPort");

    private static final String endpointAddress = "http://localhost:8080/metro/addnumbers";
    private static final String queryString = "num1=30&num2=20";
    private static final String pathInfo = "/metro/addnumbers/num1/10/num2/20";

    @Test
    public void testService() throws Exception {

        DispatchAddNumbersTest client = new DispatchAddNumbersTest();

        Service service = client.createService();
        URI endpointURI = new URI(endpointAddress);

        String path = endpointURI.getPath();
        String query = endpointURI.getQuery();

        service.addPort(portQName, HTTPBinding.HTTP_BINDING, endpointAddress);

        Dispatch<Source> d = service.createDispatch(portQName, Source.class, Service.Mode.MESSAGE);

        Map<String, Object> requestContext = d.getRequestContext();
        requestContext.put(MessageContext.HTTP_REQUEST_METHOD, "GET");

        requestContext.put(MessageContext.QUERY_STRING, queryString);
        //this is the original path part of uri
        requestContext.put(MessageContext.PATH_INFO, path);
        System.out.println("Invoking Restful GET Request with query string " + queryString);

        Source result = d.invoke(null);
        printSource(result);

        requestContext.put(MessageContext.PATH_INFO, pathInfo);

        System.out.println("Invoking Restful GET Request with path info " + pathInfo);

        result = d.invoke(null);
        printSource(result);
    }

    private Service createService() {
        Service service = Service.create(serviceQName);
        return service;
    }

    private static void printSource(Source source) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            StreamResult sr = new StreamResult(bos);
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            Properties oprops = new Properties();
            oprops.put(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperties(oprops);
            trans.transform(source, sr);
            System.out.println("**** Response ******" + bos.toString());
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
