/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.db.toplink;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Object pool allocator that leverages a {@code ConcurrentLinkedQueue} for
 * synchronization.
 * 
 * @param <T>
 *            the type of the object to pool
 */
public abstract class ObjectPool<T> {

	private volatile SoftReference<ConcurrentLinkedQueue<T>> queueRef;

	/**
	 * Allocate an object from the pool or create a new one if we cannot get one
	 * from the queue.
	 * 
	 * @return the queued or newly-created object
	 */
	public final T allocate() {
		T value = derefQueue().poll();
		return (value != null ? value : newInstance());
	}

	/**
	 * Return an object to the pool.
	 * 
	 * @param value
	 *            the object being returned
	 */
	public final void replace(T value) {
		derefQueue().offer(value);
	}

	/**
	 * Subclasses must override the object creation method.
	 * 
	 * @return a new instance of the object.
	 */
	protected abstract T newInstance();

	private final ConcurrentLinkedQueue<T> derefQueue() {
		ConcurrentLinkedQueue<T> q;

		// Only enter sync block if queue not allocated or soft reference
		// to it is cleared.
		if (queueRef == null || (q = queueRef.get()) == null) {
			synchronized (this) {
				if (queueRef == null || (q = queueRef.get()) == null) {
					q = new ConcurrentLinkedQueue<>();
					queueRef = new SoftReference<>(q);
				}
			}
		}
		return q;
	}
}
