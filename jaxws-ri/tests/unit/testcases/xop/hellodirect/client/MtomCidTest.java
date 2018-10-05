/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package xop.hellodirect.client;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPBinding;
import junit.framework.TestCase;

/**
 * If the CID contains special characters, the trunk was having an exception and
 * users raised an issue. See issue 23 at
 * https://jax-ws.dev.java.net/issues/show_bug.cgi?id=23 As per RFC 822. The
 * content-id mime header and also cid reference needs to be URL escaped, for
 * example the rfc doesn't allow special characters such as '/', ':' etc. and
 * mandates escaping of such characters. So our encoder for the outgoing message
 * sends out encoded content-id which has escaped characters. This is correct
 * behavior. However on the server side we try to decode them and it
 * shouldnt.<br>
 * For this test the CID is hard coded to
 * 6c88999f-35ff-403b-83a4-afd86c2a5ac6@urn%3Aws.ssa.gov%2Fewr%2Fv1%2FWageFileServices
 * <br>
 * which is the escaped version of
 * 6c88999f-35ff-403b-83a4-afd86c2a5ac6@urn:ws.ssa.gov/ewr/v1/WageFileServices
 *
 * See CR 6456442 and java.net issue 23
 */
public class MtomCidTest extends TestCase {
    private static String address;
    private static final String escapedCID = "6c88999f-35ff-403b-83a4-afd86c2a5ac6@urn%3Aws.ssa.gov%2Fewr%2Fv1%2FWageFileServices";
    private static final String noescapeCID = "6c88999f-35ff-403b-83a4-afd86c2a5ac6@urn:ws.ssa.gov/ewr/v1/WageFileServices";

    private static Hello port;

    public MtomCidTest(String name) throws Exception {
        super(name);
    }
    
    @Override
    public void setUp() throws Exception {
        HelloService helloService = new HelloService();
        Object obj = helloService.getHelloPort();
        assertTrue(obj != null);

        //set Mtom optimization.
        SOAPBinding binding = (SOAPBinding)((BindingProvider)obj).getBinding();
        binding.setMTOMEnabled(true);
        port = (Hello)obj;
        
        address = (String)((BindingProvider)obj).getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        if (port != null) {
            ((Closeable)port).close();
        }
    }

    private String createMessage(String cid, String mimeCid) {
        String message = "--uuid:713d6c50-6bbd-4ae3-b631-2022594ef048\n"
                + "Content-Type: application/xop+xml;charset=utf-8;type=\"text/xml\"\n"
                + "Content-Transfer-Encoding: binary\n"
                + "\n"
                + "<?xml version=\"1.0\" ?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Header></S:Header><S:Body><data xmlns=\"http://example.org/mtom/data\"><Include \n"
                + "xmlns=\"http://www.w3.org/2004/08/xop/include\" href=\"cid:"
                + cid
                + "\"/></data></S:Body></S:Envelope>\n"
                + "--uuid:713d6c50-6bbd-4ae3-b631-2022594ef048\n"
                + "Content-Type: application/octet-stream\n"
                + "Content-Id: <"
                + mimeCid
                + ">\n"
                + "Content-Transfer-Encoding: binary\n"
                + "\n"
                + "Test Data\n"
                + "--uuid:713d6c50-6bbd-4ae3-b631-2022594ef048--";

        return message;
    }
    private BufferedReader _br;
    
    public void testEscapedMtomCid() {
        sendMessage(escapedCID, escapedCID);
    }

    public void testNonEscapedMtomCid() {
        sendMessage(noescapeCID, noescapeCID);
    }

    public void testIndigoMtomStyleMessage() {
        sendMessage(escapedCID, noescapeCID);
    }

    /**
     * Test by sending the message to the server using an HTTP POST operation
     *
     * @param cid the message to send
     */
    private void sendMessage(String cid, String mimeCid) {
        try {
            URL url = new URL(address);
            // URL connection channel.
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            // No caching,
            httpConn.setUseCaches(false);

            // Specify the content type.
            httpConn.setRequestProperty("Content-Type", "Multipart/Related;type=\"application/xop+xml\";boundary=\"uuid:713d6c50-6bbd-4ae3-b631-2022594ef048\";start-info=\"text/xml\"");

            // Send POST output.
            PrintWriter _pw = new PrintWriter(httpConn.getOutputStream());
            _pw.write(createMessage(cid, mimeCid));
            _pw.flush();
            _pw.close();

            InputStream _is;
            // 500 should be the code for the message above to reproduce the bug
            if (httpConn.getResponseCode() == 500) {
                System.out.println(" Reproduced bug with CID " + cid);
                _is = httpConn.getErrorStream();
                assertTrue(false);
            } else {
                System.out.println(" Successfully sent MTOM message with CID " + cid);
                _is = httpConn.getInputStream();
                assertTrue(true);
            }

            _br = new BufferedReader(new InputStreamReader(_is));
            String _line;
            while (((_line = _br.readLine()) != null)) {
                System.out.print(_line);
                System.out.print("\n");
            }
            _br.close();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}

