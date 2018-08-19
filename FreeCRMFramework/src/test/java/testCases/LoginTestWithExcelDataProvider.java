package testCases;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import browserFactory.BrowserFactory;
import dataProvider.ConfigDataProvider;
import dataProvider.ExcelDataProvider;
import dataProvider.ExcelDataProvider;
import pages.LoginPageWithExcelData;
import utility.BaseClass;

public class LoginTestWithExcelDataProvider extends BaseClass {
	

	@BeforeMethod
	public void setUp() throws Exception {
		initialisation();
	}

	@Test
	public void clickOnSignInLink() {

		ExcelDataProvider reader=new ExcelDataProvider("./ApplicationTestData/ApplicationData.xlsx");
		LoginPageWithExcelData login = PageFactory.initElements(driver, LoginPageWithExcelData.class);
		login.applicationLogin(reader.getCellData("Login","UserName",2),reader.getCellData("Login", "Password", 2));

		Assert.assertTrue(driver.getTitle().contains("My Account"), "Title is Demo Store");
	}

	@AfterMethod
	public void tearDown() {
		termination();
	}

}
