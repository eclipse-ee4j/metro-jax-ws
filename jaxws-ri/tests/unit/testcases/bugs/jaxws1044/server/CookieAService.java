/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1044.server;

import javax.annotation.Resource;
import jakarta.jws.WebService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import java.util.logging.Logger;

/**
 * Service setting received cookie(s) (+adding new) back to client to test fuctionality
 * handling cookies (issue http://java.net/jira/browse/JAX_WS-1044)
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
@WebService(portName = "A")
public class CookieAService {
    
    static final Logger logger = Logger.getLogger(CookieAService.class.getName());

    @Resource
    WebServiceContext context;

    int call = 0;

    public void operation() {
        logAndCopyReceivedCookies();
        addNewCookie();
        call++;
    }

    protected void addNewCookie() {
        MessageContext mctx = context.getMessageContext();
        HttpServletResponse res = (HttpServletResponse) mctx.get(MessageContext.SERVLET_RESPONSE);
        Cookie cookie = new Cookie("S" + call, "S" + call);
        res.addCookie(cookie);
        logger.fine("   << SERVICE: received + " + cookieToString(new StringBuilder(), "", cookie).toString());
    }

    protected void logAndCopyReceivedCookies() {
        MessageContext mctx = context.getMessageContext();
        HttpServletRequest req = (HttpServletRequest) mctx.get(MessageContext.SERVLET_REQUEST);
        logger.fine("   >> SERVICE: " + cookiesToString(req.getCookies()).toString());
        HttpServletResponse res = (HttpServletResponse) mctx.get(MessageContext.SERVLET_RESPONSE);
        for (Cookie cookie : req.getCookies()) {
            res.addCookie(cookie);
        }
    }

    protected StringBuilder cookiesToString(Cookie[] cookies) {
        StringBuilder sb = new StringBuilder();
        if (cookies != null) {
            String delim = "";
            for (Cookie c : cookies) {
                cookieToString(sb, delim, c);
                delim = " | ";
            }
        }
        return sb;
    }

    private StringBuilder cookieToString(StringBuilder sb, String delim, Cookie c) {
        sb.append(delim);
        sb.append(c.getName());
        sb.append(" = ");
        sb.append(c.getValue());
        return sb;
    }

}
