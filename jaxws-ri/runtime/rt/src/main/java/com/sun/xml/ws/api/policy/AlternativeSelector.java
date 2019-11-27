/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.api.policy;

import com.sun.xml.ws.policy.EffectiveAlternativeSelector;
import com.sun.xml.ws.policy.EffectivePolicyModifier;
import com.sun.xml.ws.policy.PolicyException;

/**
 *
 * @author Fabian Ritzmann
 */
public class AlternativeSelector extends EffectiveAlternativeSelector {

    public static void doSelection(final EffectivePolicyModifier modifier) throws PolicyException {
        final ValidationProcessor validationProcessor = ValidationProcessor.getInstance();
        selectAlternatives(modifier, validationProcessor);
    }
    
}
