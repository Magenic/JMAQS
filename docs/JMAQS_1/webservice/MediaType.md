# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Media Type

## Overview
This class is the placeholder for the MediaType 

## These are the media types that are supported:
-JSON: APP_JSON("application/json"),
-XML: APP_XML("application/xml"),
-Plain Text: PLAIN_TEXT("text/plain"),
-Images in PNG format: IMAGE_PNG("image/png");

You can reference them like this:
```java
MediaType mediaType = MediaType.APP_XML;
```

You can also get the String of the Enum by using
```java
String mediaString = MediaType.APP_XML.getMediaTypeString();
```
