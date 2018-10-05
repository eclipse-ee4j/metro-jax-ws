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

/**
 * Used as a holder for information about an http response. This
 * class is used by ClientServerTestUtil to return information
 * after making a manual request to an endpoint.
 */
public class HTTPResponseInfo {

    int responseCode;
    String responseMessage;
    String responseBody;
    
    public HTTPResponseInfo(int responseCode,
        String responseMessage, 
        String responseBody) {
            
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.responseBody = responseBody;
    }
    
    /**
     * Get the response code from the http request. It returns the
     * value of getResponseCode() called on the http connection object.
     *
     * @return The integer response code
     */
    public int getResponseCode() {
        return responseCode;
    }
    
    /**
     * Get the http response message, such as "OK". This comes from
     * getResponseMessage() called on the http connection object.
     */
    public String getResponseMessage() {
        return responseMessage;
    }
    
    /**
     * Get the actual response (the body) of the http call. This
     * String comes from the input stream or error stream of the
     * http connection object.
     *
     * @return The response from the call.
     */
    public String getResponseBody() {
        return responseBody;
    }
    
}
