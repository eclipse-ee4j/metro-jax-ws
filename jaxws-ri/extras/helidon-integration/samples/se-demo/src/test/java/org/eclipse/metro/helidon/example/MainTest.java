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

package org.eclipse.metro.helidon.example;

import io.helidon.webserver.WebServer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import org.example.duke.AddNumbersPortType;
import org.example.duke.AddNumbersService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author lukas
 */
public class MainTest {

    private static WebServer webServer;

    private static final String SOAP_REQUEST
            = "<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
            + "    <S:Body>\n"
            + "        <ns2:greet xmlns:ns2=\"http://ws.example.helidon.metro.eclipse.org/\">\n"
            + "            <arg0>Jack</arg0>\n"
            + "        </ns2:greet>\n"
            + "    </S:Body>\n"
            + "</S:Envelope>\n";

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
    public void testSoapService() throws Exception {
        HttpURLConnection conn = getURLConnection("http://localhost:" + webServer.port() + "/metro/SoapWsService", "POST");
        conn.setRequestProperty("Accept", "text/xml; charset=UTF-8");
        conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
        conn.setRequestProperty("Content-Length", Integer.toString(SOAP_REQUEST.length()));
        conn.setDoOutput(true);
        OutputStream os = conn.getOutputStream();
        os.write(SOAP_REQUEST.getBytes(StandardCharsets.UTF_8));
        os.close();

        Assertions.assertEquals(200, conn.getResponseCode(), "SOAP response");

        String rsp = readStream(conn.getInputStream());
        Assertions.assertTrue(rsp.contains("Jack"), "Jack not found!");
    }

    @Test
    public void testFromWSDL() throws Exception {
        HttpURLConnection conn = getURLConnection("http://localhost:" + webServer.port() + "/metro/addnumbers?wsdl", "GET");
        conn.setRequestProperty("Accept", "text/xml; charset=UTF-8");
        conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
        Assertions.assertEquals(200, conn.getResponseCode(), "SOAP response");

        String rsp = readStream(conn.getInputStream());
        Assertions.assertFalse(rsp.contains("import"), "import found");

        Assertions.assertEquals(200, conn.getResponseCode(), "SOAP response");

        AddNumbersService client = new AddNumbersService(new URL("http://localhost:" + webServer.port() + "/metro/addnumbers?wsdl"));
        AddNumbersPortType port = client.getAddNumbersPort();

        // nothing is returned, just a message on System.out is printed by the service
        port.oneWayInt(42);

        Assertions.assertEquals(12, port.addNumbers(5, 7));
    }

    @Test
    public void testSoapBehindRest() throws Exception {
        HttpURLConnection conn = null;
        String baseUrl = "http://localhost:" + webServer.port() + "/rest";
        try {
            conn = getURLConnection(baseUrl + "/toWords?number=123", "GET");
            conn.setRequestProperty("Accept", "text/plain");
            Assertions.assertEquals(200, conn.getResponseCode(), "toWords HTTP response");

            String rsp = readStream(conn.getInputStream());
            Assertions.assertEquals("123 == one hundred and twenty three ", rsp);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            conn = getURLConnection(baseUrl + "/toDollars?number=456", "GET");
            conn.setRequestProperty("Accept", "text/plain");
            Assertions.assertEquals(200, conn.getResponseCode(), "toDollars HTTP response");

            String rsp = readStream(conn.getInputStream());
            Assertions.assertEquals("456 == four hundred and fifty six dollars", rsp);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private HttpURLConnection getURLConnection(String u, String method) throws Exception {
        URL url = new URL(u);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        return conn;
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        }
        return sb.toString();
    }
}
