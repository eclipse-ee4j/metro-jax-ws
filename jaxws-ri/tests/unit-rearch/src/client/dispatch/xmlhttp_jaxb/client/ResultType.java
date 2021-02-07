/*
 * Copyright (c) 2004, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v@@BUILD_VERSION@@ 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.04.06 at 03:27:58 PM EDT 
//


package client.dispatch.xmlhttp_jaxb.client;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Summary" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ClickUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NewsSource" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NewsSourceUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Language" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PublishDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ModificationDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Thumbnail" type="{urn:yahoo:yn}ImageType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultType", propOrder = {
    "title",
    "summary",
    "url",
    "clickUrl",
    "newsSource",
    "newsSourceUrl",
    "language",
    "publishDate",
    "modificationDate",
    "thumbnail"
})
public class ResultType {

    @XmlElement(name = "Title", namespace = "urn:yahoo:yn", required = true)
    protected String title;
    @XmlElement(name = "Summary", namespace = "urn:yahoo:yn", required = true)
    protected String summary;
    @XmlElement(name = "Url", namespace = "urn:yahoo:yn", required = true)
    protected String url;
    @XmlElement(name = "ClickUrl", namespace = "urn:yahoo:yn", required = true)
    protected String clickUrl;
    @XmlElement(name = "NewsSource", namespace = "urn:yahoo:yn", required = true)
    protected String newsSource;
    @XmlElement(name = "NewsSourceUrl", namespace = "urn:yahoo:yn", required = true)
    protected String newsSourceUrl;
    @XmlElement(name = "Language", namespace = "urn:yahoo:yn", required = true)
    protected String language;
    @XmlElement(name = "PublishDate", namespace = "urn:yahoo:yn", required = true)
    protected String publishDate;
    @XmlElement(name = "ModificationDate", namespace = "urn:yahoo:yn")
    protected String modificationDate;
    @XmlElement(name = "Thumbnail", namespace = "urn:yahoo:yn")
    protected ImageType thumbnail;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the summary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the value of the summary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummary(String value) {
        this.summary = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the clickUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClickUrl() {
        return clickUrl;
    }

    /**
     * Sets the value of the clickUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClickUrl(String value) {
        this.clickUrl = value;
    }

    /**
     * Gets the value of the newsSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewsSource() {
        return newsSource;
    }

    /**
     * Sets the value of the newsSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewsSource(String value) {
        this.newsSource = value;
    }

    /**
     * Gets the value of the newsSourceUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewsSourceUrl() {
        return newsSourceUrl;
    }

    /**
     * Sets the value of the newsSourceUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewsSourceUrl(String value) {
        this.newsSourceUrl = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the publishDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublishDate() {
        return publishDate;
    }

    /**
     * Sets the value of the publishDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublishDate(String value) {
        this.publishDate = value;
    }

    /**
     * Gets the value of the modificationDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModificationDate() {
        return modificationDate;
    }

    /**
     * Sets the value of the modificationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModificationDate(String value) {
        this.modificationDate = value;
    }

    /**
     * Gets the value of the thumbnail property.
     * 
     * @return
     *     possible object is
     *     {@link ImageType }
     *     
     */
    public ImageType getThumbnail() {
        return thumbnail;
    }

    /**
     * Sets the value of the thumbnail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageType }
     *     
     */
    public void setThumbnail(ImageType value) {
        this.thumbnail = value;
    }

}