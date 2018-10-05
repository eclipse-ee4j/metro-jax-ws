/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package mtom.client;

import com.sun.xml.ws.developer.StreamingDataHandler;

import javax.activation.DataHandler;
import javax.xml.ws.soap.MTOMFeature;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MtomApp {

    public static void main (String[] args){
        try {
            MtomSample port = new MtomService().getMtomPort(new MTOMFeature());
            if(port == null){
                System.out.println ("FAILURE: Couldnt get port!");
                System.exit (-1);
            }

            testUpload(port);

            testDownload(port);
        } catch (Exception ex) {
            System.out.println ("SOAP 1.1 MtomApp FAILED!");
            ex.printStackTrace ();
        }
    }

    /**
     * Uploads an Image to the endpoint using MTOM
     */
    public static void testUpload (MtomSample port) throws Exception{
        Image image = getImage ("java.jpg");
        port.upload (image);
        if(image != null)
            System.out.println ("SOAP 1.1 testUpdate() PASSED!");
        else
            System.out.println ("SOAP 1.1 testUpdate() FAILED!");
    }

    /**
     * Downloads 20MB binary data using MTOM in streaming fashion
     */
    public static void testDownload(MtomSample port) throws Exception{
        int size = 20000000;//20MB

        DataHandler dh = port.download(size);
        validateDataHandler(size, dh);
    }
    
    private static void validateDataHandler(int expTotal, DataHandler dh)
		throws IOException {

        // readOnce() doesn't store attachment on the disk in some cases
        // for e.g when only one attachment is in the message
        StreamingDataHandler sdh = (StreamingDataHandler)dh;
        InputStream in = sdh.readOnce();
        byte[] buf = new byte[8192];
        int total = 0;
        int len;
        while((len=in.read(buf, 0, buf.length)) != -1) {
            for(int i=0; i < len; i++) {
                if ((byte)('A'+(total+i)%26) != buf[i]) {
                    System.out.println("FAIL: DataHandler data is different");
                }
            }
            total += len;
            if (total%(8192*250) == 0) {
            	System.out.println("Total so far="+total);
            }
        }
        System.out.println("Total Received="+total);
        if (total != expTotal) {
           System.out.println("FAIL: DataHandler data size is different. Expected="+expTotal+" Got="+total);
        }
        in.close();
        sdh.close();
    }

    private static Image getImage (String imageName) throws Exception {
        String location = getDataDir () + imageName;
        return javax.imageio.ImageIO.read (new File (location));
    }

    private static String getDataDir () {
        String userDir = System.getProperty ("user.dir");
        String sepChar = System.getProperty ("file.separator");
        return userDir+sepChar+ "common_resources/";
    }
}
