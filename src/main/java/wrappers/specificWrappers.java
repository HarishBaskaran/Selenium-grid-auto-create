package wrappers;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/*
* *Author - Harish Baskaran - 2019
*/
public class specificWrappers extends genericWrappers {

	boolean remote = false;

	@Parameters({ "remote", "remoteUrl", "headless" })
	@BeforeSuite
	public void beforeSuite(@Optional("false") boolean remote, @Optional("") String remoteUrl,
			@Optional("false") boolean headless) {
		loadObjects();
		this.remote = remote;
		invokeApp(remote, remoteUrl, headless);
	}

	@BeforeClass
	public void beforeClass() {
	}

	@AfterClass(alwaysRun = true)
	public void defaultAfterClass() {
		quitBrowser();
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {
		deloadObjects(remote);
	}
}