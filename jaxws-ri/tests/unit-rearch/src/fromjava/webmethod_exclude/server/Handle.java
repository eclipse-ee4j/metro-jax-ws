/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.webmethod_exclude.server;

/**
 *
 * @author rkpulavarthi
 */
public interface Handle extends java.rmi.Remote {
    //private String handle;
    /** Creates a new instance of Handle */
    
    public byte[] decode();   
    
}
