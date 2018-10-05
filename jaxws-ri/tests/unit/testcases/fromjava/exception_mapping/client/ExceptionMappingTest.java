/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.exception_mapping.client;

import junit.framework.TestCase;

import javax.xml.ws.Holder;

import java.util.List;
import java.util.ArrayList;

/**
 * This tests the mapping of Exceptions from Java to WSDL covering the rules defined in JAX-WS for request/response
 * and oneway operations.  
 *
 * @author Rama Pulavarthi
 */
public class ExceptionMappingTest extends TestCase {

    public void test1() {
        EchoImpl port =new EchoImplService().getEchoImplPort();
        Bar bar = new Bar();
        bar.setName("JavaOne");
        bar.setAge(13);
        try {
            port.echoBar(bar);
        } catch (EchoException_Exception e) {
            //do nothing this is the only checked exception that this method should throw
        }
    }

    public void test2() {
        EchoImpl port =new EchoImplService().getEchoImplPort();
        port.echoString("Duke");
    }

    public void test3() {
        EchoImpl port =new EchoImplService().getEchoImplPort();
        Holder<String> holder = new Holder<String>();
        holder.value = "Duke";
        port.echoStringHolder(holder);
    }

    public void test4() {
        EchoImpl port =new EchoImplService().getEchoImplPort();
        Bar bar = new Bar();
                bar.setName("JavaOne");
                bar.setAge(13);

        List<Bar> list = new ArrayList<Bar>();
        list.add(bar);
        port.echoBarList(list);
    }

    public void test5() {
        EchoImpl port =new EchoImplService().getEchoImplPort();
        port.notify("Duke");        
    }

}
