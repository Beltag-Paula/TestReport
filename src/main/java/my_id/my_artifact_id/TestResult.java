package my_id.my_artifact_id;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TestResult")
public class TestResult {
	private String testName;
	private TestStatus testStatus;
	/**
	 * storeStatus[0] retains the number of tests with TestStatus.PASSED
	 * storeStatus[1] retains the number of tests with TestStatus.FAILED
	 * storeStatus[2] retains the number of tests with TestStatus.NOT_EXECUTED I
	 * have added the variable storeStatus[] for these certain reasons - it is
	 * necessary to attach the variable in method : TestResult.countStatus to count
	 * the number of the tests that have been failed/not executed/passed - and to
	 * count the stored numbers in method Reporting.printReport
	 * 
	 */
	private int[] storeStatus = new int[3];
	public char[] ge;

	/**
	 * Explicit constructor with parameters Assigns testName & testStatus to the
	 * fields with the same name
	 * 
	 * @param testName
	 * @param testStatus
	 */
	public TestResult(String testName, TestStatus testStatus) {
		this.testName = testName;
		this.testStatus = testStatus;
		storeStatus[0] = 0;
		storeStatus[1] = 0;
		storeStatus[2] = 0;
	}

	public TestResult() {
		super();
	}

	/**
	 * Get method that returns an instance of a storeStatus[] with 3 positions In
	 * the position array[0] it will be stored the number of TestStatus.PASSED
	 * array[1] it will be stored the number of TestStatus.FAILED array[2] it will
	 * be stored the number of TestStatus.NOT_EXECUTED
	 * 
	 * @return
	 */
	public int[] getStoreStatus() {
		return this.storeStatus;
	}

	/**
	 * This setter sums the value of storeStatus[] 's positions and retains the
	 * values in.
	 * 
	 * @param testResult1
	 * @param testResult2
	 */
	public void setStoreStatusPartTwo(int[] testResult1, int[] testResult2) {
		this.storeStatus[0] = testResult1[0] + testResult2[0];
		this.storeStatus[1] = testResult1[1] + testResult2[1];
		this.storeStatus[2] = testResult1[2] + testResult2[2];
	}

	public void setStoreStatusPartOne(int[] array) {
		this.storeStatus[0] = array[0];
		this.storeStatus[1] = array[1];
		this.storeStatus[2] = array[2];
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public TestStatus getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(TestStatus testStatus) {
		this.testStatus = testStatus;
	}

	/**
	 * Depending on the value from result.getTestStatus the method converts the
	 * status of TestStatus of a certain instantiated object TestResult and stores
	 * it in storeStatus[]
	 * 
	 * @param result
	 * @return
	 */
	public TestResult countStatus(TestResult result) {
		int passed = 0;
		int failed = 0;
		int notExe = 0;

		passed = countPassStatus(result.testName);
		failed = countFailStatus(result.testName);
		notExe = countNotExeStatus(result.testName);

		result.storeStatus[0] = passed;
		result.storeStatus[1] = failed;
		result.storeStatus[2] = notExe;
		return result;

	}

	public int countPassStatus(String name) {
		int passed = 0;
		if (this.testName == name && this.testStatus == TestStatus.PASSED) {
			passed++;
		}

		return passed;
	}

	public int countFailStatus(String name) {
		int failed = 0;
		if (this.testName == name && this.testStatus == TestStatus.FAILED) {
			failed++;
		}

		return failed;
	}

	public int countNotExeStatus(String name) {
		int notExe = 0;
		if (this.testName == name && this.testStatus == TestStatus.NOT_EXECUTED) {
			notExe++;
		}

		return notExe;
	}

}