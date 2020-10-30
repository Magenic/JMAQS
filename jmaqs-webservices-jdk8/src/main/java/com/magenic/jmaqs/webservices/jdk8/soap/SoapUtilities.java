/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8.soap;

import java.io.IOException;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.apache.http.client.methods.CloseableHttpResponse;

public class SoapUtilities {

  private SoapUtilities() {

  }

  public static Object getResponseBodyAsObjects(CloseableHttpResponse closeableHttpResponse, Unmarshaller unMarshaller)
      throws SOAPException, IOException, JAXBException {
    SOAPMessage soapResp = MessageFactory.newInstance()
        .createMessage(null, closeableHttpResponse.getEntity().getContent());
    return unMarshaller.unmarshal(soapResp.getSOAPBody().extractContentAsDocument());
  }
}
