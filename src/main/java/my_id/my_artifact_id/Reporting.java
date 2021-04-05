package my_id.my_artifact_id;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Reporting {

	private static int maxNumberTest;
	private static int thread;

	private static TestReport readTestResults() {
		List<TestResult> testResultList = new ArrayList<>();
		
		System.out.println("Enter the maximum number of tests you want to create");
		Scanner scan = new Scanner(System.in);
		maxNumberTest = scan.nextInt();
		System.out.println("\nEnter how many test threads you want to create");
		thread = scan.nextInt();
		scan.close();
		

		for (int i=0; i<thread; i++) {

			testResultList.add(i, new TestResult(randomTestResultName(), TestStatus.randomEnumTestStatus()));

		}

		testResultList.sort((trl1, trl2) -> compareNatural(trl1.getTestName(), trl2.getTestName()));

		TestExecution testExecution1 = new TestExecution(testResultList);
		List<TestExecution> testExecutionList = new ArrayList<>();
		testExecutionList.add(testExecution1);
		TestReport testReport = new TestReport(testExecutionList);

		/**
		 * Writes the test results objects in a xml file
		 */
		try {
			JAXBContext jc = JAXBContext.newInstance(TestReport.class);
			Marshaller ms = jc.createMarshaller();
			ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ms.marshal(testReport, new FileOutputStream("test_report.xml"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		/**
		 * Reads the test results from the xml and prints the number of how a certain
		 * TestResult appeared with a certain status
		 * 
		 */
		TestReport testReportJAXB = new TestReport();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(TestReport.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			testReportJAXB = (TestReport) jaxbUnmarshaller.unmarshal(new File("test_report.xml"));

			List<TestExecution> testExecutionListJAXB = testReportJAXB.getTestExecutionList();

			for (TestExecution item : testExecutionListJAXB) {
				for (TestResult testResultItem : item.getTestResults()) {

					System.out.println(testResultItem.getTestName());
					System.out.println(testResultItem.getTestStatus());

				}
			}

			System.out.println("\nReading from xml");

			String[] convertTestResultListToStringArray2 = printReport2(testReportJAXB);

			String[] convertTestResultListToStringArrayWithoutDuplicates2 = Arrays
					.stream(convertTestResultListToStringArray2).distinct().toArray(s -> new String[s]);

			System.out.println("REPORT");
			System.out.println("      " + "  PASSED " + " FAILED" + "   NOT EXECUTED" + "   TOTAL");

			for(String strTemp : convertTestResultListToStringArrayWithoutDuplicates2) {
				System.out.println(strTemp);
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		/**
		 * Write in output.txt
		 */

		try {

			testReport.writeInFile(testReport.getTestExecutionList());

		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * Read from output.txt while creating a list<TestResult>
		 */

		List<TestResult> testResultListFromOutputFile = new ArrayList<TestResult>();

		try {
			testResultListFromOutputFile = testReport.readFromFile();

			System.out.println("\nReading from output.txt");
			for(TestResult item : testResultListFromOutputFile) {
				System.out.println(item.getTestName() +" : "+item.getTestStatus());
			}

			TestExecution testExecutionFromOutput = new TestExecution(testResultListFromOutputFile);
			List<TestExecution> testExecutionListFromOutput = new ArrayList<>();
			testExecutionListFromOutput.add(testExecutionFromOutput);
			TestReport testReportFromOutput = new TestReport(testExecutionListFromOutput);

			/**
			 * Writes the Report into text file : output2
			 * 
			 */

			printReport2(testReportFromOutput);

			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter("output2.txt"));

				writer.write("Report");
				writer.write("\n        " + "   PASSED " + " FAILED " + "  NOT EXECUTED" + "   TOTAL");
				writer.write("\n");

				String[] convertTestResultListToStringArray = printReport2(testReportFromOutput);

				/**
				 * Searches duplicates from cString[] convertTestResultListToStringArray and
				 * deletes the duplicates. The remainders are written in output2.txt
				 */

				String[] convertTestResultListToStringArrayWithoutDuplicates = Arrays
						.stream(convertTestResultListToStringArray).distinct().toArray(s -> new String[s]);
				
				for(String strTemp : convertTestResultListToStringArrayWithoutDuplicates) {
					writer.write("\n"+strTemp);
				}

				writer.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println("Success...");

		} catch (IOException e) {

			e.printStackTrace();
		}

		System.out.println("Return testReport");
		return testReport;
	}

	/**
	 * Method returns a random String (Test+number) 
	 */
	public static String randomTestResultName() {
		String t1 = "Test";
		int randomNum = ThreadLocalRandom.current().nextInt(1, maxNumberTest + 1);
		String t2 = Integer.toString(randomNum);
		return t1 + t2;
	}

	/**
	 * Compares the length of numbers found in the two strings. When encountering
	 * two numbers of the same length the alphanumeric compare is resumed as normal.
	 * 
	 */
	public static int compareNatural(String testResultName1, String testResultName2) {
		int lengthOfTestResultName1 = testResultName1.length();
		int lengthOfTestResultName2 = testResultName2.length();
		int browseTestResultName1 = 0;
		int browseTestResultName2 = 0;
		while (true) {
			if (browseTestResultName1 == lengthOfTestResultName1)
				return browseTestResultName2 == lengthOfTestResultName2 ? 0 : -1;
			if (browseTestResultName2 == lengthOfTestResultName2)
				return 1;
			if (testResultName1.charAt(browseTestResultName1) >= '0'
					&& testResultName1.charAt(browseTestResultName1) <= '9'
					&& testResultName2.charAt(browseTestResultName2) >= '0'
					&& testResultName2.charAt(browseTestResultName2) <= '9') {
				int na = 0;
				int nb = 0;
				while (browseTestResultName1 < lengthOfTestResultName1
						&& testResultName1.charAt(browseTestResultName1) == '0')
					browseTestResultName1++;
				while (browseTestResultName1 + na < lengthOfTestResultName1
						&& testResultName1.charAt(browseTestResultName1 + na) >= '0'
						&& testResultName1.charAt(browseTestResultName1 + na) <= '9')
					na++;
				while (browseTestResultName2 < lengthOfTestResultName2
						&& testResultName2.charAt(browseTestResultName2) == '0')
					browseTestResultName2++;
				while (browseTestResultName2 + nb < lengthOfTestResultName2
						&& testResultName2.charAt(browseTestResultName2 + nb) >= '0'
						&& testResultName2.charAt(browseTestResultName2 + nb) <= '9')
					nb++;
				if (na > nb)
					return 1;
				if (nb > na)
					return -1;
				if (browseTestResultName1 == lengthOfTestResultName1)
					return browseTestResultName2 == lengthOfTestResultName2 ? 0 : -1;
				if (browseTestResultName2 == lengthOfTestResultName2)
					return 1;

			}
			if (testResultName1.charAt(browseTestResultName1) != testResultName2.charAt(browseTestResultName2))
				return testResultName1.charAt(browseTestResultName1) - testResultName2.charAt(browseTestResultName2);
			browseTestResultName1++;
			browseTestResultName2++;
		}
	}

	/**
	 * Creates a new list<TestResult> with the field testStatus as default : TestStatus.Unknown
	 * 
	 * @param testResultList is the list with the most TestResult instantiate objects
	 * @return a list<TestResult> to execute handle/manipulate the main List<TestResult>
	 */
	private static List<TestResult> manipulateList(List<TestResult> testResultList) {
		List<TestResult> testResultListHandler = new ArrayList<>();
		
		for(TestResult item : testResultList) {
			TestResult tr = new TestResult(item.getTestName(),TestStatus.UNKNOWN);
			testResultListHandler.add(tr);
		}
		return testResultListHandler;
	}

	/**
	 * @param testReport REPORT 
	 *                         PASSED FAILED NOT_EXECUTED TOTAL 
	 *                  test1    3      2         1        5 
	 *                  test2    3      2         1        5 
	 *                  test3    4      2         0        6
	 * 
	 */

	public static void printReport(TestReport testReport) {
		System.out.println("\nThe test report counts " + testReport.getTestExecutionList().size() + " executions!!");

		System.out.println("REPORT");
		System.out.println("      " + " PASSED " + " FAILED " + "  NOT EXECUTED" + " TOTAL");

		List<TestResult> testResultsReport = Reporting
				.manipulateList(testReport.getTestExecutionList().get(0).getTestResults());

		for (TestExecution item : testReport.getTestExecutionList()) {
			for (TestResult result : item.getTestResults()) {

				result = result.countStatus(result);
				for (int i = 0; i < testResultsReport.size(); i++) {
					if (testResultsReport.get(i).getTestName().equals(result.getTestName())) {
						testResultsReport.get(i).setStoreStatusPartTwo(testResultsReport.get(i).getStoreStatus(),
								result.getStoreStatus());
					}
				}
			
			
			}
		}

		Reporting.printFinal(testResultsReport);

	}

	/**
	 * Method for formatting and displaying a TestResult instance
	 * 
	 * @param result
	 */
	private static void printInFormat(TestResult result) {
		int[] p = result.getStoreStatus();
		System.out.println(result.getTestName() + " :     " + p[0] + "       " + p[1] + "              " + p[2]
				+ "     " + (p[0] + p[1] + p[2]));
	}

	/**
	 * Method for formatting and displaying a list of instances of type TestResult
	 * 
	 * @param testResultList
	 */
	private static void printFinal(List<TestResult> testResultList) {

		
		for(TestResult item : testResultList) {
			Reporting.printInFormat(item);
		}
	}

	/**
	 * The next 4 methods are used to write the List<TestResult> in output2.txt &
	 * test.xml Are similar to the last methods, only difference is that they return
	 * the list and converts the fields as String.
	 * 
	 * @param testResult
	 * @return
	 */
	private static String[] printFinal2(List<TestResult> testResultList) {

		String storeString[] = new String[testResultList.size()];

		for (int i = 0; i < testResultList.size(); i++) {

			storeString[i] = (Reporting.printInFormat2(testResultList.get(i)));

		}
		return storeString;
	}

	private static String printInFormat2(TestResult testResult) {
		int[] p = testResult.getStoreStatus();
		return (testResult.getTestName() + " :    " + p[0] + "       " + p[1] + "              " + p[2] + "     "
				+ (p[0] + p[1] + p[2]));
	}

	public static String[] printReport2(TestReport testReport) {

		List<TestResult> testResultList = Reporting
				.manipulateList(testReport.getTestExecutionList().get(0).getTestResults());

		for (TestExecution item : testReport.getTestExecutionList()) {
			for (TestResult result : item.getTestResults()) {

				result = result.countStatus(result);
				for (int i = 0; i < testResultList.size(); i++) {
					if (testResultList.get(i).getTestName().equals(result.getTestName())) {
						testResultList.get(i).setStoreStatusPartTwo(testResultList.get(i).getStoreStatus(),
								result.getStoreStatus());
					}
				}

			}
		}

		return Reporting.printFinal2(testResultList);
	}

	/**
	 * Is called in class Main
	 */
	public static void main() {
		TestReport tr = readTestResults();
		printReport(tr);

	}

}
