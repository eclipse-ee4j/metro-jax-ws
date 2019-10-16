/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import com.sun.istack.NotNull;
import com.sun.xml.ws.api.server.SDDocument;
import com.sun.xml.ws.wsdl.SDDocumentResolver;

import java.util.*;

/**
 * WSDL, schema document metadata utility class.
 *
 * @author Jitendra Kotamraju
 */
public class MetadataUtil {

    /**
     * Gets closure of all the referenced documents from the primary document(typically
     * the service WSDL). It traverses the WSDL and schema imports and builds a closure
     * set of documents.
     *
     * @param systemId primary wsdl or the any root document
     * @param resolver used to get SDDocumentImpl for a document
     * @param onlyTopLevelSchemas if true, the imported schemas from a schema would be ignored
     * @return all the documents
     */
    public static Map<String, SDDocument> getMetadataClosure(@NotNull String systemId,
            @NotNull SDDocumentResolver resolver, boolean onlyTopLevelSchemas) {
        Map <String, SDDocument> closureDocs = new HashMap<String, SDDocument>();
        Set<String> remaining = new HashSet<String>();
        remaining.add(systemId);

        while(!remaining.isEmpty()) {
            Iterator<String> it = remaining.iterator();
            String current = it.next();
            remaining.remove(current);

            SDDocument currentDoc = resolver.resolve(current);
            SDDocument old = closureDocs.put(currentDoc.getURL().toExternalForm(), currentDoc);
            assert old == null;

            Set<String> imports =  currentDoc.getImports();
            if (!currentDoc.isSchema() || !onlyTopLevelSchemas) {
                for(String importedDoc : imports) {
                    if (closureDocs.get(importedDoc) == null) {
                        remaining.add(importedDoc);
                    }
                }
            }
        }

        return closureDocs;
    }

}
