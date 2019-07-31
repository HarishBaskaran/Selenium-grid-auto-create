package wrappers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import utils.Endpoints;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;

/*
* *Author - Harish Baskaran - 2019
*/
public class genericWrappers {

	Process hub = null;
	List<Process> nodes = new ArrayList<Process>();
	int nodesCount = 10;

	public void loadObjects() {
		try {
			nodesCount = Endpoints.getNodesCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void quitBrowser() {
		WebDriverRunner.closeWebDriver();
	}

	public void invokeApp(boolean remote, String remoteUrl, boolean headless) {
		try {

			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setBrowserName("chrome");
			cap.setPlatform(Platform.ANY);
			cap.setCapability("chrome.switches", Arrays.asList("--disable-notifications"));

			Configuration.browserCapabilities = cap;
			Configuration.browser = "chrome";
			Configuration.timeout = 60000;

			if (remote && !remoteUrl.isEmpty()) {
				Configuration.remote = remoteUrl;
			} else {
				setupSeleniumGrid();
				Configuration.remote = "http://localhost:4444/wd/hub";
			}

			if (headless) {
				Configuration.headless = true;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void deloadObjects(boolean remote) {
		if (!remote) {
			destroySeleniumGrid();
		}

	}

	public void setupSeleniumGrid() {
//Initiate hub
		hub = initiateProcess("cmd /c start grid.bat");
		Selenide.sleep(3000);
//Initiate nodes
		for (int i = 1; i <= nodesCount ; i++) {
			nodes.add(initiateProcess("cmd /c start node.bat " + (9150 + i)));
			Selenide.sleep(1000);
		}
		Selenide.sleep(3000);
	}

	public void destroySeleniumGrid() {
//Destroy nodes
		for (int i = 1; i <= nodesCount ; i++) {
			deleteProcess("cmd /c FOR /F \"tokens=5 delims= \" %P IN ('netstat -a -n -o ^| findstr :" + (9150 + i)
					+ "') DO TaskKill.exe /PID %P /F");
			Selenide.sleep(1000);
		}

//Destroy hub
		deleteProcess(
				"cmd /c FOR /F \"tokens=5 delims= \" %P IN ('netstat -a -n -o ^| findstr :4444') DO TaskKill.exe /PID %P /F");

//Close all command prompt
		deleteProcess("cmd /c taskkill /IM cmd.exe /F");

//Remove all chrome driver
		deleteProcess("cmd /c taskkill /IM chromedriver.exe /F");

	}

	public Process initiateProcess(String command) {
		String file = System.getProperty("user.dir") + "/src/main/java/grid/";
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command, null, new File(file));
		} catch (IOException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	public Process deleteProcess(String command) {
		String file = System.getProperty("user.dir") + "/src/main/java/grid/";
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command, null, new File(file));
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return p;
	}
}
