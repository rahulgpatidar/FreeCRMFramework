package testCases;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.LoginPage;
import utility.BaseClass;

public class LoginTest extends BaseClass {

	@BeforeMethod
	public void setUp() throws Exception {

		initialisation();
	}

	@Test
	public void clickOnSignInLink() {

		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
		login.login();
		Assert.assertTrue(driver.getTitle().contains("abc"), "Title is Demo Store");
	}

	@AfterMethod
	public void tearDown() {
		termination();
	}

}
