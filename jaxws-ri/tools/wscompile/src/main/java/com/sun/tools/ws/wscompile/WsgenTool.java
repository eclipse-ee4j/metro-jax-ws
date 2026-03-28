/*
 * Copyright (c) 1997, 2023 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wscompile;

import com.oracle.webservices.api.databinding.WSDLResolver;
import com.sun.tools.ws.ToolVersion;
import com.sun.tools.ws.processor.modeler.annotation.WebServiceAp;
import com.sun.tools.ws.processor.modeler.wsdl.ConsoleErrorReporter;
import com.sun.tools.ws.resources.WscompileMessages;
import com.sun.tools.xjc.util.NullStream;
import com.sun.xml.txw2.TXW;
import com.sun.xml.txw2.TypedXmlWriter;
import com.sun.xml.txw2.annotation.XmlAttribute;
import com.sun.xml.txw2.annotation.XmlElement;
import com.sun.xml.txw2.output.StreamSerializer;
import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.databinding.DatabindingConfig;
import com.sun.xml.ws.api.databinding.DatabindingFactory;
import com.sun.xml.ws.api.databinding.WSDLGenInfo;
import com.sun.xml.ws.api.server.Container;
import com.sun.xml.ws.api.wsdl.writer.WSDLGeneratorExtension;
import com.sun.xml.ws.binding.WebServiceFeatureList;
import com.sun.xml.ws.model.ExternalMetadataReader;
import com.sun.xml.ws.model.AbstractSEIModelImpl;
import com.sun.xml.ws.util.ServiceFinder;
import org.xml.sax.SAXParseException;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.xml.namespace.QName;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import jakarta.xml.ws.Holder;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;

import org.xml.sax.ext.Locator2Impl;

/**
 * @author Vivek Pandey
 */

/*
 * All annotation types are supported.
 */
public class WsgenTool {
    private final PrintStream out;
    private final WsgenOptions options = new WsgenOptions();
    private static final String RELEASE_MARK = "releaseMark";
    private static final String JAVA_VERSION_MARK = "javaVersion";
    private static final Pattern RELEASE_VERSION_PATTERN =
            Pattern.compile("(?<" + RELEASE_MARK + ">--release=)(?<" + JAVA_VERSION_MARK + ">[0-9]+)");
    private static final int JAVA_MODULE_VERSION = 9;
    private static final String CLASSPATH_KEY = "--class-path";
    private static final String MODULEPATH_KEY = "--module-path";


    public WsgenTool(OutputStream out, Container container) {
        this.out = (out instanceof PrintStream) ? (PrintStream) out : new PrintStream(out);
        this.container = container;
    }


    public WsgenTool(OutputStream out) {
        this(out, null);
    }

    public boolean run(String[] args) {
        final Listener listener = new Listener();
        for (String arg : args) {
            if (arg.equals("-version")) {
                listener.message(
                        WscompileMessages.WSGEN_VERSION(ToolVersion.VERSION.MAJOR_VERSION));
                return true;
            }
            if (arg.equals("-fullversion")) {
                listener.message(
                        WscompileMessages.WSGEN_FULLVERSION(ToolVersion.VERSION.toString()));
                return true;
            }
        }
        try {
            options.parseArguments(args);
            options.validate();
            if (!buildModel(options.endpoint.getName(), listener)) {
                return false;
            }
        } catch (Options.WeAreDone done) {
            usage(done.getOptions());
        } catch (BadCommandLineException e) {
            if (e.getMessage() != null) {
                System.out.println(e.getMessage());
                System.out.println();
            }
            usage(e.getOptions());
            return false;
        } catch (AbortException e) {
            //error might have been reported
        } finally {
            if (!options.keep) {
                options.removeGeneratedFiles();
            }
        }
        return true;
    }

    private final Container container;

    /**
     * @param endpoint
     * @param listener
     * @return
     * @throws BadCommandLineException
     */
    public boolean buildModel(String endpoint, WsimportListener listener) throws BadCommandLineException {
        final ErrorReceiverFilter errReceiver = new ErrorReceiverFilter(listener);

        if (!options.nosource) {
            List<String> args = new ArrayList<>(6 + (options.nocompile ? 1 : 0)
                    + (options.encoding != null ? 2 : 0));

            args.add("-d");
            args.add(options.destDir.getAbsolutePath());

            int javaVersion = 0;
            if (options.javacOptions != null) {
                for (String option : options.javacOptions) {
                    final Matcher matcher = RELEASE_VERSION_PATTERN.matcher(option);
                    if (matcher.find()) {
                        javaVersion = Integer.parseInt(matcher.group(JAVA_VERSION_MARK));
                        break;
                    }
                }
            }
            if (javaVersion < JAVA_MODULE_VERSION || options.noModules) {
                args.add("-classpath");
                args.add(options.classpath);
            } else {
                args.addAll(generatePathArgs(args));
            }

            args.add("-s");
            args.add(options.sourceDir.getAbsolutePath());
            if (options.nocompile) {
                args.add("-proc:only");
            }
            if (options.encoding != null) {
                args.add("-encoding");
                args.add(options.encoding);
            }
            if (options.javacOptions != null) {
                args.addAll(options.getJavacOptions(args, listener));
            }

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                out.println(WscompileMessages.WSCOMPILE_CANT_GET_COMPILER(property("java.home"), property("java.version"), property("java.vendor")));
                return false;
            }
            DiagnosticListener<JavaFileObject> diagnostics = diagnostic -> {
                boolean fromFile = diagnostic.getSource() != null;
                StringBuilder message = new StringBuilder();
                if (fromFile) {
                    message.append(diagnostic.getSource().getName());
                }
                message.append(diagnostic.getMessage(Locale.getDefault()));
                if (fromFile) {
                    message.append("");
                }
                switch (diagnostic.getKind()) {
                    case ERROR:
                        Locator2Impl l = new Locator2Impl();
                        if (fromFile) {
                            l.setSystemId(diagnostic.getSource().getName());
                        } else {
                            l.setSystemId(null);
                        }
                        l.setLineNumber((int) diagnostic.getLineNumber());
                        l.setColumnNumber((int) diagnostic.getColumnNumber());
                        SAXParseException ex = new SAXParseException(message.toString(), l);
                        listener.error(ex);
                        break;
                    case MANDATORY_WARNING:
                    case WARNING:
                        listener.message(message.toString());
                        break;
                    default:
                        if (options.verbose) {
                            listener.message(message.toString());
                        }
                }
            };

            StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
            JavaCompiler.CompilationTask task = compiler.getTask(
                    null,
                    fileManager,
                    diagnostics,
                    args,
                    Collections.singleton(endpoint.replaceAll("\\$", ".")),
                    null);
            task.setProcessors(Collections.singleton(new WebServiceAp(options, out)));
            boolean result = task.call();

            if (!result) {
                out.println(WscompileMessages.WSCOMPILE_ERROR(WscompileMessages.WSCOMPILE_COMPILATION_FAILED()));
                return false;
            }
        }
        if (options.genWsdl) {
            DatabindingConfig config = new DatabindingConfig();

            List<String> externalMetadataFileNames = options.externalMetadataFiles;
            boolean disableXmlSecurity = options.disableXmlSecurity;
            if (externalMetadataFileNames != null && !externalMetadataFileNames.isEmpty()) {
                config.setMetadataReader(new ExternalMetadataReader(getExternalFiles(externalMetadataFileNames), null, null, true, disableXmlSecurity));
            }

            String tmpPath = options.destDir.getAbsolutePath() + File.pathSeparator + options.classpath;
            ClassLoader classLoader = new URLClassLoader(Options.pathToURLs(tmpPath),
                    this.getClass().getClassLoader());
            Class<?> endpointClass;
            try {
                endpointClass = classLoader.loadClass(endpoint);
            } catch (ClassNotFoundException e) {
                throw new BadCommandLineException(WscompileMessages.WSGEN_CLASS_NOT_FOUND(endpoint));
            }

            BindingID bindingID = options.getBindingID(options.protocol);
            if (!options.protocolSet) {
                bindingID = BindingID.parse(endpointClass);
            }
            WebServiceFeatureList wsfeatures = new WebServiceFeatureList(endpointClass);
//            RuntimeModeler rtModeler = new RuntimeModeler(endpointClass, options.serviceName, bindingID, wsfeatures.toArray());
//            rtModeler.setClassLoader(classLoader);
            if (options.portName != null)
                config.getMappingInfo().setPortName(options.portName);//rtModeler.setPortName(options.portName);
//            AbstractSEIModelImpl rtModel = rtModeler.buildRuntimeModel();

            DatabindingFactory fac = DatabindingFactory.newInstance();
            config.setEndpointClass(endpointClass);
            config.getMappingInfo().setServiceName(options.serviceName);
            config.setFeatures(wsfeatures.toArray());
            config.setClassLoader(classLoader);
            config.getMappingInfo().setBindingID(bindingID);
            com.sun.xml.ws.db.DatabindingImpl rt = (com.sun.xml.ws.db.DatabindingImpl) fac.createRuntime(config);

            final File[] wsdlFileName = new File[1]; // used to capture the generated WSDL file.
            final Map<String, File> schemaFiles = new HashMap<>();

            WSDLGenInfo wsdlGenInfo = new WSDLGenInfo();
            wsdlGenInfo.setSecureXmlProcessingDisabled(disableXmlSecurity);

            wsdlGenInfo.setWsdlResolver(
                    new WSDLResolver() {
                        private File toFile(String suggestedFilename) {
                            return new File(options.nonclassDestDir, suggestedFilename);
                        }

                        private Result toResult(File file) {
                            Result result;
                            try {
                                result = new StreamResult(new FileOutputStream(file));
                                result.setSystemId(file.getPath().replace('\\', '/'));
                            } catch (FileNotFoundException e) {
                                errReceiver.error(e);
                                return null;
                            }
                            return result;
                        }

                        @Override
                        public Result getWSDL(String suggestedFilename) {
                            File f = toFile(suggestedFilename);
                            wsdlFileName[0] = f;
                            return toResult(f);
                        }

                        public Result getSchemaOutput(String namespace, String suggestedFilename) {
                            if (namespace == null)
                                return null;
                            File f = toFile(suggestedFilename);
                            schemaFiles.put(namespace, f);
                            return toResult(f);
                        }

                        @Override
                        public Result getAbstractWSDL(Holder<String> filename) {
                            return toResult(toFile(filename.value));
                        }

                        @Override
                        public Result getSchemaOutput(String namespace, Holder<String> filename) {
                            return getSchemaOutput(namespace, filename.value);
                        }
                        // TODO pass correct impl's class name
                    });

            wsdlGenInfo.setContainer(container);
            wsdlGenInfo.setExtensions(ServiceFinder.find(WSDLGeneratorExtension.class, ServiceLoader.load(WSDLGeneratorExtension.class)).toArray());
            wsdlGenInfo.setInlineSchemas(options.inlineSchemas);
            rt.generateWSDL(wsdlGenInfo);


            if (options.wsgenReport != null)
                generateWsgenReport(endpointClass, (AbstractSEIModelImpl) rt.getModel(), wsdlFileName[0], schemaFiles);
        }
        return true;
    }

    private List<String> generatePathArgs(List<String> args) {
        List<String> result = new ArrayList<>();
        Map<String, String> separatedDependencies = new HashMap<>();
        Map<String, String> separatedWsgenClasspath = splitWsgenClasspath();
        separatedWsgenClasspath.computeIfPresent(CLASSPATH_KEY, separatedDependencies::put);
        separatedWsgenClasspath.computeIfPresent(MODULEPATH_KEY, separatedDependencies::put);
        if (options.modulepath != null) {
            Map<String, String> separatedExtraClasspath = splitExtraClasspath();
            separatedExtraClasspath.computeIfPresent(CLASSPATH_KEY, (key, value) ->
                    separatedDependencies.merge(
                            CLASSPATH_KEY, value,
                            (oldValue, newValue) -> oldValue + File.pathSeparator + value
                    ));
            separatedExtraClasspath.computeIfPresent(MODULEPATH_KEY, (key, value) ->
                    separatedDependencies.merge(
                            MODULEPATH_KEY, value,
                            (oldValue, newValue) -> oldValue + File.pathSeparator + value
                    ));
        }
        if (separatedDependencies.containsKey(CLASSPATH_KEY)) {
            result.add(CLASSPATH_KEY);
            result.add(separatedDependencies.get(CLASSPATH_KEY));
        }
        if (separatedDependencies.containsKey(MODULEPATH_KEY)) {
            result.add(MODULEPATH_KEY);
            result.add(separatedDependencies.get(MODULEPATH_KEY));
        }
        return result;
    }

    private Map<String, String> splitExtraClasspath() {
        Map<String, String> result = new HashMap<>();
        String cp = options.wsgenExtraClasspath;
        String mp = options.modulepath;
        if (cp == null || cp.isEmpty()) {
            if (mp == null || mp.isEmpty()){
                return result;
            }
            result.put(MODULEPATH_KEY, mp);
            return result;
        }
        if (mp == null || mp.isEmpty()){
            result.put(CLASSPATH_KEY, mp);
            return result;
        }
        Set<String> splitCP = new HashSet(Arrays.asList(cp.split(File.pathSeparator)));
        Set<String> splitMP = new HashSet(Arrays.asList(mp.split(File.pathSeparator)));
        Set<String> onlyCP = new HashSet<>(splitCP);
        onlyCP.removeAll(splitMP);
        if (!onlyCP.isEmpty()) {
            result.put(CLASSPATH_KEY, String.join(File.pathSeparator, onlyCP));
        }
        if (!splitMP.isEmpty()) {
            result.put(MODULEPATH_KEY, mp);
        }
        return result;
    }

    private Map<String, String> splitWsgenClasspath() {
        Map<String, String> result = new HashMap<>();
        String mp = options.wsgenClasspath;
        if (mp == null || mp.isEmpty()) {
            return result;
        }
        List<String> classpathJarNames = Arrays.asList("ha-api");
        Set<String> splitMP = new HashSet(Arrays.asList(mp.split(File.pathSeparator)));
        Set<String> onlyMP = new HashSet<>(splitMP);
        Set<String> onlyCP = onlyMP.stream()
                .filter(name -> classpathJarNames.stream().anyMatch(name::contains))
                .collect(Collectors.toSet());
        onlyMP.removeAll(onlyCP);
        if (!onlyMP.isEmpty()) {
            result.put(MODULEPATH_KEY, String.join(File.pathSeparator, onlyMP));
        }
        if (!onlyCP.isEmpty()) {

            result.put(CLASSPATH_KEY, String.join(File.pathSeparator, onlyCP));
        }
        return result;
    }

    private String property(String key) {
        try {
            String property = System.getProperty(key);
            return property != null ? property : "UNKNOWN";
        } catch (SecurityException ignored) {
            return "UNKNOWN";
        }
    }

    private List<File> getExternalFiles(List<String> exts) {
        List<File> files = new ArrayList<>();
        for (String ext : exts) {
            // first try absolute path ...
            File file = new File(ext);
            if (!file.exists()) {
                // then relative path ...
                file = new File(options.sourceDir.getAbsolutePath() + File.separator + ext);
            }
            files.add(file);
        }
        return files;
    }

    /**
     * Generates a small XML file that captures the key activity of wsgen,
     * so that test harness can pick up artifacts.
     */
    private void generateWsgenReport(Class<?> endpointClass, AbstractSEIModelImpl rtModel, File wsdlFile, Map<String, File> schemaFiles) {
        try {
            ReportOutput.Report report = TXW.create(ReportOutput.Report.class,
                    new StreamSerializer(new BufferedOutputStream(new FileOutputStream(options.wsgenReport))));

            report.wsdl(wsdlFile.getAbsolutePath());
            ReportOutput.writeQName(rtModel.getServiceQName(), report.service());
            ReportOutput.writeQName(rtModel.getPortName(), report.port());
            ReportOutput.writeQName(rtModel.getPortTypeName(), report.portType());

            report.implClass(endpointClass.getName());

            for (Map.Entry<String, File> e : schemaFiles.entrySet()) {
                ReportOutput.Schema s = report.schema();
                s.ns(e.getKey());
                s.location(e.getValue().getAbsolutePath());
            }

            report.commit();
        } catch (IOException e) {
            // this is code for the test, so we can be lousy in the error handling
            throw new Error(e);
        }
    }

    /**
     * "Namespace" for code needed to generate the report file.
     */
    static class ReportOutput {
        @XmlElement("report")
        interface Report extends TypedXmlWriter {
            @XmlElement
            void wsdl(String file); // location of WSDL

            @XmlElement
            QualifiedName portType();

            @XmlElement
            QualifiedName service();

            @XmlElement
            QualifiedName port();

            /**
             * Name of the class that has {@link jakarta.jws.WebService}.
             */
            @XmlElement
            void implClass(String name);

            @XmlElement
            Schema schema();
        }

        interface QualifiedName extends TypedXmlWriter {
            @XmlAttribute
            void uri(String ns);

            @XmlAttribute
            void localName(String localName);
        }

        interface Schema extends TypedXmlWriter {
            @XmlAttribute
            void ns(String ns);

            @XmlAttribute
            void location(String filePath);
        }

        private static void writeQName(QName n, QualifiedName w) {
            w.uri(n.getNamespaceURI());
            w.localName(n.getLocalPart());
        }
    }

    protected void usage(Options options) {
        // Just don't see any point in passing WsgenOptions
        // BadCommandLineException also shouldn't have options
        if (options == null)
            options = this.options;
        if (options instanceof WsgenOptions) {
            System.out.println(WscompileMessages.WSGEN_HELP("WSGEN",
                    ((WsgenOptions) options).protocols,
                    ((WsgenOptions) options).nonstdProtocols.keySet()));
            System.out.println(WscompileMessages.WSGEN_USAGE_EXTENSIONS());
            System.out.println(WscompileMessages.WSGEN_USAGE_EXAMPLES());
        }
    }

    class Listener extends WsimportListener {
        ConsoleErrorReporter cer = new ConsoleErrorReporter(out == null ? new PrintStream(new NullStream()) : out);

        @Override
        public void generatedFile(String fileName) {
            message(fileName);
        }

        @Override
        public void message(String msg) {
            out.println(msg);
        }

        @Override
        public void error(SAXParseException exception) {
            cer.error(exception);
        }

        @Override
        public void fatalError(SAXParseException exception) {
            cer.fatalError(exception);
        }

        @Override
        public void warning(SAXParseException exception) {
            cer.warning(exception);
        }

        @Override
        public void info(SAXParseException exception) {
            cer.info(exception);
        }
    }
}
