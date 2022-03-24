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
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleTypeVisitor9;
import javax.lang.model.util.Types;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author dkohlert
 */
public class MakeSafeTypeVisitor extends SimpleTypeVisitor9<TypeMirror, Types> {

    TypeElement collectionType;
    TypeElement mapType;

    /**
     * Creates a new instance of MakeSafeTypeVisitor
     */
    public MakeSafeTypeVisitor(ProcessingEnvironment processingEnvironment) {
        collectionType = processingEnvironment.getElementUtils().getTypeElement(Collection.class.getName());
        mapType = processingEnvironment.getElementUtils().getTypeElement(Map.class.getName());
    }

    @Override
    public TypeMirror visitDeclared(DeclaredType t, Types types) {
        if (TypeModeler.isSubElement((TypeElement) t.asElement(), collectionType)
                || TypeModeler.isSubElement((TypeElement) t.asElement(), mapType)) {
            Collection<? extends TypeMirror> args = t.getTypeArguments();
            TypeMirror[] safeArgs = new TypeMirror[args.size()];
            int i = 0;
            for (TypeMirror arg : args) {
                safeArgs[i++] = visit(arg, types);
            }
            return types.getDeclaredType((TypeElement) t.asElement(), safeArgs);
        }
        return types.erasure(t);
    }

    @Override
    public TypeMirror visitNoType(NoType type, Types types) {
        return type;
    }
    @Override
    protected TypeMirror defaultAction(TypeMirror e, Types types) {
        return types.erasure(e);
    }
}
