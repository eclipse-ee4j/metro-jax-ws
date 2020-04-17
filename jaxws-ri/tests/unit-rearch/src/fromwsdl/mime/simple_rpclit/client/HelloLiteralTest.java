/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.mime.simple_rpclit.client;

import junit.framework.TestCase;
import testutil.AttachmentHelper;
import testutil.ClientServerTestUtil;

import jakarta.activation.DataHandler;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.Holder;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Arrays;


/**
 * @author JAX-RPC RI Development Team
 */
public class HelloLiteralTest extends TestCase {

    private static CatalogPortType port;
    private AttachmentHelper helper = new AttachmentHelper();

    public HelloLiteralTest(String name) throws Exception {
        super(name);
        CatalogService service = new CatalogService();
        port = service.getCatalogPort();
        ClientServerTestUtil.setTransport(port);
    }


    public void testHello() throws Exception {
        try {
            String ret = port.echoString("Hello");
            assertTrue(ret.equals("Hello"));
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }


    public void testEchoAllAttachmentTypesTest() {
        try {

            Holder<Source> attach10 = new Holder<Source>();
            attach10.value = getSampleXML();
            VoidRequest request = new VoidRequest();
            OutputResponseAll response = port.echoAllAttachmentTypes(request, attach10);
        } catch(Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testGetFooWithMimeDisabled(){
        try{
            FooType ft = new FooType();
            ft.setName("SUNW");
            ft.setValue(100);
            FooType resp = port.getFooWithMimeDisabled(true, ft);
            assertTrue(resp.getName().equals("SUNW") && (resp.getValue() == 100));
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }
    public void testDataHandler(){
        DataHandler dh = new DataHandler(getSampleXML(), "text/xml");
        DataHandler resp = port.testDataHandler("test", dh);
        try {
            assertTrue(AttachmentHelper.compareSource(getSampleXML(), (Source)resp.getContent()));
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void testNormalWithMimeBinding(){
        try{
            Holder<Integer> hdr = new Holder<Integer>(100);
            String resp = port.testNormalWithMimeBinding("Hello World!", hdr);
            assertTrue(resp.equals("Hello World!") && (hdr.value == 100));
        }catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testDefaultTypeTest(){
        try {
            DimensionsType dt = new DimensionsType();
            dt.setHeight(36);
            dt.setWidth(24);
            dt.setDepth(36);
            ProductDetailsType pdt = new ProductDetailsType();
            pdt.setDimensions(dt);
            pdt.setDimensionsUnit("Inch");
            pdt.setWeight(125);
            pdt.setWeightUnit("LB");
            Holder<String> status = new Holder<String>();
            Holder<Integer> prodNum = new Holder<Integer>();
            port.defaultTypeTest("tv", pdt, prodNum, status);
            assertTrue(status.value.equals("ok"));
            assertTrue(prodNum.value == 12345);
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }


    public void testVoidTest() {
        try {
            //java.awt.Image ret = port.voidTest(getImage());
            byte[] req = AttachmentHelper.getImageBytes(getImage(), "image/jpeg");
            byte[] ret = port.voidTest(req);
            assertTrue(ret != null);
            assertTrue(Arrays.equals(ret, req));
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }


    public void testGetCatalogWithImages() {
        try{
            //invoke the method
            GetCatalogWithImagesType cit = new GetCatalogWithImagesType();
            DataHandler dh = new DataHandler(getSampleXML(), "text/xml");
            cit.setThumbnail(dh);
            ProductCatalogType out1 = port.getCatalogWithImages(cit);
            List<ProductType> out = out1.getProduct();

            assertTrue((out != null) && (out.size() > 0));

            for(ProductType pt : out) {
                //System.out.println("\nProductType ["+i+"]:");

                //System.out.println("Name: "+ pt.getName()+"\n"+
                  //  "Description: "+ pt.getDescription()+"\n"+
                   // "ProductNumber: "+ pt.getProductNumber()+"\n"+
                   // "Category: "+ pt.getCategory()+"\n"+
                   // "Brand: "+ pt.getBrand()+"\n"+
                   // "Price: "+ pt.getPrice()+"\n"+
                   // "Thumbnail: "+pt.getThumbnail());
                DataHandler tn = pt.getThumbnail();
                assertTrue(tn!=null);
                assertTrue(tn.getContent() instanceof StreamSource);
                StreamSource thumbnail =  (StreamSource)tn.getContent();
                assertTrue(AttachmentHelper.compareSource(getSampleXML(), thumbnail));
                //assertTrue(AttachmentHelper.compareStreamSource(getSampleXML(), thumbnail));
            }
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    public void testGetAProductDetailsType() {
       invokeGetProductDetails(1);
    }

    private void invokeGetProductDetails(int productNumber) {
        try {
            GetProductDetailsType input = new GetProductDetailsType();
            input.setProductNumber(productNumber);
            Holder<Image> imageHolder = new Holder<Image>();
            Holder<Source> sourceHolder = new Holder<Source>();
            Holder<ProductDetailsType> bodyHolder = new Holder<ProductDetailsType>();
            port.getProductDetails(input, bodyHolder, imageHolder, sourceHolder);
            ProductDetailsType output = bodyHolder.value;
            assertTrue(output != null);
            /*
            System.out.println("--------------getProductDetails() response...");
            if(output == null)
                System.out.println("No response!");
            else {
                System.out.println("ProductDetailsType:");
                System.out.println("Weight: "+output.getWeight() +"\n"+
                                    "Weight Unit: "+output.getWeightUnit() + "\n" +
                                    "Dimension Unit: "+output.getDimensionsUnit() +"\n"+
                                    "Dimension:\n"+
                                    "\t"+"Height: "+output.getDimensions().getHeight() +"\n"+
                                    "\t"+"Weight: "+output.getDimensions().getWidth() +"\n"+
                                    "\t"+"Depth: "+output.getDimensions().getDepth());
            }
            */

            assertTrue(imageHolder.value != null);
            assertTrue(AttachmentHelper.compareImages(getImage(), imageHolder.value));

            assertTrue(sourceHolder.value != null);
            assertTrue(AttachmentHelper.compareSource(getSampleXML(), sourceHolder.value));
        }catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    private Image getImage() throws Exception {
        String image = getDataDir() + "vivek.jpg";
        return javax.imageio.ImageIO.read(new File(image));
    }

    private String getDataDir() {
        String userDir = System.getProperty("user.dir");
        String sepChar = System.getProperty("file.separator");
        return userDir + sepChar + "src/fromwsdl/mime/simple_rpclit/common_resources/WEB-INF/";
    }

/*
    private static String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
            "<START><A>Hello</A><B>World</B></START>";

    private StreamSource getSpec(){
        StringReader reader = new StringReader(xml);
        return new StreamSource(reader);
    }
  */
    private StreamSource getSampleXML() {
        try {
            String location = getDataDir() + "sample.xml";
            File f = new File(location);
            FileInputStream is = new FileInputStream(f);
            return new StreamSource(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getSampleXMLLength() {
        String location = getDataDir() + "sample.xml";
        File f = new File(location);
        return (int) f.length();
    }
}
