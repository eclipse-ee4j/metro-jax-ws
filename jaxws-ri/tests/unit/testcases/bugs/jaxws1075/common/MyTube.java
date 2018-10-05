/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1075.common;

import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.NextAction;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.transport.http.WSHTTPConnection;


/**
 * @author qiang.wang@oracle.com
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

    //set HttpConnection status as 403, for test requirement
    public NextAction processResponse(Packet response) {
	((WSHTTPConnection)response.webServiceContextDelegate).setStatus(403);
	return doReturnWith(response);
    }
    
}
