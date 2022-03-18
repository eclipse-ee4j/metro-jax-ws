/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.custom_server_jaxbcontext.server;

import jakarta.jws.*;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.ws.developer.JAXBContextFactory;
import com.sun.xml.ws.developer.UsesJAXBContext;
import com.sun.xml.ws.api.model.SEIModel;
import org.glassfish.jaxb.runtime.api.JAXBRIContext;
import org.glassfish.jaxb.runtime.api.TypeReference;


/**
 * JAX-WS RI runtime uses the custom JAXBRIContext provided by the
 * applicaiton. This endpoint plugs-in a custom JAXBRIContext to
 * achieve type substitution
 *
 * @author Jitendra Kotamraju
 */
// DO NOT put @XmlSeeAlso({Totyota.class})
@WebService
@UsesJAXBContext(value= EchoImpl.ServerJAXBContextFactory.class)
public class EchoImpl {
    
    public Car echo(Car car) {
        return car;
    }

    public static class ServerJAXBContextFactory implements JAXBContextFactory {

        public JAXBRIContext createJAXBContext(SEIModel sei,
            List<Class> classesToBind, List<TypeReference> typeReferences)
                throws JAXBException {
            System.out.println("Using server's custom JAXBContext");

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
