/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.runtime.config;

import com.sun.istack.logging.Logger;
import com.sun.xml.ws.config.metro.dev.FeatureReader;
import com.sun.xml.ws.config.metro.util.ParserUtil;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.ws.WebServiceException;
import java.util.Iterator;

/**
 *
 * @author Fabian Ritzmann
 */
public class TubelineFeatureReader implements FeatureReader {

    private static final Logger LOGGER = Logger.getLogger(TubelineFeatureReader.class);
    private static final QName NAME_ATTRIBUTE_NAME = new QName("name");

    // TODO implement
    public TubelineFeature parse(XMLEventReader reader) throws WebServiceException {
        try {
            final StartElement element = reader.nextEvent().asStartElement();
            boolean attributeEnabled = true;
            final Iterator iterator = element.getAttributes();
            while (iterator.hasNext()) {
                final Attribute nextAttribute = (Attribute) iterator.next();
                final QName attributeName = nextAttribute.getName();
                if (ENABLED_ATTRIBUTE_NAME.equals(attributeName)) {
                    attributeEnabled = ParserUtil.parseBooleanValue(nextAttribute.getValue());
                } else if (NAME_ATTRIBUTE_NAME.equals(attributeName)) {
                    // TODO use name attribute
                } else {
                    // TODO logging message
                    throw LOGGER.logSevereException(new WebServiceException("Unexpected attribute"));
                }
            }
            return parseFactories(attributeEnabled, element, reader);
        } catch (XMLStreamException e) {
            throw LOGGER.logSevereException(new WebServiceException("Failed to unmarshal XML document", e));
        }
    }

    private TubelineFeature parseFactories(final boolean enabled, final StartElement element, final XMLEventReader reader)
            throws WebServiceException {
        int elementRead = 0;
        loop:
        while (reader.hasNext()) {
            try {
                final XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.COMMENT:
                        break; // skipping the comments and start document events
                    case XMLStreamConstants.CHARACTERS:
                        if (event.asCharacters().isWhiteSpace()) {
                            break;
                        }
                        else {
                            // TODO: logging message
                            throw LOGGER.logSevereException(new WebServiceException("No character data allowed, was " + event.asCharacters()));
                        }
                    case XMLStreamConstants.START_ELEMENT:
                        // TODO implement
                        elementRead++;
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        elementRead--;
                        if (elementRead < 0) {
                            final EndElement endElement = event.asEndElement();
                            if (!element.getName().equals(endElement.getName())) {
                                // TODO logging message
                                throw LOGGER.logSevereException(new WebServiceException("End element does not match " + endElement));
                            }
                            break loop;
                        }
                        else {
                            break;
                        }
                    default:
                        // TODO logging message
                        throw LOGGER.logSevereException(new WebServiceException("Unexpected event, was " + event));
                }
            } catch (XMLStreamException e) {
                // TODO logging message
                throw LOGGER.logSevereException(new WebServiceException("Failed to unmarshal XML document", e));
            }
        }

        // TODO implement
        return new TubelineFeature(enabled);
    }

}
