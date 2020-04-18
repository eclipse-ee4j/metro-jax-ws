/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package bugs.jaxws1049.client;

import com.sun.xml.ws.api.PropertySet;
import junit.framework.TestCase;

import java.util.Map;

import static jakarta.xml.ws.BindingProvider.SOAPACTION_URI_PROPERTY;

/**
 * Test for new implementation of {@link com.sun.xml.ws.api.PropertySet#asMap()} ()} - it should be used instead of the
 * old implementation {@link com.sun.xml.ws.api.PropertySet#createMapView()} which is read only ...
 *
 * @author Miroslav Kos (miroslav.kos at oracle.com)
 */
public class PropertySetAsMapTest extends TestCase {

    public void test1NonExtensible() {

        MyPropertySet ctx = new MyPropertySet();
        Map<String, Object> map = ctx.asMap();

        assertSOAPActionCorrect(ctx, map, null);

        ctx.setSoapAction("customAction");
        assertSOAPActionCorrect(ctx, map, "customAction");

        map.put(SOAPACTION_URI_PROPERTY, "ANOTHERAction");
        assertSOAPActionCorrect(ctx, map, "ANOTHERAction");

        map.keySet(); // shouldn't change anything
        assertSOAPActionCorrect(ctx, map, "ANOTHERAction");

        map.put(SOAPACTION_URI_PROPERTY, null);
        assertSOAPActionCorrect(ctx, map, null);

        ctx.setSoapAction("YET ANOTHER ONE");
        assertSOAPActionCorrect(ctx, map, "YET ANOTHER ONE");

        try {
            map.put("unknownProperty", "Anything");
        } catch (IllegalStateException e) {
            // ok
        }

        // reading not existing property is ok(?)
        try {
            map.get("unknownProperty");
        } catch (IllegalStateException e) {
            // ok
        }
    }

    public void testExtensible() {

        MyExtensiblePropertySet ctx = new MyExtensiblePropertySet();
        Map<String, Object> map = ctx.asMap();

        assertSOAPActionCorrect(ctx, map, null);

        ctx.setSoapAction("customAction");
        assertSOAPActionCorrect(ctx, map, "customAction");

        map.put(SOAPACTION_URI_PROPERTY, "ANOTHERAction");
        assertSOAPActionCorrect(ctx, map, "ANOTHERAction");

        map.keySet(); // shouldn't change anything
        assertSOAPActionCorrect(ctx, map, "ANOTHERAction");

        map.put(SOAPACTION_URI_PROPERTY, null);
        assertSOAPActionCorrect(ctx, map, null);

        ctx.setSoapAction("YET ANOTHER ONE");
        assertSOAPActionCorrect(ctx, map, "YET ANOTHER ONE");

        map.put("unknownProperty", "Anything");
        assertSame(map.get("unknownProperty"), "Anything");
    }

    private void assertSOAPActionCorrect(MyPropertySet ctx, Map<String, Object> map, Object expected) {
        assertSame("Incorrect SOAPAction got via strongly typed getter", expected, ctx.getSoapAction());
        assertSame("Incorrect SOAPAction got via PropertySet.asMap()", expected, map.get(SOAPACTION_URI_PROPERTY));
    }

    private void assertSOAPActionCorrect(MyExtensiblePropertySet ctx, Map<String, Object> map, Object expected) {
        assertSame("Incorrect SOAPAction got via strongly typed getter", expected, ctx.getSoapAction());
        assertSame("Incorrect SOAPAction got via PropertySet.asMap()", expected, map.get(SOAPACTION_URI_PROPERTY));
    }

    class MyPropertySet extends PropertySet {

        @Property(SOAPACTION_URI_PROPERTY)
        private String soapAction;

        @Override
        protected PropertyMap getPropertyMap() {
            return parse(MyPropertySet.class);
        }

        public String getSoapAction() {
            return soapAction;
        }

        public void setSoapAction(String soapAction) {
            this.soapAction = soapAction;
        }
    }

    class MyExtensiblePropertySet extends PropertySet {

        @Property(SOAPACTION_URI_PROPERTY)
        private String soapAction;

        @Override
        protected PropertyMap getPropertyMap() {
            return parse(MyExtensiblePropertySet.class);
        }

        public String getSoapAction() {
            return soapAction;
        }

        public void setSoapAction(String soapAction) {
            this.soapAction = soapAction;
        }

        @Override
        protected boolean mapAllowsAdditionalProperties() {
            return true;
        }
    }

}
