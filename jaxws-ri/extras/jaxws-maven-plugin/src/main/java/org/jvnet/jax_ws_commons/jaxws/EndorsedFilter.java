/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013, 2020 Oracle and/or its affiliates. All rights reserved.
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

import java.util.Arrays;
import java.util.List;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.graph.DependencyFilter;
import org.eclipse.aether.graph.DependencyNode;

/**
 *
 * @author lukas
 */
public class EndorsedFilter implements DependencyFilter {

    private static final List<String> APIS = Arrays.asList(
            "jakarta.xml.bind-api", "jakarta.xml.ws-api",
            "jakarta.xml.soap-api", "jakarta.jws-api",
            "jakarta.annotation-api", "jakarta.activation-api",
            "jaxws-api", "jaxb-api",
            "saaj-api", "jsr181-api",
            "javax.annotation-api", "javax.activation-api",
            "webservices-api");

    @Override
    public boolean accept(DependencyNode dn, List<DependencyNode> list) {
        Artifact a = dn.getDependency().getArtifact();
        if (APIS.contains(a.getArtifactId())) {
            return true;
        } else if (a.getArtifactId().startsWith("javax.xml.ws")
                || a.getArtifactId().startsWith("javax.xml.bind")) {
            return true;
        } else if (a.getArtifactId().startsWith("jakarta.xml.ws")
                || a.getArtifactId().startsWith("jakarta.xml.bind")) {
            return true;
        }
        return false;
    }

}
