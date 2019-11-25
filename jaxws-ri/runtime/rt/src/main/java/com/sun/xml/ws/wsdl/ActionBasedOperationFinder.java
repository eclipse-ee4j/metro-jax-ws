/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.wsdl;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.api.model.WSDLOperationMapping;
import com.sun.xml.ws.api.model.wsdl.WSDLPort;
import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
import com.sun.xml.ws.api.addressing.AddressingVersion;
import com.sun.xml.ws.api.message.AddressingUtils;
import com.sun.xml.ws.api.message.Message;
import com.sun.xml.ws.api.message.MessageHeaders;
import com.sun.xml.ws.api.message.Messages;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.model.AbstractSEIModelImpl;
import com.sun.xml.ws.model.JavaMethodImpl;
import static com.sun.xml.ws.wsdl.PayloadQNameBasedOperationFinder.*;
import com.sun.xml.ws.resources.AddressingMessages;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * An {@link WSDLOperationFinder} implementation that uses
 * WS-Addressing Action Message Addressing Property, <code>wsa:Action</code> and SOAP Payload QName,
 * as the key for dispatching.
 * <br>
 * This should be used only when AddressingFeature is enabled.
 * A map of all {@link ActionBasedOperationSignature}s in the port and the corresponding and the WSDL Operation QNames
 * is maintained.
 * <br>
 *
 * @author Rama Pulavarthi
 */
final class ActionBasedOperationFinder extends WSDLOperationFinder {
    
    private static final Logger LOGGER = Logger.getLogger(ActionBasedOperationFinder.class.getName());
    private final Map<ActionBasedOperationSignature, WSDLOperationMapping> uniqueOpSignatureMap;
    private final Map<String, WSDLOperationMapping> actionMap;

    private final @NotNull AddressingVersion av;

    public ActionBasedOperationFinder(WSDLPort wsdlModel, WSBinding binding, @Nullable SEIModel seiModel) {
        super(wsdlModel, binding, seiModel);

        assert binding.getAddressingVersion() != null;    // this dispatcher can be only used when addressing is on.
        av = binding.getAddressingVersion();
        uniqueOpSignatureMap = new HashMap<ActionBasedOperationSignature, WSDLOperationMapping>();
        actionMap = new HashMap<String,WSDLOperationMapping>();

        if (seiModel != null) {
            for (JavaMethodImpl m : ((AbstractSEIModelImpl) seiModel).getJavaMethods()) {
                if(m.getMEP().isAsync)
                    continue;

                String action = m.getInputAction();
                QName payloadName = m.getRequestPayloadName();
                if (payloadName == null)
                    payloadName = EMPTY_PAYLOAD;
                //first look at annotations and then in wsdlmodel
                if (action == null || action.equals("")) {
                    if (m.getOperation() != null) action = m.getOperation().getOperation().getInput().getAction();
//                    action = m.getInputAction();
                }
                if (action != null) {
                    ActionBasedOperationSignature opSignature = new ActionBasedOperationSignature(action, payloadName);
                    if(uniqueOpSignatureMap.get(opSignature) != null) {
                        LOGGER.warning(AddressingMessages.NON_UNIQUE_OPERATION_SIGNATURE(
                                uniqueOpSignatureMap.get(opSignature),m.getOperationQName(),action,payloadName));
                    }
                    uniqueOpSignatureMap.put(opSignature, wsdlOperationMapping(m));
                    actionMap.put(action,wsdlOperationMapping(m));
                }
            }
        } else {
            for (WSDLBoundOperation wsdlOp : wsdlModel.getBinding().getBindingOperations()) {
                QName payloadName = wsdlOp.getRequestPayloadName();
                if (payloadName == null)
                    payloadName = EMPTY_PAYLOAD;
                String action = wsdlOp.getOperation().getInput().getAction();
                ActionBasedOperationSignature opSignature = new ActionBasedOperationSignature(
                        action, payloadName);
                if(uniqueOpSignatureMap.get(opSignature) != null) {
                    LOGGER.warning(AddressingMessages.NON_UNIQUE_OPERATION_SIGNATURE(
                                    uniqueOpSignatureMap.get(opSignature),wsdlOp.getName(),action,payloadName));

                }
                uniqueOpSignatureMap.put(opSignature, wsdlOperationMapping(wsdlOp));
                actionMap.put(action,wsdlOperationMapping(wsdlOp));
            }
        }
    }

//    /**
//     *
//     * @param request  Request Packet that is used to find the associated WSDLOperation
//     * @return WSDL operation Qname.
//     *         return null if WS-Addressing is not engaged. 
//     * @throws DispatchException with WSA defined fault message when it cannot find an associated WSDL operation.
//     *
//     */
//    @Override
//    public QName getWSDLOperationQName(Packet request) throws DispatchException {
//        return getWSDLOperationMapping(request).getWSDLBoundOperation().getName();
//    }

    public WSDLOperationMapping getWSDLOperationMapping(Packet request) throws DispatchException {
        MessageHeaders hl = request.getMessage().getHeaders();
        String action = AddressingUtils.getAction(hl, av, binding.getSOAPVersion());

        if (action == null)
            // Addressing is not enagaged, return null to use other ways to dispatch.
            return null;

        Message message = request.getMessage();
        QName payloadName;
        String localPart = message.getPayloadLocalPart();
        if (localPart == null) {
            payloadName = EMPTY_PAYLOAD;
        } else {
            String nsUri = message.getPayloadNamespaceURI();
            if (nsUri == null)
                nsUri = EMPTY_PAYLOAD_NSURI;
            payloadName = new QName(nsUri, localPart);
        }

        WSDLOperationMapping opMapping = uniqueOpSignatureMap.get(new ActionBasedOperationSignature(action, payloadName));
        if (opMapping != null)
            return opMapping;

        //Seems like in Wstrust STS wsdls, the payload does not match what is specified in the wsdl leading to incorrect
        //  wsdl operation resolution. Use just wsa:Action to dispatch as a last resort.
        //try just with wsa:Action
        opMapping = actionMap.get(action);
        if (opMapping != null)
            return opMapping;

        // invalid action header
        Message result = Messages.create(action, av, binding.getSOAPVersion());

        throw new DispatchException(result);

    }
}
