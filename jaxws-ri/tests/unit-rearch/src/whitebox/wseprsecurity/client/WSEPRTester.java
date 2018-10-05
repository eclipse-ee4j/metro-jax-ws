/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.wseprsecurity.client;

import com.sun.xml.ws.api.addressing.WSEndpointReference;
import junit.framework.TestCase;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.EndpointReference;
import java.io.*;
import java.net.*;
import java.security.*;
import java.util.*;

/**
 * @author Jitendra Kotamraju
 */

public class WSEPRTester extends TestCase {

    public WSEPRTester(String name) {
        super(name);
        Policy core = Policy.getPolicy();
        Policy custom = new CustomPolicy();
        Policy composite = new CompositePolicy(core, custom);
        Policy.setPolicy(composite);
        System.setSecurityManager(new SecurityManager());
    }

    public void testEPRsWithSecurityMgr() throws Exception {
        new Test();
    }

    private static final class Test {
        Test() throws Exception {
            URL res = getClass().getResource("../epr/ms_epr_metadata.xml");
            File folder = new File(res.getFile()).getParentFile();   // assuming that this is a file:// URL.

            for (File f : folder.listFiles()) {
                if(!f.getName().endsWith(".xml"))
                    continue;
                InputStream is = new FileInputStream(f);
                StreamSource s = new StreamSource(is);
                EndpointReference epr = EndpointReference.readFrom(s);
                WSEndpointReference wsepr = new WSEndpointReference(epr);
                WSEndpointReference.Metadata metadata = wsepr.getMetaData();
            }
        }
    }

    private static final class CustomPolicy extends Policy {

        public PermissionCollection getPermissions(CodeSource codesource) {
            //System.out.println("CodeSource=" + codesource);
            Permissions col = new Permissions();
            URL location = codesource.getLocation();

            // Hack for now to find out the application code
            if (location.toExternalForm().contains("jaxws-ri/test/build/temp/classes")) {
                // Give appropriate permissions to application code
                System.out.println("NOT giving all permissions to app");

                col.add(new PropertyPermission("*", "read,write"));

                // Without FilePermission cannot read META-INF/services
                // So cannot create XMLInputFactory etc
                col.add(new FilePermission("<<ALL FILES>>", "read,write"));
            } else {
                // Give all permssions to JAX-WS runtime
                col.add(new AllPermission());
            }
            return col;
        }

        public void refresh() {
            // no op
        }
    }

    private static final class CompositePolicy extends Policy {

        private final Policy[] policies;

        public CompositePolicy(Policy... policies) {
            this.policies = policies;
        }

        public PermissionCollection getPermissions(ProtectionDomain domain) {
            Permissions perms = new Permissions();

            for (Policy p : policies) {
                PermissionCollection permCol = p.getPermissions(domain);
                for (Enumeration<Permission> en = permCol.elements(); en.hasMoreElements();) {
                    perms.add(en.nextElement());
                }
            }
            return perms;
        }

        public boolean implies(ProtectionDomain domain, Permission permission) {

            for (Policy p : policies) {
                if (p.implies(domain, permission)) {
                    return true;
                }
            }
            return false;
        }


        public PermissionCollection getPermissions(CodeSource codesource) {
            Permissions perms = new Permissions();
            for (Policy p : policies) {
                PermissionCollection permsCol = p.getPermissions(codesource);
                for (Enumeration<Permission> en = permsCol.elements(); en.hasMoreElements();) {
                    perms.add(en.nextElement());
                }
            }
            return perms;
        }

        public void refresh() {
            for (Policy p : policies) {
                p.refresh();
            }
        }
    }

}
