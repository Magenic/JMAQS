package com.magenic.jmaqs.webservices.jdk11;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.magenic.jmaqs.utilities.helper.StringProcessor;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

/**
 * Web service Utilities class.
 */
public class WebServiceUtilities {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private static final ObjectMapper xmlMapper = new XmlMapper();

  // private constructor
  private WebServiceUtilities() {
  }

  /**
   * Gets response body.
   *
   * @param response the response
   * @return the response body
   * @throws IOException the io exception
   */
  public static String getResponseBody(HttpResponse<String> response) {
    return response.body();
  }

  /**
   * Gets response body.
   *
   * @param <T>         the type parameter
   * @param response    the response
   * @param contentType the content type
   * @param type        the type
   * @return the response body
   * @throws IOException the io exception
   */
  public static <T> T getResponseBody(HttpResponse<String> response, ContentType contentType, Type type)
      throws IOException {
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
   * Create string entity string entity.
   *
   * @param <T>       the type parameter
   * @param body      the body
   * @param encoding  the encoding
   * @param mediaType the media type
   * @return the string entity
   * @throws JsonProcessingException the json processing exception
   */
  public static <T> StringEntity createStringEntity(T body, Charset encoding, String mediaType)
      throws JsonProcessingException {
    ContentType contentType = ContentType.create(mediaType, encoding);
    return createStringEntity(body, contentType);
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
  public static <T> StringEntity createStringEntity(T body, ContentType contentType) throws JsonProcessingException {
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
   * Deserialize json t.
   *
   * @param <T>     the type parameter
   * @param message the message
   * @param type    the type
   * @return the t
   * @throws IOException the io exception
   */
  public static <T> T deserializeJson(HttpResponse<String> message, Type type) throws IOException {
    String responseEntity = getResponseBody(message);
    return objectMapper.readValue(responseEntity, objectMapper.getTypeFactory().constructType(type));
  }

  /**
   * Deserialize xml t.
   *
   * @param <T>     the type parameter
   * @param message the message
   * @param type    the type
   * @return the t
   * @throws IOException the io exception
   */
  public static <T> T deserializeXml(HttpResponse<String> message, Type type) throws IOException {
    String responseEntity = getResponseBody(message);
    return xmlMapper.readValue(responseEntity, xmlMapper.getTypeFactory().constructType(type));
  }
}
