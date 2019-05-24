package com.sun.tools.ws.test.processor.modeler.annotation;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@WebService()
public class NillableTest {

        private java.lang.String[] entitlements;

        @XmlElementWrapper(name="titles")
        @XmlElement(name="title01", nillable=false)
        public java.lang.String[] getEntitlements() {
                return this.entitlements;
        }

        public void setEntitlements(String[] entitlements) {
                this.entitlements = entitlements;
        }
}
