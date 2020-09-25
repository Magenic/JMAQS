# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Web Service Utilities

## Overview
The WebServiceUtils class is a utility class for working with HTTP content and serialization 

# Available methods
[CreateEntity](#CreateEntity)  
[GetResponseBody](#GetResponseBody)  
[CreateStringEntity](#CreateStringEntity)  
[SerializeJSON](#SerializeJson)  
[SerializeXML](#SerializeXml)  
[DeserializeXmlDocument](#DeserializeXmlDocument)  
[DeserializeJson](#DeserializeJson)  

## CreateEntity
Creates a HTTP entity with a content message string and a content type.
```java
HttpEntity newEntity = new StringEntity(contentMessage, contentType);
return newEntity;
```

## GetResponseBody
Gets the body from a HTTP response with a closeable response.
```java
HttpEntity entity = response.getEntity();
return EntityUtils.toString(entity);
```
Gets the body from a HTTP response with a closeable response, a content type, and a type.
```java
 if (contentType.toString().toUpperCase().contains("JSON")) {
      responseBody = deserializeJson(response, type);
    }
    else if (contentType.toString().toUpperCase().contains("XML")) {
      responseBody = deserializeXml(response, type);
    }
    else {
      throw new IllegalArgumentException(
              StringProcessor.safeFormatter("Only xml and json conversions are currently supported"));
    }
```

## CreateStringEntity
Creates the string entity with an object body, Charset, and a string media type.
```java
ContentType contentType = ContentType.create(mediaType, encoding);
return createStringEntity(body, contentType);
```
Creates the string entity with an object body and a ContentType.
```java
if (contentType.toString().toUpperCase().contains("XML")) {
      return new StringEntity(serializeXml(body), contentType);
    } else if (contentType.toString().toUpperCase().contains("JSON")) {
      return new StringEntity(serializeJson(body), contentType);
    } else {
      throw new IllegalArgumentException(
              StringProcessor.safeFormatter("Only xml and json conversions are currently supported"));
    }
```

## SerializeJson
Serialize the object into a string.
```java
private final Product product = new Product(1, "Milk", "Dairy", BigDecimal.TEN);
String actualJson = WebServiceUtilities.serializeJson(this.product);
```

## SerializeXml
Serialize the object into a string.
```java
private final Product product = new Product(1, "Milk", "Dairy", BigDecimal.TEN);
String actualXml = WebServiceUtilities.serializeXml(this.product);
```

## DeserializeXmlDocument
Deserialize the body of a response message.  
*This will only work if the body is XML and can be deserialized as the given object.
```java
CloseableHttpResponse response = this.getWebServiceDriver().getContent(
"/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_XML, true);
Product product = WebServiceUtilities.deserializeXml(response, Product.class);
Assert.assertNotNull(product);
```

## DeserializeJson
Deserialize the body of a response message.  
*This will only work if the body is JSON and can be deserialized as the given object.
```java
CloseableHttpResponse response = this.getWebServiceDriver().getContent(
"/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_JSON, true);
Product product = WebServiceUtilities.deserializeJson(response, Product.class);
Assert.assertNotNull(product);
```
