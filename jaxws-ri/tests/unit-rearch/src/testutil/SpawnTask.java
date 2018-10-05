/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testutil;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.ExecTask;

/**
 * Extends the Apache Exec task to execute a given command in a separate thread and return control.
 * Supports the same attributes and elements as the exec task.
 * This task is useful for running blocking server programs where you wish
 * the server to be spawned off in a separate thread and execution to proceed
 * without blocking for the server.
 *
 * @author <a href="mailto"nandkumar.kesavan@sun.com">Nandkumar Kesavan</a>
 * @see <a href="http://ant.apache.org/manual/CoreTasks/exec.html">Apache Exec Task</a>
 */
public class SpawnTask extends ExecTask implements Runnable{

    /**
     * Run the command in a new thread
     */
    public void execute() throws BuildException {

        //Instantiate a new thread and run the command in this thread.
        Thread taskRunner = new Thread(this);
        taskRunner.start();

    }

    public void run() {

        //Run the parent ExecTask in a separate thread
        super.execute();

    }

}
