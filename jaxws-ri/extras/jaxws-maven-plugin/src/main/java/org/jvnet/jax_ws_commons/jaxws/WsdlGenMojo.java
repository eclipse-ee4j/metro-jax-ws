/*
 * Copyright 2021 Eclipse Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
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
import java.util.List;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * Reads a JAX-WS service endpoint implementation class
 * and generates WSDL and XML Schema files for a Jakarta
 * XML Web Service.
 *
 * @author lukas
 */
@Mojo(name = "wsdlgen", defaultPhase = LifecyclePhase.PROCESS_CLASSES, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class WsdlGenMojo extends AbstractWsGenMojo {

    /**
     * Directory containing the generated wsdl files.
     */
    @Parameter(defaultValue = "${project.build.directory}/generated-sources/wsdl")
    private File resourceDestDir;

    @Override
    protected File getResourceDestDir() {
        return resourceDestDir;
    }

    @Override
    protected File getClassesDir() {
        return new File(project.getBuild().getOutputDirectory());
    }

    @Override
    protected File getDestDir() {
        return resourceDestDir;
    }

    @Override
    protected File getSourceDestDir() {
        return getDefaultSrcOut();
    }

    @Override
    protected boolean getGenWSDL() {
        return true;
    }

    @Override
    protected File getDefaultSrcOut() {
        return new File(project.getBuild().getDirectory(), "generated-sources/wsgen");
    }

    protected void processSei(String sei) throws MojoExecutionException {
        getLog().info("Processing: " + sei);
        List<String> args = getWsGenArgs(sei, false);
        args.add("-Xnosource");
        getLog().info("jaxws:wsgen args: " + args);
        exec(args);
    }

}
