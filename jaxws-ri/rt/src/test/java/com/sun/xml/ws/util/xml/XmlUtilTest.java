package com.sun.xml.ws.util.xml;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import junit.framework.TestCase;

public class XmlUtilTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testXmlSecurityDisabled() throws InstantiationException, IllegalAccessException, NoSuchFieldException, 
                    NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Method method = com.sun.xml.ws.util.xml.XmlUtil.class.getDeclaredMethod("xmlSecurityDisabled", Boolean.TYPE);
        method.setAccessible(true);
        
        Field fieldRunWithSecurityManager = com.sun.xml.ws.util.xml.XmlUtil.class.getDeclaredField("RUN_WITH_SECURITY_MANAGER");
        fieldRunWithSecurityManager.setAccessible(true);
        Field fieldDisabledBySetting = com.sun.xml.ws.util.xml.XmlUtil.class.getDeclaredField("XML_SECURITY_DISABLED");
        fieldDisabledBySetting.setAccessible(true);
        
        fieldRunWithSecurityManager.set(com.sun.xml.ws.util.xml.XmlUtil.class, true);
        
        fieldDisabledBySetting.set(com.sun.xml.ws.util.xml.XmlUtil.class, true);
        assertFalse((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, true));
        assertFalse((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, false));
        
        fieldDisabledBySetting.set(com.sun.xml.ws.util.xml.XmlUtil.class, false);
        assertFalse((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, true));
        assertFalse((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, false));
        
        fieldRunWithSecurityManager.set(com.sun.xml.ws.util.xml.XmlUtil.class, false);
        
        fieldDisabledBySetting.set(com.sun.xml.ws.util.xml.XmlUtil.class, true);
        assertTrue((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, true));
        assertTrue((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, false));
        
        fieldDisabledBySetting.set(com.sun.xml.ws.util.xml.XmlUtil.class, false);
        assertTrue((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, true));
        assertFalse((Boolean)method.invoke(com.sun.xml.ws.util.xml.XmlUtil.class, false));
    }
}

