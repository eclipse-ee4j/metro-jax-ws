/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.assembler;

import com.sun.xml.ws.runtime.config.TubeFactoryConfig;
import com.sun.xml.ws.runtime.config.TubeFactoryList;
import junit.framework.TestCase;

import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Marek Potociar
 */
public class MetroConfigLoaderTest extends TestCase {

    private static final class TestDataInfo {

        final String appConfigFileName;
        final int expectedTubes;

        public TestDataInfo(String appConfigFileName, int expectedTubes) {
            this.appConfigFileName = appConfigFileName;
            this.expectedTubes = expectedTubes;
        }
    }
    private static final TestDataInfo[] APP_METRO_CONFIGS = new TestDataInfo[]{
        new TestDataInfo("jaxws-tubes.xml", 12),
        new TestDataInfo("jaxws-tubes-no-default.xml", 13),
        new TestDataInfo("jaxws-tubes-no-default-single-tube.xml", 1)
    };

    public MetroConfigLoaderTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getTubelineForEndpoint method, of class MetroConfigLoader.
     */
    public void testGetEndpointSideTubeFactoriesTest() throws URISyntaxException {
        TestDataInfo tdi = APP_METRO_CONFIGS[0];
        MetroConfigLoader configLoader = new MetroConfigLoader(MockupMetroConfigLoader.createMockupContainer(tdi.appConfigFileName),
                MetroTubelineAssembler.JAXWS_TUBES_CONFIG_NAMES);

        TubeFactoryList result;
        result = configLoader.getEndpointSideTubeFactories(new URI("http://org.sample#wsdl11.port(PingService/HttpPingPort)"));
        assertTrue(containsTubeFactoryConfig(result, "server"));
        assertEquals(tdi.expectedTubes, result.getTubeFactoryConfigs().size());

        result = configLoader.getEndpointSideTubeFactories(new URI("http://org.sample#wsdl11.port(PingService/JmsPingPort)"));
        assertTrue(containsTubeFactoryConfig(result, "default-server"));
        assertEquals(tdi.expectedTubes, result.getTubeFactoryConfigs().size());

        result = configLoader.getEndpointSideTubeFactories(new URI("http://org.sample#wsdl11.port(PingService/OtherPingPort)"));
        assertTrue(containsTubeFactoryConfig(result, "default-server"));
        assertEquals(tdi.expectedTubes, result.getTubeFactoryConfigs().size());
    }

    /**
     * Test of getTubelineForEndpoint method, of class MetroConfigLoader.
     */
    public void testGetClientSideTubeFactoriesTest() throws URISyntaxException {
        TestDataInfo tdi = APP_METRO_CONFIGS[0];
        MetroConfigLoader configLoader = new MetroConfigLoader(MockupMetroConfigLoader.createMockupContainer(tdi.appConfigFileName),
                        MetroTubelineAssembler.JAXWS_TUBES_CONFIG_NAMES);

        TubeFactoryList result;
        result = configLoader.getClientSideTubeFactories(new URI("http://org.sample#wsdl11.port(PingService/HttpPingPort)"));
        assertTrue(containsTubeFactoryConfig(result, "default-client"));
        assertEquals(tdi.expectedTubes, result.getTubeFactoryConfigs().size());

        result = configLoader.getClientSideTubeFactories(new URI("http://org.sample#wsdl11.port(PingService/JmsPingPort)"));
        assertTrue(containsTubeFactoryConfig(result, "client"));
        assertEquals(tdi.expectedTubes, result.getTubeFactoryConfigs().size());

        result = configLoader.getClientSideTubeFactories(new URI("http://org.sample#wsdl11.port(PingService/OtherPingPort)"));
        assertTrue(containsTubeFactoryConfig(result, "default-client"));
        assertEquals(tdi.expectedTubes, result.getTubeFactoryConfigs().size());
    }

    /**
     * Test of getTubelineForEndpoint method, of class MetroConfigLoader - loading from default Metro config
     */
    public void testGetEndpointSideTubeFactoriesLoadFromDefaultConfig() throws URISyntaxException {
        for (int i = 1; i < APP_METRO_CONFIGS.length; i++) {
            TestDataInfo tdi = APP_METRO_CONFIGS[i];

            MetroConfigLoader configLoader = new MetroConfigLoader(MockupMetroConfigLoader.createMockupContainer(tdi.appConfigFileName),
                            MetroTubelineAssembler.JAXWS_TUBES_CONFIG_NAMES);

            TubeFactoryList result;
            result = configLoader.getEndpointSideTubeFactories(new URI("http://org.sample#wsdl11.port(PingService/HttpPingPort)"));
            assertTrue(containsTubeFactoryConfig(result, "server"));
            assertEquals(tdi.expectedTubes, result.getTubeFactoryConfigs().size());

            result = configLoader.getEndpointSideTubeFactories(new URI("http://org.sample#wsdl11.port(PingService/JmsPingPort)"));
            assertFalse(result.getTubeFactoryConfigs().isEmpty());
            assertFalse(containsTubeFactoryConfig(result, "server"));

            result = configLoader.getEndpointSideTubeFactories(new URI("http://org.sample#wsdl11.port(PingService/OtherPingPort)"));
            assertFalse(result.getTubeFactoryConfigs().isEmpty());
            assertFalse(containsTubeFactoryConfig(result, "server"));
        }
    }

    /**
     * Test of getTubelineForEndpoint method, of class MetroConfigLoader - loading from default Metro config
     */
    public void testGetClientSideTubeFactoriesLoadFromDefaultConfig() throws URISyntaxException {
        for (int i = 1; i < APP_METRO_CONFIGS.length; i++) {
            TestDataInfo tdi = APP_METRO_CONFIGS[i];

            MetroConfigLoader configLoader = new MetroConfigLoader(MockupMetroConfigLoader.createMockupContainer(tdi.appConfigFileName),
                            MetroTubelineAssembler.JAXWS_TUBES_CONFIG_NAMES);

            TubeFactoryList result;
            result = configLoader.getClientSideTubeFactories(new URI("http://org.sample#wsdl11.port(PingService/HttpPingPort)"));
            assertFalse(result.getTubeFactoryConfigs().isEmpty());
            assertFalse(containsTubeFactoryConfig(result, "client"));

            result = configLoader.getClientSideTubeFactories(new URI("http://org.sample#wsdl11.port(PingService/JmsPingPort)"));
            assertTrue(containsTubeFactoryConfig(result, "client"));
            assertEquals(tdi.expectedTubes, result.getTubeFactoryConfigs().size());

            result = configLoader.getClientSideTubeFactories(new URI("http://org.sample#wsdl11.port(PingService/OtherPingPort)"));
            assertFalse(result.getTubeFactoryConfigs().isEmpty());
            assertFalse(containsTubeFactoryConfig(result, "client"));
        }
    }

    private boolean containsTubeFactoryConfig(TubeFactoryList tubeList, String tubeFactoryName) {
        for (TubeFactoryConfig config : tubeList.getTubeFactoryConfigs()) {
            if (config.getClassName().equals(tubeFactoryName)) {
                return true;
            }
        }

        return false;
    }
}
