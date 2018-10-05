/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.nosei.server;


public final class GenericValue<T> {

  /**
   * The value contained in the holder.
   **/
  public T value;
    
  /**
   * Creates a new holder with a <code>null</code> value.
   **/
  public GenericValue() {
  }

  /**
   * Create a new holder with the specified value.
   *
   * @param value The value to be stored in the holder.
   **/
  public GenericValue(T value) {
      this.value = value;
  }
}
