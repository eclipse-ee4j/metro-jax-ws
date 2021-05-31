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

package asyncservice;

import asyncservice.client.Hello;
import asyncservice.client.HelloResponse;
import asyncservice.client.Hello_Service;
import asyncservice.client.Hello_Type;
import io.helidon.webserver.WebServer;
import java.util.concurrent.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AsyncTest {

    private int noReqs = 0;
    private int noResps = 0;

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
    public void testAsyncService() throws Exception {
        Hello_Service service = new Hello_Service();
        final Hello proxy = service.getHelloAsyncPort();

        ExecutorService executor = Executors.newCachedThreadPool();
        synchronized (this) {
            noReqs = 4;
            noResps = 0;
        }
        for (int i = 0; i < noReqs; i++) {
            final int j = i;
            executor.execute(() -> {
                try {
                    run(proxy, "Hello", "Duke" + j);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
        while (!executor.awaitTermination(20L, TimeUnit.SECONDS));
        synchronized (this) {
            if (noReqs != noResps) {
                throw new Exception("No. of requests and responses did not match");
            }
        }
    }

    private void run(Hello proxy, String arg, String extra) throws Exception {
        Hello_Type req = new Hello_Type();
        req.setArgument(arg);
        req.setExtra(extra);
        System.out.println("Invoking Web Service with = " + arg + "," + extra);
        HelloResponse response = proxy.hello(req, req);
        System.out.println("arg=" + response.getArgument() + " extra=" + response.getExtra());

        if (!arg.equals(response.getArgument()) || !extra.equals(response.getExtra())) {
            throw new Exception("Mismatch in comparision");
        }

        synchronized (this) {
            ++noResps;
        }
    }
}
