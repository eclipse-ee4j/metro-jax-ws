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

package asyncprovider;

import asyncprovider.server.Hello;
import asyncprovider.server.HelloResponse;
import asyncprovider.server.Hello_Service;
import asyncprovider.server.Hello_Type;
import io.helidon.webserver.WebServer;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HelloAsyncTest {

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
    public void testAsyncProvider() {
        Hello_Service service = new Hello_Service();
        Hello proxy = service.getHelloAsyncPort();

        test(proxy, "sync", "source");
        test(proxy, "sync", "bean");
        test(proxy, "async", "source");
        test(proxy, "async", "bean");

    }

    private static void test(Hello proxy, String arg, String extra) {
        Hello_Type req = new Hello_Type();
        req.setArgument(arg);
        req.setExtra(extra);
        System.out.println("Invoking Web Service with = " + arg + "," + extra);
        HelloResponse response = proxy.hello(req, req);
        System.out.println("arg=" + response.getArgument()
                + " extra=" + response.getExtra());
    }
}
