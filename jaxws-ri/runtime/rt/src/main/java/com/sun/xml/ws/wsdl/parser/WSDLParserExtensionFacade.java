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

import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtension;
import com.sun.xml.ws.api.wsdl.parser.WSDLParserExtensionContext;
import com.sun.xml.ws.api.model.wsdl.editable.*;
import com.sun.xml.ws.streaming.XMLStreamReaderUtil;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.Location;

import org.xml.sax.Locator;
import org.xml.sax.helpers.LocatorImpl;

/**
 * {@link WSDLParserExtension} that delegates to
 * multiple {@link WSDLParserExtension}s.
 *
 * <p>
 * This simplifies {@link RuntimeWSDLParser} since it now
 * only needs to work with one {@link WSDLParserExtension}.
 *
 * <p>
 * This class is guaranteed to return true from
 * all the extension callback methods.
 *
 * @author Kohsuke Kawaguchi
 */
final class WSDLParserExtensionFacade extends WSDLParserExtension {
    private final WSDLParserExtension[] extensions;

    WSDLParserExtensionFacade(WSDLParserExtension... extensions) {
        assert extensions!=null;
        this.extensions = extensions;
    }

    @Override
    public void start(WSDLParserExtensionContext context) {
        for (WSDLParserExtension e : extensions) {
            e.start(context);
        }
    }

    @Override
    public boolean serviceElements(EditableWSDLService service, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            if(e.serviceElements(service,reader))
                return true;
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public void serviceAttributes(EditableWSDLService service, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions)
            e.serviceAttributes(service,reader);
    }

    @Override
    public boolean portElements(EditableWSDLPort port, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            if(e.portElements(port,reader))
                return true;
        }
        //extension is not understood by any WSDlParserExtension
        //Check if it must be understood.
        if(isRequiredExtension(reader)) {
            port.addNotUnderstoodExtension(reader.getName(),getLocator(reader));
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public boolean portTypeOperationInput(EditableWSDLOperation op, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions)
            e.portTypeOperationInput(op,reader);

        return false;
    }

    @Override
    public boolean portTypeOperationOutput(EditableWSDLOperation op, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions)
            e.portTypeOperationOutput(op,reader);

        return false;
    }

    @Override
    public boolean portTypeOperationFault(EditableWSDLOperation op, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions)
            e.portTypeOperationFault(op,reader);

        return false;
    }

    @Override
    public void portAttributes(EditableWSDLPort port, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions)
            e.portAttributes(port,reader);
    }

    @Override
    public boolean definitionsElements(XMLStreamReader reader){
        for (WSDLParserExtension e : extensions) {
            if (e.definitionsElements(reader)) {
                return true;
            }
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public boolean bindingElements(EditableWSDLBoundPortType binding, XMLStreamReader reader){
        for (WSDLParserExtension e : extensions) {
            if (e.bindingElements(binding, reader)) {
                return true;
            }
        }
        //extension is not understood by any WSDlParserExtension
        //Check if it must be understood.
        if (isRequiredExtension(reader)) {
            binding.addNotUnderstoodExtension(
                    reader.getName(), getLocator(reader));
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public void bindingAttributes(EditableWSDLBoundPortType binding, XMLStreamReader reader){
        for (WSDLParserExtension e : extensions) {
            e.bindingAttributes(binding, reader);
        }
    }

    @Override
    public boolean portTypeElements(EditableWSDLPortType portType, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            if (e.portTypeElements(portType, reader)) {
                return true;
            }
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public void portTypeAttributes(EditableWSDLPortType portType, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            e.portTypeAttributes(portType, reader);
        }
    }

    @Override
    public boolean portTypeOperationElements(EditableWSDLOperation operation, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            if (e.portTypeOperationElements(operation, reader)) {
                return true;
            }
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public void portTypeOperationAttributes(EditableWSDLOperation operation, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            e.portTypeOperationAttributes(operation, reader);
        }
    }

    @Override
    public boolean bindingOperationElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            if (e.bindingOperationElements(operation, reader)) {
                return true;
            }
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public void bindingOperationAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            e.bindingOperationAttributes(operation, reader);
        }
    }

    @Override
    public boolean messageElements(EditableWSDLMessage msg, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            if (e.messageElements(msg, reader)) {
                return true;
            }
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public void messageAttributes(EditableWSDLMessage msg, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            e.messageAttributes(msg, reader);
        }
    }

    @Override
    public boolean portTypeOperationInputElements(EditableWSDLInput input, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            if (e.portTypeOperationInputElements(input, reader)) {
                return true;
            }
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public void portTypeOperationInputAttributes(EditableWSDLInput input, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            e.portTypeOperationInputAttributes(input, reader);
        }
    }

    @Override
    public boolean portTypeOperationOutputElements(EditableWSDLOutput output, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            if (e.portTypeOperationOutputElements(output, reader)) {
                return true;
            }
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public void portTypeOperationOutputAttributes(EditableWSDLOutput output, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            e.portTypeOperationOutputAttributes(output, reader);
        }
    }

    @Override
    public boolean portTypeOperationFaultElements(EditableWSDLFault fault, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            if (e.portTypeOperationFaultElements(fault, reader)) {
                return true;
            }
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public void portTypeOperationFaultAttributes(EditableWSDLFault fault, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            e.portTypeOperationFaultAttributes(fault, reader);
        }
    }

    @Override
    public boolean bindingOperationInputElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            if (e.bindingOperationInputElements(operation, reader)) {
                return true;
            }
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public void bindingOperationInputAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            e.bindingOperationInputAttributes(operation, reader);
        }
    }

    @Override
    public boolean bindingOperationOutputElements(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            if (e.bindingOperationOutputElements(operation, reader)) {
                return true;
            }
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public void bindingOperationOutputAttributes(EditableWSDLBoundOperation operation, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            e.bindingOperationOutputAttributes(operation, reader);
        }
    }

    @Override
    public boolean bindingOperationFaultElements(EditableWSDLBoundFault fault, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            if (e.bindingOperationFaultElements(fault, reader)) {
                return true;
            }
        }
        XMLStreamReaderUtil.skipElement(reader);
        return true;
    }

    @Override
    public void bindingOperationFaultAttributes(EditableWSDLBoundFault fault, XMLStreamReader reader) {
        for (WSDLParserExtension e : extensions) {
            e.bindingOperationFaultAttributes(fault, reader);
        }
    }

    @Override
    public void finished(WSDLParserExtensionContext context) {
        for (WSDLParserExtension e : extensions) {
            e.finished(context);
        }
    }

    @Override
    public void postFinished(WSDLParserExtensionContext context) {
        for (WSDLParserExtension e : extensions) {
            e.postFinished(context);
        }
    }
    /**
     *
     * @return If the element has wsdl:required attribute set to true
     */

    private boolean isRequiredExtension(XMLStreamReader reader) {
        String required = reader.getAttributeValue(WSDLConstants.NS_WSDL, "required");
        if(required != null)
            return Boolean.parseBoolean(required);
        return false;
    }

    private Locator getLocator(XMLStreamReader reader) {
        Location location = reader.getLocation();
            LocatorImpl loc = new LocatorImpl();
            loc.setSystemId(location.getSystemId());
            loc.setLineNumber(location.getLineNumber());
        return loc;
    }

}
