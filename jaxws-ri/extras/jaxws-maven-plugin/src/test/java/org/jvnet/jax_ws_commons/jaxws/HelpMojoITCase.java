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
 * @author Lukas Jungmann
 */
public class HelpMojoITCase {

    private static final File PROJECTS_DIR = new File(System.getProperty("it.projects.dir"));
    private File project;

    @Test
    public void testHelp() throws IOException {
        project = new File(PROJECTS_DIR, "help");

        Assertions.assertFileContains(project, "build.log", "This plugin has 6 goals:");
        Assertions.assertFileContains(project, "build.log", "jaxws:help");
        Assertions.assertFileContains(project, "build.log", "jaxws:wsdlgen");
        Assertions.assertFileContains(project, "build.log", "jaxws:wsgen");
        Assertions.assertFileContains(project, "build.log", "jaxws:wsimport");
        Assertions.assertFileContains(project, "build.log", "jaxws:wsgen-test");
        Assertions.assertFileContains(project, "build.log", "jaxws:wsimport-test");
    }
}
