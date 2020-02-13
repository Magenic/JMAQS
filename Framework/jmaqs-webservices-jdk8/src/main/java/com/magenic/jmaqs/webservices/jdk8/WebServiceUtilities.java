/*
 * Copyright 2020 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

/**
 * Web service utilities.
 */
public final class WebServiceUtilities {
  /**
   * ObjecMapper for serializing and deserializing json
   */
  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * XmlMapper used for serializing and deserializing xml
   */
  private static final ObjectMapper xmlMapper = new XmlMapper();

  // private constructor
  private WebServiceUtilities() {
  }

  /**
   * Get the body from a HTTP response.
   *
   * @param response The response
   * @return The response body
   * @throws ParseException There was an error parsing the response as a string
   * @throws IOException    There was a problem reading the response
   */
  public static String getResponseBody(CloseableHttpResponse response) throws IOException {
    HttpEntity entity = response.getEntity();
    return EntityUtils.toString(entity);
  }

  /**
   * Get the body from a HTTP response.
   *
   * @param response    The response
   * @param contentType The content type of the request
   * @param type        The type the response is being deserialized to
   * @return The response body
   * @throws ParseException There was an error parsing the response as a string
   * @throws IOException    There was a problem reading the response
   */
  public static <T> T getResponseBody(CloseableHttpResponse response, ContentType contentType,
      Type type) throws IOException {
    T responseBody;

    if (contentType.toString().toUpperCase().contains("JSON")) {
      responseBody = deserializeJson(response, type);
    } else if (contentType.toString().toUpperCase().contains("XML")) {
      responseBody = deserializeXml(response, type);
    } else {
      throw new IllegalArgumentException(
          StringProcessor.safeFormatter("Only xml and json conversions are currently supported"));
    }

    return responseBody;
  }

  /**
   * Create the string entity
   *
   * @param body      The body being set in the entity
   * @param encoding  The charset encoding of the message
   * @param mediaType The mime type of the message
   * @return The string entity
   */
  public static <T> StringEntity createStringEntity(T body, Charset encoding, String mediaType)
      throws JsonProcessingException {
    ContentType contentType = ContentType.create(mediaType, encoding);
    return createStringEntity(body, contentType);
  }

  /**
   * Creates the string content
   *
   * @param body        The body being set in the entity
   * @param contentType The content type of the
   * @param <T>         The type of the body
   * @return The string entity
   * @throws JsonProcessingException If the json or xml failed to deserialize
   */
  public static <T> StringEntity createStringEntity(T body, ContentType contentType)
      throws JsonProcessingException {
    if (contentType.toString().toUpperCase().contains("XML")) {
      return new StringEntity(serializeXml(body), contentType);
    } else if (contentType.toString().toUpperCase().contains("JSON")) {
      return new StringEntity(serializeJson(body), contentType);
    } else {
      throw new IllegalArgumentException(
          StringProcessor.safeFormatter("Only xml and json conversions are currently supported"));
    }
  }

  /**
   * Gets the json object as a string
   *
   * @param body The body being serialized to a string
   * @param <T>  The type of the body
   * @return The body as a json string
   * @throws JsonProcessingException If the json serialization fails
   */
  public static <T> String serializeJson(T body) throws JsonProcessingException {
    return objectMapper.writeValueAsString(body);
  }

  /**
   * Gets the xml object as a string
   *
   * @param body The body being serialized to a string
   * @param <T>  The type of the body
   * @return The body as an xml string
   * @throws JsonProcessingException If the xml serialization fails
   */
  public static <T> String serializeXml(T body) throws JsonProcessingException {
    return xmlMapper.writeValueAsString(body);
  }

  /**
   * Deserialzizes the response to the object specified
   *
   * @param message The httpresponse message being deserialized
   * @param <T>     The type the message will be deserialized to
   * @return The json as the type specified
   * @throws IOException If the object can't be written to that type
   */
  public static <T> T deserializeJson(CloseableHttpResponse message, Type type) throws IOException {
    String responseEntity = getResponseBody(message);
    return objectMapper
        .readValue(responseEntity, objectMapper.getTypeFactory().constructType(type));
  }

  /**
   * Deserializes the response to the object specified
   *
   * @param message The http response message being deserialized
   * @param <T>     The tyep the message will be deserialied to
   * @return The json as the type specified
   * @throws IOException If the object can't be written to that type
   */
  public static <T> T deserializeXml(CloseableHttpResponse message, Type type) throws IOException {
    String responseEntity = getResponseBody(message);
    return xmlMapper.readValue(responseEntity, xmlMapper.getTypeFactory().constructType(type));
  }
}
