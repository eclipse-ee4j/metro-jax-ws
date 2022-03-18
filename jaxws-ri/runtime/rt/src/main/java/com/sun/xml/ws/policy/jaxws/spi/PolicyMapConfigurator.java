/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.jaxws.spi;

import com.sun.xml.ws.api.WSBinding;
import com.sun.xml.ws.api.model.SEIModel;
import com.sun.xml.ws.policy.PolicyException;
import com.sun.xml.ws.policy.PolicyMap;
import com.sun.xml.ws.policy.PolicySubject;
import java.util.Collection;

/**
 * The service provider implementing this interface will be discovered and called to extend created PolicyMap instance with additional policy
 * bindings. The call is performed directly after WSIT config file is parsed.
 *
 * @since JAX-WS 2.2
 * @author Marek Potociar
 * @author Fabian Ritzmann
 * @author Rama.Pulavarthi@sun.com
 */
public interface PolicyMapConfigurator {

  /**
   * A callback method that allows to retrieve policy related information from provided parameters
   * return a collection of new policies that are added to the map.
   *
   * When new policies are returned, the caller may merge them with existing policies
   * in the policy map.
   *
   * @param policyMap This map contains the policies that were already created
   * @param model The WSDL model of the service
   * @param wsBinding The binding of the service
   * @return A collection of policies and the subject to which they are attached.
   *   May return null or an empty collection.
   * @throws PolicyException Throw this exception if an error occurs
   */
  Collection<PolicySubject> update(PolicyMap policyMap, SEIModel model, WSBinding wsBinding) throws PolicyException;
}
