/*
 * Copyright (c) 2013, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package whitebox.mex.resolver;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.wsdl.parser.MetaDataResolver;
import com.sun.xml.ws.api.wsdl.parser.MetadataResolverFactory;
import com.sun.xml.ws.api.wsdl.parser.ServiceDescriptor;
import com.sun.xml.ws.util.DOMUtil;
import com.sun.xml.ws.util.JAXWSUtils;
import com.sun.xml.ws.util.xml.XmlUtil;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import jakarta.xml.ws.WebServiceException;
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MetadataFactoryImpl extends MetadataResolverFactory {
    public
    @NotNull
    MetaDataResolver metadataResolver(@Nullable EntityResolver resolver) {
        return new MetaDataResolverImpl();
    }

    class MetaDataResolverImpl extends MetaDataResolver{

        public
        @Nullable
        ServiceDescriptor resolve(@NotNull URI location) {
            return new ServiceDescriptorImpl(location);
        }
    }

    class ServiceDescriptorImpl extends ServiceDescriptor{
        private final File wsdl;
        public ServiceDescriptorImpl(URI location) {
            wsdls.clear();
            schemas.clear();
            this.wsdl = new File(location);
        }

        public
        @NotNull
        List<? extends Source> getWSDLs() {
            Node n;
            try {
                n = DOMUtil.createDOMNode(new FileInputStream(wsdl));
                fetchWSDLs(n);
            } catch (FileNotFoundException e) {
                throw new WebServiceException(e);
            }
            return wsdls;
        }

        private void fetchWSDLs(Node node) {
            if (node.getNodeType() == Node.DOCUMENT_NODE)
                node = node.getFirstChild();

            NodeList metadatas = ((Element)node).getElementsByTagNameNS("http://schemas.xmlsoap.org/ws/2004/09/mex", "Metadata");
            assert metadatas.getLength() == 1;
            Element metadata = (Element) metadatas.item(0);
            NodeList nl = metadata.getElementsByTagNameNS(MEX_NS, "MetadataSection");
            for(int i = 0; i < nl.getLength(); i++){
                Element e = (Element) nl.item(i);
                String dialect = e.getAttribute("Dialect");
                assert dialect != null;
                if(!dialect.equals(WSDL_DIALECT))
                    continue;
                String id = e.getAttribute("Identifier");
                Element wsdl = DOMUtil.getFirstChild(e, "http://schemas.xmlsoap.org/wsdl/", "definitions");
                if(wsdl != null){
                    //patch wsdl:import@location if its ""
                    NodeList imports = wsdl.getElementsByTagNameNS("http://schemas.xmlsoap.org/wsdl/", "import");
                    if(imports.getLength() > 0){
                        Element wsdlImport = (Element) imports.item(0);
                        String location = wsdlImport.getAttribute("location");
                        if(location == null || location.equals("")){
                            location = wsdlImport.getAttribute("namespace");
                        }
                        wsdlImport.setAttribute("location", location);
                    }
                    if(wsdl.getNodeType() == Node.DOCUMENT_NODE)
                        wsdl = (Element) wsdl.getFirstChild();
                    DOMSource wsdlSrc = new DOMSource(wsdl);
                    if(id == null)
                        id = wsdl.getAttribute("targetNamespace");
                    assert id != null;
                    wsdlSrc.setSystemId(id);
                    wsdls.add(wsdlSrc);
                }
            }
        }

        public
        @NotNull
        List<? extends Source> getSchemas() {
            return schemas;
        }
    }

    private static final String WSDL_DIALECT = "http://schemas.xmlsoap.org/wsdl/";
    private static final String MEX_NS = "http://schemas.xmlsoap.org/ws/2004/09/mex";
    private static final List<DOMSource> wsdls = new ArrayList<DOMSource>();
    private static final List<DOMSource> schemas = new ArrayList<DOMSource>();
}
