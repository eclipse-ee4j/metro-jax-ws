/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.processor.modeler.wsdl;

import com.sun.tools.ws.resources.WscompileMessages;
import com.sun.tools.ws.wscompile.ErrorReceiver;
import org.xml.sax.SAXParseException;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.UnknownHostException;

public class ConsoleErrorReporter extends ErrorReceiver {

    private boolean hasError;
    private PrintStream output;
    private boolean debug;

    public ConsoleErrorReporter(PrintStream stream) {
        this.output = stream;
    }

    public ConsoleErrorReporter(OutputStream outputStream) {
        this.output = new PrintStream(outputStream);
    }

    public boolean hasError() {
        return hasError;
    }

    @Override
    public void error(SAXParseException e) {
        if(debug)
            e.printStackTrace();
        hasError = true;
        if((e.getSystemId() == null && e.getPublicId() == null) && (e.getCause() instanceof UnknownHostException)) {
            print(WscompileMessages.WSIMPORT_ERROR_MESSAGE(e.toString()), e);
        } else {
            print(WscompileMessages.WSIMPORT_ERROR_MESSAGE(e.getMessage()), e);
        }
    }



    @Override
    public void fatalError(SAXParseException e) {
        if(debug)
            e.printStackTrace();
        hasError = true;
        print(WscompileMessages.WSIMPORT_ERROR_MESSAGE(e.getMessage()), e);
    }

    @Override
    public void warning(SAXParseException e) {
        print(WscompileMessages.WSIMPORT_WARNING_MESSAGE(e.getMessage()), e);
    }
    
    /**
     * Used to report possibly verbose information that
     * can be safely ignored.
     */
    @Override
    public void info(SAXParseException e) {
        print(WscompileMessages.WSIMPORT_INFO_MESSAGE(e.getMessage()), e);
    }

    @Override
    public void debug(SAXParseException e){
        print(WscompileMessages.WSIMPORT_DEBUG_MESSAGE(e.getMessage()), e);
    }


    private void print(String message, SAXParseException e) {
        output.println(message);
        output.println(getLocationString(e));
        output.println();
    }

    public void enableDebugging(){
        this.debug = true;
    }

}
