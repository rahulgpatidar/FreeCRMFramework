package testCases;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import browserFactory.BrowserFactory;
import dataProvider.ConfigDataProvider;
import pages.LoginPage;
import utility.BaseClass;

public class LoginTestWithReport2 {
	WebDriver driver;
	
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;
	
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
		logger=extent.createTest("Login Test");
		logger.log(Status.INFO, "Testing login functionality");
	
		LoginPage login=PageFactory.initElements(driver, LoginPage.class);
		login.login();
		Assert.assertTrue(driver.getTitle().contains("av"), "Title is Demo Store");
		
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		
		if(result.getStatus()==ITestResult.FAILURE) {
			String temp=BaseClass.captureScreenshot(driver,result.getName());
			logger.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
		else if(result.getStatus()==ITestResult.SKIP) {
			String temp=BaseClass.captureScreenshot(driver,result.getName());
			logger.skip(result.getTestName().toString(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
		else if(result.getStatus()==ITestResult.SUCCESS) {
			String temp=BaseClass.captureScreenshot(driver,result.getName());
			logger.pass(result.getTestName().toString(), MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
		}
		BrowserFactory.closeBrowser();
		extent.flush();
	}

}
