/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util.pipe;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;

import com.sun.xml.ws.api.BindingID;
import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.NextAction;
import com.sun.xml.ws.api.pipe.Tube;
import com.sun.xml.ws.api.pipe.TubeCloner;
import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;

import junit.framework.TestCase;

public class AbstractSchemaValidationTubeTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testCreateSameTnsPseudoSchema() throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, TransformerException {
    StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?><xsd:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n");
		sb.append("<xsd:include schemaLocation=\"a.xsd\"/>\n");
		sb.append("<xsd:include schemaLocation=\"b.xsd\"/>\n");
		sb.append("</xsd:schema>");
		
		String strResult_null = runCreateSameTnsPseudoSchema(null);
		assertEquals(sb.toString(), strResult_null);
		assertEquals(-1, strResult_null.indexOf("targetNamespace"));
		
		String tns="null";
		String strResult_nullText = runCreateSameTnsPseudoSchema(tns);
		assertEquals(sb.toString(), strResult_nullText);
		assertEquals(-1, strResult_nullText.indexOf("targetNamespace"));
		
	}

	private String runCreateSameTnsPseudoSchema(String tns)
	    throws NoSuchMethodException, IllegalAccessException,
	    InvocationTargetException, TransformerFactoryConfigurationError,
	    TransformerConfigurationException, TransformerException {
		BindingID bindingId = BindingID.SOAP11_HTTP;
		WSBinding binding = bindingId.createBinding();
		
		Class<AbstractSchemaValidationTube> clazz = AbstractSchemaValidationTube.class;
		Object instance = new StractSchemaValidationTubeMock(binding, new TubeMock());
		Method method = clazz.getDeclaredMethod("createSameTnsPseudoSchema", new Class[]{String.class,Collection.class,String.class});
		method.setAccessible(true);  
		
		List<String> docs = new ArrayList<>();
		docs.add("a.xsd");
		docs.add("b.xsd");
		String pseudoSystemId = "file:x-jax-ws-include-0";
		Object result = method.invoke(instance, new Object[]{tns, docs, pseudoSystemId});
		StreamSource schemaStream = (StreamSource)result;
		
		StringWriter writer = new StringWriter();
    StreamResult StreamResult = new StreamResult(writer);
    TransformerFactory tFactory = TransformerFactory.newInstance();
    Transformer transformer = tFactory.newTransformer();
    transformer.transform(schemaStream,StreamResult);
    String strResult = writer.toString();
		return strResult;
	}
	
	class StractSchemaValidationTubeMock extends AbstractSchemaValidationTube {
		public StractSchemaValidationTubeMock(WSBinding binding, Tube next) {
			super(binding, next);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected Validator getValidator() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected boolean isNoValidation() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public AbstractTubeImpl copy(TubeCloner cloner) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	class TubeMock implements Tube{

		@Override
		public NextAction processRequest(Packet request) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NextAction processResponse(Packet response) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public NextAction processException(Throwable t) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void preDestroy() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Tube copy(TubeCloner cloner) {
			// TODO Auto-generated method stub
			return null;
		}
	}

}
