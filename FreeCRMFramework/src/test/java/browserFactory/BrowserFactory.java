package browserFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import dataProvider.ConfigDataProvider;

public class BrowserFactory {

	public static WebDriver driver;
	public static File folder;
	
	public static WebDriver getBrowser(String browser) throws IOException {
		
		ConfigDataProvider config = new ConfigDataProvider();
		
		if (browser.equalsIgnoreCase("Chrome")) 
		{
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-extensions");
			options.addArguments("disable-geolocation");
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);

			// 0-default, 1-allow, 2-block
			prefs.put("profile.default_content_setting_values.notifications", 2);
			
			folder=new File(UUID.randomUUID().toString());
			folder.mkdir();
			
			prefs.put("profile.default_content_settings.popups", "0");
			prefs.put("download.default_directory", folder.getAbsolutePath());
			options.setExperimentalOption("prefs", prefs);
		
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);

			System.setProperty("webdriver.chrome.driver", config.getChromePath());
			driver = new ChromeDriver(capabilities);

		} 
		else if (browser.equalsIgnoreCase("IE")) {
			
			DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			System.setProperty("webdriver.ie.driver", config.getIEPath());
			driver = new InternetExplorerDriver(caps);
		} 
		else if (browser.equalsIgnoreCase("Gecho")) {
			folder=new File(UUID.randomUUID().toString());
			folder.mkdir();
			
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("network.proxy.type", 0);
			FirefoxOptions options = new FirefoxOptions();
			profile.setPreference("browser.download.folderList",2);
			profile.setPreference("browser.download.dir",folder.getAbsolutePath());
			//profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/x-download");
			options.setProfile(profile);
			System.setProperty("webdriver.gecho.driver", config.getGechoPath());
			driver = new FirefoxDriver(options);

		}

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(1000, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		return driver;
	}

	public static void closeBrowser() 
	{
		try 
		{
			driver.quit();
			for (File file : folder.listFiles()) 
			{
				file.delete();
			}
			folder.delete();
		} 
		catch (Exception e) 
		{
			driver.quit();
			System.out.println(e.getStackTrace());
		}
	}
}
