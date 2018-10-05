/*
 * Copyright (c) 2007, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.transport;

import com.sun.xml.ws.transport.Headers;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import junit.framework.TestCase;
/**
 * @author Muruganandam Chinnaiah
 */
public class HeadersTest extends TestCase {

    public void testputAllMap() throws Exception {

	List<String> set_cookie = new ArrayList(1);
	set_cookie.add("cookie_lowercase");
	List<String> Set_Cookie = new ArrayList(1);
	Set_Cookie.add("Cookie_CamelCase");
	List<String> SET_COOKIE = new ArrayList(1);
	SET_COOKIE.add("COOKIE_UPPERCASE");
	
	
	Map<String, List<String>> map = new HashMap<String, List<String>> ();
	map.put("set-cookie",set_cookie);
	map.put("Set-Cookie",Set_Cookie);
	map.put("SET-COOKIE",SET_COOKIE);
	
	Headers out = new Headers();
	out.putAll(map);
        assertEquals(out.getFirst("set-cookie"),out.getFirst("Set-Cookie"));
        assertEquals(out.getFirst("Set-Cookie"),out.getFirst("SET-COOKIE"));
        assertEquals(out.getFirst("SET-COOKIE"),out.getFirst("set-cookie")); 
        assertEquals(out.size(),1);
        assertEquals(out.get("set-cookie").size(),3);
	assertTrue(out.get("set-cookie").contains("cookie_lowercase"));
	assertTrue(out.get("Set-Cookie").contains("Cookie_CamelCase"));
	assertTrue(out.get("SET-COOKIE").contains("COOKIE_UPPERCASE"));
    }
	
	}
