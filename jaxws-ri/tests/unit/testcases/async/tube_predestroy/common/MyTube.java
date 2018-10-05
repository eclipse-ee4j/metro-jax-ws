/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package async.tube_predestroy.common;

import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.Tube;

/**
 * @author Martin Grebac
 */
public class MyTube extends AbstractFilterTubeImpl {

    public volatile static int instancesCreated = 0;
    
    public MyTube(ClientTubeAssemblerContext context, Tube next) {
        super(next);
        instancesCreated += 1;
    }

    protected MyTube(MyTube that, TubeCloner cloner) {
        super(that,cloner);
        instancesCreated += 1;
    }

    @Override
    public AbstractTubeImpl copy(TubeCloner cloner) {
        // the tube must not be cloned more times in this case
        throw new Incorrect();
    }

    
}
