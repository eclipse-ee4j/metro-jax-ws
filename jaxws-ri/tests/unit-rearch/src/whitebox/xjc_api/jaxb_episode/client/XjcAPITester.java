/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.xjc_api.jaxb_episode.client;

import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.Mapping;
import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
import com.sun.codemodel.JCodeModel;

import javax.xml.stream.XMLStreamReader;
import javax.xml.namespace.QName;
import java.net.URL;
import java.net.URI;
import java.io.File;

import junit.framework.TestCase;

public class XjcAPITester extends TestCase {
    public XjcAPITester(String s) {
        super(s);
    }

    public void testEpisode1() throws Exception {
        SchemaCompiler schemaCompiler = XJC.createSchemaCompiler();
        URL schemaRes = getClass().getClassLoader().getResource("wrap.xsd");
        //Since it needs SystemId, it has to be abosulte path
        XMLStreamReader schema =XMLStreamReaderFactory.create(schemaRes.toURI().toString(), schemaRes.openStream(),true);
        schemaCompiler.parseSchema(schemaRes.toURI().toString(),schema);
        URL episodefile = getClass().getClassLoader().getResource("episode.jar");
        schemaCompiler.getOptions().scanEpisodeFile(new File(episodefile.toURI()));

        S2JJAXBModel model = schemaCompiler.bind();
//        JCodeModel jcm = model.generateCode(null, null);
//        jcm.build(schemaCompiler.getOptions().createCodeWriter());
        Mapping mapping = model.get(new QName("urn:test:types","Hello"));
        assertNotNull(mapping);
        
    }

}
