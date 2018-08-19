package testCases;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import browserFactory.BrowserFactory;
import dataProvider.ConfigDataProvider;
import pages.LoginPage;

public class LoginTestWithReport {
	WebDriver driver;
	
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;

	@BeforeMethod
	public void setUp() throws IOException {

		driver=BrowserFactory.getBrowser("chrome");
		driver.get(ConfigDataProvider.getApplicationURL());
	}
	
	@Test
	public void clickOnSignInLink() {
		
		htmlReporter = new ExtentHtmlReporter("./Reports/login.html");
		
		extent=new ExtentReports();
		extent.attachReporter(htmlReporter);
		ExtentTest logger=extent.createTest("Login Test");
		logger.log(Status.INFO, "Testing login functionality");
	
		LoginPage login=PageFactory.initElements(driver, LoginPage.class);
		login.login();
		Assert.assertTrue(driver.getTitle().contains("My Account"), "Title is Demo Store");
		
		logger.log(Status.PASS, "Success");
	}
	
	@AfterMethod
	public void tearDown() {
		BrowserFactory.closeBrowser();
		extent.flush();
	}

}
