/*
 * Copyright (c) 2004, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testutil;

import com.sun.xml.ws.api.server.DocumentAddressResolver;
import com.sun.xml.ws.api.server.PortAddressResolver;
import com.sun.xml.ws.api.server.SDDocument;
import com.sun.xml.ws.api.server.ServiceDefinition;
import com.sun.xml.ws.api.server.WSEndpoint;
import com.sun.xml.ws.transport.http.DeploymentDescriptorParser;
import com.sun.xml.ws.transport.http.DeploymentDescriptorParser.AdapterFactory;
import com.sun.xml.ws.transport.local.FileSystemResourceLoader;
import com.sun.xml.ws.util.ByteArrayBuffer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.xml.namespace.QName;

/**
 * Generates WSDL for local transport if there is no primary wsdl for the
 * endpoint in the WAR.
 *
 * @author Jitendra Kotamraju
 */
public class WSDLGen {

    static public boolean useLocal() {
        return Boolean.getBoolean("uselocal");
    }

    public static void main(String[] args) throws Exception {
        System.exit(run());
    }

    public static int run() throws Exception {
        if (!useLocal()) {
            return 0;
        }

        String outputDir = System.getProperty("tempdir");
        if (outputDir == null) {
            System.err.println("**** Set tempdir system property ****");
            return -1;
        }
        String riFile = outputDir+"/WEB-INF/sun-jaxws.xml";

        DeploymentDescriptorParser<WSEndpoint> parser = new DeploymentDescriptorParser<>(
                Thread.currentThread().getContextClassLoader(),
                new FileSystemResourceLoader(new File(outputDir)), null,
                new AdapterFactory<>() {
                    @Override
                    public WSEndpoint createAdapter(String name, String urlPattern, WSEndpoint<?> endpoint) {
                        return endpoint;
                    }
                });

        List<WSEndpoint> endpoints = parser.parse(new File(riFile));

        final String addr = new File(outputDir).toURI().toURL().toExternalForm();
        final String address = "local"+addr.substring(4);// file:// -> local://
        for(WSEndpoint endpoint : endpoints) {
			ServiceDefinition def = endpoint.getServiceDefinition();
            if (def == null) {
				continue;
			}
			SDDocument primary = def.getPrimary();
			File file = new File(primary.getURL().toURI());
			if (file.exists()) {
				System.out.println("**** Primary WSDL "+file+" already exists - not generating any WSDL artifacts ****");
				continue;				// Primary WSDL already exists
			}
			for(SDDocument doc : def) {
                int index= doc.getURL().getFile().indexOf("/WEB-INF/wsdl");
                String name = "";
                if(index == -1)
                    name = outputDir+"/WEB-INF/wsdl"+ doc.getURL().getFile();
                else
                    name = doc.getURL().getFile();
				System.out.println("Creating WSDL artifact="+name);
                ByteArrayBuffer buffer = new ByteArrayBuffer();
                doc.writeTo(
					new PortAddressResolver() {
						@Override
                        public String getAddressFor(QName serviceName, String portName) {
							return address;
						}
					},
					new DocumentAddressResolver() {
						@Override
                        public String getRelativeAddressFor(
							SDDocument current, SDDocument referenced) {
							String rel = referenced.getURL().toExternalForm();
							return rel.substring(6);	// remove file:/
						}
					},
					buffer);
                FileOutputStream fos = new FileOutputStream(name);
				buffer.writeTo(fos);
                fos.close();
			}
		}
        return 0;
    }

}
