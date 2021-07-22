/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.tools.ws.wscompile;

import com.sun.tools.ws.resources.WscompileMessages;

import javax.annotation.processing.Filer;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.tools.FileObject;

/**
 * Provide common jaxws tool options.
 *
 * @author Vivek Pandey
 */
public class Options {

    private static final String JAVAX = "javax.xml.bind";
    private static final String JAKARTA = "jakarta.xml.bind";
    private static final String JAXB_CORE = "org.glassfish.jaxb.core";
    private static final String BIND = "com.sun.xml.bind";

    protected final Map<String, String> classNameReplacer = new HashMap<>();

    /**
     * -verbose
     */
    public boolean verbose;

    /**
     * - quite
     */
    public boolean quiet;

    /**
     * -keep
     */
    public boolean keep;

    /**
     * -d
     */
    public File destDir = new File(".");

    /**
     * -s
     */
    public File sourceDir;

    /**
     * The filer that can use used to write out the generated files
     */
    public Filer filer;

    /**
     * -encoding
     */
    public String encoding;

    public String classpath = System.getProperty("java.class.path");

    public String wsgenExtraClasspath = System.getProperty("wsgen.extra.classpath");

    public String wsgenClasspath = System.getProperty("wsgen.classpath");

    /**
     * -javacOptions
     *
     * @since 2.2.9
     */
    public List<String> javacOptions;

    /**
     * -Xnocompile
     */
    public boolean nocompile;

    /**
     * If true XML security features when parsing XML documents will be disabled.
     * The default value is false.
     *
     * Boolean
     * @since 2.2.9
     */
    public boolean disableXmlSecurity;

    /**
     * -Xno-modules
     * If it is true, java compiler will not use modules during the compilation. All dependencies will be put to the classpath.
     */
    public boolean noModules;

    /**
     * -modulepath
     */
    public String modulepath;

    public enum Target {

        V2_0("2.0"), V2_1("2.1"), V2_2("2.2"), V2_3("2.3"), V3_0("3.0");

        private final String version;

        private Target(String version) {
            this.version = version;
        }

        /**
         * Returns true if this version is equal or later than the given one.
         */
        public boolean isLaterThan(Target t) {
            return this.ordinal() >= t.ordinal();
        }

        /**
         * Parses token into the {@link Target} object.
         *
         * @return null for parsing failure.
         */
        public static Target parse(String token) {
            for (Target target : Target.values()) {
                if (target.getVersion().equals(token)) {
                    return target;
                }
            }
            return null;
        }

        /**
         * Gives the String representation of the {@link Target}
         */
        public String getVersion(){
            return version;
        }

        public static Target getDefault() {
            return V3_0;
        }

        public static Target getLoadedAPIVersion() {
            return LOADED_API_VERSION;
        }

        private static final Target LOADED_API_VERSION = Target.V3_0;

    }

    public Target target = Target.V3_0;

    /**
     * Type of input schema language. One of the {@code SCHEMA_XXX}
     * strictly follow the compatibility rules specified in JAXWS spec
     */
    public static final int STRICT = 1;

    /**
     * loosely follow the compatibility rules and allow the use of vendor
     * binding extensions
     */
    public static final int EXTENSION = 2;

    /**
     * this switch determines how carefully the compiler will follow
     * the compatibility rules in the spec. Either {@code STRICT}
     * or {@code EXTENSION}.
     */
    public int compatibilityMode = STRICT;

    public boolean isExtensionMode() {
        return compatibilityMode == EXTENSION;
    }

    public boolean debug = false;

    /**
     * -Xdebug - gives complete stack trace
     */
    public boolean debugMode = false;


    private final List<File> generatedFiles = new ArrayList<File>();
    private ClassLoader classLoader;


    /**
     * Remember info on generated source file so that it
     * can be removed later, if appropriate.
     *
     * @param file generated File
     *
     * @deprecated Use {@link #addGeneratedFile(javax.tools.FileObject) } instead.
     */
    @Deprecated
    public void addGeneratedFile(File file) {
        generatedFiles.add(file);
    }

    /**
     * Remember info on generated file so that it can be removed later, if appropriate.
     *
     * @param fo generated FileObject
     */
    public void addGeneratedFile(FileObject fo) {
        generatedFiles.add(new File(fo.toUri()));
    }

    /**
     * Remove generated files
     */
    public void removeGeneratedFiles(){
        for(File file : generatedFiles){
            if (file.getName().endsWith(".java")) {
                boolean deleted = file.delete();
                if (verbose && !deleted) {
                    System.out.println(MessageFormat.format("{0} could not be deleted.", file));
                }
            }
        }
        generatedFiles.clear();
    }

    /**
     * Return all the generated files and its types.
     */
    public Iterable<File> getGeneratedFiles() {
        return generatedFiles;
    }

    /**
     * Delete all the generated source files made during the execution
     * of this environment (those that have been registered with the
     * "addGeneratedFile" method).
     */
    public void deleteGeneratedFiles() {
        synchronized (generatedFiles) {
            for (File file : generatedFiles) {
                if (file.getName().endsWith(".java")) {
                    boolean deleted = file.delete();
                    if (verbose && !deleted) {
                        System.out.println(MessageFormat.format("{0} could not be deleted.", file));
                    }
                }
            }
            generatedFiles.clear();
        }
    }

    /**
     * Parses arguments and fill fields of this object.
     *
     * @exception BadCommandLineException
     *      thrown when there's a problem in the command-line arguments
     */
    public void parseArguments( String[] args ) throws BadCommandLineException {

        for (int i = 0; i < args.length; i++) {
            if(args[i].length()==0)
                throw new BadCommandLineException();
            if (args[i].charAt(0) == '-') {
                int j = parseArguments(args,i);
                if(j==0)
                    throw new BadCommandLineException(WscompileMessages.WSCOMPILE_INVALID_OPTION(args[i]));
                i += (j-1);
            } else {
                addFile(args[i]);
            }
        }
        if(destDir == null)
            destDir = new File(".");
        if(sourceDir == null)
            sourceDir = destDir;
    }


    /**
     * Adds a file from the argume
     *
     * @param arg a file, could be a wsdl or xsd or a Class
     */
    protected void addFile(String arg) throws BadCommandLineException {}

    /**
     * Parses an option {@code args[i]} and return
     * the number of tokens consumed.
     *
     * @return
     *      0 if the argument is not understood. Returning 0
     *      will let the caller report an error.
     * @exception BadCommandLineException
     *      If the callee wants to provide a custom message for an error.
     */
    protected int parseArguments(String[] args, int i) throws BadCommandLineException {
        if (args[i].equals("-g")) {
            debug = true;
            return 1;
        } else if (args[i].equals("-Xdebug")) {
            debugMode = true;
            return 1;
        } else if (args[i].equals("-Xendorsed")) {
            // this option is processed much earlier, so just ignore.
            return 1;
        } else if (args[i].equals("-verbose")) {
            verbose = true;
            return 1;
        } else if (args[i].equals("-quiet")) {
            quiet = true;
            return 1;
        } else if (args[i].equals("-keep")) {
            keep = true;
            return 1;
        }  else if (args[i].equals("-target")) {
            String token = requireArgument("-target", args, ++i);
            target = Target.parse(token);
            if(target == null)
                throw new BadCommandLineException(WscompileMessages.WSIMPORT_ILLEGAL_TARGET_VERSION(token));
            addClassNameReplacers(target);
            return 2;
        } else if (args[i].equals("-classpath") || args[i].equals("-cp")) {
            classpath = requireArgument("-classpath", args, ++i) + File.pathSeparator + System.getProperty("java.class.path");
            return 2;
        } else if (args[i].equals("-d")) {
            destDir = new File(requireArgument("-d", args, ++i));
            if (!destDir.exists())
                throw new BadCommandLineException(WscompileMessages.WSCOMPILE_NO_SUCH_DIRECTORY(destDir.getPath()));
            return 2;
        } else if (args[i].equals("-s")) {
            sourceDir = new File(requireArgument("-s", args, ++i));
            keep = true;
            if (!sourceDir.exists()) {
                throw new BadCommandLineException(WscompileMessages.WSCOMPILE_NO_SUCH_DIRECTORY(sourceDir.getPath()));
            }
            return 2;
        } else if (args[i].equals("-extension")) {
            compatibilityMode = EXTENSION;
            return 1;
        } else if (args[i].startsWith("-help")) {
            WeAreDone done = new WeAreDone();
            done.initOptions(this);
            throw done;
        } else if (args[i].equals("-Xnocompile")) {
            // -nocompile implies -keep. this is undocumented switch.
            nocompile = true;
            keep = true;
            return 1;
        } else if (args[i].equals("-encoding")) {
            encoding = requireArgument("-encoding", args, ++i);
            try {
                if (!Charset.isSupported(encoding)) {
                    throw new BadCommandLineException(WscompileMessages.WSCOMPILE_UNSUPPORTED_ENCODING(encoding));
                }
            } catch (IllegalCharsetNameException icne) {
                throw new BadCommandLineException(WscompileMessages.WSCOMPILE_UNSUPPORTED_ENCODING(encoding));
            }
            return 2;
        } else if (args[i].equals("-disableXmlSecurity")) {
            disableXmlSecurity();
            return 1;
        } else if (args[i].startsWith("-J")) {
            if (javacOptions == null) {
                javacOptions = new ArrayList<String>();
            }
            javacOptions.add(args[i].substring(2));
            return 1;
        } else if (args[i].equals("-Xno-modules")) {
            noModules = true;
            return 1;
        } else if (args[i].equals("-modulepath")) {
                modulepath = requireArgument("-modulepath", args, ++i);
            return 2;
        }
        return 0;
    }

    private void addClassNameReplacers(Target target) {
        if (target.ordinal() < Target.V3_0.ordinal()) {
            classNameReplacer.put(JAKARTA, JAVAX);
            classNameReplacer.put(JAXB_CORE, BIND);
        }
    }

    // protected method to allow overriding
    protected void disableXmlSecurity() {
        disableXmlSecurity= true;
    }

    /**
     * Obtains an operand and reports an error if it's not there.
     */
    public String requireArgument(String optionName, String[] args, int i) throws BadCommandLineException {
        //if (i == args.length || args[i].startsWith("-")) {
        if (args[i].startsWith("-")) {
            throw new BadCommandLineException(WscompileMessages.WSCOMPILE_MISSING_OPTION_ARGUMENT(optionName));
        }
        return args[i];
    }

    public List<String> getJavacOptions(List<String> existingOptions, WsimportListener listener) {
        List<String> result = new ArrayList<String>();
        for (String o: javacOptions) {
            if (o.contains("=") && !o.startsWith("A")) {
                int i = o.indexOf('=');
                String key = o.substring(0, i);
                if (existingOptions.contains(key)) {
                    listener.message(WscompileMessages.WSCOMPILE_EXISTING_OPTION(key));
                } else {
                    result.add(key);
                    result.add(o.substring(i + 1));
                }
            } else {
                if (existingOptions.contains(o)) {
                    listener.message(WscompileMessages.WSCOMPILE_EXISTING_OPTION(o));
                } else {
                    result.add(o);
                }
            }
        }
        return result;
    }

    /**
     * Used to signal that we've finished processing.
     */
    public static final class WeAreDone extends BadCommandLineException {}

    /**
     * Get a URLClassLoader from using the classpath
     */
    public ClassLoader getClassLoader() {
        if (classLoader == null) {
            classLoader =
                new URLClassLoader(pathToURLs(classpath),
                    this.getClass().getClassLoader());
        }
        return classLoader;
    }

    /**
     * Utility method for converting a search path string to an array
     * of directory and JAR file URLs.
     *
     * @param path the search path string
     * @return the resulting array of directory and JAR file URLs
     */
    public static URL[] pathToURLs(String path) {
        StringTokenizer st = new StringTokenizer(path, File.pathSeparator);
        URL[] urls = new URL[st.countTokens()];
        int count = 0;
        while (st.hasMoreTokens()) {
            URL url = fileToURL(new File(st.nextToken()));
            if (url != null) {
                urls[count++] = url;
            }
        }
        if (urls.length != count) {
            URL[] tmp = new URL[count];
            System.arraycopy(urls, 0, tmp, 0, count);
            urls = tmp;
        }
        return urls;
    }

    /**
     * Returns the directory or JAR file URL corresponding to the specified
     * local file name.
     *
     * @param file the File object
     * @return the resulting directory or JAR file URL, or null if unknown
     */
    public static URL fileToURL(File file) {
        String name;
        try {
            name = file.getCanonicalPath();
        } catch (IOException e) {
            name = file.getAbsolutePath();
        }
        name = name.replace(File.separatorChar, '/');
        if (!name.startsWith("/")) {
            name = "/" + name;
        }

        // If the file does not exist, then assume that it's a directory
        if (!file.isFile()) {
            name = name + "/";
        }
        try {
            return new URL("file", "", name);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("file");
        }
    }

}
