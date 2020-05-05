/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.custom_client_jaxbcontext.client;

import junit.framework.TestCase;
import com.sun.xml.ws.developer.JAXBContextFactory;
import com.sun.xml.ws.developer.UsesJAXBContextFeature;
import com.sun.xml.ws.api.model.SEIModel;
import org.glassfish.jaxb.runtime.api.JAXBRIContext;
import org.glassfish.jaxb.runtime.api.TypeReference;

import jakarta.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Holder;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Jitendra Kotamraju
 */
public class JAXBContextTest extends TestCase {

    private EchoImpl proxy;

    public JAXBContextTest(String name) throws Exception {
        super(name);
    }

    protected void setUp() throws Exception {
        UsesJAXBContextFeature f = new UsesJAXBContextFeature(ClientJAXBContextFactory.class);
        proxy = new EchoImplService().getEchoImplPort(f);
    }

    public void testEcho() throws Exception {
        Toyota toyota = new Toyota();
        toyota.setModel("camry");
        toyota.setYear("2000");
        toyota.setColor("blue");
        Holder<Car> holder = new Holder<Car>(toyota);
        proxy.echo(holder);
        assertTrue(holder.value instanceof Toyota);
        assertEquals("Toyota", holder.value.getMake());
        assertEquals("camry", holder.value.getModel());
        assertEquals("2000", holder.value.getYear());
        assertEquals("blue", ((Toyota)holder.value).getColor());
    }

    public static class ClientJAXBContextFactory implements JAXBContextFactory {

        public JAXBRIContext createJAXBContext(SEIModel sei,
            List<Class> classesToBind, List<TypeReference> typeReferences)
                throws JAXBException {
            System.out.println("Using client's custom JAXBContext");

            // Adding Toyota.class to our JAXBRIContext
            List<Class> classList = new ArrayList<Class>();
            classList.addAll(classesToBind);
            classList.add(Toyota.class);

            List<TypeReference> refList = new ArrayList<TypeReference>();
            refList.addAll(typeReferences);
            refList.add(new TypeReference(new QName("","arg0"),Toyota.class));

            return JAXBRIContext.newInstance(classList.toArray
                    (new Class[classList.size()]),
                    refList, null, sei.getTargetNamespace(), false, null);
        }

    }

}
