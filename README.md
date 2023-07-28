# Test Reporting Project Documentation

## Overview
The Test Reporting Project is a Java application that generates random test names along with their corresponding test statuses. It then stores these test results in various formats such as XML and text files for reporting and analysis purposes. The project consists of six classes that work together to achieve this functionality.

## Classes Overview

### 1. [Main](https://github.com/Beltag-Paula/TestReport/blob/main/src/main/java/my_id/my_artifact_id/Main.java)
- Entry point of the Java application.
- Calls the `Reporting.main()` method to start the reporting process.

### 2. [Reporting](https://github.com/Beltag-Paula/TestReport/blob/main/src/main/java/my_id/my_artifact_id/Reporting.java)
- Manages test reporting functionalities.
- Reads user input for the maximum number of tests and the number of test threads.
- Generates random test results and sorts them in a natural order.
- Writes test results to an XML file (`test_report.xml`).
- Reads test results from the XML file and displays them.
- Writes test results to `output.txt` and reads them back from the file.

### 3. [TestExecution](https://github.com/Beltag-Paula/TestReport/blob/main/src/main/java/my_id/my_artifact_id/TestExecution.java)
- Represents a test execution with a list of `TestResult` objects.
- Contains methods for getting and setting the test results and execution time.
- Provides a method to compare and count test statuses between two `TestResult` objects.

### 4. [TestReport](https://github.com/Beltag-Paula/TestReport/blob/main/src/main/java/my_id/my_artifact_id/TestReport.java)
- Represents a test report with a list of `TestExecution` objects.
- Contains methods for getting and setting the test execution list.
- Provides methods to write test results to `output.txt` and read them back from the file.

### 5. [TestResult](https://github.com/Beltag-Paula/TestReport/blob/main/src/main/java/my_id/my_artifact_id/TestResult.java)
- Represents a test result with the test name, test status, and a store status array.
- Provides methods for getting and setting the test name and status.
- Contains methods for counting and storing test statuses in the store status array.

### 6. [TestStatus](https://github.com/Beltag-Paula/TestReport/blob/main/src/main/java/my_id/my_artifact_id/TestStatus.java) (Enum)
- Represents different test statuses.
- Provides methods to get the exit code corresponding to each status and to get a random test status.

## Workflow

1. The application starts with the `Main` class, which serves as the entry point. It calls the `Reporting.main()` method to begin the test reporting process.

2. In the `Reporting` class, the user is prompted to enter the maximum number of tests to be created and the number of test threads. These values are taken as input using the `Scanner` class.

3. Using the provided inputs, the `Reporting` class generates random test results with names in the format "TestX" (where X is a random number between 1 and the maximum number of tests). Test statuses are also randomly assigned using the `TestStatus.randomEnumTestStatus()` method.

4. The generated test results are sorted in a natural order based on their test names using the `compareNatural` method, ensuring that Test1 comes before Test10, for example.

5. The sorted test results are then stored in an XML file named `test_report.xml` using JAXB (Java Architecture for XML Binding).

6. The `TestReport` class is responsible for reading the test results from the XML file and displaying them.

7. The `Reporting` class also writes the test results to a text file named `output.txt` using the `writeInFile` method.

8. The `TestReport` class reads the test results from `output.txt` and displays them in the console.

9. The `TestResult` class is used to represent each individual test result with its name, status, and a store status array. The store status array keeps track of the number of tests with different statuses (PASSED, FAILED, NOT_EXECUTED) for each test name.

10. The `TestExecution` class represents a test execution, which consists of a list of `TestResult` objects. It provides methods to manipulate and analyze the test results within the execution.

## How to Use
1. Clone the repository and open the project in your preferred Java development environment.

2. Compile the Java files to build the project.

3. Run the `Main` class to generate random test results and see the test reporting process in action.


## Conclusion
The Test Reporting Project demonstrates how to generate and manage random test results, store them in different formats, and perform basic reporting tasks. With its modular design and clear organization, the project can serve as a foundation for building more extensive test reporting systems and similar applications.

For more detailed information on each class's functionalities, methods, and usage, please refer to the code comments and wiki pages (if available).
