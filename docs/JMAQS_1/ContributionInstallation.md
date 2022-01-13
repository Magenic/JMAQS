# <img src="resources/jmaqslogo.jpg" height="32" width="32"> JMAQS Contribution Installation Guide

---

## I. Cloning JMAQS Repository

JMAQS – Installation Guide via IntelliJ and GitHub

1. Navigate to [https://github.com/Magenic/JMAQS](https://github.com/Magenic/JMAQS)
2.  Click the **Clone or download button**
3.  Copy the URL - [https://github.com/Magenic/JMAQS.git](https://github.com/Magenic/JMAQS.git)  
    ![alt text](../resources/installationImages/MagenicJMAQS.png)

4. Open IntelliJ (This guide is using version 2019.2.2)  
   Note:  You can download IntelliJ Idea from: [https://www.jetbrains.com/idea/download/#section=windows](https://www.jetbrains.com/idea/download/#section=windows)
5. Click **Check out from Version Control**
6. Select **Git**  
   ![alt text](../resources/installationImages/IntelliJ_Idea.png)

7. Paste in the JMAQS Git URL
8. Select a directory for the project
9. Click **Clone**  
   ![alt text](../resources/installationImages/clone01.png)

10. Click  **No**  
    ![alt text](../resources/installationImages/clone02.png)


---  
## II. Settings and Configurations – JMAQS Framework

1. Click **Import Project**  
   ![alt text](../resources/installationImages/IntelliJ_Idea_Import.png)

2. Navigate to the project directory and select the **Framework** folder
3. Click **OK**  
   ![alt text](../resources/installationImages/FileDirImport.png)

4. Select **Import project from external model**
5. Select **Maven**
6. Click **Next**  
   ![alt text](../resources/installationImages/ImportProject.png)

7. Check **Search for projects recursively**
8. Check **Import Maven projects automatically**
9. Check **Create module groups for multi-module Maven projects**
10. Click **Next**  
    ![alt text](../resources/installationImages/SearchProject.png)

11. Verify **com.magenic.jmaqs:jmaqs-framework** is selected
12. Click **Next**  
    ![alt text](../resources/installationImages/jmaqsframework.png)

13. Select the project SDK
14. Click **Next**  
    ![alt text](../resources/installationImages/projectSDK.png)

15. Verify the project name and file location
16. Click **Finish**  
    ![alt text](../resources/installationImages/projectLocation.png)

17. When finished loading, the project structure should have Framework at the top-level:  
    ![alt text](../resources/installationImages/FrameworkTop.png)

18. Framework expanded:  
    ![alt text](../resources/installationImages/FrameworkExpanded.png)

---   
## III. Code Style Settings - JMAQS Formatter

1.  Navigate to File > **Settings**
2.   Navigate to Editor > **Code Style**
3.   Select the gear icon next to Scheme
4.   Select Import Scheme > **Eclipse XML Profile**  
     ![alt text](../resources/installationImages/EclipseXMLprofile.png)

5.   Navigate to the project directory and select **maqs_formatter.xml**
6.   Click **OK**  
     ![alt text](../resources/installationImages/MAQSformatter.png)

7.   Click **OK**  
     ![alt text](../resources/installationImages/EclipseJMAQSbase.png)

8.   A success popup should be displayed
9.    Click **Apply**  
      ![alt text](../resources/installationImages/CodeStyle.png)

####Plugins: CheckStyle-IDEA and SonarLint
1.   Navigate to (File > Settings) **Plugins**
2.   Enter **Checkstyle** into the search bar(Note:Make sure Marketplace tab is selected)
3.   Select **CheckStyle-IDEA**
4.   Click **Install**  
     ![alt text](../resources/installationImages/plugins1.png)

5.   If the Third-party Plugins Privacy Note popup is displayed, read the popup and click **Accept**  
     ![alt text](../resources/installationImages/plugins2.png)

6.   Restart IDE should be displayed next to CheckStyle-IDEA after installation  
     ![alt text](../resources/installationImages/plugins3.png)

7.   Enter **SonarLint** into the search bar
8.   Select **SonarLint**
9.   Click **Install**  
     ![alt text](../resources/installationImages/InstallSonarlint.png)

10.   **Restart IDE** should be displayed next to SonarLint after installation
11.  Click **OK** or **Restart IDE**  
     ![alt text](../resources/installationImages/RestartIDE.png)

12.  Click **Restart**  
     ![alt text](../resources/installationImages/RestartConfirm.png)

13.  When the project has reloaded, navigate to File > **Settings**
14.  Navigate to Other Settings > **Checkstyle**
15.  Set the **Checkstyle version to 7.6.1**
16.  Set the **Scan Scope** to **All sources (including tests)**
17.  Under Configuration File, click the **+** (add) button  
     ![alt text](../resources/installationImages/AddCheckStyle.png)

18.  Click **Browse** under **Use a local Checkstyle file**
19.  Navigate to the project directory, expand the Framework folder, select **maqs_checks.xml**
20.  Click **OK**  
     ![alt text](https://magenic365.sharepoint.com/sites/JMAQS/Shared%20Documents/General/Images/JMAQS_Checkstyle.png)

21.  Enter a Description
22.  Check **Store relative to project location**
23.  Click **Next**  
     ![alt text](../resources/installationImages/MAQScheckstyleDescription.png)

24.  Click **Finish**  
     ![alt text](../resources/installationImages/Finish.png)


25.  Check the **Active** box next to the added Configuration File
26.  Click **Apply**
27.  Click **OK**  
     ![alt text](../resources/installationImages/TheEnd.png) 