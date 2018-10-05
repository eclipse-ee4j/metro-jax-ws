/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package epr.fromjava_epr_extensions.common;

import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.Tube;

/**
 * @author Rama.Pulavarthi@sun.com
 */
public class MyTube extends AbstractFilterTubeImpl {

    public MyTube(ServerTubeAssemblerContext context, Tube next) {
        super(next);        
    }

    protected MyTube(MyTube that, TubeCloner cloner) {
        super(that,cloner);
    }
    public AbstractTubeImpl copy(TubeCloner cloner) {
        return new MyTube(this, cloner);
    }
}
