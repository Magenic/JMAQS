# <img src="resources/jmaqslogo.jpg" height="32" width="32"> List Processor

Class used for processing lists.

## CreateCommaDelimitedString
This method creates a comma delimited string from an Array list.
```java
String createCommaDelimitedString(List<String> stringList, boolean sort)
```

Method used in context:
```java
ArrayList<String> unsortedList = new ArrayList<>();

    for (WebElement element : webDriver.findElements(by)) {
      unsortedList.add(element.getText().trim());
    }

    return ListProcessor.createCommaDelimitedString(unsortedList, sort);
```

## ListOfStringsComparer
Compares two lists to see if they contain the same values.
```java
boolean listOfStringsComparer(List<String> expectedList, List<String> actualList,
      StringBuilder results, boolean verifyOrder)
```

Method used in context:
```java
final StringBuilder results = new StringBuilder();
    ArrayList<String> itemsToSelect = new ArrayList<String>();
    itemsToSelect.add("Monitor");
    itemsToSelect.add("Hard Drive");
    itemsToSelect.add("Keyboard");
    navigateToUrl();
    ElementHandler.selectMultipleElementsFromListBox(getWebDriver(), computerPartsList, itemsToSelect);
    ArrayList<String> selectedItems = (ArrayList<String>) ElementHandler.getSelectedOptionsFromDropdown(getWebDriver(), computerPartsList);
    ListProcessor.listOfStringsComparer(itemsToSelect, selectedItems, results, false);
```