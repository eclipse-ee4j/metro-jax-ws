/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import com.sun.xml.ws.api.Component;
import com.sun.xml.ws.api.ComponentEx;
import com.sun.xml.ws.api.server.ContainerResolver;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ServiceLoader;


/**
 * A simple service-provider lookup mechanism.  A <i>service</i> is a
 * well-known set of interfaces and (usually abstract) classes.  A <i>service
 * provider</i> is a specific implementation of a service.  The classes in a
 * provider typically implement the interfaces and subclass the classes defined
 * in the service itself.  Service providers may be installed in an
 * implementation of the Java platform in the form of extensions, that is, jar
 * files placed into any of the usual extension directories.  Providers may
 * also be made available by adding them to the applet or application class
 * path or by some other platform-specific means.
 * <br>
 * <p> In this lookup mechanism a service is represented by an interface or an
 * abstract class.  (A concrete class may be used, but this is not
 * recommended.)  A provider of a given service contains one or more concrete
 * classes that extend this <i>service class</i> with data and code specific to
 * the provider.  This <i>provider class</i> will typically not be the entire
 * provider itself but rather a proxy that contains enough information to
 * decide whether the provider is able to satisfy a particular request together
 * with code that can create the actual provider on demand.  The details of
 * provider classes tend to be highly service-specific; no single class or
 * interface could possibly unify them, so no such class has been defined.  The
 * only requirement enforced here is that provider classes must have a
 * zero-argument constructor so that they may be instantiated during lookup.
 * <br>
 * <p> A service provider identifies itself by placing a provider-configuration
 * file in the resource directory {@code META-INF/services}.  The file's name
 * should consist of the fully-qualified name of the abstract service class.
 * The file should contain a list of fully-qualified concrete provider-class
 * names, one per line.  Space and tab characters surrounding each name, as
 * well as blank lines, are ignored.  The comment character is {@code '#'}
 * ({@code 0x23}); on each line all characters following the first comment
 * character are ignored.  The file must be encoded in UTF-8.
 * <br>
 * <p> If a particular concrete provider class is named in more than one
 * configuration file, or is named in the same configuration file more than
 * once, then the duplicates will be ignored.  The configuration file naming a
 * particular provider need not be in the same jar file or other distribution
 * unit as the provider itself.  The provider must be accessible from the same
 * class loader that was initially queried to locate the configuration file;
 * note that this is not necessarily the class loader that found the file.
 * <br>
 * <p> <b>Example:</b> Suppose we have a service class named
 * {@code java.io.spi.CharCodec}.  It has two abstract methods:
 * <br>
 * <pre>
 *   public abstract CharEncoder getEncoder(String encodingName);
 *   public abstract CharDecoder getDecoder(String encodingName);
 * </pre>
 * <br>
 * Each method returns an appropriate object or {@code null} if it cannot
 * translate the given encoding.  Typical {@code CharCodec} providers will
 * support more than one encoding.
 * <br>
 * <p> If {@code sun.io.StandardCodec} is a provider of the {@code CharCodec}
 * service then its jar file would contain the file
 * {@code META-INF/services/java.io.spi.CharCodec}.  This file would contain
 * the single line:
 * <br>
 * <pre>
 *   sun.io.StandardCodec    # Standard codecs for the platform
 * </pre>
 * <br>
 * To locate an codec for a given encoding name, the internal I/O code would
 * do something like this:
 * <br>
 * <pre>
 *   CharEncoder getEncoder(String encodingName) {
 *       for( CharCodec cc : ServiceFinder.find(CharCodec.class) ) {
 *           CharEncoder ce = cc.getEncoder(encodingName);
 *           if (ce != null)
 *               return ce;
 *       }
 *       return null;
 *   }
 * </pre>
 * <br>
 * The provider-lookup mechanism always executes in the security context of the
 * caller.  Trusted system code should typically invoke the methods in this
 * class from within a privileged security context.
 *
 * @author Mark Reinhold
 * @version 1.11, 03/12/19
 * @param <T> The type of the service to be loaded by this finder
 * @since 1.3
 */
public final class ServiceFinder<T> implements Iterable<T> {

    private final @NotNull Class<T> serviceClass;
    private final @NotNull ServiceLoader<T> serviceLoader;
    private final @Nullable ComponentEx component;

    public static <T> ServiceFinder<T> find(@NotNull Class<T> service, @Nullable ClassLoader loader, Component component) {
        ClassLoader cl = loader == null ? Thread.currentThread().getContextClassLoader() : loader;
        return find(service, component, ServiceLoader.load(service, cl));
    }

    public static <T> ServiceFinder<T> find(@NotNull Class<T> service, Component component, @NotNull ServiceLoader<T> serviceLoader) {
        Class<T> svc = Objects.requireNonNull(service);
        ServiceLoader<T> sl = Objects.requireNonNull(serviceLoader);
        return new ServiceFinder<>(svc, component, sl);
    }

    public static <T> ServiceFinder<T> find(@NotNull Class<T> service, Component component) {
        return find(service, component, ServiceLoader.load(service, Thread.currentThread().getContextClassLoader()));
    }

    /**
     * Locates and incrementally instantiates the available providers of a
     * given service using the given class loader.
     * <br>
     * <p> This method transforms the name of the given service class into a
     * provider-configuration filename as described above and then uses the
     * {@code getResources} method of the given class loader to find all
     * available files with that name.  These files are then read and parsed to
     * produce a list of provider-class names.  The iterator that is returned
     * uses the given class loader to lookup and then instantiate each element
     * of the list.
     * <br>
     * <p> Because it is possible for extensions to be installed into a running
     * Java virtual machine, this method may return different results each time
     * it is invoked. <p>
     *
     * @param <T> The type of the service to be loaded by this finder
     * @param service The service's abstract service class
     * @param loader  The class loader to be used to load provider-configuration files
     *                and instantiate provider classes, or {@code null} if the system
     *                class loader (or, failing that the bootstrap class loader) is to
     *                be used
     * @return A new service finder
     * @throws ServiceConfigurationError If a provider-configuration file violates the specified format
     *                                   or names a provider class that cannot be found and instantiated
     * @see #find(Class)
     */
    public static <T> ServiceFinder<T> find(@NotNull Class<T> service, @Nullable ClassLoader loader) {
        return find(service, loader, ContainerResolver.getInstance().getContainer());
    }

    /**
     * Locates and incrementally instantiates the available providers of a
     * given service using the context class loader.  This convenience method
     * is equivalent to
     * <br>
     * <pre>
     *   ClassLoader cl = Thread.currentThread().getContextClassLoader();
     *   return Service.providers(service, cl);
     * </pre>
     *
     * @param <T> The type of the service to be loaded by this finder
     * @param service The service's abstract service class
     * @return A new service finder
     *
     * @throws ServiceConfigurationError If a provider-configuration file violates the specified format
     *                                   or names a provider class that cannot be found and instantiated
     * @see #find(Class, ClassLoader)
     */
    public static <T> ServiceFinder<T> find(@NotNull Class<T> service) {
        return find(service, ServiceLoader.load(service, Thread.currentThread().getContextClassLoader()));
    }

    public static <T> ServiceFinder<T> find(@NotNull Class<T> service, @NotNull ServiceLoader<T> serviceLoader) {
        return find(service, ContainerResolver.getInstance().getContainer(), serviceLoader);
    }

    private ServiceFinder(Class<T> service, Component component, ServiceLoader<T> serviceLoader) {
        this.serviceClass = service;
        this.component = getComponentEx(component);
        this.serviceLoader = serviceLoader;
    }

    /**
     * Returns discovered objects incrementally.
     *
     * @return An {@code Iterator} that yields provider objects for the given
     *         service, in some arbitrary order.  The iterator will throw a
     *         {@code ServiceConfigurationError} if a provider-configuration
     *         file violates the specified format or if a provider class cannot
     *         be found and instantiated.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        Iterator<T> it = serviceLoader.iterator();
        return component != null
                ? new CompositeIterator<>(component.getIterableSPI(serviceClass).iterator(), it)
                : it;
    }

    /**
     * Returns discovered objects all at once.
     *
     * @return
     *      can be empty but never null.
     *
     * @throws ServiceConfigurationError
     */
    public T[] toArray() {
        List<T> result = new ArrayList<>();
        for (T t : this) {
            result.add(t);
        }
        return result.toArray((T[]) Array.newInstance(serviceClass, result.size()));
    }

    private static ComponentEx getComponentEx(Component component) {
        if (component instanceof ComponentEx) {
            return (ComponentEx) component;
        }

        return component != null ? new ComponentExWrapper(component) : null;
    }

    private static class ComponentExWrapper implements ComponentEx {

        private final Component component;

        public ComponentExWrapper(Component component) {
            this.component = component;
        }

        @Override
        public <S> S getSPI(Class<S> spiType) {
            return component.getSPI(spiType);
        }

        @Override
        public <S> Iterable<S> getIterableSPI(Class<S> spiType) {
            S item = getSPI(spiType);
            if (item != null) {
                Collection<S> c = Collections.singletonList(item);
                return c;
            }
            return Collections.emptySet();
        }
    }

    private static class CompositeIterator<T> implements Iterator<T> {

        private final Iterator<Iterator<T>> it;
        private Iterator<T> current = null;

        public CompositeIterator(Iterator<T>... iterators) {
            it = Arrays.asList(iterators).iterator();
        }

        @Override
        public boolean hasNext() {
            if (current != null && current.hasNext()) {
                return true;
            }

            while (it.hasNext()) {
                current = it.next();
                if (current.hasNext()) {
                    return true;
                }

            }

            return false;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return current.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
