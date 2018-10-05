/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.cr6940117.pak2;

import fromjava.cr6940117.pak2.bean.Page;

import javax.jws.WebMethod;

@javax.jws.WebService(serviceName = "PageService", targetNamespace = "http://namespace1", portName = "PageServicePort")
public class PageService {

	@WebMethod
	public Page getPage(String page_id, String client_id) {
		Page p = new Page();
        p.setIsEmailLinkRequired("yes");
        return p;
	}
}
