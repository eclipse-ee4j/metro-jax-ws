/*
 * Copyright (c) 2010, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.modeler.annotation;

import com.sun.istack.logging.Logger;
import com.sun.tools.ws.processor.generator.GeneratorUtil;
import com.sun.tools.ws.processor.modeler.ModelerException;
import com.sun.tools.ws.resources.WebserviceapMessages;
import com.sun.tools.ws.wscompile.AbortException;
import com.sun.tools.ws.wscompile.WsgenOptions;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import jakarta.jws.WebService;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceProvider;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

/**
 * WebServiceAp is a AnnotationProcessor for processing jakarta.jws.* and
 * jakarta.xml.ws.* annotations. This class is used either by the WsGen (CompileTool) tool or
 * indirectly when invoked by javac.
 *
 * @author WS Development Team
 */
@SupportedAnnotationTypes({
        "jakarta.jws.HandlerChain",
        "jakarta.jws.Oneway",
        "jakarta.jws.WebMethod",
        "jakarta.jws.WebParam",
        "jakarta.jws.WebResult",
        "jakarta.jws.WebService",
        "jakarta.jws.soap.InitParam",
        "jakarta.jws.soap.SOAPBinding",
        "jakarta.jws.soap.SOAPMessageHandler",
        "jakarta.jws.soap.SOAPMessageHandlers",
        "jakarta.xml.ws.BindingType",
        "jakarta.xml.ws.RequestWrapper",
        "jakarta.xml.ws.ResponseWrapper",
        "jakarta.xml.ws.ServiceMode",
        "jakarta.xml.ws.WebEndpoint",
        "jakarta.xml.ws.WebFault",
        "jakarta.xml.ws.WebServiceClient",
        "jakarta.xml.ws.WebServiceProvider",
        "jakarta.xml.ws.WebServiceRef"
})
@SupportedOptions({WebServiceAp.DO_NOT_OVERWRITE, WebServiceAp.IGNORE_NO_WEB_SERVICE_FOUND_WARNING, WebServiceAp.VERBOSE})
public class WebServiceAp extends AbstractProcessor implements ModelBuilder {

    private static final Logger LOGGER = Logger.getLogger(WebServiceAp.class);
    
    public static final String DO_NOT_OVERWRITE = "doNotOverWrite";
    public static final String IGNORE_NO_WEB_SERVICE_FOUND_WARNING = "ignoreNoWebServiceFoundWarning";
    public static final String VERBOSE = "verbose";

    private WsgenOptions options;
    protected AnnotationProcessorContext context;
    private File sourceDir;
    private boolean doNotOverWrite;
    private boolean ignoreNoWebServiceFoundWarning = false;
    private TypeMirror exceptionElement;
    private TypeMirror runtimeExceptionElement;
    private TypeElement defHolderElement;
    private boolean isCommandLineInvocation;
    private PrintStream out;
    private Collection<TypeElement> processedTypeElements = new HashSet<TypeElement>();

    public WebServiceAp() {
        this.context = new AnnotationProcessorContext();
    }

    public WebServiceAp(WsgenOptions options, PrintStream out) {
        this.options = options;
        this.sourceDir = (options != null) ? options.sourceDir : null;
        this.doNotOverWrite = (options != null) && options.doNotOverWrite;
        this.context = new AnnotationProcessorContext();
        this.out = out;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        exceptionElement = processingEnv.getElementUtils().getTypeElement(Exception.class.getName()).asType();
        runtimeExceptionElement = processingEnv.getElementUtils().getTypeElement(RuntimeException.class.getName()).asType();
        defHolderElement = processingEnv.getElementUtils().getTypeElement(Holder.class.getName());
        if (options == null) {
            options = new WsgenOptions();

            out = new PrintStream(new ByteArrayOutputStream());
            doNotOverWrite = getOption(DO_NOT_OVERWRITE);
            ignoreNoWebServiceFoundWarning = getOption(IGNORE_NO_WEB_SERVICE_FOUND_WARNING);
            options.verbose = getOption(VERBOSE);
            isCommandLineInvocation = true;
        }
        options.filer = processingEnv.getFiler();
    }

    private boolean getOption(String key) {
        String value = processingEnv.getOptions().get(key);
        if (value != null) {
            return Boolean.valueOf(value);
        }
        return false;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (context.getRound() != 1) {
            return true;
        }
        context.incrementRound();
        WebService webService;
        WebServiceProvider webServiceProvider;
        WebServiceVisitor webServiceVisitor = new WebServiceWrapperGenerator(this, context);
        boolean processedEndpoint = false;
        Collection<TypeElement> classes = new ArrayList<TypeElement>();
        filterClasses(classes, roundEnv.getRootElements());
        for (TypeElement element : classes) {
            webServiceProvider = element.getAnnotation(WebServiceProvider.class);
            webService = element.getAnnotation(WebService.class);
            if (webServiceProvider != null) {
                if (webService != null) {
                    processError(WebserviceapMessages.WEBSERVICEAP_WEBSERVICE_AND_WEBSERVICEPROVIDER(element.getQualifiedName()));
                }
                processedEndpoint = true;
            }

            if (webService == null) {
                continue;
            }

            element.accept(webServiceVisitor, null);
            processedEndpoint = true;
        }
        if (!processedEndpoint) {
            if (isCommandLineInvocation) {
                if (!ignoreNoWebServiceFoundWarning) {
                    processWarning(WebserviceapMessages.WEBSERVICEAP_NO_WEBSERVICE_ENDPOINT_FOUND());
                }
            } else {
                processError(WebserviceapMessages.WEBSERVICEAP_NO_WEBSERVICE_ENDPOINT_FOUND());
            }
        }
        return true;
    }

    private void filterClasses(Collection<TypeElement> classes, Collection<? extends Element> elements) {
        for (Element element : elements) {
            if (element.getKind().equals(ElementKind.CLASS)) {
                classes.add((TypeElement) element);
                filterClasses(classes, ElementFilter.typesIn(element.getEnclosedElements()));
            }
        }
    }

    @Override
    public void processWarning(String message) {
        if (isCommandLineInvocation) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, message);
        } else {
            report(message);
        }
    }

    protected void report(String msg) {
        if (out == null) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, "No output set for web service annotation processor reporting.");
            }
            return;
        }
        out.println(msg);
        out.flush();
    }

    @Override
    public void processError(String message) {
        if (isCommandLineInvocation) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
            throw new AbortException();
        } else {
            throw new ModelerException(message);
        }
    }

    @Override
    public void processError(String message, Element element) {
        if (isCommandLineInvocation) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
        } else {
            throw new ModelerException(message);
        }
    }

    @Override
    public boolean canOverWriteClass(String className) {
        return !((doNotOverWrite && GeneratorUtil.classExists(options, className)));
    }

    @Override
    public File getSourceDir() {
        return sourceDir;
    }

    @Override
    public boolean isRemote(TypeElement typeElement) {
        return TypeModeler.isRemote(typeElement);
    }

    @Override
    public boolean isServiceException(TypeMirror typeMirror) {
        return processingEnv.getTypeUtils().isSubtype(typeMirror, exceptionElement)
                && !processingEnv.getTypeUtils().isSubtype(typeMirror, runtimeExceptionElement)
                && !TypeModeler.isRemoteException(processingEnv, typeMirror);
    }

    @Override
    public TypeMirror getHolderValueType(TypeMirror type) {
        return TypeModeler.getHolderValueType(type, defHolderElement, processingEnv);
    }

    @Override
    public boolean checkAndSetProcessed(TypeElement typeElement) {
        if (!processedTypeElements.contains(typeElement)) {
            processedTypeElements.add(typeElement);
            return false;
        }
        return true;
    }

    @Override
    public void log(String message) {
        if (options != null && options.verbose) {
            message = new StringBuilder().append('[').append(message).append(']').toString(); // "[%s]"
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
        }
    }

    @Override
    public WsgenOptions getOptions() {
        return options;
    }

    @Override
    public ProcessingEnvironment getProcessingEnvironment() {
        return processingEnv;
    }

    @Override
    public String getOperationName(Name messageName) {
        return messageName != null ? messageName.toString() : null;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
}



