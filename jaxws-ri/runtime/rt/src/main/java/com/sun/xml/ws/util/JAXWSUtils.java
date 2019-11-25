/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package com.sun.xml.ws.util;

import java.util.UUID;
import java.util.regex.Pattern;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.File;
import java.io.IOException;

import javax.xml.namespace.QName;

/**
 * @author Vivek Pandey
 *
 * Wrapper utility class to be used from the generated code or run time.
 */
public final class JAXWSUtils {
    public static String getUUID(){
         return UUID.randomUUID().toString();
    }



    public static String getFileOrURLName(String fileOrURL) {
        try{
            try {
                return escapeSpace(new URL(fileOrURL).toExternalForm());
            } catch (MalformedURLException e) {
                return new File(fileOrURL).getCanonicalFile().toURL().toExternalForm();
            }
        } catch (Exception e) {
            // try it as an URL
            return fileOrURL;
        }
    }

    public static URL getFileOrURL(String fileOrURL) throws IOException {
        try {
          URL url = new URL(fileOrURL);
          String scheme = String.valueOf(url.getProtocol()).toLowerCase();
          if (scheme.equals("http") || scheme.equals("https"))
            return new URL(url.toURI().toASCIIString());
          return url;
        } catch (URISyntaxException e) {
            return new File(fileOrURL).toURL();
        } catch (MalformedURLException e) {
            return new File(fileOrURL).toURL();
        }
    }

  public static URL getEncodedURL(String urlStr) throws MalformedURLException {
      URL url = new URL(urlStr);
      String scheme = String.valueOf(url.getProtocol()).toLowerCase();
      if (scheme.equals("http") || scheme.equals("https")) {
          try {
              return new URL(url.toURI().toASCIIString());
          } catch (URISyntaxException e) {
              MalformedURLException malformedURLException = new MalformedURLException(e.getMessage());
              malformedURLException.initCause(e);
              throw malformedURLException;
          }
       }
       return url;
  }

    private static String escapeSpace( String url ) {
        // URLEncoder didn't work.
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < url.length(); i++) {
            // TODO: not sure if this is the only character that needs to be escaped.
            if (url.charAt(i) == ' ')
                buf.append("%20");
            else
                buf.append(url.charAt(i));
        }
        return buf.toString();
    }

    public static String absolutize(String name) {
        // absolutize all the system IDs in the input,
        // so that we can map system IDs to DOM trees.
        try {
            URL baseURL = new File(".").getCanonicalFile().toURL();
            return new URL(baseURL, name).toExternalForm();
        } catch( IOException e) {
            //ignore
        }
        return name;
    }

    /**
     * Checks if the system ID is absolute.
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static  void checkAbsoluteness(String systemId) {
        // we need to be able to handle system IDs like "urn:foo", which java.net.URL can't process,
        // but OTOH we also need to be able to process system IDs like "file://a b c/def.xsd",
        // which java.net.URI can't process. So for now, let's fail only if both of them fail.
        // eventually we need a proper URI class that works for us.
        try {
            new URL(systemId);
        } catch( MalformedURLException mue) {
            try {
                new URI(systemId);
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("system ID '"+systemId+"' isn't absolute",e);
            }
        }
    }

    /*
     * To match, both QNames must have the same namespace and the local
     * part of the target must match the local part of the 'pattern'
     * QName, which may contain wildcard characters.
     */
    public static boolean matchQNames(QName target, QName pattern) {
        if ((target == null) || (pattern == null))  {
            // if no service or port is in descriptor
            return false;
        }
        if (pattern.getNamespaceURI().equals(target.getNamespaceURI())) {
            String regex = pattern.getLocalPart().replaceAll("\\*",  ".*");
            return Pattern.matches(regex, target.getLocalPart());
        }
        return false;
    }

}
