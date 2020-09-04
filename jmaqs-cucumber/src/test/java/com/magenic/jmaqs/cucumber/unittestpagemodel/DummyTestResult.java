package com.magenic.jmaqs.cucumber.unittestpagemodel;

import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.util.Set;

/**
 * A dummy test result class for unit testing
 */
public class DummyTestResult implements ITestResult {

    /**
     * The test context
     */
    private ITestContext testContext;

    /**
     * Initializes a new instance of the DummyTestResult class
     */
    public DummyTestResult() {
        this.testContext = null;
    }

    /**
     * Initializes a new instance of the DummyTestResult class
     * @param testContext The desired test context
     */
    public DummyTestResult(ITestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Gets the test status
     * @return Will always be a success
     */
    @Override
    public int getStatus() {
        return this.SUCCESS;
    }

    /**
     * Gets the test context
     * @return The test context
     */
    @Override
    public ITestContext getTestContext() {
        return testContext;
    }

    @Override
    public void setStatus(int i) {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public ITestNGMethod getMethod() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public Object[] getParameters() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public void setParameters(Object[] objects) {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public IClass getTestClass() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public Throwable getThrowable() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public void setThrowable(Throwable throwable) {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public long getStartMillis() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public long getEndMillis() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public void setEndMillis(long l) {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public boolean isSuccess() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public String getHost() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public Object getInstance() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public Object[] getFactoryParameters() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public String getTestName() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public String getInstanceName() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public void setTestName(String s) {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public boolean wasRetried() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public void setWasRetried(boolean b) {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public int compareTo(ITestResult o) {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public Object getAttribute(String s) {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public void setAttribute(String s, Object o) {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public Set<String> getAttributeNames() {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }

    @Override
    public Object removeAttribute(String s) {
        throw new UnsupportedOperationException("This method is intentionally left blank");
    }
}