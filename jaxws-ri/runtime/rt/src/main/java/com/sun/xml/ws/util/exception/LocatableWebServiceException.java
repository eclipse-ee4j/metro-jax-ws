/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util.exception;

import com.sun.istack.NotNull;
import com.sun.xml.ws.resources.UtilMessages;
import org.xml.sax.Locator;
import org.xml.sax.helpers.LocatorImpl;

import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamReader;
import jakarta.xml.ws.WebServiceException;
import java.util.Arrays;
import java.util.List;

/**
 * {@link WebServiceException} with source location informaiton.
 *
 * <p>
 * This exception should be used wherever the location information is available,
 * so that the location information is carried forward to users (to assist
 * error diagnostics.)
 *
 * @author Kohsuke Kawaguchi
 */
public class LocatableWebServiceException extends WebServiceException {

    private static final long serialVersionUID = -1576525669206836528L;

    /**
     * Locations related to error.
     */
    private final Locator[] location;

    public LocatableWebServiceException(String message, Locator... location) {
        this(message,null,location);
    }

    public LocatableWebServiceException(String message, Throwable cause, Locator... location) {
        super(appendLocationInfo(message,location), cause);
        this.location = location;
    }

    public LocatableWebServiceException(Throwable cause, Locator... location) {
        this(cause.toString(),cause,location);
    }

    public LocatableWebServiceException(String message, XMLStreamReader locationSource) {
        this(message,toLocation(locationSource));
    }

    public LocatableWebServiceException(String message, Throwable cause, XMLStreamReader locationSource) {
        this(message,cause,toLocation(locationSource));
    }

    public LocatableWebServiceException(Throwable cause, XMLStreamReader locationSource) {
        this(cause,toLocation(locationSource));
    }

    /**
     * Locations related to this exception.
     *
     * @return
     *      Can be empty but never null.
     */
    public @NotNull List<Locator> getLocation() {
        return Arrays.asList(location);
    }

    private static String appendLocationInfo(String message, Locator[] location) {
        StringBuilder buf = new StringBuilder(message);
        for( Locator loc : location )
            buf.append('\n').append(UtilMessages.UTIL_LOCATION( loc.getLineNumber(), loc.getSystemId() ));
        return buf.toString();
    }

    private static Locator toLocation(XMLStreamReader xsr) {
        LocatorImpl loc = new LocatorImpl();
        Location in = xsr.getLocation();
        loc.setSystemId(in.getSystemId());
        loc.setPublicId(in.getPublicId());
        loc.setLineNumber(in.getLineNumber());
        loc.setColumnNumber(in.getColumnNumber());
        return loc;
    }
}
