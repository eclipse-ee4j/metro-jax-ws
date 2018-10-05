/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromjava.jaxws943.client;

import fromjava.jaxws943.client.book.*;

import junit.framework.TestCase;

import java.util.logging.Logger;

/**
 * Stateful web service client demonstration.
 */
public class StatefulInstanceTimeoutTest extends TestCase {

    private static final Logger logger = Logger.getLogger(StatefulInstanceTimeoutTest.class.getName());

    public void testIt() throws InterruptedException {
        BookStore bookstore = new BookStoreService().getBookStorePort();
        BookService service = new BookService();

        // obtain references to two differentbooks and add to the different nuber of reviews:
        // abc001 - 10 reviews
        // abc002 - 5 reviews
        Book book;
        book = service.getPort(bookstore.getProduct("abc001"),Book.class);

        for(int i=0; i < 10; i++) {

            // timeout (timeout defined as 5secs)
            // - if the first callback isn't canceled (bug JAXWS_943), it will fail sometimes during this loop
            logger.finest("waiting 1 sec to reproduce timeout in case the very first callback invoked ...");
            Thread.sleep(1000L);

            book = service.getPort(bookstore.getProduct("abc001"),Book.class);
            book.postReview("review #" + i);

            if (i % 2 == 0) {
                book = service.getPort(bookstore.getProduct("abc002"),Book.class);
                book.postReview("review #" + i);
            }
        }

        // assert all the reviews are in the place:
        book = service.getPort(bookstore.getProduct("abc001"),Book.class);
        assertTrue(book.getReviews().size() == 10);
        logger.finest("book1 reviews = " + book.getReviews());

        book = service.getPort(bookstore.getProduct("abc002"),Book.class);
        assertTrue(book.getReviews().size() == 5);
        logger.finest("book2 reviews = " + book.getReviews());

        // timeout (timeout defined as 5secs)
        logger.finest("waiting to reproduce timeout ...");
        Thread.sleep(8000L);

        // after timeout we should get brand new books (old are trashed)
        book = service.getPort(bookstore.getProduct("abc001"),Book.class);
        assertTrue(book.getReviews().size() == 0);
        logger.finest("book1 reviews = " + book.getReviews());

        book = service.getPort(bookstore.getProduct("abc002"),Book.class);
        assertTrue(book.getReviews().size() == 0);
        logger.finest("book2 reviews = " + book.getReviews());
    }
}
