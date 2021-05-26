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

module org.eclipse.metro.helidon.example {

    exports org.eclipse.metro.helidon.example.addressing;
    exports org.eclipse.metro.helidon.example.ws;
    exports org.eclipse.metro.helidon.example.fromwsdl;

    // opened for WebServiceContext to be set
    opens org.eclipse.metro.helidon.example.fromwsdl;
    // jaxws needs request/response wrapper beans
    // created either dynamically or manually using annotation processor
    // opened to at least JAXB
    // note: dynamic creation requires extra cmd line option:
    // --add-opens java.base/jdk.internal.misc=com.sun.xml.ws.rt
    opens org.eclipse.metro.helidon.example.addressing.jaxws;
    opens org.eclipse.metro.helidon.example.ws.jaxws;

    opens com.dataaccess.webservicesserver;
    opens org.example.duke;

    requires java.logging;

    requires java.annotation;
    requires java.jws;
    requires java.xml.bind;
    requires java.xml.ws;
    
    requires io.helidon.config;
    requires io.helidon.webserver;
    requires org.eclipse.metro.helidon;

//    requires com.sun.xml.ws.rt;
}
