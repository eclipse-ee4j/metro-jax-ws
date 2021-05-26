/*
 * Copyright (c) 2021 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.eclipse.metro.helidon;

import com.oracle.webservices.api.message.BasePropertySet;
import com.oracle.webservices.api.message.PropertySet;
import com.sun.xml.ws.api.message.Packet;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author lukas
 */
final class ConnectionProperties extends BasePropertySet {

    private static final PropertyMap model;
    private ServerRequest req;
    private ServerResponse res;

    static {
        model = parse(ConnectionProperties.class, MethodHandles.lookup());
    }

    ConnectionProperties(ServerRequest rq, ServerResponse rs) {
        this.req = rq;
        this.res = rs;
    }

    @Override
    protected PropertyMap getPropertyMap() {
        return model;
    }

    @PropertySet.Property(MessageContext.HTTP_REQUEST_METHOD)
    public String getRequestMethod() {
        return req.method().name();
    }

    @PropertySet.Property({MessageContext.HTTP_REQUEST_HEADERS, Packet.INBOUND_TRANSPORT_HEADERS})
    public Map<String, List<String>> getRequestHeaders() {
        return req.headers().toMap();
    }

    @PropertySet.Property(MessageContext.QUERY_STRING)
    public String getQueryString() {
        return req.query();
    }

    @PropertySet.Property(MessageContext.PATH_INFO)
    public String getPathInfo() {
        return req.path().param("pathinfo");
    }

    @PropertySet.Property(MessageContext.HTTP_RESPONSE_CODE)
    public int getStatus() {
        return res.status().code();
    }

    @Property({MessageContext.HTTP_RESPONSE_HEADERS, Packet.OUTBOUND_TRANSPORT_HEADERS})
    public Map<String, List<String>> getResponseHeaders() {
        return res.headers().toMap();
    }

}
