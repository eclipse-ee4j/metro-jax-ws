/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.message;

import com.sun.istack.Nullable;

/**
 * A set of {@link Attachment} on a {@link Message}.
 *
 * <p>
 * A particular attention is made to ensure that attachments
 * can be read and parsed lazily as requested.
 *
 * @see Message#getAttachments()
 */
public interface AttachmentSet extends Iterable<Attachment> {
    /**
     * Gets the attachment by the content ID.
     *
     * @param contentId
     *      The content ID like "foo-bar-zot@abc.com", without
     *      surrounding '&lt;' and '>' used as the transfer syntax.
     *
     * @return null
     *      if no such attachment exist.
     */
    @Nullable
    Attachment get(String contentId);

    /**
     * Returns true if there's no attachment.
     */
    boolean isEmpty();

    /**
     * Adds an attachment to this set.
     *
     * <p>
     * Note that it's OK for an {@link Attachment} to belong to
     * more than one {@link AttachmentSet} (which is in fact
     * necessary when you wrap a {@link Message} into another.
     *
     * @param att
     *      must not be null.
     */
    public void add(Attachment att);

}
