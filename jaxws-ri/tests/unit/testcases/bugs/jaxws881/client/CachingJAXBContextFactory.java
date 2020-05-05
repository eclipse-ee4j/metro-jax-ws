/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws881.client;

/**
 * TODO: Write some description here ...
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */

import com.sun.istack.NotNull;
import org.glassfish.jaxb.runtime.api.JAXBRIContext;
import org.glassfish.jaxb.runtime.api.TypeReference;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.developer.JAXBContextFactory;

import jakarta.xml.bind.JAXBException;
import java.util.List;

/**

 This implementation should only be used on one service whose definition does
 not change. It caches the JAXBRIContext
 created the first time it is called and always returns that.
 */
public class CachingJAXBContextFactory implements JAXBContextFactory {

    private JAXBRIContext context = null;

    @Override
    public JAXBRIContext createJAXBContext(@NotNull SEIModel sei, @NotNull List<Class> classesToBind, @NotNull List<TypeReference> typeReferences) throws JAXBException {
        if (this.context == null) {
            this.context = JAXBContextFactory.DEFAULT.createJAXBContext(sei, classesToBind, typeReferences);
        }

        return this.context;
    }
}

