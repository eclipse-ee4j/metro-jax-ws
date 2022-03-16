/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.policy.privateutil;

import com.sun.xml.ws.policy.PolicyException;
import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.logging.Level;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * This is a wrapper class for various utilities that may be reused within Policy API implementation.
 * The class is not part of public Policy API. Do not use it from your client code!
 *
 * @author Marek Potociar
 */
public final class PolicyUtils {
    private PolicyUtils() { }

    public static class Commons {
        /**
         * Method returns the name of the method that is on the {@code methodIndexInStack}
         * position in the call stack of the current {@link Thread}.
         *
         * @param methodIndexInStack index to the call stack to get the method name for.
         * @return the name of the method that is on the {@code methodIndexInStack}
         *         position in the call stack of the current {@link Thread}.
         */
        public static String getStackMethodName(final int methodIndexInStack) {
            final Optional<String> method = StackWalker.getInstance().walk(frames ->
                            frames.map(StackWalker.StackFrame::getMethodName)
                                  .skip(methodIndexInStack)
                                  .findFirst());
            return method.orElse("UNKNOWN METHOD");
        }

        /**
         * Function returns the name of the caller method for the method executing this
         * function.
         *
         * @return caller method name from the call stack of the current {@link Thread}.
         */
        public static String getCallerMethodName() {
            String result = getStackMethodName(3);
            return result;
        }
    }

    public static class IO {
        private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyUtils.IO.class);

        /**
         * If the {@code resource} is not {@code null}, this method will try to close the
         * {@code resource} instance and log warning about any unexpected
         * {@link IOException} that may occur.
         *
         * @param resource resource to be closed
         */
        public static void closeResource(Closeable resource) {
            if (resource != null) {
                try {
                    resource.close();
                } catch (IOException e) {
                    LOGGER.warning(LocalizationMessages.WSP_0023_UNEXPECTED_ERROR_WHILE_CLOSING_RESOURCE(resource.toString()), e);
                }
            }
        }

        /**
         * If the {@code reader} is not {@code null}, this method will try to close the
         * {@code reader} instance and log warning about any unexpected
         * {@link IOException} that may occur.
         *
         * @param reader resource to be closed
         */
        public static void closeResource(XMLStreamReader reader) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (XMLStreamException e) {
                    LOGGER.warning(LocalizationMessages.WSP_0023_UNEXPECTED_ERROR_WHILE_CLOSING_RESOURCE(reader.toString()), e);
                }
            }
        }
    }

    /**
     * Text utilities wrapper.
     */
    public static class Text {
        /**
         * System-specific line separator character retrieved from the Java system property
         * <code>line.separator</code>
         */
        public final static String NEW_LINE = System.getProperty("line.separator");

        /**
         * Method creates indent string consisting of as many {@code TAB} characters as specified by {@code indentLevel} parameter
         *
         * @param indentLevel indentation level
         * @return indentation string as specified by indentation level
         *
         */
        public static String createIndent(final int indentLevel) {
            final char[] charData = new char[indentLevel * 4];
            Arrays.fill(charData, ' ');
            return String.valueOf(charData);
        }
    }

    public static class Comparison {
        /**
         * The comparator comapres QName objects according to their publicly accessible attributes, in the following
         * order of attributes:
         *
         * 1. namespace (not null String)
         * 2. local name (not null String)
         */
        public static final Comparator<QName> QNAME_COMPARATOR = new Comparator<>() {
            public int compare(final QName qn1, final QName qn2) {
                if (qn1 == qn2 || qn1.equals(qn2)) {
                    return 0;
                }

                int result;

                result = qn1.getNamespaceURI().compareTo(qn2.getNamespaceURI());
                if (result != 0) {
                    return result;
                }

                return qn1.getLocalPart().compareTo(qn2.getLocalPart());
            }
        };

        /**
         * Compares two boolean values in the following way: {@code false < true}
         *
         * @return {@code -1} if {@code b1 < b2}, {@code 0} if {@code b1 == b2}, {@code 1} if {@code b1 > b2}
         */
        public static int compareBoolean(final boolean b1, final boolean b2) {
            final int i1 = (b1) ? 1 : 0;
            final int i2 = (b2) ? 1 : 0;

            return i1 - i2;
        }

        /**
         * Compares two String values, that may possibly be null in the following way: {@code null < "string value"}
         *
         * @return {@code -1} if {@code s1 < s2}, {@code 0} if {@code s1 == s2}, {@code 1} if {@code s1 > s2}
         */
        public static int compareNullableStrings(final String s1, final String s2) {
            return ((s1 == null) ? ((s2 == null) ? 0 : -1) : ((s2 == null) ? 1 : s1.compareTo(s2)));
        }
    }

    public static class Collections {
        private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyUtils.Collections.class);
        /**
         * TODO javadocs
         *
         * @param initialBase the combination base that will be present in each combination. May be {@code null} or empty.
         * @param options options that should be combined. May be {@code null} or empty.
         * @param ignoreEmptyOption flag identifies whether empty options should be ignored or whether the method should halt
         *        processing and return {@code null} when an empty option is encountered
         * @return TODO
         */
        public static <E, T extends Collection<? extends E>, U extends Collection<? extends E>> Collection<Collection<E>> combine(final U initialBase, final Collection<T> options, final boolean ignoreEmptyOption) {
            List<Collection<E>> combinations = null;
            if (options == null || options.isEmpty()) {
                // no combination creation needed
                if (initialBase != null) {
                    combinations = new ArrayList<>(1);
                    combinations.add(new ArrayList<>(initialBase));
                }
                return combinations;
            }

            // creating defensive and modifiable copy of the base
            final Collection<E> base = new LinkedList<>();
            if (initialBase != null && !initialBase.isEmpty()) {
                base.addAll(initialBase);
            }
            /*
              now we iterate over all options and build up an option processing queue:
                1. if ignoreEmptyOption flag is not set and we found an empty option, we are going to stop processing and return null. Otherwise we
                   ignore the empty option.
                2. if the option has one child only, we add the child directly to the base.
                3. if there are more children in examined node, we add it to the queue for further processing and precoumpute the final size of
                   resulting collection of combinations.
             */
            int finalCombinationsSize = 1;
            final Queue<T> optionProcessingQueue = new LinkedList<>();
            for (T option : options) {
                final int optionSize =  option.size();

                if (optionSize == 0) {
                    if (!ignoreEmptyOption) {
                        return null;
                    }
                } else if (optionSize == 1) {
                    base.addAll(option);
                } else {
                    boolean entered = optionProcessingQueue.offer(option);
                    if (!entered) {
                        throw LOGGER.logException(new RuntimePolicyUtilsException(LocalizationMessages.WSP_0096_ERROR_WHILE_COMBINE(option)), false, Level.WARNING);
                    }
                    finalCombinationsSize *= optionSize;
                }
            }

            // creating final combinations
            combinations = new ArrayList<>(finalCombinationsSize);
            combinations.add(base);
            if (finalCombinationsSize > 1) {
                T processedOption;
                while ((processedOption = optionProcessingQueue.poll()) != null) {
                    final int actualSemiCombinationCollectionSize = combinations.size();
                    final int newSemiCombinationCollectionSize = actualSemiCombinationCollectionSize * processedOption.size();

                    int semiCombinationIndex = 0;
                    for (E optionElement : processedOption) {
                        for (int i = 0; i < actualSemiCombinationCollectionSize; i++) {
                            final Collection<E> semiCombination = combinations.get(semiCombinationIndex); // unfinished combination

                            if (semiCombinationIndex + actualSemiCombinationCollectionSize < newSemiCombinationCollectionSize) {
                                // this is not the last optionElement => we create a new combination copy for the next child
                                combinations.add(new LinkedList<>(semiCombination));
                            }

                            semiCombination.add(optionElement);
                            semiCombinationIndex++;
                        }
                    }
                }
            }
            return combinations;
        }
    }

    /**
     * Reflection utilities wrapper
     */
    static class Reflection {
        private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyUtils.Reflection.class);

        /**
         * Reflectively invokes specified method on the specified target
         */
        static <T> T invoke(final Object target, final String methodName,
                final Class<T> resultClass, final Object... parameters) throws RuntimePolicyUtilsException {
            Class[] parameterTypes;
            if (parameters != null && parameters.length > 0) {
                parameterTypes = new Class[parameters.length];
                int i = 0;
                for (Object parameter : parameters) {
                    parameterTypes[i++] = parameter.getClass();
                }
            } else {
                parameterTypes = null;
            }

            return invoke(target, methodName, resultClass, parameters, parameterTypes);
        }

        /**
         * Reflectively invokes specified method on the specified target
         */
        public static <T> T invoke(final Object target, final String methodName, final Class<T> resultClass,
                final Object[] parameters, final Class[] parameterTypes) throws RuntimePolicyUtilsException {
            try {
                final Method method = target.getClass().getMethod(methodName, parameterTypes);
                final Object result = MethodUtil.invoke(target, method,parameters);

                return resultClass.cast(result);
            } catch (IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                throw LOGGER.logSevereException(new RuntimePolicyUtilsException(createExceptionMessage(target, parameters, methodName), e));
            } catch (IllegalAccessException e) {
                throw LOGGER.logSevereException(new RuntimePolicyUtilsException(createExceptionMessage(target, parameters, methodName), e.getCause()));
            }
        }

        private static String createExceptionMessage(final Object target, final Object[] parameters, final String methodName) {
            return LocalizationMessages.WSP_0061_METHOD_INVOCATION_FAILED(target.getClass().getName(), methodName,
                    parameters == null ? null : Arrays.asList(parameters).toString());
        }
    }

    public static class ConfigFile {
        /**
         * Generates a config file resource name from provided config file identifier.
         * The generated file name can be transformed into a URL instance using
         * {@link #loadFromContext(String, Object)} or {@link #loadFromClasspath(String)}
         * method.
         *
         * @param configFileIdentifier the string used to generate the config file URL that will be parsed. Each WSIT config
         *        file is in form of <code>wsit-<i>{configFileIdentifier}</i>.xml</code>. Must not be {@code null}.
         * @return generated config file resource name
         * @throws PolicyException If configFileIdentifier is null.
         */
        public static String generateFullName(final String configFileIdentifier) throws PolicyException {
            if (configFileIdentifier != null) {
                final StringBuilder buffer = new StringBuilder("wsit-");
                buffer.append(configFileIdentifier).append(".xml");
                return buffer.toString();
            } else {
                throw new PolicyException(LocalizationMessages.WSP_0080_IMPLEMENTATION_EXPECTED_NOT_NULL());
            }
        }

        /**
         * Returns a URL pointing to the given config file. The file name is
         * looked up as a resource from a ServletContext.
         *
         * May return null if the file can not be found.
         *
         * @param configFileName The name of the file resource
         * @param context A ServletContext object. May not be null.
         */
        public static URL loadFromContext(final String configFileName, final Object context) {
            return Reflection.invoke(context, "getResource", URL.class, configFileName);
        }

        /**
         * Returns a URL pointing to the given config file. The file is looked up as
         * a resource on the classpath.
         *
         * May return null if the file can not be found.
         *
         * @param configFileName the name of the file resource. May not be {@code null}.
         */
        public static URL loadFromClasspath(final String configFileName) {
            final ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl == null) {
                return ClassLoader.getSystemResource(configFileName);
            } else {
                return cl.getResource(configFileName);
            }
        }
    }

    public static class Rfc2396 {

        private static final PolicyLogger LOGGER = PolicyLogger.getLogger(PolicyUtils.Reflection.class);

        // converts "hello%20world" into "hello world"
        public static String unquote(final String quoted) {
            if (null == quoted) {
                return null;
            }
            final byte[] unquoted = new byte[quoted.length()]; // result cannot be longer than original string
            int newLength = 0;
            char c;
            int hi, lo;
            for (int i=0; i < quoted.length(); i++) {    // iterarate over all chars in the input
                c = quoted.charAt(i);
                if ('%' == c) {                         // next escape sequence found
                    if ((i + 2) >= quoted.length()) {
                        throw LOGGER.logSevereException(new RuntimePolicyUtilsException(LocalizationMessages.WSP_0079_ERROR_WHILE_RFC_2396_UNESCAPING(quoted)), false);
                    }
                    hi = Character.digit(quoted.charAt(++i), 16);
                    lo = Character.digit(quoted.charAt(++i), 16);
                    if ((0 > hi) || (0 > lo)) {
                        throw LOGGER.logSevereException(new RuntimePolicyUtilsException(LocalizationMessages.WSP_0079_ERROR_WHILE_RFC_2396_UNESCAPING(quoted)), false);
                    }
                    unquoted[newLength++] = (byte) (hi * 16 + lo);
                } else { // regular character found
                    unquoted[newLength++] = (byte) c;
                }
            }
            return new String(unquoted, 0, newLength, StandardCharsets.UTF_8);
        }
    }
}
