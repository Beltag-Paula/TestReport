package my_id.my_artifact_id;

import java.util.Random;



public enum TestStatus {

	PASSED(0), VALIDATION_FAILED(1), FAILED(2), NOT_COMPLETED(3), NOT_EXECUTED(4), UNABLE_PARSE_ARGUMENTS(5),
	UNKNOWN(6);

	private final int exitCode;

	TestStatus(int exitCode) {
		this.exitCode = exitCode;
	}

	public int getExitCode() {
		return exitCode;
	}

	public static TestStatus fromExitCode(int exitCode) {

		for (TestStatus testStatus : TestStatus.values()) {
			if (testStatus.getExitCode() == exitCode) {
				return testStatus;
			}
		}

		return TestStatus.UNKNOWN;
	}

	/**
	 * The method returns a random enumeration TestStatus value that will be used to
	 * instantiate TestResult objects within a for loop
	 * 
	 * @return
	 */
	public static TestStatus randomEnumTestStatus() {
		return TestStatus.values()[new Random().nextInt(TestStatus.values().length)];
	}

}
