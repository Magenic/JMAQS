package com.magenic.jmaqs.webservices.jdk11;

import com.magenic.jmaqs.utilities.helper.Config;
import com.magenic.jmaqs.utilities.helper.ConfigSection;

/**
 * Web service specific configuration settings.
 */
public class WebServiceConfig {
  // private constructor
  private WebServiceConfig() {
  }

  /**
   * The web service configuration section.
   */
  private static final ConfigSection WEBSERVICE_SECTION = ConfigSection.WebServiceMaqs;

  /**
   * Grabs the URI for the Web Service.
   * @return A String containing the URI for the WebService to test
   */
  public static String getWebServiceUri() {
    return Config.getValueForSection(WEBSERVICE_SECTION, "WebServiceUri");
  }

  /**
   * Gets the expected time out in seconds.
   * @return the Time Out value for the web service test, or -1 if none are configured
   */
  public static int getWebServiceTimeOut() {
    return Integer
        .parseInt(Config.getValueForSection(WEBSERVICE_SECTION, "WebServiceTimeout", "-1"));
  }

  /**
   * Get if we want to use a proxy for the web driver traffic.
   * @return True if we want to use the proxy
   */
  public static boolean getUseProxy() {
    return Config.getValueForSection(ConfigSection.WebServiceMaqs, "UseProxy", "No").equals("Yes");
  }

  /**
   * Get the proxy address to use.
   * @return The proxy address
   */
  public static String getProxyAddress() {
    return Config.getValueForSection(ConfigSection.WebServiceMaqs, "ProxyAddress");
  }
}
