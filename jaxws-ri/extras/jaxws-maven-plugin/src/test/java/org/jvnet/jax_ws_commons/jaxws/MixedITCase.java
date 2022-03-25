/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jvnet.jax_ws_commons.jaxws;

import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

/**
 *
 * @author lukas
 */
public class MixedITCase {

    private static final File PROJECTS_DIR = new File(System.getProperty("it.projects.dir"));
    private File project;

    public MixedITCase() {
    }

    @Test
    public void jaxwscommons81_wsimport() throws IOException {
        project = new File(PROJECTS_DIR, "jaxwscommons-81");
        String version = System.getProperty("jaxws-ri.version");

        //check HelloWs
        Assertions.assertFilePresent(project, "target/classes/org/jvnet/jax_ws_commons/wsimport/test/HelloWs.class");
        Assertions.assertFileNotPresent(project, "target/test-classes/org/jvnet/jax_ws_commons/wsimport/test/HelloWs.class");
        Assertions.assertFilePresent(project, "target/generated-sources/wsimport/org/jvnet/jax_ws_commons/wsimport/test/HelloWs_Service.java");
        //this needs to be fixed as there should be a way to not generate sources
        //assertFileNotPresent(project, "target/jaxws/wsimport/org/jvnet/jax_ws_commons/wsimport/test/HelloWs.java");

        //check sample.wsdl (url)
//        Assertions.assertFilePresent(project, "target/custom/classes/org/jvnet/jaxwsri/sample/GreetersPortT.class");
//        Assertions.assertFilePresent(project, "target/custom/sources/org/jvnet/jaxwsri/sample/MyGreeter.java");
        //-wsdlLocation
//        Assertions.assertFileContains(project, "target/custom/sources/org/jvnet/jaxwsri/sample/MyGreeter.java", "http://example.com:43210/my?wsdl");
        //default dependency on JDK version
//        Assertions.assertFileContains(project, "target/custom/sources/org/jvnet/jaxwsri/sample/GreetersPortT.java", "JAX-WS RI " + version);
        //-target 2.0
//        assertFileContains(project, "target/custom/sources/org/jvnet/jaxwsri/sample/GreetersPortT.java", "Generated source version: 2.0");
        //-XadditionalHeaders
//        Assertions.assertFileContains(project, "target/custom/sources/org/jvnet/jaxwsri/sample/GreetersPortT.java", "Holder<String> additionalHeader2");
//        //xjc plugins (-Xequals etc)
//        assertFileContains(project, "target/custom/sources/org/jvnet/jaxwsri/sample/EchoType.java", "import org.jvnet.jaxb2_commons.lang.Equals;");

        //check AddService
        Assertions.assertFilePresent(project, "target/test-classes/wsimport/test/AddService.class");
        Assertions.assertFilePresent(project, "target/test-classes/wsimport/test/schema/SumType.class");
        Assertions.assertFilePresent(project, "target/generated-sources/test-wsimport/wsimport/test/SumUtil.java");
        Assertions.assertFileNotPresent(project, "target/classes/wsimport/test/AddService.class");
        Assertions.assertFileContains(project, "target/generated-sources/test-wsimport/wsimport/test/SumUtil.java", "JAX-WS RI " + version);
    }

    @Test
    public void jaxwscommons81_wsgen() throws IOException {
        project = new File(PROJECTS_DIR, "jaxwscommons-81");
        String version = System.getProperty("jaxws-ri.version");

        //check EchoService
        Assertions.assertFilePresent(project, "target/custom/sources/org/jvnet/jax_ws_commons/jaxws/test/jaxws/EchoResponse.java");
        Assertions.assertFilePresent(project, "target/custom/classes/org/jvnet/jax_ws_commons/jaxws/test/jaxws/Echo.class");
        Assertions.assertFilePresent(project, "target/classes/org/jvnet/jax_ws_commons/jaxws/test/EchoService.class");
        //-wsdl[...]
        Assertions.assertFilePresent(project, "target/wsdl/EchoService.wsdl");
//        //-inlineSchemas
//        assertFileContains(project, "target/wsdl/EchoService.wsdl", "xs:complexType");
//        assertFileNotPresent(project, "target/wsdl/EchoService_schema1.xsd");
        Assertions.assertFileNotPresent(project, "target/jaxws/wsgen/wsdl/EchoService.wsdl");
        Assertions.assertFileNotPresent(project, "target/generated-sources/wsdl/EchoService.wsdl");
        Assertions.assertFileNotPresent(project, "target/generated-sources/test-wsdl/EchoService.wsdl");
        //-wsdl:Xsoap12 + -extension
        Assertions.assertFileContains(project, "target/wsdl/EchoService.wsdl", "http://schemas.xmlsoap.org/wsdl/soap12/");
        //default dependency on JDK version
        Assertions.assertFileContains(project, "target/wsdl/EchoService.wsdl", "JAX-WS RI " + version);

        //check AddService
        Assertions.assertFilePresent(project, "target/classes/org/jvnet/jax_ws_commons/jaxws/test/jaxws/Add.class");
        Assertions.assertFilePresent(project, "target/classes/org/jvnet/jax_ws_commons/jaxws/test/AddService.class");
        Assertions.assertFileNotPresent(project, "target/classes/org/jvnet/jax_ws_commons/jaxws/test/jaxws/Add.java");
        Assertions.assertFileNotPresent(project, "target/classes/org/jvnet/jax_ws_commons/jaxws/test/AddService.java");
        Assertions.assertFileNotPresent(project, "target/wsdl/AddService.wsdl");
        Assertions.assertFileNotPresent(project, "target/jaxws/wsgen/wsdl/AddService.wsdl");

        //check TService
        Assertions.assertFilePresent(project, "target/generated-sources/test-wsdl/TService.wsdl");
        Assertions.assertFilePresent(project, "target/generated-sources/test-wsdl/ExService.wsdl");
        Assertions.assertFilePresent(project, "target/test-classes/org/jvnet/jax_ws_commons/jaxws/test/TService.class");
        Assertions.assertFilePresent(project, "target/test-classes/org/jvnet/jax_ws_commons/jaxws/test/jaxws/HelloResponse.class");
        Assertions.assertFilePresent(project, "target/generated-sources/test-wsgen/org/jvnet/jax_ws_commons/jaxws/test/jaxws/HelloResponse.java");
        Assertions.assertFileNotPresent(project, "target/test-classes/org/jvnet/jax_ws_commons/jaxws/test/TService.java");
        Assertions.assertFileNotPresent(project, "target/test-classes/org/jvnet/jax_ws_commons/jaxws/test/jaxws/HelloResponse.java");
        //default dependency on JDK version
        Assertions.assertFileContains(project, "target/generated-sources/test-wsdl/ExService.wsdl", "JAX-WS RI " + version);
        //-portname
        Assertions.assertFileContains(project, "target/generated-sources/test-wsdl/ExService.wsdl", "port name=\"ExPort\"");
        //-servicename
        Assertions.assertFileContains(project, "target/generated-sources/test-wsdl/ExService.wsdl", "service name=\"ExService\"");

        //package wsdl
        Assertions.assertJarContains(project, "jaxwscommons81-2.2.6.jar", "META-INF/wsdl/EchoService.wsdl");
//        assertJarNotContains(project, "jaxwscommons81-2.2.6.jar", "META-INF/wsdl/EchoService_schema1.xsd");
        Assertions.assertJarNotContains(project, "jaxwscommons81-2.2.6.jar", "META-INF/EchoService_schema.xsd");
        Assertions.assertJarNotContains(project, "jaxwscommons81-2.2.6.jar", "EchoService_schema.xsd");
        Assertions.assertJarNotContains(project, "jaxwscommons81-2.2.6.jar", "META-INF/wsdl/ExService.wsdl");
        Assertions.assertJarNotContains(project, "jaxwscommons81-2.2.6.jar", "ExService.wsdl");
    }
}
