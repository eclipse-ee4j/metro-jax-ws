/*
 * Copyright (c) 2012, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.db.sdo;

import org.eclipse.persistence.oxm.attachment.XMLAttachmentUnmarshaller;

import jakarta.activation.DataHandler;

/**
 * Created by IntelliJ IDEA.
 * User: giglee
 * Date: May 13, 2009
 * Time: 3:52:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class SDOAttachmentUnmarshaller implements XMLAttachmentUnmarshaller {
    private jakarta.xml.bind.attachment.AttachmentUnmarshaller jbu;



    public SDOAttachmentUnmarshaller(jakarta.xml.bind.attachment.AttachmentUnmarshaller jbu) {
        this.jbu = jbu;
    }

    public byte[] getAttachmentAsByteArray(String cid) {
        return jbu.getAttachmentAsByteArray(cid);
    }

    public DataHandler getAttachmentAsDataHandler(String cid) {
       return jbu.getAttachmentAsDataHandler(cid);
    }

    public boolean isXOPPackage() {
        return jbu.isXOPPackage();
    }
}
