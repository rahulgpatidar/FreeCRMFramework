package testCases;

import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dataProvider.ExcelDataProvider;
import utility.BaseClass;

public class LoginWithDataProvider extends BaseClass {

	@BeforeMethod
	public void setUp() throws Exception {

		initialisation();
	}
	
	public static ArrayList<Object[]> getDataFromExcel() {

		ArrayList<Object[]> myData = new ArrayList<Object[]>();
		reader = new ExcelDataProvider("./ApplicationTestData/ApplicationData.xlsx");

		for (int rowNum = 2; rowNum <= reader.getRowCount("Login"); rowNum++) {

			String username = reader.getCellData("Login", "UserName", rowNum);
			String password = reader.getCellData("Login", "Password", rowNum);

			Object obj[] = {username,password};
			myData.add(obj);
		}
		return myData;
	}
	@DataProvider
	public static Iterator<Object[]>passData(){
		ArrayList<Object[]>data=getDataFromExcel();
		return data.iterator();
	}
	
	@Test(dataProvider="passData")
	public void clickOnSignInLinkAndEnterCredential(String UserName, String Password) {

		driver.findElement(By.linkText("SIGN IN")).click();
		
		driver.findElement(By.name("login[username]")).sendKeys(UserName);
		driver.findElement(By.name("login[password]")).sendKeys(Password);
		
		driver.findElement(By.xpath("//button[@type='submit']//span[text()='Login']")).click();

	}

	@AfterMethod
	public void tearDown() {
		termination();
	}
}
