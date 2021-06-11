/*
 * Copyright 2021 (C) Magenic, All rights Reserved
 */

package com.magenic.jmaqs.accessibility;

import com.deque.html.axecore.results.*;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.deque.html.axecore.selenium.ResultType;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;

public class HtmlReporter {
  private static final String classString = "class";

  protected HtmlReporter() {
  }

  public static void createAxeHtmlReport(WebDriver webDriver, String destination)
      throws IOException, ParseException {
    createAxeHtmlReport(webDriver, destination, EnumSet.allOf(ResultType.class));
  }

  public static void createAxeHtmlReport(WebDriver webDriver, String destination, Set<ResultType> requestedResults)
      throws IOException, ParseException {
    createAxeHtmlReport(webDriver, new AxeBuilder().analyze(webDriver), destination, requestedResults);
  }

  public static void createAxeHtmlReport(WebDriver webDriver, WebElement element, String destination)
      throws IOException, ParseException {
    createAxeHtmlReport(webDriver, element, destination, EnumSet.allOf(ResultType.class));
  }

  public static void createAxeHtmlReport(WebDriver webDriver, WebElement element, String destination,
      Set<ResultType> requestedResults) throws IOException, ParseException {
    createAxeHtmlReport(webDriver, new AxeBuilder().analyze(webDriver, element), destination, requestedResults);
  }

  public static void createAxeHtmlReport(WebDriver webDriver, Results results, String destination)
      throws IOException, ParseException {
    createAxeHtmlReport(webDriver, results, destination, EnumSet.allOf(ResultType.class));
  }

  public static void createAxeHtmlReport(WebDriver webDriver, Results results, String destination,
      Set<ResultType> requestedResults) throws IOException, ParseException {
    createAxeHtmlReportFile(webDriver, results, destination, requestedResults);
  }

  private static void createAxeHtmlReportFile(SearchContext context, Results results, String destination,
      Set<ResultType> requestedResults) throws IOException, ParseException {
    // Get the unwrapped element if we are using a wrapped element
    context = (context instanceof WrapsElement)
        ? ((WrapsElement) context).getWrappedElement() : context;

    HashSet<String> selectors = new HashSet<>();
    final int violationCount = getCount(results.getViolations(), selectors);
    final int incompleteCount = getCount(results.getIncomplete(), selectors);
    final int passCount = getCount(results.getPasses(), selectors);
    final int inapplicableCount = getCount(results.getInapplicable(), selectors);

    String stringBuilder = "<!DOCTYPE html>\r\n" + "<html lang=\"en\">" + "<head>"
        + "<meta charset=\"utf-8\">"
        + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
        + "<title>Accessibility Check</title><style></style>"
        + "</head>" + "<body><content></content><script></script></body>" + "</html>";

    Document doc = Jsoup.parse(stringBuilder);

    doc.select("style").append(getCss(context));

    Element contentArea = doc.select("content").first();

    Element reportTitle = new Element("h1");
    reportTitle.text("Accessibility Check");
    contentArea.appendChild(reportTitle);

    Element metaFlex = new Element("div");
    metaFlex.attributes().put("id", "metadata");
    contentArea.appendChild(metaFlex);

    Element contextGroup = new Element("div");
    contextGroup.attributes().put("id", "context");
    metaFlex.appendChild(contextGroup);

    Element contextHeader = new Element("h3");
    contextHeader.text("Context:");
    contextGroup.appendChild(contextHeader);

    Element contextContent = new Element("div");
    contextContent.attributes().put(classString, "emOne");
    contextContent.attributes().put("id", "reportContext");
    getContextContent(results, contextContent);
    contextGroup.appendChild(contextContent);

    Element imgGroup = new Element("div");
    imgGroup.attributes().put("id", "image");
    metaFlex.appendChild(imgGroup);

    Element imageHeader = new Element("h3");
    imageHeader.appendText("Image:");
    imgGroup.appendChild(imageHeader);

    Element imageContent = new Element("img");
    imageContent.attributes().put(classString, "thumbnail");
    imageContent.attributes().put("id", "screenshotThumbnail");
    imageContent.attributes().put("alt", "A Screenshot of the page");
    imageContent.attributes().put("width", "33%");
    imageContent.attributes().put("height", "auto");
    imgGroup.appendChild(imageContent);

    Element countsGroup = new Element("div");
    countsGroup.attributes().put("id", "counts");
    metaFlex.appendChild(countsGroup);

    Element countsHeader = new Element("h3");
    countsHeader.appendText("Counts:");
    countsGroup.appendChild(countsHeader);

    Element countsContent = new Element("div");
    countsContent.attributes().put(classString, "emOne");
    getCountContent(violationCount, incompleteCount, passCount, inapplicableCount, requestedResults, countsContent);
    countsGroup.appendChild(countsContent);

    Element resultsFlex = new Element("div");
    resultsFlex.attributes().put("id", "results");
    contentArea.appendChild(resultsFlex);


    if (results.isErrored()) {
      Element errorHeader = new Element("h2");
      errorHeader.appendText("SCAN ERRORS:");
      contentArea.appendChild(errorHeader);

      Element errorContent = new Element("div");
      errorContent.attributes().put("id", "ErrorMessage");
      errorContent.appendText(results.getErrorMessage());
      contentArea.appendChild(errorContent);
    }

    if (violationCount > 0 && requestedResults.contains(ResultType.Violations)) {
      getReadableAxeResults(results.getViolations(), ResultType.Violations, resultsFlex);
    }

    if (incompleteCount > 0 && requestedResults.contains(ResultType.Incomplete)) {
      getReadableAxeResults(results.getIncomplete(), ResultType.Incomplete, resultsFlex);
    }

    if (passCount > 0 && requestedResults.contains(ResultType.Passes)) {
      getReadableAxeResults(results.getPasses(), ResultType.Passes, resultsFlex);
    }

    if (inapplicableCount > 0 && requestedResults.contains(ResultType.Inapplicable)) {
      getReadableAxeResults(results.getInapplicable(), ResultType.Inapplicable, resultsFlex);
    }

    Element modal = new Element("div");
    modal.attributes().put("id", "modal");
    contentArea.appendChild(modal);

    Element modalClose = new Element("div");
    modalClose.text("X");
    modalClose.attributes().put("id", "modalclose");
    modal.appendChild(modalClose);

    Element modalImage = new Element("img");
    modalImage.attributes().put("id", "modalimage");
    modal.appendChild(modalImage);

    Element script = doc.select("script").first();
    script.text(getJavascriptToString());

    FileUtils.writeStringToFile(new File(destination), doc.outerHtml(), StandardCharsets.UTF_8);
  }

  private static void getReadableAxeResults(List<Rule> results, ResultType type, Element body) {
    Element resultWrapper = new Element("div");
    resultWrapper.attributes().put(classString, "resultWrapper");
    body.appendChild(resultWrapper);

    Element sectionButton = new Element("button");
    sectionButton.attributes().put(classString, "sectionbutton active");
    resultWrapper.appendChild(sectionButton);

    HashSet<String> selectors = new HashSet<>();

    Element sectionButtonHeader = new Element("h2");
    sectionButtonHeader.attributes().put(classString, "buttonInfoText");
    sectionButtonHeader.text(type.name() + ": " + getCount(results, selectors));
    sectionButton.appendChild(sectionButtonHeader);

    Element sectionButtonExpando = new Element("h2");
    sectionButtonExpando.attributes().put(classString, "buttonExpandoText");
    sectionButtonExpando.text("-");
    sectionButton.appendChild(sectionButtonExpando);

    Element section = new Element("div");
    section.attributes().put(classString, "majorSection");
    section.attributes().put("id", type.name() + "Section");
    resultWrapper.appendChild(section);

    int loops = 1;

    for (Rule element : results) {
      Element childEl = new Element("div");
      childEl.attributes().put(classString, "findings");
      childEl.appendText(loops++ + ": " + element.getHelp());
      section.appendChild(childEl);

      Element content = new Element("div");
      content.attributes().put(classString, "emTwo");
      content.text("Description: " + element.getDescription());
      content.appendChild(new Element("br"));
      content.appendText("Help: " + element.getHelp());
      content.appendChild(new Element("br"));
      content.appendText("Help URL: ");

      Element link = new Element("a");
      link.attributes().put("href", element.getHelpUrl());
      link.text(element.getHelpUrl());

      content.appendChild(link);
      content.appendChild(new Element("br"));

      if (!element.getImpact().isEmpty()) {
        content.appendText("Impact: " + element.getImpact());
        content.appendChild(new Element("br"));
      }

      content.appendText("Tags: ").append(String.join(", ", element.getTags()));
      content.appendChild(new Element("br"));

      if (!element.getNodes().isEmpty()) {
        content.appendText("Element(s):");
      }

      Element childEl2 = new Element("div");
      childEl2.attributes().put(classString, "emTwo");
      childEl.appendChild(content);

      for (CheckedNode item : element.getNodes()) {
        Element elementNodes = new Element("div");
        elementNodes.attr(classString, "htmlTable");
        childEl.appendChild(elementNodes);

        Element htmlAndSelectorWrapper = new Element("div");
        htmlAndSelectorWrapper.attr(classString, "emThree");
        htmlAndSelectorWrapper.text("Html:");
        htmlAndSelectorWrapper.appendChild(new Element("br"));
        elementNodes.appendChild(htmlAndSelectorWrapper);

        Element htmlAndSelector = new Element("p");
        htmlAndSelector.attr(classString, "wrapOne");
        htmlAndSelector.html(item.getHtml());
        htmlAndSelector.text(item.getHtml());
        htmlAndSelectorWrapper.appendChild(htmlAndSelector);
        htmlAndSelectorWrapper.appendText("Selector(s):");

        htmlAndSelector = new Element("p");
        htmlAndSelector.attributes().put(classString, "wrapTwo");

        for (Object target : Collections.singletonList(item.getTarget())) {
          String targetString = target.toString().replace("[", "").replace("]", "");
          htmlAndSelector.text(targetString);
          htmlAndSelector.html(targetString);
        }

        htmlAndSelectorWrapper.appendChild(htmlAndSelector);
        addFixes(item, type, htmlAndSelectorWrapper);
      }
    }
  }

  private static void addFixes(CheckedNode resultsNode, ResultType type, Element htmlAndSelectorWrapper) {
    Element htmlAndSelector = new Element("div");

    List<Check> anyCheckResults = resultsNode.getAny();
    List<Check> allCheckResults = resultsNode.getAll();
    List<Check> noneCheckResults = resultsNode.getNone();

    int checkResultsCount = anyCheckResults.size() + allCheckResults.size() + noneCheckResults.size();

    // Add fixes if this is for violations
    if (ResultType.Violations.equals(type) && checkResultsCount > 0) {
      htmlAndSelector.text("To solve:");
      htmlAndSelectorWrapper.appendChild(htmlAndSelector);

      htmlAndSelector = new Element("p");
      htmlAndSelector.attr("class", "wrapTwo");
      htmlAndSelectorWrapper.appendChild(htmlAndSelector);

      if (!allCheckResults.isEmpty() || !noneCheckResults.isEmpty()) {
        htmlAndSelector = new Element("p");
        htmlAndSelector.attr("class", "wrapOne");
        htmlAndSelector.text("Fix all of the following issues:");

        Element htmlSet = new Element("ul");

        for (Check checkResult : allCheckResults) {
          Element bulletPoints = new Element("li");
          bulletPoints.text(checkResult.getImpact().toUpperCase() + ": " + checkResult.getMessage());
          htmlSet.appendChild(bulletPoints);
        }

        for (Check checkResult : noneCheckResults) {
          Element bulletPoints = new Element("li");
          bulletPoints.text(checkResult.getImpact().toUpperCase() + ": " + checkResult.getMessage());
          htmlSet.appendChild(bulletPoints);
        }

        htmlAndSelector.appendChild(htmlSet);
        htmlAndSelectorWrapper.appendChild(htmlAndSelector);
      }

      if (!anyCheckResults.isEmpty()) {
        htmlAndSelector = new Element("p");
        htmlAndSelector.attr("class", "wrapOne");
        htmlAndSelector.text("Fix at least one of the following issues:");

        Element htmlSet = new Element("ul");

        for (Check checkResult : anyCheckResults) {
          Element bulletPoints = new Element("li");
          bulletPoints.text(checkResult.getImpact().toUpperCase() + ": " + checkResult.getMessage());
          htmlSet.appendChild(bulletPoints);
        }

        htmlAndSelector.appendChild(htmlSet);
        htmlAndSelectorWrapper.appendChild(htmlAndSelector);
      }
    }
  }

  private static String getCss(SearchContext context) {
    return ".thumbnail{" + "content: url('" + getDataImageString(context)
        + "; border: 1px solid black;margin-left:1em;margin-right:1em;width:auto;max-height:150px;"
        + "} .thumbnail:hover{border:2px solid black;}"
        + ".wrap .wrapTwo .wrapThree{margin:2px;max-width:70vw;}"
        + ".wrapOne {margin-left:1em;overflow-wrap:anywhere;}"
        + ".wrapTwo {margin-left:2em;overflow-wrap:anywhere;}"
        + ".wrapThree {margin-left:3em;overflow-wrap:anywhere;}"
        + ".emOne {margin-left:1em;margin-right:1em;overflow-wrap:anywhere;}"
        + ".emTwo {margin-left:2em;overflow-wrap:anywhere;}"
        + ".emThree {margin-left:3em;overflow-wrap:anywhere;}"
        + "#modal {display: none;position: fixed;z-index: 1;left: 0;top: 0;width: 100%;"
        + "height: 100%;overflow: auto;background-color: rgba(0, 0, 0, 0.9);  flex-direction: column;}"
        + "#modalclose{font-family: Lucida Console; font-size: 35px; width: auto; "
        + "color: white; text-align: right; padding: 20px;"
        + "cursor: pointer; max-height: 10%}"
        + "#modalimage {margin: auto;display: block;max-width: 95%; padding: 10px; max-height: 90%}"
        + ".htmlTable{border-top:double lightgray;width:100%;display:table;}"
        + ".sectionbutton{background-color: #000000; color: #FFFFFF; cursor: pointer; padding: 18px; width: 100%;"
        + "text-align: left; outline: none; transition: 0.4s; border: 1px solid black;}"
        + ".sectionbutton:hover {background-color: #828282;}"
        + ".buttonInfoText {width: 50%; float: left;}"
        + ".buttonExpandoText {text-align: right; width: 50%; float: right;}"
        + ".majorSection{padding: 0 18px;background-color:white; overflow:hidden;"
        + "transition: max-height 0.2s ease-out;}"
        + ".findings{margin-top: 5px; border-top:1px solid black;}"
        + ".active {background-color: #474747; margin-bottom: 0px;}"
        + ".resultWrapper {margin: 5px}" + "#context {width: 50%;}"
        + "#image {width: 50%; height: 220px;}" + "#counts {width: 100%;}"
        + "#metadata {display: flex; flex-wrap: wrap;}"
        + "#results {display: flex; flex-direction: column;}"
        + "@media only screen and (max-width: 800px) {#metadata {flex-direction: column;}"
        + "#context {width: 100%;}" + "#image {width: 100%;}";
  }

  private static void getContextContent(Results results, Element element) throws ParseException {
    element.text("Url: " + results.getUrl());
    element.appendChild(new Element("br"));
    element.appendText("Orientation: " + results.getTestEnvironment().getOrientationType());
    element.appendChild(new Element("br"));
    element.appendText("Size: " + results.getTestEnvironment().getwindowWidth() + " x  "
        + results.getTestEnvironment().getWindowHeight());
    element.appendChild(new Element("br"));
    element.appendText("Time: " + getDateFormat(results.getTimestamp()));
    element.appendChild(new Element("br"));
    element.appendText("User agent: " + results.getTestEnvironment().getUserAgent());
    element.appendChild(new Element("br"));
    element.appendText("Using: " + results.getTestEngine().getName() + " ("
        + results.getTestEngine().getVersion() + ")");
  }

  private static int getCount(List<Rule> results, HashSet<String> uniqueList) {
    int count = 0;
    for (Rule item : results) {
      for (Node node : item.getNodes()) {
        for (Object target : Collections.singletonList(node.getTarget())) {
          count++;
          uniqueList.add(target.toString());
        }
      }

      // Still add one if no targets are included
      if (item.getNodes().isEmpty()) {
        count++;
      }
    }
    return count;
  }

  private static void getCountContent(int violationCount, int incompleteCount, int passCount,
      int inapplicableCount, Set<ResultType> requestedResults, Element element) {
    if (requestedResults.contains(ResultType.Violations)) {
      element.text(" Violations: " + violationCount);
      element.appendChild(new Element("br"));
    }

    if (requestedResults.contains(ResultType.Incomplete)) {
      element.appendText(" Incomplete: " + incompleteCount);
      element.appendChild(new Element("br"));
    }

    if (requestedResults.contains(ResultType.Passes)) {
      element.appendText(" Passes: " + passCount);
      element.appendChild(new Element("br"));
    }

    if (requestedResults.contains(ResultType.Inapplicable)) {
      element.appendText(" Inapplicable: " + inapplicableCount);
    }
  }

  private static String getDataImageString(SearchContext context) {
    TakesScreenshot newScreen = (TakesScreenshot) context;
    return "data:image/png;base64," + newScreen.getScreenshotAs(OutputType.BASE64) + "');";
  }

  private static String getDateFormat(String timestamp) throws ParseException {
    Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(timestamp);
    return new SimpleDateFormat("dd-MMM-yy HH:mm:ss Z").format(date);
  }

  private static String getJavascriptToString() {
    return StringEscapeUtils.escapeEcmaScript(
        "var buttons = document.getElementsByClassName(\"sectionbutton\");\n"
            + "                              var i;\n"
            + "\n"
            + "                              for (i = 0; i < buttons.length; i++) \n"
            + "                              {\n"
            + "                                  buttons[i].addEventListener(\"click\", function() \n"
            + "                                  {\n"
            + "                              var expandoText = this.getElementsByClassName(\"buttonExpandoText\")[0];\n"
            + "                                      \n"
            + "                                      this.classList.toggle(\"active\");\n"
            + "\n"
            + "                                      var content = this.nextElementSibling;\n"
            + "                                      if (expandoText.innerHTML == \"-\") \n"
            + "                                      {\n"
            + "                                          content.style.maxHeight = 0;\n"
            + "                                          expandoText.innerHTML = \"+\";\n"
            + "                                      } \n"
            + "                                      else \n"
            + "                                      {\n"
            + "                                          content.style.maxHeight = content.scrollHeight + \"px\";\n"
            + "                                          expandoText.innerHTML = \"-\";\n"
            + "                                      }\n"
            + "                                  })\n"
            + "                              }\n"
            + "\n"
            + "                              var thumbnail = document.getElementById(\"screenshotThumbnail\");\n"
            + "                              var thumbnailStyle = getComputedStyle(thumbnail);      \n"
            + "                              var modal = document.getElementById(\"modal\");\n"
            + "                              var modalimg = modal.getElementsByTagName(\"img\")[0]\n"
            + "\n"
            + "                              modal.addEventListener('click',function(){\n"
            + "                                 modal.style.display = \"none\";\n"
            + "                                 modalimg.style.content = \"\";\n"
            + "                                 modalimg.alt = \"\";\n"
            + "                               })\n"
            + "\n"
            + "                              thumbnail.addEventListener('click',function(){\n"
            + "                                 modal.style.display = \"flex\";\n"
            + "                                 modalimg.style.content = thumbnailStyle.content;\n"
            + "                                 modalimg.alt = thumbnail.alt;\n"
            + "                               })");
  }
}