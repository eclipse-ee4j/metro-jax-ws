/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.pipe;

import java.util.Map;

/**
 * Clones the whole pipeline.
 *
 * <p>
 * Since {@link Pipe}s may form an arbitrary directed graph, someone needs
 * to keep track of isomorphism for a clone to happen correctly. This class
 * serves that role.
 *
 * @deprecated
 *      Use {@link TubeCloner}.
 * @author Kohsuke Kawaguchi
 */
public abstract class PipeCloner extends TubeCloner {
    /**
     * {@link Pipe} version of {@link #clone(Tube)}
     */
    public static Pipe clone(Pipe p) {
        return new PipeClonerImpl().copy(p);
    }

    // no need to be constructed publicly. always use the static clone method.
    /*package*/ PipeCloner(Map<Object,Object> master2copy) {
    	super(master2copy);
    }

    /**
     * {@link Pipe} version of {@link #copy(Tube)}
     */
    @SuppressWarnings("unchecked")
	public abstract <T extends Pipe> T copy(T p);


    /**
     * The {@link Pipe} version of {@link #add(Tube, Tube)}.
     */
    public abstract void add(Pipe original, Pipe copy);
}
