/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8.soap;

import com.magenic.jmaqs.webservices.jdk8.WebServiceConfig;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.function.Consumer;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class SoapWebServiceDriverFactory {

  private SoapWebServiceDriverFactory() {
  }

  public static SOAPMessage getDefaultMessage() throws SOAPException {
    SOAPMessage message = MessageFactory.newInstance().createMessage();
    SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
    if (!SoapConfig.getSoapPrefix().isEmpty()) {
      modifySoapPrefix(envelope);
    }

    if (!SoapConfig.getSoapNamespaces().isEmpty()) {
      addNameSpaceCollectionToMessage(envelope, (HashMap<String, String>) SoapConfig.getSoapNamespaces());
    }
    return message;
  }

  public static SoapWebServiceDriver getDefaultSoapDriver() throws SOAPException, URISyntaxException {

    return new SoapWebServiceDriver(getDefaultMessage(), WebServiceConfig.getWebServiceUri());

  }

  protected static SOAPEnvelope modifySoapPrefix(SOAPEnvelope envelope) throws SOAPException {
    envelope.removeNamespaceDeclaration(envelope.getPrefix());
    addNameSpaceToMessage(envelope, SoapConfig.getSoapPrefix(), "http://schemas.xmlsoap.org/soap/envelope/");
    envelope.setPrefix(SoapConfig.getSoapPrefix());
    envelope.getBody().setPrefix(SoapConfig.getSoapPrefix());
    envelope.getHeader().setPrefix(SoapConfig.getSoapPrefix());
    return envelope;
  }

  protected static void addNameSpaceCollectionToMessage(SOAPEnvelope envelope, HashMap<String, String> map) {
    Consumer<String> consumer = key -> {
      String value = map.get(key);
      try {
        addNameSpaceToMessage(envelope, key, value);
      } catch (SOAPException e) {
        e.printStackTrace();
      }
    };
    map.keySet().stream().iterator().forEachRemaining(consumer);
  }

  public static void addNameSpaceToMessage(SOAPEnvelope envelope, String key, String value) throws SOAPException {
    envelope.addNamespaceDeclaration(key, value);
  }
}
