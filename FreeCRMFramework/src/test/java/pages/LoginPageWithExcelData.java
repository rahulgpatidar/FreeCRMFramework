package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import dataProvider.ExcelDataProvider;

public class LoginPageWithExcelData {
	
	WebDriver driver;
	
	public LoginPageWithExcelData(WebDriver driver) {
		this.driver=driver;
	}

	@FindBy(linkText="SIGN IN") WebElement signInLink;
	
	@FindBy(name="login[username]")WebElement userName;
	
	@FindBy(name="login[password]")WebElement password;
	
	@FindBy(xpath="//button[@type='submit']//span[text()='Login']")WebElement loginButton;
	
	
	public void applicationLogin(String user,String pass) {
		signInLink.click();
		userName.sendKeys(user);
		password.sendKeys(pass);
		
		loginButton.click();
	}
}
