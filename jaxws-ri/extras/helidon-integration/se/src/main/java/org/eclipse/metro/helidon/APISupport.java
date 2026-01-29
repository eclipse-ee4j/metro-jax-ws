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

import com.oracle.webservices.api.message.PropertySet;
import io.helidon.common.serviceloader.HelidonServiceLoader;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import java.util.Iterator;
import java.util.ServiceLoader;
import jakarta.xml.ws.WebServiceException;

/**
 *
 * @author lukas
 */
final class APISupport {

    private APISupport() {

    }

    static PropertySet createPropertySet(ServerRequest req, ServerResponse res) {
        return INSTANCE.createPropertySet(req, res);
    }

    static RuntimeException createException(String msg) {
        return INSTANCE.createException(msg);
    }

    static RuntimeException createException(String msg, Throwable t) {
        return INSTANCE.createException(msg, t);
    }

    private static final APISupportImpl INSTANCE = create();

    private static APISupportImpl create() {
        Iterator<APISupportImpl> loader = HelidonServiceLoader.create(ServiceLoader.<APISupportImpl>load(APISupportImpl.class)).iterator();
        if (loader.hasNext()) {
            return loader.next();
        } else {
            return new APISupportImpl() {

                @Override
                public PropertySet createPropertySet(ServerRequest req, ServerResponse res) {
                    return new ConnectionProperties(req, res);
                }

                @Override
                public RuntimeException createException(String msg) {
                    return new WebServiceException(msg);
                }

                @Override
                public RuntimeException createException(String msg, Throwable t) {
                    return new WebServiceException(msg, t);
                }
            };
        }
    }
}
