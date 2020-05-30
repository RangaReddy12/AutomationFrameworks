package CommonFunLibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

public class LogoutPage {
WebDriver driver;
Actions ac;
public LogoutPage(WebDriver driver)
{
	this.driver=driver;
}
@FindBy(xpath="//td//td//td//td[3]//a[1]//img[1]")
WebElement clickLogout;
@FindBy(name="login")
WebElement loginbutton;
public boolean verifyLogout()throws Throwable
{
	ac=new Actions(driver);
	ac.moveToElement(clickLogout).click().perform();
	Thread.sleep(5000);
	if(loginbutton.isDisplayed())
	{
		Reporter.log("Logout Success",true);
		return true;
	}
	else
	{
		Reporter.log("Logout Fail",true);
		return false;	
	}
}
}
