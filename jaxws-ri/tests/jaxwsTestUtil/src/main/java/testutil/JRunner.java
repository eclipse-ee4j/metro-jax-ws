/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testutil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

/**
 * @author JAX-WSA SI Development Team
 */
public class JRunner extends Task {

    /** whether to run the tests in local or HTTP mode */
    private boolean local;

    public boolean getLocal() {
        return this.local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    /** list of targets to invoke */
    private String targets;

    public String getTargets() {
        return targets;
    }

    public void setTargets(String targets) {
        this.targets = targets;
    }
    
    /** Content negotiation for FI */
    private String contentNegotiation;

    public String getContentNegotiation() {
        return contentNegotiation;
    }

    public void setContentNegotiation(String contentNegotiation) {
        this.contentNegotiation = contentNegotiation;
    }

    /** verbose option */
    protected boolean verbose = false;

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    /** failonerror option */
    protected boolean failonerror = false;

    public boolean isFailonerror() {
        return failonerror;
    }

    public void setFailonerror(boolean failonerror) {
        this.failonerror = failonerror;
    }

    protected List<FileSet> buildFileFileset = new ArrayList<FileSet>();

    public void addConfiguredBuildFile(FileSet fileset) {
        buildFileFileset.add(fileset);
    }

    void prepareBuildFileList(Set<File> buildFileFiles) throws BuildException {
        if (buildFileFileset != null) {
            for (FileSet fileset : buildFileFileset) {
                DirectoryScanner ds = fileset.getDirectoryScanner(getProject());
                String[] includedFiles = ds.getIncludedFiles();
                File baseDir = ds.getBasedir();
                for (int i = 0; i < includedFiles.length; ++i) {
                    buildFileFiles.add(new File(baseDir, includedFiles[i]));
                }
            }
        }
    }

    /** Called by the project to let the task do it's work * */
    public void execute() throws BuildException {
        Set<File> buildFileFiles = new HashSet<File>();
        prepareBuildFileList(buildFileFiles);

        int count = 0;
        for (File buildFile : buildFileFiles) {
            log(++count + "th build file: " + buildFile.getAbsolutePath());

            // initialize the project
            Project project = new Project();
            initializeProject(project, buildFile);

            project.setProperty("uselocal", String.valueOf(getLocal()));
            
            if (contentNegotiation != null && contentNegotiation.length() > 0) {
                project.setProperty("contentNegotiation", getContentNegotiation());
            }

            // parse the list of tokens
            StringTokenizer tokens = new StringTokenizer(getTargets(), ",");
            while (tokens.hasMoreTokens()) {
                String target = tokens.nextToken().trim();

                if (verbose) {
                    log("Invoking ... " + target);
                }


                // shoot
                try {
                    project.executeTarget(target);
                } catch (BuildException ex) {
                    if (failonerror)
                        throw ex;
                }
            }
        }
    }
    
    private void initializeProject(Project project, File buildFile) {
        project.init();
        
        DefaultLogger consoleLogger = new DefaultLogger();
        consoleLogger.setErrorPrintStream(System.err);
        consoleLogger.setOutputPrintStream(System.out);
        consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
        project.addBuildListener(consoleLogger);
        
        project.setUserProperty("ant.file", buildFile.getAbsolutePath());
        ProjectHelper projectHelper = ProjectHelper.getProjectHelper();
        projectHelper.parse(project, buildFile);        
    }
}
