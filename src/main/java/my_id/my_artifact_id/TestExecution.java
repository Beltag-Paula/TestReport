package my_id.my_artifact_id;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "TestExecution")
public class TestExecution {

	private Date executionTime;
	private List<TestResult> testResults;

	public TestExecution(List<TestResult> testResults) {
		this.testResults = testResults;
	}

	public TestExecution() {
		// TODO Auto-generated constructor stub
	}

	public List<TestResult> getTestResults() {
		return testResults;
	}

	public void setTestResults(List<TestResult> testResults) {
		this.testResults = testResults;
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
	}

	/**
	 * Method compares two objects of type TestResult. If they have the same name
	 * than it will stored their statuses and if also their statuses are the same
	 * increment the number and stored it in int[] storeStatus from TestResult.
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 */
	public TestResult count(TestResult t1, TestResult t2) {
		TestResult testResultCount = new TestResult("", TestStatus.UNKNOWN);
		for (int i = 0; i < testResults.size(); i++) {
			if (t1.getTestName().equals(t2.getTestName())) {
				testResultCount.setTestName(t1.getTestName());
				if (t1.getTestStatus().equals(t2.getTestStatus())) {
					testResultCount.setTestStatus(t1.getTestStatus());

					t1 = t1.countStatus(t1);
					t2 = t2.countStatus(t2);
					testResultCount.setStoreStatusPartTwo(t1.getStoreStatus(), t2.getStoreStatus());
				} else {

					t1 = t1.countStatus(t1);
					testResultCount.setStoreStatusPartOne(t1.getStoreStatus());
				}

			}

		}

		return testResultCount;
	}

}
