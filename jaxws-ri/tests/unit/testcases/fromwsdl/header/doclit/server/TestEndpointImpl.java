/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.header.doclit.server;

/**
 * @author Vivek Pandey
 */
import jakarta.xml.ws.Holder;

@jakarta.jws.WebService(endpointInterface="fromwsdl.header.doclit.server.HelloPortType")
public class TestEndpointImpl implements HelloPortType {
    public EchoResponseType echo(EchoType reqBody,
                                              EchoType reqHeader,
                                              Echo2Type req2Header) {
        EchoResponseType response = new EchoResponseType();
        response.setRespInfo(reqBody.getReqInfo() + reqHeader.getReqInfo() + req2Header.getReqInfo());
        return response;
    }

    public String echo2(String info) {
        return info+"bar";
    }

    public void echo3(jakarta.xml.ws.Holder<java.lang.String> reqInfo) {
        reqInfo.value += "bar";
    }

    public void echo4(Echo4Type reqBody,
                                     Echo4Type reqHeader,
                                     String req2Header,
                                     Holder<String> respBody,
                                     Holder<String> respHeader) {
                 try {
            String response = reqBody.getExtra() +
                              reqBody.getArgument() +
                              reqHeader.getExtra() +
                              reqHeader.getArgument() +
                              req2Header;

            respBody.value = response;
            respHeader.value = req2Header;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String echo5(EchoType body) {
        return body.getReqInfo();
    }

    public String echo6(Holder<String> name, EchoType header, Holder<EchoType> body) {
        System.out.println("Name: "+ name.value);
        System.out.println("Body: "+ body.value.getReqInfo());
        name.value = body.value.getReqInfo();
        body.value.setReqInfo(name.value +"'s Response"); ;
        return name.value + "'s Address: "+ header.getReqInfo();
    }

    public NameType echo7(Holder<String> address, Holder<String> personDetails, java.lang.String lastName, java.lang.String firstName) {
        try{
        address.value = "Sun Micro Address";
        personDetails.value = firstName + " "+ lastName;
        NameType nameType = new NameType();
        nameType.setName("Employee");
        return nameType;
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

}
