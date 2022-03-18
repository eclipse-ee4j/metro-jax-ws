/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport.http.servlet;

import com.sun.xml.ws.transport.http.HttpAdapter;
import com.sun.xml.ws.transport.http.WSHTTPConnection;

import java.io.IOException;
import java.util.logging.Logger;
import jakarta.servlet.AsyncEvent;
import jakarta.servlet.AsyncListener;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author Rama Pulavarthi
 */
public class WSAsyncListener {
    final private WSHTTPConnection con;
    final private HttpAdapter.CompletionCallback callback;

    WSAsyncListener(WSHTTPConnection con, HttpAdapter.CompletionCallback callback) {
        this.con = con;
        this.callback = callback;
    }

    public void addListenerTo(AsyncContext context, final ServletAdapter.AsyncCompletionCheck completionCheck) {
        context.addListener(new AsyncListener() {

            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                LOGGER.finer("Asynchronous Servlet Invocation completed for "+((HttpServletRequest)event.getAsyncContext().getRequest()).getRequestURL());
                callback.onCompletion();

            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                completionCheck.markComplete();                                
                LOGGER.fine("Time out on Request:" + ((HttpServletRequest)event.getAsyncContext().getRequest()).getRequestURL());
                con.close();
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                LOGGER.fine("Error processing Request:" + ((HttpServletRequest)event.getAsyncContext().getRequest()).getRequestURL());
                con.close();
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                LOGGER.finer("Asynchronous Servlet Invocation started for "+((HttpServletRequest)event.getAsyncContext().getRequest()).getRequestURL());
            }
        });
    }
    private static final Logger LOGGER = Logger.getLogger(WSAsyncListener.class.getName());
}
