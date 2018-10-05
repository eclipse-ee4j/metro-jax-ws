/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.mime.simple_rpclit.server;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.xml.transform.Source;
import javax.xml.ws.Holder;
import javax.xml.ws.WebServiceException;
import java.awt.*;
import java.math.BigDecimal;

/**
 * @author Jitendra Kotamraju
 */
@WebService(endpointInterface = "fromwsdl.mime.simple_rpclit.server.CatalogPortType")
public class CatalogPortType_Impl {

    public OutputResponseAll echoAllAttachmentTypes(VoidRequest request,
                                                    Holder<Source> attach10) {

        OutputResponseAll theResponse = new OutputResponseAll();
        theResponse.setResult("ok");
        theResponse.setReason("ok");

        if (attach10 == null || attach10.value == null) {
            if (theResponse.getReason().equals("ok"))
                theResponse.setReason("attach10.value is null (unexpected)");
            else
                theResponse.setReason(theResponse.getReason() +
                        "\nattach10.value is null (unexpected)");
            theResponse.setResult("not ok");
        }
        return theResponse;
    }

    public FooType getFooWithMimeDisabled(boolean param, FooType foo) {
        return foo;
    }

    public String testNormalWithMimeBinding(String inBody, Holder<Integer> header) {
        return inBody;
    }

    public void defaultTypeTest(String order, ProductDetailsType pdt,
            Holder<Integer> prodNum, Holder<String> status) {

        if (!order.equals("tv"))
            throw new WebServiceException("Wrong order! expected \"tv\", got: " + order);

        DimensionsType dt = pdt.getDimensions();
        if ((dt.getHeight() != 36) || (dt.getWidth() != 24) || (dt.getDepth() != 36))
            throw new WebServiceException("Wrong Dimension! height: " + dt.getHeight() + ", width: " + dt.getWidth() + ", depth: " + dt.getDepth());

        if (!pdt.getDimensionsUnit().equals("Inch") || (pdt.getWeight() != 125) || !pdt.getWeightUnit().equals("LB"))
            throw new WebServiceException("Wrong ProductDetailsType! DimensionType: " + pdt.getDimensionsUnit() + ", wwight: " +
                    pdt.getWeight() + ", weightUnit: " + pdt.getWeightUnit());

        prodNum.value = 12345;
        status.value = "ok";
    }

    public DataHandler testDataHandler(String body, DataHandler attachIn) {
        return attachIn;
    }

    public String echoString(String input) {
        return input;
    }

    public byte[] voidTest(byte[] attach1) {
        return attach1;
    }

    public ProductCatalogType getCatalogWithImages(GetCatalogWithImagesType request) {
        DataHandler tn = request.getThumbnail();
        ProductCatalogType pct = new ProductCatalogType();
        java.util.List<ProductType> pts = pct.getProduct();
        ProductType pt = new ProductType();
        pt.setName("JAXWS2.0");
        pt.setBrand("SUN");
        pt.setCategory("Web Services");
        pt.setDescription("WEB SERVICE Development");
        pt.setPrice(new BigDecimal("2000"));
        pt.setProductNumber(1);
        pt.setThumbnail(tn);
        pts.add(pt);
        return pct;
    }

    // Not invoked by client since it doesn't populate image, and xml
    public void getProductDetails(GetProductDetailsType request,
                                  Holder<ProductDetailsType> body,
                                  Holder<Image> pic, Holder<Source> specs) {
        //pic.value = getImage();
        //specs.value = getSampleXML();
        if (request.getProductNumber() == 1) {
            DimensionsType dt = new DimensionsType();
            dt.setHeight(36);
            dt.setWidth(24);
            dt.setDepth(36);
            ProductDetailsType pdt = new ProductDetailsType();
            pdt.setDimensions(dt);
            pdt.setDimensionsUnit("Inch");
            pdt.setWeight(125);
            pdt.setWeightUnit("LB");
            body.value = pdt;
        }
    }

}
