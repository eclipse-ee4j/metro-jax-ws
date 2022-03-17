/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.modeler.annotation;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author dkohlert
 */
public class TypeMonikerFactory {

    public static TypeMoniker getTypeMoniker(TypeMirror typeMirror) {
        if (typeMirror == null)
            throw new NullPointerException();

        if (typeMirror.getKind().isPrimitive())
            return new PrimitiveTypeMoniker((PrimitiveType) typeMirror);
        else if (typeMirror.getKind().equals(TypeKind.ARRAY))
            return new ArrayTypeMoniker((ArrayType) typeMirror);
        else if (typeMirror.getKind().equals(TypeKind.DECLARED))
            return new DeclaredTypeMoniker((DeclaredType) typeMirror);
        return getTypeMoniker(typeMirror.toString());
    }

    public static TypeMoniker getTypeMoniker(String typeName) {
        return new StringMoniker(typeName);
    }

    static class ArrayTypeMoniker implements TypeMoniker {
        private TypeMoniker arrayType;

        public ArrayTypeMoniker(ArrayType type) {
            arrayType = TypeMonikerFactory.getTypeMoniker(type.getComponentType());
        }

        @Override
        public TypeMirror create(ProcessingEnvironment apEnv) {
            return apEnv.getTypeUtils().getArrayType(arrayType.create(apEnv));
        }
    }
    static class DeclaredTypeMoniker implements TypeMoniker {
        private Name typeDeclName;
        private Collection<TypeMoniker> typeArgs = new ArrayList<>();

        public DeclaredTypeMoniker(DeclaredType type) {
            typeDeclName = ((TypeElement) type.asElement()).getQualifiedName();
            for (TypeMirror arg : type.getTypeArguments())
                typeArgs.add(TypeMonikerFactory.getTypeMoniker(arg));
        }

        @Override
        public TypeMirror create(ProcessingEnvironment apEnv) {
            TypeElement typeDecl = apEnv.getElementUtils().getTypeElement(typeDeclName);
            TypeMirror[] tmpArgs = new TypeMirror[typeArgs.size()];
            int idx = 0;
            for (TypeMoniker moniker : typeArgs)
                tmpArgs[idx++] = moniker.create(apEnv);

            return apEnv.getTypeUtils().getDeclaredType(typeDecl, tmpArgs);
        }
    }
    static class PrimitiveTypeMoniker implements TypeMoniker {
        private TypeKind kind;

        public PrimitiveTypeMoniker(PrimitiveType type) {
            kind = type.getKind();
        }

        @Override
        public TypeMirror create(ProcessingEnvironment apEnv) {
            return apEnv.getTypeUtils().getPrimitiveType(kind);
        }
    }
    static class StringMoniker implements TypeMoniker {
        private String typeName;

        public StringMoniker(String typeName) {
            this.typeName = typeName;
        }

        @Override
        public TypeMirror create(ProcessingEnvironment apEnv) {
            return apEnv.getTypeUtils().getDeclaredType(apEnv.getElementUtils().getTypeElement(typeName));
        }
    }
}
