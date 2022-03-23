/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl.parser;

import jakarta.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A list of {@link InaccessibleWSDLException} wrapped in one exception.
 *
 * <p>
 * This exception is used to report all the errors during WSDL parsing from {@link RuntimeWSDLParser#parse(java.net.URL, javax.xml.transform.Source, org.xml.sax.EntityResolver, boolean, com.sun.xml.ws.api.server.Container, com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension[])}
 *
 * @author Vivek Pandey
 */
public class InaccessibleWSDLException extends WebServiceException {

    private final List<Throwable> errors;

    private static final long serialVersionUID = 1L;

    public InaccessibleWSDLException(List<Throwable> errors) {
        super(errors.size()+" counts of InaccessibleWSDLException.\n");
        assert !errors.isEmpty() : "there must be at least one error";
        this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append('\n');

        for( Throwable error : errors )
            sb.append(error.toString()).append('\n');

        return sb.toString();
    }

    /**
     * Returns a read-only list of {@link InaccessibleWSDLException}s
     * wrapped in this exception.
     *
     * @return
     *      a non-null list.
     */
    public List<Throwable> getErrors() {
        return errors;
    }

    public static class Builder implements ErrorHandler {
        private final List<Throwable> list = new ArrayList<>();
        public Builder() {}
        @Override
        public void error(Throwable e) {
            list.add(e);
        }
        /**
         * If an error was reported, throw the exception.
         * Otherwise exit normally.
         */
        public void check() throws InaccessibleWSDLException {
            if(list.isEmpty())
                return;
            throw new InaccessibleWSDLException(list);
        }
    }

}
