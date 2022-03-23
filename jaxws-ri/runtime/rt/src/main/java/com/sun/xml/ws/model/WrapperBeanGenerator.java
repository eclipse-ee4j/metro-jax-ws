/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.model;

import org.glassfish.jaxb.core.v2.model.annotation.AnnotationReader;
import org.glassfish.jaxb.runtime.v2.model.annotation.RuntimeInlineAnnotationReader;
import org.glassfish.jaxb.core.v2.model.nav.Navigator;
import com.sun.xml.ws.model.AbstractWrapperBeanGenerator.BeanMemberFactory;
import com.sun.xml.ws.org.objectweb.asm.AnnotationVisitor;
import com.sun.xml.ws.org.objectweb.asm.ClassWriter;
import com.sun.xml.ws.org.objectweb.asm.FieldVisitor;
import com.sun.xml.ws.org.objectweb.asm.MethodVisitor;
import com.sun.xml.ws.org.objectweb.asm.Opcodes;
import com.sun.xml.ws.org.objectweb.asm.Type;

import java.lang.annotation.Annotation;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.xml.bind.annotation.XmlAttachmentRef;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlList;
import jakarta.xml.bind.annotation.XmlMimeType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Runtime Wrapper and exception bean generator implementation.
 * It uses ASM to generate request, response and exception beans.
 *
 * @author Jitendra Kotamraju
 */
public final class WrapperBeanGenerator {

    private static final Logger LOGGER = Logger.getLogger(WrapperBeanGenerator.class.getName());

    private static final FieldFactory FIELD_FACTORY = new FieldFactory();

    private static final AbstractWrapperBeanGenerator RUNTIME_GENERATOR =
            new RuntimeWrapperBeanGenerator(new RuntimeInlineAnnotationReader(),
                    Utils.REFLECTION_NAVIGATOR, FIELD_FACTORY);

    private static final class RuntimeWrapperBeanGenerator extends AbstractWrapperBeanGenerator<java.lang.reflect.Type, Class, java.lang.reflect.Method, Field> {

        protected RuntimeWrapperBeanGenerator(AnnotationReader<java.lang.reflect.Type, Class, ?, Method> annReader, Navigator<java.lang.reflect.Type, Class, ?, Method> nav, BeanMemberFactory<java.lang.reflect.Type, Field> beanMemberFactory) {
            super(annReader, nav, beanMemberFactory);
        }

        @Override
        protected java.lang.reflect.Type getSafeType(java.lang.reflect.Type type) {
            return type;
        }

        @Override
        protected java.lang.reflect.Type getHolderValueType(java.lang.reflect.Type paramType) {
            if (paramType instanceof ParameterizedType) {
                ParameterizedType p = (ParameterizedType)paramType;
                if (p.getRawType().equals(Holder.class)) {
                    return p.getActualTypeArguments()[0];
                }
            }
            return null;
        }

        @Override
        protected boolean isVoidType(java.lang.reflect.Type type) {
            return type == Void.TYPE;
        }

    }

    private static final class FieldFactory implements BeanMemberFactory<java.lang.reflect.Type, Field> {
        @Override
        public Field createWrapperBeanMember(java.lang.reflect.Type paramType,
                String paramName, List<Annotation> jaxb) {
            return new Field(paramName, paramType, getASMType(paramType), jaxb);
        }
    }

    private WrapperBeanGenerator() {}

    // Creates class's bytes
    private static byte[] createBeanImage(String className,
                               String rootName, String rootNS,
                               String typeName, String typeNS,
                               Collection<Field> fields) throws Exception {

        ClassWriter cw = new ClassWriter(0);
        //org.objectweb.asm.util.TraceClassVisitor cw = new org.objectweb.asm.util.TraceClassVisitor(actual, new java.io.PrintWriter(System.out));

        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, replaceDotWithSlash(className), null, "java/lang/Object", null);

        AnnotationVisitor root = cw.visitAnnotation("Ljakarta/xml/bind/annotation/XmlRootElement;", true);
        root.visit("name", rootName);
        root.visit("namespace", rootNS);
        root.visitEnd();

        AnnotationVisitor type = cw.visitAnnotation("Ljakarta/xml/bind/annotation/XmlType;", true);
        type.visit("name", typeName);
        type.visit("namespace", typeNS);
        if (fields.size() > 1) {
            AnnotationVisitor propVisitor = type.visitArray("propOrder");
            for(Field field : fields) {
                propVisitor.visit("propOrder", field.fieldName);
            }
            propVisitor.visitEnd();
        }
        type.visitEnd();

        for(Field field : fields) {
            FieldVisitor fv = cw.visitField(Opcodes.ACC_PUBLIC, field.fieldName, field.asmType.getDescriptor(), field.getSignature(), null);

            for(Annotation ann : field.jaxbAnnotations) {
                if (ann instanceof XmlMimeType) {
                    AnnotationVisitor mime = fv.visitAnnotation("Ljakarta/xml/bind/annotation/XmlMimeType;", true);
                    mime.visit("value", ((XmlMimeType)ann).value());
                    mime.visitEnd();
                } else if (ann instanceof XmlJavaTypeAdapter) {
                    AnnotationVisitor ada = fv.visitAnnotation("Ljakarta/xml/bind/annotation/adapters/XmlJavaTypeAdapter;", true);
                    ada.visit("value", getASMType(((XmlJavaTypeAdapter)ann).value()));
                    // XmlJavaTypeAdapter.type() is for package only. No need to copy.
                    // ada.visit("type", ((XmlJavaTypeAdapter)ann).type());
                    ada.visitEnd();
                } else if (ann instanceof XmlAttachmentRef) {
                    AnnotationVisitor att = fv.visitAnnotation("Ljakarta/xml/bind/annotation/XmlAttachmentRef;", true);
                    att.visitEnd();
                } else if (ann instanceof XmlList) {
                    AnnotationVisitor list = fv.visitAnnotation("Ljakarta/xml/bind/annotation/XmlList;", true);
                    list.visitEnd();
                } else if (ann instanceof XmlElement) {
                    AnnotationVisitor elem = fv.visitAnnotation("Ljakarta/xml/bind/annotation/XmlElement;", true);
                    XmlElement xmlElem = (XmlElement)ann;
                    elem.visit("name", xmlElem.name());
                    elem.visit("namespace", xmlElem.namespace());
                    if (xmlElem.nillable()) {
                        elem.visit("nillable", true);
                    }
                    if (xmlElem.required()) {
                        elem.visit("required", true);
                    }
                    elem.visitEnd();
                } else {
                    throw new WebServiceException("Unknown JAXB annotation " + ann);
                }
            }
            
            fv.visitEnd();
        }

        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        cw.visitEnd();

        if (LOGGER.isLoggable(Level.FINE)) {
            // Class's @XmlRootElement
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            sb.append("@XmlRootElement(name=").append(rootName)
                    .append(", namespace=").append(rootNS).append(")");

            // Class's @XmlType
            sb.append("\n");
            sb.append("@XmlType(name=").append(typeName)
                    .append(", namespace=").append(typeNS);
            if (fields.size() > 1) {
                sb.append(", propOrder={");
                for(Field field : fields) {
                    sb.append(" ");
                    sb.append(field.fieldName);
                }
                sb.append(" }");
            }
            sb.append(")");

            // class declaration
            sb.append("\n");
            sb.append("public class ").append(className).append(" {");

            // fields declaration
            for(Field field : fields) {
                sb.append("\n");

                // Field's other JAXB annotations
                for(Annotation ann : field.jaxbAnnotations) {
                    sb.append("\n    ");

                    if (ann instanceof XmlMimeType) {
                        sb.append("@XmlMimeType(value=").append(((XmlMimeType)ann).value()).append(")");
                    } else if (ann instanceof XmlJavaTypeAdapter) {
                        sb.append("@XmlJavaTypeAdapter(value=").append(getASMType(((XmlJavaTypeAdapter)ann).value())).append(")");
                    } else if (ann instanceof XmlAttachmentRef) {
                        sb.append("@XmlAttachmentRef");
                    } else if (ann instanceof XmlList) {
                        sb.append("@XmlList");
                    } else if (ann instanceof XmlElement) {
                        XmlElement xmlElem = (XmlElement)ann;
                        sb.append("\n    ");
                        sb.append("@XmlElement(name=").append(xmlElem.name())
                                .append(", namespace=").append(xmlElem.namespace());
                        if (xmlElem.nillable()) {
                            sb.append(", nillable=true");
                        }
                        if (xmlElem.required()) {
                            sb.append(", required=true");
                        }
                        sb.append(")");
                    } else {
                        throw new WebServiceException("Unknown JAXB annotation " + ann);
                    }
                }

                // Field declaration
                sb.append("\n    ");
                sb.append("public ");
                if (field.getSignature() == null) {
                    sb.append(field.asmType.getDescriptor());
                } else {
                    sb.append(field.getSignature());
                }
                sb.append(" ");
                sb.append(field.fieldName);
            }

            sb.append("\n\n}");
            LOGGER.fine(sb.toString());
        }

        return cw.toByteArray();
    }

    private static String replaceDotWithSlash(String name) {
        return name.replace('.', '/');
    }

    static Class createRequestWrapperBean(String className, Method method, QName reqElemName, ClassLoader cl) {

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Request Wrapper Class : {0}", className);
        }

        List<Field> requestMembers = RUNTIME_GENERATOR.collectRequestBeanMembers(
                method);

        byte[] image;
        try {
            image = createBeanImage(className, reqElemName.getLocalPart(), reqElemName.getNamespaceURI(),
                reqElemName.getLocalPart(), reqElemName.getNamespaceURI(),
                requestMembers);
        } catch(Exception e) {
            throw new WebServiceException(e);
        }
//        write(image, className);
        return Injector.inject(cl, className, image);
    }

    static Class createResponseWrapperBean(String className, Method method, QName resElemName, ClassLoader cl) {

        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "Response Wrapper Class : {0}", className);
        }

        List<Field> responseMembers = RUNTIME_GENERATOR.collectResponseBeanMembers(method);

        byte[] image;
        try {
            image = createBeanImage(className, resElemName.getLocalPart(), resElemName.getNamespaceURI(),
                resElemName.getLocalPart(), resElemName.getNamespaceURI(),
                responseMembers);
        } catch(Exception e) {
            throw new WebServiceException(e);
        }
//      write(image, className);

        return Injector.inject(cl, className, image);
    }


    private static Type getASMType(java.lang.reflect.Type t) {
        assert t!=null;

        if (t instanceof Class) {
            return Type.getType((Class)t);
        }

        if (t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)t;
            if (pt.getRawType() instanceof Class) {
                return Type.getType((Class)pt.getRawType());
            }
        }
        if (t instanceof GenericArrayType) {
            return Type.getType(FieldSignature.vms(t));
        }

        if (t instanceof WildcardType) {
            return Type.getType(FieldSignature.vms(t));
        }

        if (t instanceof TypeVariable) {
            TypeVariable tv = (TypeVariable)t;
            if (tv.getBounds()[0] instanceof Class) {
                return Type.getType((Class)tv.getBounds()[0]);
            }
        }

        throw new IllegalArgumentException("Not creating ASM Type for type = "+t);
    }


    static Class createExceptionBean(String className, Class exception, String typeNS, String elemName, String elemNS, ClassLoader cl) {
        return createExceptionBean(className, exception, typeNS, elemName, elemNS, cl, true);
    }
    
    static Class createExceptionBean(String className, Class exception, String typeNS, String elemName, String elemNS, ClassLoader cl, boolean decapitalizeExceptionBeanProperties) {

        Collection<Field> fields = RUNTIME_GENERATOR.collectExceptionBeanMembers(exception, decapitalizeExceptionBeanProperties);

        byte[] image;
        try {
            image = createBeanImage(className, elemName, elemNS,
                exception.getSimpleName(), typeNS,
                fields);
        } catch(Exception e) {
            throw new WebServiceException(e);
        }

        return Injector.inject(cl, className, image);
    }

    /** 
     * Note: this class has a natural ordering that is inconsistent with equals.
     */
    private static class Field implements Comparable<Field> {
        private final java.lang.reflect.Type reflectType;
        private final Type asmType;
        private final String fieldName;
        private final List<Annotation> jaxbAnnotations;

        Field(String paramName, java.lang.reflect.Type paramType, Type asmType,
              List<Annotation> jaxbAnnotations) {
            this.reflectType = paramType;
            this.asmType = asmType;
            this.fieldName = paramName;
            this.jaxbAnnotations = jaxbAnnotations;
        }

        String getSignature() {
            if (reflectType instanceof Class) {
                return null;
            }
            if (reflectType instanceof TypeVariable) {
                return null;
            }
            return FieldSignature.vms(reflectType);
        }

        @Override
        public int compareTo(Field o) {
            return fieldName.compareTo(o.fieldName);
        }
    }

    static void write(byte[] b, String className) {
        className = className.substring(className.lastIndexOf(".")+1);
        try (FileOutputStream fo = new FileOutputStream(className + ".class")) {
            fo.write(b);
            fo.flush();
        } catch (IOException e) {
            LOGGER.log(Level.INFO, "Error Writing class", e);
        }
    }

}
