/*
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testutil.converter;

import java.io.File;

/**
 * @author JAX-RPC Development Team
 */
public class TargetMover {
    String target;
    File dir;
    
    public TargetMover(String dirName, String target) {
        this.target = target;
        
        dir = new File(dirName);
        System.out.println("Processing \"" + dirName + "\" ...");
        if (!dir.isDirectory())
            throw new IllegalArgumentException(dirName + " must be a directory");
    }
    
    public void move() {
        try {
            File targetDir = new File(dir + target);
            targetDir.mkdir();
            
            File clientTargetDir = new File(targetDir + "client");
            clientTargetDir.mkdir();
            
            File serverTargetDir = new File(targetDir + "server");
            serverTargetDir.mkdir();
            
            File configTargetDir = new File(targetDir + "config");
            configTargetDir.mkdir();
            
            
        } catch (Exception ex) {
            
        }
    }

}

