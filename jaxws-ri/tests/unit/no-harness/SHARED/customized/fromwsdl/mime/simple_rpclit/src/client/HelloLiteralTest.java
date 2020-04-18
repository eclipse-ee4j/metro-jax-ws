/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

import jakarta.activation.DataHandler;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceException;
import java.awt.*;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


/**
 * @author Jitendra Kotamraju
 */
public class HelloLiteralTest extends TestCase {

    private CatalogPortType port;
    private AttachmentHelper helper = new AttachmentHelper();

    public HelloLiteralTest(String name) throws Exception {
        super(name);
        CatalogService service = new CatalogService();
        port = service.getCatalogPort();
    }

    public void testHello() throws Exception {
        String ret = port.echoString("Hello");
        assertTrue(ret.equals("Hello"));
    }

    public void testEchoAllAttachmentTypesTest() throws Exception {
        Holder<Source> attach10 = new Holder<Source>();
        attach10.value = getSource("sample.xml");
        VoidRequest request = new VoidRequest();
        OutputResponseAll response = port.echoAllAttachmentTypes(request, attach10);
    }

    public void testGetFooWithMimeDisabled() {
        FooType ft = new FooType();
        ft.setName("SUNW");
        ft.setValue(100);
        FooType resp = port.getFooWithMimeDisabled(true, ft);
        assertTrue(resp.getName().equals("SUNW") && (resp.getValue() == 100));
    }

    public void testDataHandler() throws Exception {
        DataHandler dh = new DataHandler(getSource("sample.xml"), "text/xml");
        DataHandler resp = port.testDataHandler("test", dh);
        assertTrue(AttachmentHelper.compareSource(getSource("sample.xml"), (Source) resp.getContent()));
    }

    public void testNormalWithMimeBinding() {
        Holder<Integer> hdr = new Holder<Integer>(100);
        String resp = port.testNormalWithMimeBinding("Hello World!", hdr);
        assertTrue(resp.equals("Hello World!") && (hdr.value == 100));
    }

    public void testDefaultTypeTest() {
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
    }

    public void testVoidTest() throws Exception  {
        //java.awt.Image ret = port.voidTest(getImage());
        byte[] req = AttachmentHelper.getImageBytes(getImage("vivek.jpg"), "image/jpeg");
        byte[] ret = port.voidTest(req);
        assertTrue(ret != null);
        assertTrue(Arrays.equals(ret, req));
    }

    public void testGetCatalogWithImages() throws Exception  {
        GetCatalogWithImagesType cit = new GetCatalogWithImagesType();
        DataHandler dh = new DataHandler(getSource("sample.xml"), "text/xml");
        cit.setThumbnail(dh);
        ProductCatalogType out1 = port.getCatalogWithImages(cit);
        List<ProductType> out = out1.getProduct();

        assertTrue((out != null) && (out.size() > 0));

        for (ProductType pt : out) {
            //System.out.println("\nProductType ["+i+"]:");

            //System.out.println("Name: "+ pt.getName()+"\n"+
            //  "Description: "+ pt.getDescription()+"\n"+
            // "ProductNumber: "+ pt.getProductNumber()+"\n"+
            // "Category: "+ pt.getCategory()+"\n"+
            // "Brand: "+ pt.getBrand()+"\n"+
            // "Price: "+ pt.getPrice()+"\n"+
            // "Thumbnail: "+pt.getThumbnail());
            DataHandler tn = pt.getThumbnail();
            assertTrue(tn != null);
            assertTrue(tn.getContent() instanceof StreamSource);
            StreamSource thumbnail = (StreamSource) tn.getContent();
            assertTrue(AttachmentHelper.compareSource(getSource("sample.xml"), thumbnail));
            //assertTrue(AttachmentHelper.compareStreamSource(getSampleXML(), thumbnail));
        }
    }

    // SERVER SIDE is not populating imageHolder, sourceHolder
    // so not running this test
    public void xtestGetAProductDetailsType() throws Exception {
        GetProductDetailsType input = new GetProductDetailsType();
        input.setProductNumber(1);
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
        assertTrue(AttachmentHelper.compareImages(getImage("vivek.jpg"), imageHolder.value));

        assertTrue(sourceHolder.value != null);
        assertTrue(AttachmentHelper.compareSource(getSource("sample.xml"), sourceHolder.value));
    }

    private Image getImage(String image) throws Exception {
        InputStream is = getClass().getModule().getResourceAsStream(image);
        if (is == null) {
            throw new WebServiceException("Cannot load " + image);
        }
        return javax.imageio.ImageIO.read(is);
    }

    private StreamSource getSource(String file) throws Exception {
        InputStream is = getClass().getModule().getResourceAsStream(file);
        if (is == null) {
            throw new WebServiceException("Cannot load " + file);
        }
        return new StreamSource(is);
    }
}
