package parallelExcution;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ParallelTesting {
	
	WebDriver driver;
	DesiredCapabilities capabilities;
	
	@BeforeMethod
	public void setUp() {
		
		capabilities=new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
		capabilities.setCapability(CapabilityType.PLATFORM, Platform.WIN10);
		
		
	
/*		capabilities.setCapability("name", "myTestName");
		capabilities.setCapability("build", "myTestBuild");
		capabilities.setCapability("idleTimeout", 150);
		capabilities.setCapability("screenResolution", "1280x720");
		capabilities.setCapability("recordVideo", false);*/
		
		Cookie cookie = new Cookie("zaleniumTestPassed", "true");
	    driver.manage().addCookie(cookie);
	}
	
	@Test
	public void test() throws Exception {
		
		String huburl="http://localhost:4444/wd/hub";
		driver=new RemoteWebDriver(new URL(huburl), capabilities);
		
		driver.get("http://www.freecrm.com");
		System.out.println(driver.getTitle());
	}

}
