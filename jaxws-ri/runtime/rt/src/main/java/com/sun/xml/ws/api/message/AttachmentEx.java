/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.message;

import com.sun.istack.NotNull;

import java.util.Iterator;

/**
 * Attachment extended interface exposing custom MIME headers.
 * @since 2.2.6
 */
public interface AttachmentEx extends Attachment {
	/**
	 * MIME header
	 */
	public interface MimeHeader {
		/**
		 * MIME header name
		 * @return name
		 */
		public String getName();
		/**
		 * MIME header value
		 * @return value
		 */
		public String getValue();
	}

	/**
	 * Iterator of custom MIME headers associated with this attachment
	 * @return MIME header iterator
	 */
	public @NotNull Iterator<MimeHeader> getMimeHeaders();
}
