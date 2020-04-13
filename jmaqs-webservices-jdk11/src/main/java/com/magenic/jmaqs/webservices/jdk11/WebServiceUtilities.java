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
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private static final ObjectMapper xmlMapper = new XmlMapper();

  // private constructor
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

    if (contentType.equals(MediaType.APP_XML)) {
      responseBody = deserializeJson(response, type);
    } else if (contentType.equals(MediaType.APP_JSON)) {
      responseBody = deserializeXml(response, type);
    } else {
      throw new IllegalArgumentException(
          StringProcessor.safeFormatter("Only xml and json conversions are currently supported"));
    }
    return responseBody;
  }

  /**
   * Create string entity string entity.
   * @param <T>         the type parameter
   * @param body        the body
   * @param contentType the content type
   * @return the string entity
   * @throws JsonProcessingException the json processing exception
   */
  public static <T> String makeStringContent(T body, MediaType contentType) throws JsonProcessingException {
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
   * @param <T>     the type parameter
   * @param type    the type
   * @return the t object
   * @throws IOException the io exception
   */
  public static <T> T deserializeResponse(HttpResponse<String> response, MediaType mediaType, Type type)
      throws IOException {
    if (mediaType == MediaType.APP_XML) {
      return deserializeXml(response, type);
    } else if (mediaType == MediaType.APP_JSON) {
      return deserializeJson(response, type);
    } else {
      return (T) response.toString();
    }
  }

  /**
   * Deserialize json t.
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
   * @param <T>     the type parameter
   * @param message the message
   * @param type    the type
   * @return the t
   * @throws IOException the io exception
   */
  public static <T> T deserializeXml(HttpResponse<String> message, Type type) throws IOException {
    String responseEntity = xmlMapper.writeValueAsString(deserializeJson(message, type));
    return xmlMapper.readValue(responseEntity, xmlMapper.getTypeFactory().constructType(type));
  }
}