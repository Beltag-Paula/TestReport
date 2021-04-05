
package my_id.my_artifact_id;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "TestReport")
public class TestReport {
	private List<TestExecution> testExecutionList;

	public TestReport() {
		super();
	}

	public TestReport(List<TestExecution> testExecutionList) {
		this.testExecutionList = testExecutionList;

	}

	@XmlElement
	public List<TestExecution> getTestExecutionList() {
		return testExecutionList;
	}

	public void setTestExecutionList(List<TestExecution> testExecutionList) {
		this.testExecutionList = testExecutionList;
	}

	/**
	 * The method will create a text file output.txt where will be stored the name
	 * and status of TestResult instantiated objects.
	 * 
	 * @param testExecutionList
	 * @throws IOException
	 */
	public void writeInFile(List<TestExecution> testExecutionList) throws IOException {
		PrintWriter pw = null;
		FileOutputStream fo = null;
		File file = null;

		try {
			file = new File("output.txt");
			pw = new PrintWriter(new FileOutputStream(file, true));
			fo = new FileOutputStream(file);

			for (TestExecution testExecutionListItem : testExecutionList) {
				for (TestResult testResultItem : testExecutionListItem.getTestResults()) {
					pw.write(testResultItem.getTestName() + " " + testResultItem.getTestStatus() + "\n");

				}
			}
		} finally {
			pw.flush();
			pw.close();
			fo.close();

		}
	}

	
	/**The method reads from while output.txt objects of type TestResult and returns them as a list<TestResult>
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public List<TestResult> readFromFile() throws FileNotFoundException {
		Scanner s = new Scanner(new File("output.txt"));

		List<TestResult> testResultWrittenFromFile = new ArrayList<TestResult>();

		while (s.hasNextLine()) {

			String[] split = s.nextLine().split(" ");

			testResultWrittenFromFile.add(new TestResult(split[0], TestStatus.valueOf(split[1])));
		}

		return testResultWrittenFromFile;

	}

}
