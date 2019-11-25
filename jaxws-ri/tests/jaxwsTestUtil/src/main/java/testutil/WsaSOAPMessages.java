/*
 * Copyright (c) 2006, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package testutil;


import javax.xml.namespace.QName;
import java.io.File;

/**
 * @author Arun Gupta
 */
public class WsaSOAPMessages {

    private String nsUri;
    private String anonymousUri;
    private String noneUri;
    public WsaSOAPMessages(String wsaNsuri) {
        if ("http://schemas.xmlsoap.org/ws/2004/08/addressing".equals(wsaNsuri)) {
            nsUri = MemberSubmissionAddressingConstants.WSA_NAMESPACE_NAME;
            anonymousUri = MemberSubmissionAddressingConstants.WSA_ANONYMOUS_ADDRESS;
            noneUri = MemberSubmissionAddressingConstants.WSA_NONE_ADDRESS;
        } else {
            nsUri = W3CAddressingConstants.WSA_NAMESPACE_NAME;
            anonymousUri = W3CAddressingConstants.WSA_ANONYMOUS_ADDRESS;
            noneUri = W3CAddressingConstants.WSA_NONE_ADDRESS;

        }
    }

    public static final QName USER_FAULT_CODE = new QName("http://example.org/echo", "EmptyEchoString");

    private String getAnonymousReplyToHeader() {
        return " <wsa:ReplyTo>\n" +
                "  <wsa:Address>" + anonymousUri + "</wsa:Address> \n" +
                " </wsa:ReplyTo>\n";
    }

    private final String getAnonymousFaultToHeader() {
        return " <wsa:FaultTo>\n" +
                "  <wsa:Address>" + anonymousUri + "</wsa:Address> \n" +
                " </wsa:FaultTo>\n";
    }

    private final String getNotifyBody() {
        return
                "<S:Body>\n" +
                        "  <notify xmlns=\"http://example.org/notify\">%s</notify> \n" +
                        "</S:Body>\n";
    }

    public final String getNoneReplyToMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                " <wsa:ReplyTo>\n" +
                "  <wsa:Address>" + noneUri + "</wsa:Address> \n" +
                "  </wsa:ReplyTo>\n" +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n" +
                getNotifyBody() +
                "</S:Envelope>";
    }

    public final String getNoneFaultToMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                " <wsa:FaultTo>\n" +
                "  <wsa:Address>" + noneUri + "</wsa:Address> \n" +
                "  </wsa:FaultTo>\n" +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n" +
                getNotifyBody() +
                "</S:Envelope>";
    }

    public final String getNoneReplyToFaultToMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                " <wsa:ReplyTo>\n" +
                "  <wsa:Address>" + noneUri + "</wsa:Address> \n" +
                "  </wsa:ReplyTo>\n" +
                " <wsa:FaultTo>\n" +
                "  <wsa:Address>" + noneUri + "</wsa:Address> \n" +
                "  </wsa:FaultTo>\n" +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n" +
                getNotifyBody() +
                "</S:Envelope>";
    }

    private final String getReplyToRefpsAnonymous() {
        return " <wsa:ReplyTo>\n" +
                "  <wsa:Address>" + anonymousUri + "</wsa:Address> \n" +
                "  <wsa:ReferenceParameters>\n" +
                "    <ck:CustomerKey xmlns:ck=\"http://example.org/customer\">Key#123456789</ck:CustomerKey> \n" +
                "  </wsa:ReferenceParameters>" +
                "  </wsa:ReplyTo>\n";
    }

    private final String getFaultToRefpsAnonymous() {
        return " <wsa:FaultTo>\n" +
                "  <wsa:Address>" + anonymousUri + "</wsa:Address> \n" +
                "  <wsa:ReferenceParameters>\n" +
                "    <ck:CustomerKey xmlns:ck=\"http://example.org/customer\">Fault#123456789</ck:CustomerKey> \n" +
                "  </wsa:ReferenceParameters>" +
                "  </wsa:FaultTo>\n";
    }

    private final String getReplyToRefpsHeader() {
        return "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                getReplyToRefpsAnonymous() +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n";
    }

    public final String getReplyToRefpsNotifyMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                getReplyToRefpsHeader() +
                getNotifyBody() +
                "</S:Envelope>";
    }

    private final String getEchoBody() {
        return "<S:Body>\n" +
                "  <echoIn xmlns=\"http://example.org/echo\">%s</echoIn> \n" +
                "</S:Body>\n";
    }

    public final String getReplyToRefpsEchoMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                getReplyToRefpsHeader() +
                getEchoBody() +
                "</S:Envelope>";
    }

    // use metadata only for W3C
    private final String getMetadata() {
        if (nsUri == W3CAddressingConstants.WSA_NAMESPACE_NAME)
            return "  <wsa:Metadata>" +
                    WsaUtils.fileToXMLString(System.getProperty("basedir") + File.separatorChar + "config" + File.separatorChar + "wsaTestService.wsdl") +
                    WsaUtils.fileToXMLString(System.getProperty("basedir") + File.separatorChar + "config" + File.separatorChar + "wsdl20.wsdl") +
                    "</wsa:Metadata> \n";
        else
            return "";
    }

    public final String getReplyToMetadataMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                " <wsa:ReplyTo>\n" +
                "  <wsa:Address>" + noneUri + "</wsa:Address> \n" +
                getMetadata() +
                "  </wsa:ReplyTo>\n" +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n" +
                getNotifyBody() +
                "</S:Envelope>";
    }


    // use metadata only for W3C
    private final String getMetadataExtensions() {
        if (nsUri == W3CAddressingConstants.WSA_NAMESPACE_NAME)
            return "  <Metadata xmlns:c=\"http://example.org/customer\" c:total=\"1\" />" +
                    "  <c:Metadata xmlns:c=\"http://example.org/customer\">\n" +
                    "    <c:extraStuff>This should be ignored</c:extraStuff> \n" +
                    "  </c:Metadata>";
        else
            return "";
    }

    public final String getReplyToExtensionsMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                " <wsa:ReplyTo>\n" +
                "  <wsa:Address>" + noneUri + "</wsa:Address> \n" +
                "  <wsa:ReferenceParameters xmlns:c=\"http://example.org/customer\" c:level=\"premium\">\n" +
                "    <c:CustomerKey>Key#123456789</c:CustomerKey> \n" +
                "  </wsa:ReferenceParameters>" +
                getMetadataExtensions() +
                "  </wsa:ReplyTo>\n" +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n" +
                getNotifyBody() +
                "</S:Envelope>";
    }

    public final String getReplyToFaultToRefpsHeader() {
        return "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                getReplyToRefpsAnonymous() +
                getFaultToRefpsAnonymous() +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n";
    }

    public final String getReplyToFaultToRefpsEchoMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                getReplyToFaultToRefpsHeader() +
                getEchoBody() +
                "</S:Envelope>";
    }

    public final String getDuplicateToMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "<wsa:To>%s</wsa:To>\n" +
                "</S:Header>\n" +
                getEchoBody() +
                "</S:Envelope>";
    }

    public final String getDuplicateReplyToMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                getAnonymousReplyToHeader() +
                "</S:Header>\n" +
                getEchoBody() +
                "</S:Envelope>";
    }

    public final String getDuplicateFaultToMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                getAnonymousFaultToHeader() +
                getAnonymousFaultToHeader() +
                "</S:Header>\n" +
                getEchoBody() +
                "</S:Envelope>";
    }

    public final String getDuplicateActionMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "<wsa:To>%s</wsa:To>\n" +
                "<wsa:Action>%s</wsa:Action>\n" +
                "<wsa:MessageID>uuid:c9251591-7b7e-4234-b193-2d242074466e</wsa:MessageID>\n"+
                "<wsa:Action>%s</wsa:Action>\n" +
                "</S:Header>\n" +
                getEchoBody() +
                "</S:Envelope>";
    }

    public final String getDuplicateMessageIDMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                " <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID>\n" +
                "</S:Header>\n" +
                getEchoBody() +
                "</S:Envelope>";
    }

    public final String getNonAnonymousReplyToAnonymousFaultToMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                " <wsa:ReplyTo>\n" +
                "  <wsa:Address>%s</wsa:Address> \n" +
                " </wsa:ReplyTo>\n" +
                getAnonymousFaultToHeader() +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n" +
                getEchoBody() +
                "</S:Envelope>";
    }

    public final String getNonAnonymousReplyToMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                " <wsa:ReplyTo>\n" +
                "  <wsa:Address>%s</wsa:Address> \n" +
                " </wsa:ReplyTo>\n" +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n" +
                getEchoBody() +
                "</S:Envelope>";
    }

    public final String getNoneTargetedNonAnonymousReplyToMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                " <wsa:ReplyTo>\n" +
                "  <wsa:Address>%s</wsa:Address> \n" +
                " </wsa:ReplyTo>\n" +
                " <wsa:ReplyTo S:role=\"http://www.w3.org/2003/05/soap-envelope/role/none\">\n" +
                "  <wsa:Address>%s</wsa:Address> \n" +
                " </wsa:ReplyTo>\n" +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n" +
                getEchoBody() +
                "</S:Envelope>";
    }

    private final String getNoneFaultToHeader() {
        return " <wsa:FaultTo>\n" +
                "  <wsa:Address>" + noneUri + "</wsa:Address> \n" +
                " </wsa:FaultTo>\n";
    }

    public final String getNonAnonymousReplyToNoneFaultToMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                " <wsa:ReplyTo>\n" +
                "  <wsa:Address>%s</wsa:Address> \n" +
                " </wsa:ReplyTo>\n" +
                getNoneFaultToHeader() +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n" +
                getEchoBody() +
                "</S:Envelope>";
    }

    private final String getNoneReplyTo() {
        return " <wsa:ReplyTo>\n" +
                "  <wsa:Address>" + noneUri + "</wsa:Address> \n" +
                "  </wsa:ReplyTo>\n";
    }

    public final String getNoneReplyToEchoMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                getNoneReplyTo() +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n" +
                getEchoBody() +
                "</S:Envelope>";
    }

    public final String getNoActionEchoMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                " <wsa:ReplyTo>\n" +
                "  <wsa:Address>" + anonymousUri + "</wsa:Address> \n" +
                "  </wsa:ReplyTo>\n" +
                "</S:Header>\n" +
                getEchoBody() +
                "</S:Envelope>";
    }

    public final String getFromMustUnderstandEchoMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "  <wsa:To>%s</wsa:To> \n" +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                " <wsa:From S:mustUnderstand=\"true\">\n" +
                "  <wsa:Address>" + anonymousUri + "</wsa:Address> \n" +
                "  </wsa:From>\n" +
                " <wsa:ReplyTo>\n" +
                "  <wsa:Address>" + anonymousUri + "</wsa:Address> \n" +
                "  </wsa:ReplyTo>\n" +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n" +
                getEchoBody() +
                "</S:Envelope>";
    }

    public final String getDuplicateToNoneReplyToMessage() {
        return "<S:Envelope xmlns:S=\"%s\" xmlns:wsa=\"" + nsUri + "\">\n" +
                "<S:Header>\n" +
                "<wsa:To>%s</wsa:To>\n" +
                "<wsa:To>%s</wsa:To>\n" +
                getNoneReplyTo() +
                "  <wsa:MessageID>" + WsaUtils.UUID + "</wsa:MessageID> \n" +
                "  <wsa:Action>%s</wsa:Action> \n" +
                "</S:Header>\n" +
                getEchoBody() +
                "</S:Envelope>";
    }

}
