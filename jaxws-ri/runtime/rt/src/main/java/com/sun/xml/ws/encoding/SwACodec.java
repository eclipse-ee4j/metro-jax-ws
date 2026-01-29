/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.WSFeatureList;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.message.Attachment;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.pipe.ContentType;
import com.sun.xml.ws.message.MimeAttachmentSet;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import java.util.Map;


/**
 * {@link Codec} that uses MIME/multipart as the base format.
 *
 * @author Jitendra Kotamraju
 */
public final class SwACodec extends MimeCodec {

    public SwACodec(SOAPVersion version, WSFeatureList f, Codec rootCodec) {
        super(version, f);
        this.mimeRootCodec = rootCodec;
    }

    private SwACodec(SwACodec that) {
        super(that);
        this.mimeRootCodec = that.mimeRootCodec.copy();
    }

    @Override
    protected void decode(MimeMultipartParser mpp, Packet packet) throws IOException {
        // TODO: handle attachments correctly
        Attachment root = mpp.getRootPart();
        Codec rootCodec = getMimeRootCodec(packet);
        if (rootCodec instanceof RootOnlyCodec) {
            ((RootOnlyCodec)rootCodec).decode(root.asInputStream(),root.getContentType(),packet, new MimeAttachmentSet(mpp));
        } else {
            rootCodec.decode(root.asInputStream(),root.getContentType(),packet);
            Map<String, Attachment> atts = mpp.getAttachmentParts();
            for(Map.Entry<String, Attachment> att : atts.entrySet()) {
                packet.getMessage().getAttachments().add(att.getValue());
            }
        }
    }

    @Override
    public ContentType encode(Packet packet, WritableByteChannel buffer) {
        //TODO: not yet implemented
        throw new UnsupportedOperationException();
    }

    @Override
    public SwACodec copy() {
        return new SwACodec(this);
    }
}
