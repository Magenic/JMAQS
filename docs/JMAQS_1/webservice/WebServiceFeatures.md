# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Web Service Features

## Overview
JMAQS provides support for testing web services.  


## BaseWebServiceTest
BaseWebServiceTest is an abstract test class you can extend.  Extending the class allows you to automatically use JMAQS' web service testing capabilities.
```java
public class MyWebServiceTests extends BaseWebServiceTest {}
```

## WebServiceDriver
The WebServiceDriver is an object that allows you to interact with web services.  
This driver wraps common web service interactions, making web service testing relatively easy.  
The driver is also thread safe, which means you can run multiple web service tests in parallel.  
*Information, such as web service base URL is pulled from the JMAQS configuration.
```java
CloseableHttpResponse response = this.getWebServiceDriver().getContent("/api/XML_JSON/GetProduct/1",ContentType.APPLICATION_JSON, true);
Product jsonProduct = WebServiceUtilities.getResponseBody(response, ContentType.APPLICATION_JSON, Product.class);
```
## Log
There is also logger (also thread safe) the can be used to add log message to your log.
```java
this.getTestObject().getLog().logMessage("I am testing with JMAQS");
```
## TestObject
The TestObject can be thought of as your test context.  It holds all the JMAQS test execution replated data. This includes the web service driver, logger, soft asserts, performance timers, plus more.
```java
CloseableHttpResponse response = this.getWebServiceDriver().getContent(
"/api/XML_JSON/GetProduct/1",ContentType.APPLICATION_JSON, true);
Product jsonProduct = WebServiceUtilities.getResponseBody(response, ContentType.APPLICATION_JSON, Product.class);
this.getTestObject().getLog().logMessage("I am testing with JMAQS");
```
*Notes:*  
* *Most of the test object objects are already accessible on the test lever. For example **this.Log** and **this.TestObject.Log** both access the same logger.*
* *You seldom what you use the test object directly.  It is usually only used when you want to share your test JMAQS context with another piece of code*

## Sample code
```java
package com.magenic.jmaqs.webservices;

import com.magenic.jmaqs.utilities.helper.TestCategories;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Web service wrapper unit tests.
 */
public class WebServiceUnitTest extends BaseWebServiceTest {

  /**
   * Verify we can get content.
   */
    @Test(groups = TestCategories.WebService)
    public void GetJsonDeserialized() {
         CloseableHttpResponse response = this.getWebServiceDriver().getContent("/api/XML_JSON/GetProduct/1", ContentType.APPLICATION_JSON, true);
         Product jsonProduct = WebServiceUtilities.getResponseBody(response, ContentType.APPLICATION_JSON, Product.class);        
         this.Log.LogMessage("I am testing with JMAQS");
         Assert.AreEqual(1, result.Id, "Expected to get product 1");
    }
  }
```