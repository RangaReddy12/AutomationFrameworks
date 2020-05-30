package CommonFunLibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

public class BranchCreationPage {
WebDriver driver;
public BranchCreationPage(WebDriver driver)
{
	this.driver=driver;
}
@FindBy(xpath="//input[@id='BtnNewBR']")
WebElement clicknewbranch;
@FindBy(name="txtbName")
WebElement bname;
@FindBy(name="txtAdd1")
WebElement address1;
@FindBy(name="Txtadd2")
WebElement address2;
@FindBy(name="txtadd3")
WebElement address3;
@FindBy(name="txtArea")
WebElement area;
@FindBy(name="txtZip")
WebElement zcode;
@FindBy(name="lst_counrtyU")
WebElement country;
@FindBy(name="lst_stateI")
WebElement state;
@FindBy(name="lst_cityI")
WebElement city;
@FindBy(name="btn_insert")
WebElement clicksubmit;
public boolean verifynewBranch(String bname,String add1,String add2,String add3,
		String area,String zcode,int country,int state,int city)throws Throwable
{
	this.clicknewbranch.click();
	Thread.sleep(5000);
	this.bname.sendKeys(bname);
	this.address1.sendKeys(add1);
	this.address2.sendKeys(add2);
	this.address3.sendKeys(add3);
	this.area.sendKeys(area);
	this.zcode.sendKeys(zcode);
	new Select(this.country).selectByIndex(country);
	Thread.sleep(3000);
	new Select(this.state).selectByIndex(state);
	Thread.sleep(3000);
	new Select(this.city).selectByIndex(city);
	Thread.sleep(3000);
	this.clicksubmit.click();
	Thread.sleep(5000);
	//get alert mesage
	String alertmessage=driver.switchTo().alert().getText();
	System.out.println(alertmessage);
	Thread.sleep(5000);
	driver.switchTo().alert().accept();
	Thread.sleep(5000);
	String expected="New Branch with";
	if(alertmessage.toLowerCase().contains(expected.toLowerCase()))
	{
		Reporter.log("New Branch Created SuccessFully",true);
		return true;
	}
	else
	{
	Reporter.log("New Branch Not Created SuccessFully",true);
	return false;
	}
}
}
