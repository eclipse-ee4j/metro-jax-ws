/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.mime.simple_rpclit.server;

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
import javax.annotation.Resource;


@WebService(endpointInterface = "fromwsdl.mime.simple_rpclit.server.CatalogPortType")

public class CatalogPortType_Impl {

   public OutputResponseAll echoAllAttachmentTypes(VoidRequest request, jakarta.xml.ws.Holder<Source> attach10)  {
	try {
	    System.out.println("Enter echoAllAttachmentTypes() ......");
	    OutputResponseAll theResponse = new OutputResponseAll();
	    theResponse.setResult("ok");
	    theResponse.setReason("ok");
//	    if(attach1 == null || attach1.value == null) {
//		System.err.println("attach1.value is null (unexpected)");
//		theResponse.setReason("attach1.value is null (unexpected)");
//		theResponse.setResult("not ok");
//	    }
//	    if(attach2 == null || attach2.value == null) {
//		System.err.println("attach2.value is null (unexpected)");
//		if(theResponse.getReason().equals("ok"))
//		    theResponse.setReason("attach2.value is null (unexpected)");
//		else
//		    theResponse.setReason(theResponse.getReason() +
//			"\nattach2.value is null (unexpected)");
//		theResponse.setResult("not ok");
//	    }
//	    if(attach3 == null || attach3.value == null) {
//		System.err.println("attach3.value is null (unexpected)");
//		if(theResponse.getReason().equals("ok"))
//		    theResponse.setReason("attach3.value is null (unexpected)");
//		else
//		    theResponse.setReason(theResponse.getReason() +
//			"\nattach3.value is null (unexpected)");
//		theResponse.setResult("not ok");
//	    }
//	    if(attach4 == null || attach4.value == null) {
//		System.err.println("attach4.value is null (unexpected)");
//		if(theResponse.getReason().equals("ok"))
//		    theResponse.setReason("attach4.value is null (unexpected)");
//		else
//		    theResponse.setReason(theResponse.getReason() +
//			"\nattach4.value is null (unexpected)");
//		theResponse.setResult("not ok");
//	    }
//	    if(attach5 == null || attach5.value == null) {
//		System.err.println("attach5.value is null (unexpected)");
//		if(theResponse.getReason().equals("ok"))
//		    theResponse.setReason("attach5.value is null (unexpected)");
//		else
//		    theResponse.setReason(theResponse.getReason() +
//			"\nattach5.value is null (unexpected)");
//		theResponse.setResult("not ok");
//	    }
//	    if(attach6 == null || attach6.value == null) {
//		System.err.println("attach6.value is null (unexpected)");
//		if(theResponse.getReason().equals("ok"))
//		    theResponse.setReason("attach6.value is null (unexpected)");
//		else
//		    theResponse.setReason(theResponse.getReason() +
//			"\nattach6.value is null (unexpected)");
//		theResponse.setResult("not ok");
//	    }
//	    if(attach7 == null || attach7.value == null) {
//		System.err.println("attach7.value is null (unexpected)");
//		if(theResponse.getReason().equals("ok"))
//		    theResponse.setReason("attach7.value is null (unexpected)");
//		else
//		    theResponse.setReason(theResponse.getReason() +
//			"\nattach7.value is null (unexpected)");
//		theResponse.setResult("not ok");
//	    }
//	    if(attach8 == null || attach8.value == null) {
//		System.err.println("attach8.value is null (unexpected)");
//		if(theResponse.getReason().equals("ok"))
//		    theResponse.setReason("attach8.value is null (unexpected)");
//		else
//		    theResponse.setReason(theResponse.getReason() +
//			"\nattach8.value is null (unexpected)");
//		theResponse.setResult("not ok");
//	    }
//	    if(attach9 == null || attach9.value == null) {
//		System.err.println("attach9.value is null (unexpected)");
//		if(theResponse.getReason().equals("ok"))
//		    theResponse.setReason("attach9.value is null (unexpected)");
//		else
//		    theResponse.setReason(theResponse.getReason() +
//			"\nattach9.value is null (unexpected)");
//		theResponse.setResult("not ok");
//	    }
	    if(attach10 == null || attach10.value == null) {
		System.err.println("attach10.value is null (unexpected)");
		if(theResponse.getReason().equals("ok"))
		    theResponse.setReason("attach10.value is null (unexpected)");
		else
		    theResponse.setReason(theResponse.getReason() +
			"\nattach10.value is null (unexpected)");
		theResponse.setResult("not ok");
	    }
	    System.out.println("Leave echoAllAttachmentTypes() ......");
	    return theResponse;
	} catch (Exception e) {
	    throw new WebServiceException(e.getMessage());
	}
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
     * @return returns fromwsdl.mime.simple_rpclit.server.ProductCatalogType
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
        return userDir+sepChar+ "src/fromwsdl/mime/simple_rpclit/common_resources/WEB-INF/";
    }

    private ServletContext getServletContext(){
        if(wsContext == null)
            return null;
        return (ServletContext)wsContext.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
    }

    @Resource
	protected WebServiceContext wsContext;
}
