/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.mime.simple_doclit.server;

import com.sun.xml.ws.developer.JAXWSProperties;

import jakarta.activation.DataHandler;
import jakarta.jws.WebService;
import jakarta.jws.WebParam;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.ws.Holder;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.WebServiceContext;
import jakarta.xml.ws.handler.MessageContext;
import javax.servlet.ServletContext;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import jakarta.annotation.Resource;


@WebService(endpointInterface = "fromwsdl.mime.simple_doclit.server.CatalogPortType")

public class CatalogPortType_Impl {

   public void echoImageWithInfo(
       String inImageType,
       Image image,
       Holder<String> outImageType,
       Holder<Image> out_image){
       outImageType.value = inImageType;
       out_image.value = image;
       MessageContext mc = wsContext.getMessageContext();
       Map<String, DataHandler> attachments = (Map<String, DataHandler>)mc.get(MessageContext.INBOUND_MESSAGE_ATTACHMENTS);

       if(attachments.size() != 2)
           throw new WebServiceException("Expected 2 attachments in MessageContext, received: "+attachments.size()+"!");

       //now copy the received attachemnts to outbound attachemnt property
       mc.put(MessageContext.OUTBOUND_MESSAGE_ATTACHMENTS, attachments);
   }

   public FooType getFooWithMimeDisabled(
        boolean param,
        FooType foo){
        return foo;       
   }
   public String testNormalWithMimeBinding(
           String inBody,
           Holder<Integer> header){
       return inBody;
   }

    public void defaultTypeTest(
        String order,
        ProductDetailsType pdt,
        Holder<Integer> prodNum,
        Holder<String> status){
        if(!order.equals("tv"))
            throw new WebServiceException("Wrong order! expected \"tv\", got: " + order);

        DimensionsType dt = pdt.getDimensions();
        if((dt.getHeight() != 36) || (dt.getWidth() != 24) || (dt.getDepth() != 36))
            throw new WebServiceException("Wrong Dimension! height: "+dt.getHeight()+", width: "+dt.getWidth()+", depth: "+dt.getDepth());

        if(!pdt.getDimensionsUnit().equals("Inch") || (pdt.getWeight() != 125) || !pdt.getWeightUnit().equals("LB"))
            throw new WebServiceException("Wrong ProductDetailsType! DimensionType: "+pdt.getDimensionsUnit()+", wwight: "+
                    pdt.getWeight()+", weightUnit: "+pdt.getWeightUnit());

        prodNum.value = 12345;
        status.value = "ok";
    }

    public DataHandler testDataHandler(String body, DataHandler attachIn){
        if(attachIn == null)
            throw new WebServiceException("Received DataHandler should not be null!");
        return attachIn;
    }

    /**
     * @param input
     * @return returns java.lang.String
     */
    public String echoString(String input) {
        return input;
    }

    /**
     * @param attach1
     * @return returns java.awt.Image
     */
    public byte[] voidTest(byte[] attach1) {
        return attach1;
    }

    /**
     * @param request
     * @return returns fromwsdl.mime.simple_doclit.server.ProductCatalogType
     */
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

    /**
     * @param specs
     * @param pic
     * @param request
     * @param body
     */
    public void getProductDetails(GetProductDetailsType request, Holder<ProductDetailsType> body,
                                  Holder<Image> pic, Holder<Source> specs) {
        pic.value = getImage();
        specs.value = getSampleXML();
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
            body.value=pdt;
        }
    }

    private Image getImage(){
        java.awt.Image image = null;
        String location = getDataDir() + "vivek.jpg";
        try {
            if(getServletContext() != null) {
                InputStream is = getServletContext().getResourceAsStream(location);

                image = javax.imageio.ImageIO.read(is);
            }else{
                image = javax.imageio.ImageIO.read(new File(location));
            }
        } catch (IOException e) {
            throw new WebServiceException(e);
        }
        return image;
    }

    private StreamSource getSampleXML() {
        try {
            String location = getDataDir() + "sample.xml";
            InputStream is = null;
            if(getServletContext() != null) {
              is = getServletContext().getResourceAsStream(location);
            } else {
                File f = new File(location);
                is = new FileInputStream(f);
            }
            return new StreamSource(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getDataDir() {
        String userDir = System.getProperty("user.dir");
        String sepChar = System.getProperty("file.separator");
        if(getServletContext() != null)
            return "/WEB-INF/";
        return userDir+sepChar+ "src/fromwsdl/mime/simple_doclit/common_resources/WEB-INF/";
    }

    private ServletContext getServletContext(){
        if(wsContext == null)
            return null;
        return (ServletContext)wsContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
    }

    @Resource
	protected WebServiceContext wsContext;
}
