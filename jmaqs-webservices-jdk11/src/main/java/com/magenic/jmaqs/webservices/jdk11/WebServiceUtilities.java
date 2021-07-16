/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.webservices.jdk11;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import com.magenic.jmaqs.webservices.jdk8.MediaType;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;

/**
 * The type Web service utilities.
 */
public class WebServiceUtilities {
  /**
   * used to serialize and deserialize json properties.
   */
  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * used to serialize and deserialize xml properties.
   */
  private static final ObjectMapper xmlMapper = new XmlMapper();

  /**
   * private class constructor.
   */
  private WebServiceUtilities() {
  }

  /**
   * Gets response body.
   * @param response the response
   * @return the response body
   */
  public static String getResponseBody(HttpResponse<String> response) {
    return response.body();
  }

  /**
   * Gets response body.
   * @param <T>         the type parameter
   * @param response    the response
   * @param contentType the content type
   * @param type        the type
   * @return the response body
   * @throws IOException the io exception
   */
  public static <T> T getResponseBody(HttpResponse<String> response, MediaType contentType, Type type)
      throws IOException {
    T responseBody;

    if (contentType.equals(MediaType.APP_JSON)) {
      responseBody = deserializeJson(response, type);
    } else if (contentType.equals(MediaType.APP_XML)) {
      responseBody = deserializeXml(response, type);
    } else {
      throw new IllegalArgumentException(
          StringProcessor.safeFormatter("Only xml and json conversions are currently supported"));
    }

    return responseBody;
  }

  /**
   * Create string entity string entity.
   *
   * @param <T>         the type parameter
   * @param body        the body
   * @param contentType the content type
   * @return the string entity
   * @throws JsonProcessingException the json processing exception
   */
  public static <T> String createStringEntity(T body, MediaType contentType) throws JsonProcessingException {
    if (contentType.equals(MediaType.APP_XML)) {
      return serializeXml(body);
    } else if (contentType.equals(MediaType.APP_JSON)) {
      return serializeJson(body);
    } else if (contentType.equals(MediaType.PLAIN_TEXT)) {
      return body.toString();
    } else {
      throw new IllegalArgumentException(
          StringProcessor.safeFormatter("Only xml and json conversions are currently supported"));
    }
  }

  /**
   * Serialize json string.
   *
   * @param <T>  the type parameter
   * @param body the body
   * @return the string
   * @throws JsonProcessingException the json processing exception
   */
  public static <T> String serializeJson(T body) throws JsonProcessingException {
    return objectMapper.writeValueAsString(body);
  }

  /**
   * Serialize xml string.
   *
   * @param <T>  the type parameter
   * @param body the body
   * @return the string
   * @throws JsonProcessingException the json processing exception
   */
  public static <T> String serializeXml(T body) throws JsonProcessingException {
    return xmlMapper.writeValueAsString(body);
  }

  /**
   * Deserialize the response based on the media type.
   * @param <T>         the type parameter
   * @param message the String Http Response message
   * @param mediaType the type the message is going to be turned into
   * @param type the class or java object to be transferred into
   * @return the response type
   * @throws IOException the io exception
   */
  public static <T> T deserializeResponse(HttpResponse<String> message, MediaType mediaType, Type type)
      throws IOException {
    if (mediaType.equals(MediaType.APP_XML)) {
      return deserializeXml(message, type);
    } else if (mediaType.equals(MediaType.APP_JSON)) {
      return deserializeJson(message, type);
    } else {
      throw new IllegalArgumentException(
          StringProcessor.safeFormatter("Only xml and json conversions are currently supported"));
    }
  }

  /**
   * Deserialize json to a specified object.
   *
   * @param <T>     the type parameter
   * @param message the message
   * @param type    the type
   * @return the t
   * @throws IOException the io exception
   */
  public static <T> T deserializeJson(HttpResponse<String> message, Type type) throws IOException {
    return checkMessageBodyEmpty(message) ? null
        : objectMapper.readValue(getResponseBody(message), objectMapper.getTypeFactory().constructType(type));
  }

  /**
   * Deserialize xml to a specified object.
   *
   * @param <T>     the type parameter
   * @param message the message
   * @param type    the type
   * @return the t
   * @throws IOException the io exception
   */
  public static <T> T deserializeXml(HttpResponse<String> message, Type type) throws IOException {
    return checkMessageBodyEmpty(message) ? null
        : xmlMapper.readValue(getResponseBody(message), xmlMapper.getTypeFactory().constructType(type));
  }

  /**
   * Checks the message body and returns a boolean if it is an Empty string or null.
   * @param message the Http Response string to get the body
   * @return boolean value if the body is empty
   */
  private static boolean checkMessageBodyEmpty(HttpResponse<String> message) {
    return message.body().isEmpty() || message.body() == null;
  }
}