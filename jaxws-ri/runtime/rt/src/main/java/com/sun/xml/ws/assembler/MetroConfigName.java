/*
 * Copyright (c) 1997, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.assembler;

/**
 * This interface is used to get the file name used for the metro configuration file.
 * This allows multiple configurations of metro in a single VM.
 * 
 * @author Bob Naugle
 */
public interface MetroConfigName {
    public String getDefaultFileName();
    
    public String getAppFileName();

}
