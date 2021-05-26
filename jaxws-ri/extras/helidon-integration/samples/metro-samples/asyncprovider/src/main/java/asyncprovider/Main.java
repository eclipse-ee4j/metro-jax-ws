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

import io.helidon.config.Config;
import io.helidon.config.ConfigSources;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import org.eclipse.metro.helidon.MetroSupport;

/**
 *
 * @author lukas
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        startServer();
    }

    static WebServer startServer() {
        setupLogging();

        // By default this will pick up application.yaml from the classpath
        Config config = buildConfig();

        // Get webserver config from the "server" section of application.yaml
        WebServer server = WebServer.builder()
                .config(config.get("server"))
                .routing(buildRouting(config))
                .build();

        // Try to start the server. If successful, print some info and arrange to
        // print a message at shutdown. If unsuccessful, print the exception.
        server.start()
                .thenAccept(ws -> {
                    System.out.println("WEB server is up! http://localhost:" + ws.port());
                    ws.whenShutdown().thenRun(()
                            -> System.out.println("WEB server is DOWN. Good bye!"));
                })
                .exceptionally(t -> {
                    System.err.println("Startup failed: " + t.getMessage());
                    t.printStackTrace(System.err);
                    return null;
                });

        // Server threads are not daemon. No need to block. Just react.
        return server;
    }

    /**
     * Configure logging from logging.properties file.
     */
    private static void setupLogging() {
        try (InputStream is = Main.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        } catch (IOException ioe) {
            System.err.println("Cannot read logging configuration: " + ioe.getMessage());
            ioe.printStackTrace(System.err);
        }
    }

    private static Routing buildRouting(Config config) {
        return Routing.builder()
                .register("/metro", MetroSupport.create(config.get("metro")))
                .build();
    }

    private static Config buildConfig() {
        return Config.builder()
                .sources(
                        ConfigSources.classpath("application.yaml"))
                .build();
    }
}
