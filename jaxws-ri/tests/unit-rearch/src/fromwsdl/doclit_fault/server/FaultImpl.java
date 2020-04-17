/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package fromwsdl.doclit_fault.server;

import org.w3c.dom.*;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import jakarta.xml.ws.ProtocolException;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.soap.SOAPFaultException;
import jakarta.xml.soap.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

import com.sun.xml.ws.fault.SOAPFaultBuilder;

@jakarta.jws.WebService(endpointInterface="fromwsdl.doclit_fault.server.Fault")
public class FaultImpl{
    public java.lang.String echo(java.lang.String type)
            throws
            Fault1Exception,
            Fault2Exception,
            Fault3Exception,
            Fault4Exception {
        if (type.equals("Fault1")) {
            FooException fault = new FooException();
            fault.setVarInt(1);
            fault.setVarString("1");
            fault.setVarFloat(1);
            System.out.println("Throwing Fault1Exception");
            throw new Fault1Exception("Fault1 message", fault);
        } else if (type.equals("Fault1-SOAPFaultException")) {
            FooException fault = new FooException();
            fault.setVarInt(1);
            fault.setVarString("1");
            fault.setVarFloat(1);
            System.out.println("Throwing Fault1Exception with Cause");
            throw new Fault1Exception("Fault1 message", fault, createSOAPFaultException());
        }else if (type.equals("Fault2")) {            
            String fault = "fault2";
            System.out.println("Throwing Fault2Exception");
            throw new Fault2Exception("Fault2 message", fault);        
        } else if (type.equals("Fault3")) {
            Integer fault = new Integer("1");
            System.out.println("Throwing Fault3Exception");
            throw new Fault3Exception("Fault3 message", fault);
        } else if (type.equals("Fault4")) {
            Fault4 fault = new Fault4();
            fault.setMessage("fault4");
            fault.setCount(1);
            System.out.println("Throwing Fault4Exception");
            throw new Fault4Exception("Fault4 message", fault);
        } else if (type.equals("SOAPFaultException")) {                          
                throw createSOAPFaultException();
        } else if (type.equals("NullPointerException")) {
            Object o = null;
            o.hashCode(); 
        } else if (type.equals("ProtocolException")) {
            throw new ProtocolException();
        } else if (type.equals("ProtocolException2")) {
            throw new ProtocolException("FaultImpl");
        } else if(type.equals("multipleDetails")){
            createSaajBug();
        }else if(type.equals("nullBean")){
            throw new Fault2Exception(null, null, new WebServiceException("User exception!"));
        }else if (type.equals("echo")) {
            return "echo"; // used in MU test
        }
        return "Unknown fault: " + type;
    }

    private SOAPFaultException createSaajBug(){
        SOAPFaultException sfe;
            try {
              SOAPFactory fac = SOAPFactory.newInstance();
              SOAPFault sf = fac.createFault(
                  "This is a fault.",
                  new QName("http://schemas.xmlsoap.org/soap/envelope/", "Client"));
              Detail d = sf.addDetail();
              SOAPElement de = d.addChildElement(new QName(
                  "http://www.example.com/faults", "myFirstDetail"));
              de.addAttribute(new QName("", "msg"), "This is the first detail message.");
              de = d.addChildElement(new QName(
                  "http://www.example.com/faults", "mySecondDetail"));
              de.addAttribute(new QName("", "msg"), "This is the second detail message.");
              sfe = new SOAPFaultException(sf);
            } catch (Exception e) {
              throw new WebServiceException(e);
            }
            throw sfe;
    }

    
    private SOAPFaultException createSOAPFaultException(){
        try {
            String namespace = "http://faultservice.org/wsdl";
            SOAPFactory soapFactory = SOAPFactory.newInstance();
            Name name = soapFactory.createName("BasicFault", "ns0",
                    namespace);
            Detail detail = soapFactory.createDetail();
            DetailEntry entry = detail.addDetailEntry(name);
            entry.addNamespaceDeclaration("ns0", namespace);
            entry.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
            entry.addNamespaceDeclaration("myenv",
                    "http://schemas.xmlsoap.org/soap/envelope/");
            entry.addNamespaceDeclaration("myns", "http://myurri/tmp");
            Name attrName = soapFactory.createName("encodingStyle", "myenv",
                    "http://schemas.xmlsoap.org/soap/envelope/");
            entry.addAttribute(attrName,
                    "http://schemas.xmlsoap.org/soap/encoding/");
            Name attrName2 = soapFactory.createName("myAttr", "myns",
                    "http://myurri/tmp");
            entry.addAttribute(attrName2, "myvalue");
            SOAPElement child = entry.addChildElement("message");
            child.addTextNode("basic fault");

            Name name2 = soapFactory.createName("AdditionalElement", "ns0",
                    namespace);
            DetailEntry entry2 = detail.addDetailEntry(name2);
            entry2.addNamespaceDeclaration("ns0", namespace);

            SOAPElement child2 = entry2.addChildElement("BOGUS");
            child2.addTextNode("2 text");

            QName qname = new QName("http://schemas.xmlsoap.org/soap/envelope/", "client");
            //printDetail(detail);
            SOAPFault sf = soapFactory.createFault("soap fault exception fault", qname);
            //Node n = sf.addDetail().getOwnerDocument().importNode(detail, true);
            Node n = sf.getOwnerDocument().importNode(detail, true);
            sf.appendChild(n);
            return new SOAPFaultException(sf);

//            return new SOAPFaultException(qname,
//                    "soap fault exception fault", null, detail);
        } catch (SOAPException e) {
            e.printStackTrace();
            QName qname = new QName("http://schemas.xmlsoap.org/soap/envelope/", "client");
            throw new WebServiceException("soap fault exception fault", e);
//            throw new SOAPFaultException(qname,
//                    "soap fault exception fault", null, null);
        }
    }

    public void printDetail(Detail detail) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            StreamResult sr = new StreamResult(bos );
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            Properties oprops = new Properties();
            oprops.put(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperties(oprops);
            trans.transform(new DOMSource(detail), sr);
            System.out.println("**** bos ******"+bos.toString());
            bos.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
