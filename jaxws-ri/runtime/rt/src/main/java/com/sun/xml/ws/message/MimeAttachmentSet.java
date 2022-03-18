/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.message;

import com.sun.xml.ws.api.message.AttachmentSet;
import com.sun.xml.ws.api.message.Attachment;
import com.sun.xml.ws.encoding.MimeMultipartParser;
import com.sun.xml.ws.resources.EncodingMessages;
import com.sun.istack.Nullable;

import jakarta.xml.ws.WebServiceException;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

/**
 * {@link AttachmentSet} backed by {@link com.sun.xml.ws.encoding.MimeMultipartParser}
 *
 * @author Vivek Pandey
 */
public final class MimeAttachmentSet implements AttachmentSet {
    private final MimeMultipartParser mpp;
    private Map<String, Attachment> atts = new HashMap<>();


    public MimeAttachmentSet(MimeMultipartParser mpp) {
        this.mpp = mpp;
    }

    @Override
    @Nullable
    public Attachment get(String contentId) {
        Attachment att;
        /*
          First try to get the Attachment from internal map, maybe this attachment
          is added by the user.
         */
        att = atts.get(contentId);
        if(att != null)
            return att;
        try {
            /*
              Attachment is not found in the internal map, now do look in
              the mpp, if found add to the internal Attachment map.
             */
            att = mpp.getAttachmentPart(contentId);
            if(att != null){
                atts.put(contentId, att);
            }
        } catch (IOException e) {
            throw new WebServiceException(EncodingMessages.NO_SUCH_CONTENT_ID(contentId), e);
        }
        return att;
    }

    /**
     * This is expensive operation, its going to to read all the underlying
     * attachments in {@link MimeMultipartParser}.
     */
    @Override
    public boolean isEmpty() {
        return atts.size() <= 0 && mpp.getAttachmentParts().isEmpty();
    }

    @Override
    public void add(Attachment att) {
        atts.put(att.getContentId(), att);
    }

    /**
     * Expensive operation.
     */
    @Override
    public Iterator<Attachment> iterator() {
        /*
          Browse thru all the attachments in the mpp, add them to #atts,
          then return whether its empty.
         */
        Map<String, Attachment> attachments = mpp.getAttachmentParts();
        for(Map.Entry<String, Attachment> att : attachments.entrySet()) {
            atts.computeIfAbsent(att.getKey(), k -> att.getValue());
        }

        return atts.values().iterator();
    }
}
