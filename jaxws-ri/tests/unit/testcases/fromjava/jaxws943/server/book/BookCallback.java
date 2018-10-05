/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.jaxws943.server.book;


import com.sun.xml.ws.developer.StatefulWebServiceManager;
import com.sun.xml.ws.developer.StatefulWebServiceManager.Callback;

public class BookCallback implements Callback<Book>{

	public void onTimeout(Book book, StatefulWebServiceManager<Book> swsm) {
		System.out.println("[ " + hashCode() + " ] Calling timeout on book : "+book.getId());
		swsm.unexport(book);		
	}

}
