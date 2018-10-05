/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.model.RuntimeModeler;

public class RuntimeModelerDriver {
    public static void main(String[] args){
        if(args.length != 1){
            System.out.println("Usage: RuntimeModelerDriver <sei_Class>");
            System.exit(-1);
        }

        try {
            Class sei = Class.forName(args[0]);
            RuntimeModeler rm = new RuntimeModeler(sei, null, BindingID.SOAP11_HTTP);
            SEIModel model = rm.buildRuntimeModel();
            System.out.println("Model generated succesful!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
