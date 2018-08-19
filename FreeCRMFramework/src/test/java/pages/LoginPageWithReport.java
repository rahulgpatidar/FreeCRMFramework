package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageWithReport {
	
	WebDriver driver;
	
	public LoginPageWithReport(WebDriver driver) {
		this.driver=driver;
	}

	@FindBy(linkText="SIGN IN") WebElement signInLink;
	
	@FindBy(name="login[username]")WebElement userName;
	
	@FindBy(name="login[password]")WebElement password;
	
	@FindBy(xpath="//button[@type='submit']//span[text()='Login']")WebElement loginButton;
	
	
	public void login() {
		signInLink.click();
		userName.sendKeys("rahulgpatidar@gmail.com");
		password.sendKeys("test@123");
		loginButton.click();
	}
}
