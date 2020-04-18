/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package server.provider.xmlbind_datasource.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.Service.Mode;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.soap.*;
import org.w3c.dom.Node;
import javax.xml.transform.dom.DOMSource;
import jakarta.activation.DataSource;
import jakarta.xml.ws.ServiceMode;
import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeBodyPart;
import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeMultipart;
import com.sun.xml.messaging.saaj.packaging.mime.internet.InternetHeaders;
import java.io.*;
import junit.framework.*;
import java.util.*;
import jakarta.xml.ws.WebServiceProvider;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.annotation.Resource;

@WebServiceProvider
@ServiceMode (value=Service.Mode.MESSAGE)
public class HelloImpl implements Provider<DataSource> {
    
    @Resource
    WebServiceContext wsContext;

    private static final JAXBContext jaxbContext = createJAXBContext ();
    private int bodyIndex;
    
    public jakarta.xml.bind.JAXBContext getJAXBContext (){
        return jaxbContext;
    }
    
    private static jakarta.xml.bind.JAXBContext createJAXBContext (){
        try{
            return JAXBContext.newInstance (ObjectFactory.class);
        }catch(jakarta.xml.bind.JAXBException e){
            throw new WebServiceException (e.getMessage (), e);
        }
    }
    
    private byte[] sendSource () {
        System.out.println ("**** sendSource ******");
        String begin = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Body>";
        String end = "</soapenv:Body></soapenv:Envelope>";
        
        String[] body  = {
            "<HelloResponse xmlns=\"urn:test:types\"><argument xmlns=\"\">foo</argument><extra xmlns=\"\">bar</extra></HelloResponse>",
                "<ans1:HelloResponse xmlns:ans1=\"urn:test:types\"><argument>foo</argument><extra>bar</extra></ans1:HelloResponse>"
        };
        int i = (++bodyIndex)%body.length;
        String content = begin+body[i]+end;
        return content.getBytes ();
    }
    
    private void recvBean (Source source) throws Exception {
        System.out.println ("**** recvBean ******");
        Hello hello = (Hello)jaxbContext.createUnmarshaller().unmarshal(source);
        if (!"ArgSetByHandler".equals(hello.getArgument())) {
            throw new WebServiceException("hello.getArgument(): expected \"Dispatch\", got \"" + hello.getArgument() + "\"");
        }
        if (!"ExtraSetByHandler".equals(hello.getExtra())) {
            throw new WebServiceException("hello.getArgument(): expected \"Test\", got \"" + hello.getExtra() + "\"");
        }
    }
    
    public DataSource invoke (DataSource dataSource) {
        System.out.println("***** invoke(DataSource) *******");

		// Checks handler set properties and updates MessageContext
		checkAndUpdateMsgCtxt();
        
        try {
            final MimeMultipart multipart = new MimeMultipart(dataSource, null);

			// Verify no of MIME parts in the datasource
			int no = multipart.getCount();
			if (no != 2) {
				throw new WebServiceException("expected=2 MIME parts Got="+no);
			}
			
            MimeBodyPart bodyPart = (MimeBodyPart)multipart.getBodyPart (0);
            
            // Create source according to type
            String contentType = bodyPart.getContentType();
            Source source = contentType.equals("application/fastinfoset") ?
                new org.jvnet.fastinfoset.FastInfosetSource(bodyPart.getInputStream())
                : new StreamSource(bodyPart.getInputStream());
            
            SOAPMessage msg = MessageFactory.newInstance ().createMessage ();
            msg.getSOAPPart ().setContent (source);
            Node node = msg.getSOAPBody ().getFirstChild ();
            recvBean (new DOMSource(node));
            
            final MimeMultipart resp = new MimeMultipart ("related");
            resp.getContentType().setParameter("type", "text/xml");     // type is mandatory
            InternetHeaders hdrs = new InternetHeaders ();
            hdrs.setHeader ("Content-Type", "text/xml");
            byte[] buf = sendSource();
            MimeBodyPart body = new MimeBodyPart (hdrs, buf, buf.length);
            resp.addBodyPart (body);
            
            return new DataSource () {
                public InputStream getInputStream () {
                    try {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream ();
                        resp.writeTo (bos);
                        bos.close ();
                        return new ByteArrayInputStream (bos.toByteArray ());
                    } catch(Exception ioe) {
                        throw new WebServiceException ("Cannot give DataSource", ioe);
                    }
                }
                
                public OutputStream getOutputStream () {
                    return null;
                }
                
                public String getContentType () {
                    return resp.getContentType().toString();
                }
                
                public String getName () {
                    return "";
                }
            };

            
        } catch(Exception e) {
            e.printStackTrace ();
            throw new WebServiceException ("Provider endpoint failed", e);
        }
    }

	private void checkAndUpdateMsgCtxt() {
		// Get a property from context
		MessageContext ctxt = wsContext.getMessageContext();
		String gotProp = (String)ctxt.get("foo");
		if (!gotProp.equals("bar")) {
			System.out.println("foo property: expected=bar Got="+gotProp);
			throw new WebServiceException(
				"foo property: expected=bar Got="+gotProp);
		}

		// Modify the same property in the context
		ctxt.put("foo", "return-bar");

		// Set a property in the context
		ctxt.put("return-foo", "return-bar");
	}
}
