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

package restful.server;

import jakarta.annotation.Resource;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.Provider;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.WebServiceProvider;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.http.HTTPBinding;
import jakarta.xml.ws.http.HTTPException;
import java.io.ByteArrayInputStream;
import java.util.StringTokenizer;

@WebServiceProvider
@BindingType(value=HTTPBinding.HTTP_BINDING)
public class AddNumbersImpl implements Provider<Source> {

    @Resource(type=Object.class)
    protected WebServiceContext wsContext;

    @Override
    public Source invoke(Source source) {
        try {
            MessageContext mc = wsContext.getMessageContext();
            String query = (String)mc.get(MessageContext.QUERY_STRING);
            String path = (String)mc.get(MessageContext.PATH_INFO);
            System.out.println("Query String = "+query);
            System.out.println("PathInfo = "+path);
            if (query != null && query.contains("num1=") &&
                query.contains("num2=")) {
                return createSource(query);
            } else if (path != null && path.contains("/num1") &&
                       path.contains("/num2")) {
                return createSource(path);
            } else {
                throw new HTTPException(404);
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new HTTPException(500);
        }
    }
    
    private Source createSource(String str) {
        StringTokenizer st = new StringTokenizer(str, "=&/");
        String token = st.nextToken();
        int number1 = Integer.parseInt(st.nextToken());
        st.nextToken();
        int number2 = Integer.parseInt(st.nextToken());
        int sum = number1+number2;
        String body =
            "<ns:addNumbersResponse xmlns:ns=\"http://duke.example.org\"><ns:return>"
            +sum
            +"</ns:return></ns:addNumbersResponse>";
        Source source = new StreamSource(
            new ByteArrayInputStream(body.getBytes()));
        return source;
    }
    
}
