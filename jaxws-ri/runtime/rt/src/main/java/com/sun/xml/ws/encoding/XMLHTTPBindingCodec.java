/*
 * Copyright (c) 1997, 2022 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.encoding;

import com.sun.xml.ws.api.SOAPVersion;
import com.sun.xml.ws.api.WSFeatureList;
import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.pipe.Codec;
import com.sun.xml.ws.api.pipe.ContentType;
import com.sun.xml.ws.client.ContentNegotiation;
import com.sun.xml.ws.encoding.xml.XMLCodec;
import com.sun.xml.ws.encoding.xml.XMLMessage;
import com.sun.xml.ws.encoding.xml.XMLMessage.MessageDataSource;
import com.sun.xml.ws.encoding.xml.XMLMessage.UnknownContent;
import com.sun.xml.ws.encoding.xml.XMLMessage.XMLMultiPart;
import com.sun.xml.ws.resources.StreamingMessages;
import com.sun.xml.ws.util.ByteArrayBuffer;
import com.sun.xml.ws.util.FastInfosetUtil;

import jakarta.activation.DataSource;
import jakarta.xml.ws.WebServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.WritableByteChannel;
import java.util.StringTokenizer;

/**
 * XML (infoset) over HTTP binding {@link Codec}.
 * <p>
 * TODO: Support FI for multipart/related
 *       Support FI for MessageDataSource
 *
 * @author Jitendra Kotamraju
 */
public final class XMLHTTPBindingCodec extends MimeCodec {
    /**
     * Base HTTP Accept request-header.
     */
    private static final String BASE_ACCEPT_VALUE =
        "*";

    /**
     * Fast Infoset MIME type.
     */
    private static final String APPLICATION_FAST_INFOSET_MIME_TYPE =
        "application/fastinfoset";
    
    /**
     * True if the Fast Infoset codec should be used
     */
    private boolean useFastInfosetForEncoding;

    /**
     * The XML codec
     */
    private final Codec xmlCodec;
    
    /**
     * The FI codec
     */
    private final Codec fiCodec;
    
    /**
     * The Accept header for XML encodings
     */
    private static final String xmlAccept = null;
    
    /**
     * The Accept header for Fast Infoset and XML encodings
     */
    private static final String fiXmlAccept = APPLICATION_FAST_INFOSET_MIME_TYPE + ", " + BASE_ACCEPT_VALUE;
        
    private ContentTypeImpl setAcceptHeader(Packet p, ContentType c) {
        ContentTypeImpl ctImpl = (ContentTypeImpl)c;
        if (p.contentNegotiation == ContentNegotiation.optimistic 
                || p.contentNegotiation == ContentNegotiation.pessimistic) {
            ctImpl.setAcceptHeader(fiXmlAccept);
        } else {
            ctImpl.setAcceptHeader(xmlAccept);
        }
        p.setContentType(ctImpl);
        return ctImpl;
    }
    
    public XMLHTTPBindingCodec(WSFeatureList f) {
        super(SOAPVersion.SOAP_11, f);
        
        xmlCodec = new XMLCodec(f);
        
        fiCodec = FastInfosetUtil.getFICodec();
    }
    
    @Override
    public String getMimeType() {
        return null;
    }
    
    @Override
    public ContentType getStaticContentType(Packet packet) {
        ContentType ct;
        if (packet.getInternalMessage() instanceof MessageDataSource) {
            final MessageDataSource mds = (MessageDataSource)packet.getInternalMessage();
            if (mds.hasUnconsumedDataSource()) {
                ct = getStaticContentType(mds);
                return (ct != null)
                    ? setAcceptHeader(packet, ct) //_adaptingContentType.set(packet, ct) 
                    : null;
            }
        }
        
        ct = super.getStaticContentType(packet);            
        return (ct != null)
            ? setAcceptHeader(packet, ct) //_adaptingContentType.set(packet, ct) 
            : null;
    }
    
    @Override
    public ContentType encode(Packet packet, OutputStream out) throws IOException {
        if (packet.getInternalMessage() instanceof MessageDataSource) {
            final MessageDataSource mds = (MessageDataSource)packet.getInternalMessage();
            if (mds.hasUnconsumedDataSource())
                return setAcceptHeader(packet, encode(mds, out));
        }
        
        return setAcceptHeader(packet, super.encode(packet, out));
    }

    @Override
    public ContentType encode(Packet packet, WritableByteChannel buffer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void decode(InputStream in, String contentType, Packet packet) throws IOException {
        /*
          Reset the encoding state when on the server side for each
          decode/encode step.
         */
        if (packet.contentNegotiation == null)
            useFastInfosetForEncoding = false;
        
        if (contentType == null) {
            xmlCodec.decode(in, contentType, packet);
        } else if (isMultipartRelated(contentType)) {
            packet.setMessage(new XMLMultiPart(contentType, in, features));
        } else if(isFastInfoset(contentType)) {
            if (fiCodec == null) {
                throw new RuntimeException(StreamingMessages.FASTINFOSET_NO_IMPLEMENTATION());
            }
            
            useFastInfosetForEncoding = true;
            fiCodec.decode(in, contentType, packet);
        } else if (isXml(contentType)) {
            xmlCodec.decode(in, contentType, packet);
        } else {
            packet.setMessage(new UnknownContent(contentType, in));
        }
        
        if (!useFastInfosetForEncoding) {
            useFastInfosetForEncoding = isFastInfosetAcceptable(packet.acceptableMimeTypes);
        }
    }
    
    @Override
    protected void decode(MimeMultipartParser mpp, Packet packet) throws IOException {
        // This method will never be invoked
    }
    
    @Override
    public MimeCodec copy() {
        return new XMLHTTPBindingCodec(features);
    }
    
    private boolean isMultipartRelated(String contentType) {
        return compareStrings(contentType, MimeCodec.MULTIPART_RELATED_MIME_TYPE);
    }
    
    private boolean isXml(String contentType) {
        return compareStrings(contentType, XMLCodec.XML_APPLICATION_MIME_TYPE)
                || compareStrings(contentType, XMLCodec.XML_TEXT_MIME_TYPE)
                || (compareStrings(contentType, "application/") && contentType.toLowerCase().contains("+xml"));
    }
    
    private boolean isFastInfoset(String contentType) {
        return compareStrings(contentType, APPLICATION_FAST_INFOSET_MIME_TYPE);
    }
    
    private boolean compareStrings(String a, String b) {
        return a.length() >= b.length() && 
                b.equalsIgnoreCase(
                    a.substring(0,
                        b.length()));
    }

    private boolean isFastInfosetAcceptable(String accept) {
        if (accept == null) return false;
        
        StringTokenizer st = new StringTokenizer(accept, ",");
        while (st.hasMoreTokens()) {
            final String token = st.nextToken().trim();
            if (token.equalsIgnoreCase(APPLICATION_FAST_INFOSET_MIME_TYPE)) {
                return true;
            }
        }        
        return false;
    }
    
    private ContentType getStaticContentType(MessageDataSource mds) {
        final String contentType = mds.getDataSource().getContentType();
        final boolean isFastInfoset = XMLMessage.isFastInfoset(contentType);
        
        if (!requiresTransformationOfDataSource(isFastInfoset, 
                useFastInfosetForEncoding)) {
            return new ContentTypeImpl(contentType);
        } else {
            return null;
        }
    }
        
    private ContentType encode(MessageDataSource mds, OutputStream out) {
        try {
            final boolean isFastInfoset = XMLMessage.isFastInfoset(
                    mds.getDataSource().getContentType());
            DataSource ds = transformDataSource(mds.getDataSource(), 
                    isFastInfoset, useFastInfosetForEncoding, features);
            
            InputStream is = ds.getInputStream();
            byte[] buf = new byte[1024];
            int count;
            while((count=is.read(buf)) != -1) {
                out.write(buf, 0, count);
            }
            return new ContentTypeImpl(ds.getContentType());
        } catch(IOException ioe) {
            throw new WebServiceException(ioe);
        }
    }    

    @Override
    protected Codec getMimeRootCodec(Packet p) {
        /*
          The following logic is only for outbound packets
          to be encoded by client.
          On the server the p.contentNegotiation == null.
         */
        if (p.contentNegotiation == ContentNegotiation.none) {
            // The client may have changed the negotiation property from
            // pessismistic to none between invocations
            useFastInfosetForEncoding = false;
        } else if (p.contentNegotiation == ContentNegotiation.optimistic) {
            // Always encode using Fast Infoset if in optimisitic mode
            useFastInfosetForEncoding = true;
        }

        return (useFastInfosetForEncoding && fiCodec != null)? fiCodec : xmlCodec;
    }

    public static boolean requiresTransformationOfDataSource(
            boolean isFastInfoset, boolean useFastInfoset) {    
        return (isFastInfoset && !useFastInfoset) || (!isFastInfoset && useFastInfoset);
    }
        
    public static DataSource transformDataSource(DataSource in, 
            boolean isFastInfoset, boolean useFastInfoset, WSFeatureList f) {
        try {
            if (isFastInfoset && !useFastInfoset) {
                // Convert from Fast Infoset to XML
                Codec codec = new XMLHTTPBindingCodec(f);
                Packet p = new Packet();
                codec.decode(in.getInputStream(), in.getContentType(), p);
                
                p.getMessage().getAttachments();
                codec.getStaticContentType(p);
                
                ByteArrayBuffer bos = new ByteArrayBuffer();
                ContentType ct = codec.encode(p, bos);
                return XMLMessage.createDataSource(ct.getContentType(), bos.newInputStream());
            } else if (!isFastInfoset && useFastInfoset) {
                // Convert from XML to Fast Infoset
                Codec codec = new XMLHTTPBindingCodec(f);
                Packet p = new Packet();
                codec.decode(in.getInputStream(), in.getContentType(), p);
                
                p.contentNegotiation = ContentNegotiation.optimistic;
                p.getMessage().getAttachments();
                codec.getStaticContentType(p);
                
                ByteArrayBuffer bos = new ByteArrayBuffer();
                com.sun.xml.ws.api.pipe.ContentType ct = codec.encode(p, bos);
                return XMLMessage.createDataSource(ct.getContentType(), bos.newInputStream());                
            }
        } catch(Exception ex) {
            throw new WebServiceException(ex);
        }
        
        return in;
    }

}
