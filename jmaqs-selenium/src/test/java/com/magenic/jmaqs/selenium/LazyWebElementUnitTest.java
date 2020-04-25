package com.magenic.jmaqs.selenium;

import com.magenic.jmaqs.utilities.helper.exceptions.ExecutionFailedException;
import org.openqa.selenium.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.magenic.jmaqs.selenium.factories.UIWaitFactory;
import com.magenic.jmaqs.utilities.helper.TestCategories;
import com.magenic.jmaqs.utilities.logging.FileLogger;
import com.magenic.jmaqs.utilities.helper.exceptions.TimeoutException;

import static org.testng.Assert.*;

/**
 * Unit test class for LazyElement
 */
public class LazyWebElementUnitTest extends BaseSeleniumTest {

	/**  String constants used for the tests */
	private static final String DISABLED = "Disabled";
	private static final String FLOWER_TABLE_BY = "#FlowerTable";
	private static final String FLOWER_TABLE = "Flower Table";
	private static final String FLOWER_TABLE_CAPTION = "Flower table caption";
	private static final String FOOTER_PARAGRAPH_BY = "FOOTER P";
	private static final String FOOTER = "Footer";

	/**
	 * Gets the disabled item
	 * @return The disabled item
	 */
	private LazyWebElement getDisabledItem() {
		return new LazyWebElement(this.getTestObject(), By.cssSelector("#disabledField INPUT"), DISABLED);
	}

	/**
	 * Gets the input box 
	 * @return The input box
	 */
	private LazyWebElement getInputBox() {
		return new LazyWebElement(this.getTestObject(), By.cssSelector("#TextFields [name='firstname']"), "Input box");
	}

	/**
	 * Gets the dialog one
	 * @return The dialog one
	 */
	private LazyWebElement getDialogOne() {
		return new LazyWebElement(this.getTestObject(), By.cssSelector("#CloseButtonShowDialog"), "Dialog");
	}

	/**
	 * Gets the dialog one button
	 * @return The dialog one button 
	 */
	private LazyWebElement getDialogOneButton() {
		return new LazyWebElement(this.getTestObject(), By.cssSelector("#showDialog1"), "Dialog");
	}

	/**
	 * Gets the submit button
	 * @return The submit button
	 */
	private LazyWebElement getSubmitButton() {
		return new LazyWebElement(this.getTestObject(), By.cssSelector("[class='btn btn-default'][type='submit']"), "Submit button");
	}

	/**
	 * Gets the not selected element
	 * @return The not selected element
	 */
	private LazyWebElement getNotSelected() {
		return new LazyWebElement(this.getTestObject(), By.cssSelector("#computerParts [value='one']"), "Not selected");
	}

	/**
	 * Gets an item that is going to be selected
	 * @return The selected element
	 */
	private LazyWebElement getSelected() {
		return new LazyWebElement(this.getTestObject(), By.cssSelector("#computerParts [value='two']"), "Selected");
	}

	/**
	 * Gets a parent element
	 * @return The parent element
	 */
	private LazyWebElement getFlowerTableLazyElement() {
		return new LazyWebElement(this.getTestObject(), By.cssSelector(FLOWER_TABLE_BY), FLOWER_TABLE);
	}

	/**
	 * Gets a child element, the second table caption
	 * @return The second table caption
	 */
	private LazyWebElement getFlowerTableCaptionWithParent() {
		return new LazyWebElement(this.getFlowerTableLazyElement(), By.cssSelector("CAPTION > Strong"), FLOWER_TABLE_CAPTION);
	}

	/**
	 * Gets the first table caption
	 * @return The first table caption
	 */
	private LazyWebElement getFirstTableCaption() {
		return new LazyWebElement(this.getTestObject(), By.cssSelector("CAPTION > Strong"), "Clothing table caption");
	}

	/**
	 * Gets the disabled DIV
	 * @return The disabled div
	 */
	private LazyWebElement getDisabledDiv()  {
		return new LazyWebElement(this.getTestObject(), By.cssSelector("#disabledField"), "Parent disabled div");
	}

	/**
	 * Gets the disabled input
	 * @return The disabled input
	 */
	private LazyWebElement getDisabledInput() {
		return new LazyWebElement(this.getDisabledDiv(), By.cssSelector("input"), FLOWER_TABLE_CAPTION);
	}

	/**
	 * Setup before a test
	 */
	@BeforeMethod
	public void navigateToTestPage() {
		this.getWebDriver().navigate().to(SeleniumConfig.getWebSiteBase() + "Automation");
		UIWaitFactory.getWaitDriver(this.getWebDriver()).waitForPageLoad();
	}

	/**
	 * Verify Lazy Element search respects the parent find by finding mismatch
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyWithParentAndWithoutDontMatch() throws TimeoutException, InterruptedException {
		// Make sure we got the table caption we are looking for
		assertEquals(FLOWER_TABLE, this.getFlowerTableCaptionWithParent().getText());

		// Make sure the the first found was not the the flower table
		assertNotEquals(this.getFlowerTableCaptionWithParent().getText(), this.getFirstTableCaption().getText());
	}

	/**
	 * Verify Lazy Element search respects the parent find by finding match
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyWithParentAndWithoutMatch() throws TimeoutException, InterruptedException {
		// Get the lazy element without a parent
		LazyWebElement flowerTableCaptionWithoutParent = new LazyWebElement(
				this.getTestObject(), 
				By.cssSelector("#FlowerTable CAPTION > Strong"),
				FLOWER_TABLE);

		String elem = this.getFlowerTableCaptionWithParent().getText();

		// Make sure we are finding the correct table
		assertEquals(FLOWER_TABLE, this.getFlowerTableCaptionWithParent().getText());

		// Make sure we got the table caption we are looking for
		assertEquals(this.getFlowerTableCaptionWithParent().getText(), flowerTableCaptionWithoutParent.getText());
	}
	/**
	 * Verify Lazy Element is cached as expected
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyPreCaching() {
		// Create the lazy element and use it
		LazyWebElement footer = new LazyWebElement(this.getTestObject(), By.cssSelector(FOOTER_PARAGRAPH_BY), FOOTER);

		// Make sure we are getting back the same cached element
		assertNull(footer.getCachedElement(), "The cached element should be null as we never triggered a find");
	}

	/**
	 * A new find does not return the cached element
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void newFindDifferentThanCached() throws TimeoutException, InterruptedException {
		// Create the lazy element and use it
		LazyWebElement footer = new LazyWebElement(this.getTestObject(), By.cssSelector(FOOTER_PARAGRAPH_BY), FOOTER);

		// Trigger a find and save off the element
		footer.getText();
		WebElement footerElementBefore = footer.getCachedElement();

		// Do the event again and save off the changed element 
		footer.getText();

		// Make sure doing a new find returns an element that is not the same as the cached element
		assertNotSame(this.getWebDriver().findElement(footer.getBy()), footerElementBefore);
	}

	/**
	 * Check that the element was cached
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementCached() throws TimeoutException, InterruptedException {
		// Create the lazy element and use it
		LazyWebElement footer = new LazyWebElement(this.getTestObject(), By.cssSelector(FOOTER_PARAGRAPH_BY), FOOTER);

		// Trigger a find and save off the element
		footer.getText();
		WebElement footerElementBefore = footer.getCachedElement();

		// Do the event again and save off the changed element 
		footer.getText();
		WebElement footerElementAfter = footer.getCachedElement();

		// Make sure the second event didn't trigger a new find
		assertEquals(footerElementBefore, footerElementAfter);
	}

	/**
	 * Stale elements trigger a new find
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyCaching() throws TimeoutException, InterruptedException {

		// Create the lazy element and use it
		LazyWebElement footer = new LazyWebElement(this.getTestObject(), By.cssSelector(FOOTER_PARAGRAPH_BY), FOOTER);

		// Trigger a find and save off the element
		footer.getText();
		WebElement footerElementBefore = footer.getCachedElement();

		// Do the event again and save off the changed element 
		footer.getText();

		// Go to another page so the old element will be stale, this will force us to get a new one
		this.getWebDriver().navigate().to(SeleniumConfig.getWebSiteBase() + "Automation/AsyncPage");

		// Trigger a new find, this should be new because the cached element is stale
		footer.getText();
		assertNotEquals(footerElementBefore, footer.getCachedElement());
	}

	/**
	 * Verify the get elements trigger new finds - We do this because we are looking for specific states
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyGetsTriggerFind()
	{
		// Create the lazy element and use it
		LazyWebElement footer = new LazyWebElement(this.getTestObject(), By.cssSelector(FOOTER_PARAGRAPH_BY), FOOTER);

		WebElement cacheFooter = footer.getRawVisibleElement();

		// Make sure the second event didn't trigger a new find
		assertEquals(cacheFooter, footer.getCachedElement());
	}

	/**
	 * Verify the get click-able element triggers new finds - We do this because we are looking for specific states
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyGetClickableTriggerFind() {
		// Create the lazy element and use it
		LazyWebElement footer = new LazyWebElement(this.getTestObject(), By.cssSelector(FOOTER_PARAGRAPH_BY), FOOTER);

		// set the cache
		footer.getRawClickableElement();

		// Make sure get click-able triggers a new find
		assertNotSame(footer.getCachedElement(), footer.getRawClickableElement());
	}

	/**
	 * Verify the get existing element triggers new finds - We do this because we are looking for specific states
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyGetExistTriggerFind() {
		// Create the lazy element and use it
		LazyWebElement footer = new LazyWebElement(this.getTestObject(), By.cssSelector(FOOTER_PARAGRAPH_BY), FOOTER);

		// set the cache
		footer.getRawExistingElement();

		// Make sure get exists triggers a new find
		assertNotSame(footer.getCachedElement(), footer.getRawExistingElement());
	}

	/**
	 * Verify the get visible element triggers new finds - We do this because we are looking for specific states
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyGetVisibleTriggerFind() {
		// Create the lazy element and use it
		LazyWebElement footer = new LazyWebElement(this.getTestObject(), By.cssSelector(FOOTER_PARAGRAPH_BY), FOOTER);

		// set the cache
		footer.getRawVisibleElement();

		// Make sure get visible triggers a new find
		assertNotSame(footer.getCachedElement(), footer.getRawVisibleElement());
	}

	/**
	 * Verify Lazy Element Clear test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementClear() throws TimeoutException, InterruptedException, ExecutionFailedException {
		// Make sure we can set the value
		this.getInputBox().sendKeys("test");
		assertEquals("test", this.getInputBox().getAttribute("value"));

		// Make sure the value is cleared
		this.getInputBox().clear();
		assertEquals("", this.getInputBox().getAttribute("value"));
	}

	/**
	 * Verify Lazy Element Click test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementClick() throws TimeoutException, InterruptedException, ExecutionFailedException {
		this.getDialogOneButton().click();
		assertTrue(this.getDialogOne().isDisplayed());
	}

	/**
	 * Verify Lazy Element get By test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementGetBy() {
		By testBy = By.cssSelector("#ItemsToAutomate");
		LazyWebElement testLazyWebElement = new LazyWebElement(this.getTestObject(), testBy, "TEST");

		assertEquals(testBy, testLazyWebElement.getBy());
	}

	/**
	 * Verify Lazy Element get of the test object
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementGetTestObject() {
		LazyWebElement testLazyWebElement = new LazyWebElement(this.getTestObject(), By.cssSelector("#ItemsToAutomate"), "TEST");
		assertEquals(this.getTestObject(), testLazyWebElement.getTestObject());
	}

	/**
	 * Verify Lazy Element GetAttribute test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementGetAttribute() throws TimeoutException, InterruptedException {
		assertEquals(DISABLED, this.getDisabledItem().getAttribute("value"));
	}

	/**
	 * Verify Lazy Element with a parent GetAttribute test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementGetAttributeWithParent() throws TimeoutException, InterruptedException {
		assertEquals(this.getDisabledInput().getValue(), DISABLED);
	}

	/**
	 * Verify Lazy Element GetCssValue test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementGetCssValue() throws TimeoutException, InterruptedException {
		assertEquals(this.getDialogOneButton().getCssValue("overflow"), "visible");
	}

	/**
	 * Verify Lazy Element with parent GetCssValue test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementGetCssValueWithParent() throws TimeoutException, InterruptedException {
		assertEquals(this.getDisabledInput().getCssValue("max-width"), "280px");
	}

	/**
	 * Verify Lazy Element SendKeys test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementSendKeys() throws TimeoutException, InterruptedException, ExecutionFailedException {
		this.getInputBox().sendKeys("test");
		assertEquals(this.getInputBox().getValue(), "test");
	}

	/**
	 * Verify Lazy Element with a parent SendKeys test
	 */
	@Test(groups=TestCategories.SELENIUM, expectedExceptions=ExecutionFailedException.class)
	public void lazyElementSendKeysWithParent() throws TimeoutException, InterruptedException, ExecutionFailedException {
		this.getDisabledInput().sendKeys("test");
	}

	/**
	 * Verify Lazy Element SendKeys test
	 * @throws IOException, Exception 
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementSendSecretKeys() throws IOException, ExecutionFailedException, InterruptedException, TimeoutException {
		this.getInputBox().sendKeys("beforeSuspendTest");
		this.getInputBox().clear();
		this.getInputBox().sendSecretKeys("secretKeys");
		this.getInputBox().clear();
		this.getInputBox().sendKeys("continueTest");

		FileLogger logger = (FileLogger)this.getTestObject().getLog();
		String filepath = logger.getFilePath();
		String logFile = Files.readAllLines(Paths.get(filepath)).stream().reduce((x, y) -> x + y + System.lineSeparator())
				.orElseThrow(() -> new FileNotFoundException(String.format("The log file %s was not found at path %s", logger.getFileName(), filepath)));
		
		assertTrue(logFile.contains("beforeSuspendTest"), "log file did not contain text 'beforeSuspendTest'.");
		assertFalse(logFile.contains("secretKeys"), "log file did contain text 'secretKeys' when it shouldn't have.");
		assertTrue(logFile.contains("continueTest"), "log file did not contain text 'continueTest'.");

		Files.delete(Paths.get(filepath));
	}

	/**
	 * Verify Lazy Element Submit test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementSubmit() throws TimeoutException, InterruptedException, ExecutionFailedException {
		this.getWebDriver().navigate().to(SeleniumConfig.getWebSiteBase() + "Employees");
		UIWait waitDriver = UIWaitFactory.getWaitDriver(this.getWebDriver());

		waitDriver.waitForClickableElement(By.cssSelector("A[href^='/Employees/Edit/']")).click();
		waitDriver.waitForPageLoad();

		this.getSubmitButton().submit();
		assertTrue(waitDriver.waitUntilAbsentElement(By.cssSelector("#[type='submit']")), "Submit did not go away");
	}

	/**
	 * Verify Lazy Element with parent Submit test
	 */
	@Test(groups=TestCategories.SELENIUM, expectedExceptions=ExecutionFailedException.class)
	public void lazyElementSubmitWithParent() throws TimeoutException, InterruptedException, ExecutionFailedException {
		this.getDisabledInput().submit();
	}

	/**
	 * Verify Lazy Element Displayed test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementDisplayed() throws TimeoutException, InterruptedException {
		assertTrue(this.getDialogOneButton().isDisplayed());
		assertFalse(this.getDialogOne().isDisplayed());
	}

	/**
	 * Verify Lazy Element with parent Displayed test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementDisplayedWithParent() throws TimeoutException, InterruptedException {
		assertTrue(this.getDisabledInput().isDisplayed());
	}

	/**
	 * Verify Lazy Element Enabled test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementEnabled() throws TimeoutException, InterruptedException {
		assertFalse(this.getDisabledItem().isEnabled());
		assertTrue(this.getInputBox().isEnabled());
	}

	/**
	 * Verify Lazy Element with parent Enabled test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementEnabledWithParent() throws TimeoutException, InterruptedException {
		assertFalse(this.getDisabledInput().isEnabled());
		assertTrue(this.getFlowerTableCaptionWithParent().isEnabled());
	}

	/**
	 * Verify Lazy Element Selected test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementSelected() throws TimeoutException, InterruptedException {
		ElementHandler.selectDropDownOptionByValue(this.getWebDriver(), By.cssSelector("#computerParts"), "two");

		assertTrue(this.getSelected().isSelected());
		assertFalse(this.getNotSelected().isSelected());
	}

	/**
	 * Verify Lazy Element Text test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementText() throws TimeoutException, InterruptedException {
		assertEquals("Show dialog", this.getDialogOneButton().getText());
	}

	/**
	 * Verify Lazy Element with parent Text test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementTextWithParent() throws TimeoutException, InterruptedException {
		LazyWebElement elem = this.getFlowerTableCaptionWithParent().getParent();
		elem.getText();

		assertEquals(this.getFlowerTableCaptionWithParent().getText(), FLOWER_TABLE);
	}

	/**
	 * Verify Lazy Element Location test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementLocation() throws TimeoutException, InterruptedException {
		Point point = this.getInputBox().getLocation();
		assertTrue(point.getX() > 0 && point.getY() > 0, "Unexpected point: " + point);
	}

	/**
	 * Verify Lazy Element with parent Location test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementLocationWithParent() throws TimeoutException, InterruptedException {
		Point earlierPoint = this.getInputBox().getLocation();
		Point laterPoint = this.getDisabledInput().getLocation();

		assertTrue(laterPoint.getX() > 0, "Unexpected point: " + laterPoint);
		assertTrue(earlierPoint.getY() < laterPoint.getY(), "Unexpected point: " + laterPoint);
	}

	/**
	 * Verify Lazy Element Size test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementSize() throws TimeoutException, InterruptedException {
		Dimension size = this.getInputBox().getSize();
		assertTrue(size.getWidth() > 0 && size.getHeight() > 0, "Height and/or width are less than 1");
	}

	/**
	 * Verify Lazy Element with parent Size test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementSizeWithParent() throws TimeoutException, InterruptedException {
		Dimension size = this.getDisabledInput().getSize();
		assertTrue(size.getWidth() > 152 && size.getHeight() > 21, "Height of greater than 22 and width of greater than 152, but got " + size);
	}

	/**
	 * Verify lazy element tag name test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementTagName() throws TimeoutException, InterruptedException {
		assertEquals("input", this.getInputBox().getTagName());
	}

	/**
	 * Verify lazy element with parent tag name test
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementTagNameWithParent() throws TimeoutException, InterruptedException {
		assertEquals("strong", this.getFlowerTableCaptionWithParent().getTagName());
	}

	/**
	 * Verify lazy element get the visible element
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementGetVisibleElement() {
		assertNotEquals(null, this.getInputBox().getRawVisibleElement());
	}

	/**
	 * Verify lazy element get the click-able element
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementGetClickableElement() {
		assertNotNull(this.getInputBox().getRawClickableElement());
	}

	/**
	 * Verify lazy element get the existing element
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementGetExistingElement() {
		assertNotNull(this.getInputBox().getRawExistingElement());
	}

	/**
	 * Verify lazy element to string
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementToString() {
		// Hard-coded userFriendlyName due to private access on LazyElement
		String stringValue = 
				this.getFlowerTableLazyElement().getBy().toString() + FLOWER_TABLE;

		assertEquals(stringValue, this.getFlowerTableLazyElement().toString());
	}

	/**
	 * Verify lazy element with parent to string
	 */
	@Test(groups=TestCategories.SELENIUM)
	public void lazyElementWithParentToString() {
		// Hard-coded userFriendlyName due to private access on LazyElement
		String stringValue =
				this.getFlowerTableLazyElement().getBy().toString() + FLOWER_TABLE +
						this.getFlowerTableCaptionWithParent().getBy().toString() + FLOWER_TABLE_CAPTION;

		assertEquals(stringValue, this.getFlowerTableCaptionWithParent().toString());
	}
}
