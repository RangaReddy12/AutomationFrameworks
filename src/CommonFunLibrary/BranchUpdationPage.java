package CommonFunLibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Reporter;

public class BranchUpdationPage {
WebDriver driver;
public BranchUpdationPage(WebDriver driver)
{
	this.driver=driver;
}
@FindBy(xpath="//tr[3]//td[7]//a[1]//img[1]")
WebElement clickedit;
@FindBy(name="txtbnameU")
WebElement bname;
@FindBy(name="txtadd1u")
WebElement address1;
@FindBy(name="txtzipu")
WebElement zcode;
@FindBy(name="btnupdate")
WebElement clickupdate;
public boolean verifyBranchupdate(String bname,String address1,String zcode)throws Throwable
{
	this.clickedit.click();
	Thread.sleep(5000);
	this.bname.clear();
	this.bname.sendKeys(bname);
	this.address1.clear();
	this.address1.sendKeys(address1);
	this.zcode.clear();
	this.zcode.sendKeys(zcode);
	this.clickupdate.click();
	Thread.sleep(5000);
	String alertmessage=driver.switchTo().alert().getText();
	System.out.println(alertmessage);
	Thread.sleep(5000);
	driver.switchTo().alert().accept();
	Thread.sleep(5000);
	String expected="Branch updated";
	if(alertmessage.toLowerCase().contains(expected.toLowerCase()))
	{
		Reporter.log("Branch Updated Successfully",true);
		return true;
	}
	else
	{
		Reporter.log("Branch Not Updated Successfully",true);
		return false;
	}
}
}
