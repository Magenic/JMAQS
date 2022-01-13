# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Web Service Driver

## Overview
The WebServiceDriver object allows you to interact with web services.

Each web driver call has a similar format
* Request URI
  * Path beyond the base URL
  * The URI may also include query parameters
* Expected response type
  * This tells the service what format the response body should take.
* Content
  * This is optional and only needs to be used when sending content to the web service.  Typically, this would not be used with **GET** calls, but would be used with **PUT** calls.

# Available calls
[Get](#Get)  
[Put](#Put)  
[Patch](#Patch)  
[Post](#Post)  
[Delete](#Delete)  

## Get
Execute a "get" call and get the response body back as a Closeable HTTP response.
```java
CloseableHttpResponse response = this.getWebServiceDriver().getContent(
"/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_JSON, true);
Product jsonProduct = WebServiceUtilities.getResponseBody(response, ContentType.APPLICATION_JSON, Product.class);
Assert.assertNotNull(jsonProduct, "Response body did not deserialize object from json correctly");
```

## Put
Execute a "put" call and get the response body back as a Closable Http Response.
```java
HttpEntity content = WebServiceUtilities.createEntity("", ContentType.TEXT_PLAIN);
CloseableHttpResponse response = this.getWebServiceDriver().putContent(
"/api/XML_JSON/Put/1", content, ContentType.APPLICATION_XML, false);
```

## Patch
Execute a "patch" call and get the response body back as a Closeable Http Response.
```java
HttpEntity content = WebServiceUtilities.createEntity("", ContentType.TEXT_PLAIN);
CloseableHttpResponse response = this.getWebServiceDriver().patchContent(
"/api/XML_JSON/Put/1", content, ContentType.APPLICATION_XML, false);
```

## Post
Execute a "post" call and get the response body back as Closeable Http Response.
```java
HttpEntity content = WebServiceUtilities.createEntity("", ContentType.TEXT_PLAIN);
CloseableHttpResponse response = this.getWebServiceDriver().postContent(
"/api/String", content, ContentType.TEXT_PLAIN, false);
```

## Delete
Execute a "get" call and get the response body back as a Closeable Http Response.
```java
 CloseableHttpResponse response = this.getWebServiceDriver()
.deleteContent("/api/XML_JSON/Delete/1", ContentType.TEXT_PLAIN, false);
Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
```
