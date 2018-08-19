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
import pages.LoginPageWithExcelData2;
import utility.BaseClass;

public class LoginTestWithExcelDataProvider2 extends BaseClass {
	

	@BeforeMethod
	public void setUp() throws Exception {
		initialisation();
	}

	@Test
	public void clickOnSignInLink() {

		ExcelDataProvider reader=new ExcelDataProvider("./ApplicationTestData/ApplicationData.xlsx");
		LoginPageWithExcelData2 login = PageFactory.initElements(driver, LoginPageWithExcelData2.class);
		
		int rowCount=reader.getRowCount("Login");
		
		for (int rowNum = 2; rowNum <=rowCount; rowNum++) {
			login.applicationLogin(reader.getCellData("Login","UserName",rowNum),reader.getCellData("Login", "Password", rowNum));
		}

		Assert.assertTrue(driver.getTitle().contains("My Account"), "Title is Demo Store");
	}

	@AfterMethod
	public void tearDown() {
		termination();
	}

}
