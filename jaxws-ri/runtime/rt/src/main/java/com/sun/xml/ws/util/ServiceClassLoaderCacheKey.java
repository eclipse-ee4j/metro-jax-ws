package com.sun.xml.ws.util;

import java.util.Objects;

public class ServiceClassLoaderCacheKey<T> {
  private final Class<T> service;
  private final ClassLoader loader;

  public ServiceClassLoaderCacheKey(Class<T> service, ClassLoader loader) {
    this.service = service;
    this.loader = loader;
  }

  public Class<T> getService() {
    return service;
  }

  public ClassLoader getLoader() {
    return loader;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ServiceClassLoaderCacheKey)) return false;
    ServiceClassLoaderCacheKey<?> that = (ServiceClassLoaderCacheKey<?>) o;
    return Objects.equals(service, that.service) && Objects.equals(loader, that.loader);
  }

  @Override
  public int hashCode() {
    return Objects.hash(service, loader);
  }
}
