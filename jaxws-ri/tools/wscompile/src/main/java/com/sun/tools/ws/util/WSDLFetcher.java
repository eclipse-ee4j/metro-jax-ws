/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.util;

import com.sun.istack.NotNull;
import com.sun.tools.ws.resources.WscompileMessages;
import com.sun.tools.ws.wscompile.WsimportListener;
import com.sun.tools.ws.wscompile.WsimportOptions;
import com.sun.tools.ws.wsdl.parser.DOMForest;
import com.sun.tools.ws.wsdl.parser.MetadataFinder;
import com.sun.xml.txw2.output.IndentingXMLStreamWriter;
import com.sun.xml.ws.api.server.PortAddressResolver;
import com.sun.xml.ws.streaming.SourceReaderFactory;
import com.sun.xml.ws.wsdl.parser.WSDLConstants;
import com.sun.xml.ws.wsdl.writer.DocumentLocationResolver;
import com.sun.xml.ws.wsdl.writer.WSDLPatcher;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.dom.DOMSource;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Rama Pulavarthi
 */
public class WSDLFetcher {
    private WsimportOptions options;
    private WsimportListener listener;
    public WSDLFetcher(WsimportOptions options, WsimportListener listener) {
        this.options = options;
        this.listener = listener;
    }


    /**
     *  Fetches the wsdls in the DOMForest to the options.destDir
     * @return location of fetched root WSDL document
     */
    public String fetchWsdls(MetadataFinder forest) throws IOException, XMLStreamException {
        String rootWsdl = null;
        for(String root: forest.getRootDocuments()) {
            rootWsdl = root;
        }

        Set<String> externalRefs = forest.getExternalReferences();
        Map<String,String> documentMap = createDocumentMap(forest, getWSDLDownloadDir(), rootWsdl, externalRefs);
        String rootWsdlName = fetchFile(rootWsdl,forest, documentMap,getWSDLDownloadDir());
        for(String reference: forest.getExternalReferences()) {
            fetchFile(reference,forest,documentMap,getWSDLDownloadDir());
        }
        return WSDL_PATH +"/" + rootWsdlName;
    }

    private String fetchFile(final String doc, DOMForest forest, final Map<String, String> documentMap, File destDir) throws IOException, XMLStreamException {

        DocumentLocationResolver docLocator = createDocResolver(doc, forest, documentMap);
        WSDLPatcher wsdlPatcher = new WSDLPatcher(new PortAddressResolver() {
            @Override
            public String getAddressFor(@NotNull QName serviceName, @NotNull String portName) {
                return null;
            }
        }, docLocator);

        XMLStreamReader xsr = null;
        XMLStreamWriter xsw = null;
        OutputStream os = null;
        String resolvedRootWsdl = null;
        try {
            XMLOutputFactory writerfactory;
            xsr = SourceReaderFactory.createSourceReader(new DOMSource(forest.get(doc)), false);
            writerfactory = XMLOutputFactory.newInstance();
            resolvedRootWsdl = docLocator.getLocationFor(null, doc);
            File outFile = new File(destDir, resolvedRootWsdl);
            os = new FileOutputStream(outFile);
            if(options.verbose) {
                listener.message(WscompileMessages.WSIMPORT_DOCUMENT_DOWNLOAD(doc,outFile));
            }
            xsw = writerfactory.createXMLStreamWriter(os);
            //DOMForest eats away the whitespace loosing all the indentation, so write it through
            // indenting writer for better readability of fetched documents 
            IndentingXMLStreamWriter indentingWriter = new IndentingXMLStreamWriter(xsw);
            wsdlPatcher.bridge(xsr, indentingWriter);
            options.addGeneratedFile(outFile);
        } finally {
            try {
                if (xsr != null) {xsr.close();}
                if (xsw != null) {xsw.close();}
            } finally {
                if (os != null) {os.close();}
            }
        }
        return resolvedRootWsdl;


    }
    private Map<String,String> createDocumentMap(MetadataFinder forest, File baseDir, final String rootWsdl, Set<String> externalReferences) {
        Map<String,String> map = new HashMap<String,String>();
        String rootWsdlFileName = rootWsdl;
        String rootWsdlName;

        int slashIndex = rootWsdl.lastIndexOf("/");
        if( slashIndex >= 0) {
            rootWsdlFileName = rootWsdl.substring(slashIndex+1);
        }
        if(!rootWsdlFileName.endsWith(WSDL_FILE_EXTENSION)) {
            Document rootWsdlDoc =  forest.get(rootWsdl);
            NodeList serviceNodes = rootWsdlDoc.getElementsByTagNameNS(WSDLConstants.QNAME_SERVICE.getNamespaceURI(),WSDLConstants.QNAME_SERVICE.getLocalPart());
            if (serviceNodes.getLength() == 0) {
                rootWsdlName = "Service";
            } else {
                Node serviceNode = serviceNodes.item(0);
                String serviceName = ((Element)serviceNode).getAttribute( WSDLConstants.ATTR_NAME);
                rootWsdlName = serviceName;
            }
            rootWsdlFileName = rootWsdlName+ WSDL_FILE_EXTENSION;
        } else {
            rootWsdlName = rootWsdlFileName.substring(0,rootWsdlFileName.length()-5);
        }

        map.put(rootWsdl,sanitize(rootWsdlFileName));

        int i =1;
        for(String ref: externalReferences) {
            Document refDoc =  forest.get(ref);
            Element rootEl = refDoc.getDocumentElement();
            String fileExtn;
            String fileName = null;
            int index = ref.lastIndexOf("/");
            if (index >= 0) {
                fileName = ref.substring(index + 1);
            }
            if(rootEl.getLocalName().equals(WSDLConstants.QNAME_DEFINITIONS.getLocalPart()) && rootEl.getNamespaceURI().equals(WSDLConstants.NS_WSDL)) {
              fileExtn = WSDL_FILE_EXTENSION;
            } else if(rootEl.getLocalName().equals(WSDLConstants.QNAME_SCHEMA.getLocalPart()) && rootEl.getNamespaceURI().equals(WSDLConstants.NS_XMLNS)) {
              fileExtn = SCHEMA_FILE_EXTENSION;
            } else {
                fileExtn = ".xml";
            }
            if(fileName != null && (fileName.endsWith(WSDL_FILE_EXTENSION) || fileName.endsWith(SCHEMA_FILE_EXTENSION))) {
                map.put(ref, rootWsdlName+"_"+fileName);                
            } else {
                map.put(ref, rootWsdlName+"_metadata"+ (i++) + fileExtn);
            }
        }
        return map;
    }

    private DocumentLocationResolver createDocResolver(final String baseWsdl, final DOMForest forest, final Map<String,String> documentMap) {
        return new DocumentLocationResolver() {
            @Override
            public String getLocationFor(String namespaceURI, String systemId) {
                try {
                    URL reference = new URL(new URL(baseWsdl),systemId);
                    systemId = reference.toExternalForm();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
                if(documentMap.get(systemId) != null) {
                    return documentMap.get(systemId);
                } else {
                    String parsedEntity = forest.getReferencedEntityMap().get(systemId);
                    return documentMap.get(parsedEntity);
                }
            }
        };
    }

    private String sanitize(String fileName) {
        fileName = fileName.replace('?', '.');
        StringBuilder sb = new StringBuilder(fileName);
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (Character.isLetterOrDigit(c) ||
                    (c == '/') ||
                    (c == '.') ||
                    (c == '_') ||
                    (c == ' ') ||
                    (c == '-')) {
                continue;
            } else {
                sb.setCharAt(i, '_');
            }
        }
        return sb.toString();
    }

    private File getWSDLDownloadDir() {
        File wsdlDir = new File(options.destDir, WSDL_PATH);
        boolean created = wsdlDir.mkdirs();
        if (options.verbose && !created) {
            listener.message(WscompileMessages.WSCOMPILE_NO_SUCH_DIRECTORY(wsdlDir));
        }
        return wsdlDir;
    }

    private static String WSDL_PATH="META-INF/wsdl";
    private static String WSDL_FILE_EXTENSION=".wsdl";
    private static String SCHEMA_FILE_EXTENSION=".xsd";
}
