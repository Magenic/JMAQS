# <img src="resources/jmaqslogo.jpg" height="32" width="32"> Generic Waits

## Overview
JMAQS includes a class of generic wait methods that can assist with waiting until a condition has been met. This provides additional flexibility when special handling is required.
We use waits to test if a condition was met within a certain amount of time in milliseconds, to do this we use wait time which is how long you wait between retries.
These retries are bound by the timeout in milliseconds and are set in the configuration.

We use Wait for if we want to throw an exception if the desired state is not met.  

We use Wait until if we want the boolean was met within the permitted timeout.

## Wait Until
### Wait Until a Func Returns True
This function will return true if the method returns true, false if the method times out or times out waiting for the method to return true.
##### Written as
```java
boolean waitUntil(Predicate<T> waitForTrue, T arg)
```
##### Example
```java
// A method that will return a false boolean
private bool falseMethod() {
    return false;
}

// The falseMethod will always return false, as the method will always return false.  The Generic Wait will return false.
bool methodResults = GenericWait.waitUntil(falseMethod);  
GenericWait.waitUntil(falseMethod());
```

### Wait Until Func With Argument Type T and Returns Type T
This function will return true if the func returns true, false if the func times out or times out waiting for the func to return true.

This wait accepts an argument that will be passed into the test method.
##### Written As
```java
boolean waitUntil(Predicate<T> waitForTrue, T arg)
```
##### Example

```java
// A method that will return a false boolean
private bool isParamTestString(string testString) {
    return testString.equals("Test String");
}

// The isParamTestString will always return false, as the method will always return false.  
// The Generic Wait will return false.
bool textResults = GenericWait.waitUntil(this::isParamTestString, teststring + "3");
```
### Wait For Method with Argument to Return Type
This function will throw an exception if it times out before the func returns true.

This wait accepts an argument of the type, this argument will be passed to the function.
##### Written As
```java
T waitFor(Function<U, T> waitFor, U arg)
```
##### Example
```java
// A method that will return a false boolean
private bool isParamTestString(string testString)
{
    return testString.Equals("Test String");
}

// The isParamTestString will always return false, as the method will always return false.  
// The generic wait will throw an exception if it times-out
bool textResults = GenericWait.waitFor((p) -> p.equals(this.IsParamTestString, "Bad"));
```

## Wait For

### Wait For a Func to Return True
This function will throw an exception if the method times out.

##### Written As
```java
T waitFor(Supplier<T> waitFor)
```
##### Example
```java 
// A method that will return a false boolean
private boolean falseMethod() {
    return false;
}

// The False method will always return false.  
// The Generic Wait will timeout and then throw an exception.
GenericWait.waitFor(falseMethod);

```
The wait time and time-out can be explicitly set, as well as the ability to suppress an exception being thrown.
##### Written As
```java
boolean wait(BooleanSupplier waitForTrue, long retryTime, long timeout, boolean throwException)
```
##### Examples
```java
private bool isFalseBool()
{
    return false;
}

// This will wait for the method IsFalseBool to return true, running and re-running the method every 100 milliseconds
// Once 500 milliseconds has passed in total, it will continue without throwing an exception
GenericWait.wait(this.isFalseBool, 100, 500, false);

// This will wait for the method isFalseBool to return true, running and re-running the method every 100 milliseconds
// Once 500 milliseconds has passed in total, it will throw an exception
GenericWait.wait(this.isFalseBool, 100, 500, true);
```

### Wait For a Func to Return Type, And Return That Type
This function will throw an exception if the method times out.

##### Written As
```java
T waitFor(Supplier<T> waitFor)
```

##### Examples
```java
// A method that will return a false boolean
private bool isParamTestString() {
    return false;
}

// The IsParamTestString will always return false, as the method will always return false.  
// The generic wait will throw an exception if it times-out.
bool textResults = GenericWait.waitFor(this.IsParamTest);
```

```java
// A method that will return a false boolean
private string isParamTestString()
{
    return "stringText";
}

// The IsParamTestString will always return false, as the method will always return false.  
// The generic wait will throw an exception if it times-out.
string textResults = GenericWait.waitFor(this.isParamTest);
```
The wait time and time-out can also be explicitly set.

#### Written as
```java
T wait(Supplier<T> waitFor, long retryTime, long timeout)
```
#### Examples
```java
// A method that will return a false boolean
private string isParamTestString() {
    return "stringText";
}

// The isParamTestString will always return false, as the method will always return false.  The generic wait will throw an exception if it times-out.
object textResults = GenericWait.WaitFor(this.isParamTest, TimeSpan.FromMilliseconds(100), TimeSpan.FromMilliseconds(500));
```

### Wait For an Expected Argument Type, And Return a Type
--------------
This function will return the type passed in or throw an exception if the method times out.

#### Written As
```java
   * @param <T>     the type parameter
   * @param <U>     the type parameter
   * @param waitFor the wait for
   * @param arg     the arg
T waitFor(Function<U, T> waitFor, U arg)
```
#### Examples
```java
private List<MailMessage> getSearchResults(params object[] args){
    List<MailMessage> messageList = new List<MailMessage>();
    for (Lazy<MailMessage> message : this.EmailConnection.SearchMessages((SearchCondition)args[0], (bool)args[1], (bool)args[2])) {
        messageList.add(message.Value);
    }

    for (MailMessage message : messageList) {
         if (message.getSubject == null) {
            throw new Exception("Invalid results - found null subject");
        }
    }
    return messageList;
}

public virtual List<MailMessage> searchMessages(SearchCondition condition, boolean headersOnly, bool markRead) {
    object[] args = { condition, headersOnly, markRead };
    return GenericWait.waitFor(this.getSearchResults, args);
}
```