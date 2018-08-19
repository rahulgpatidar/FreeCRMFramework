package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver=driver;
	}

	
	
	@FindBy(xpath="//input[@placeholder='Username']")WebElement userName;
	
	@FindBy(xpath="//input[@placeholder='Password']")WebElement password;
	
	@FindBy(xpath="//input[@value='Login']")WebElement loginButton;
	
	
	public void login() {
		
		userName.sendKeys("rahulgpatidar@gmail.com");
		password.sendKeys("Rahualgoru@123");
		loginButton.click();
	}
}
