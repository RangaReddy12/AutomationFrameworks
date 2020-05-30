package DriverFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utilities.ExcelFileUtil;

public class DataDriven {
ExtentReports report;
ExtentTest test;
WebDriver driver;
String inputpath="C:\\aprilmrngbatch\\Automation_Frameworks\\TestInput\\Registerdata.xlsx";
String outputpath="C:\\aprilmrngbatch\\Automation_Frameworks\\TestOutput\\datadriven.xlsx";
File screen;
Properties p;
FileInputStream fi;
@BeforeTest
public void setUp()throws Throwable
{
report=new ExtentReports("./ExtentReports/Report.html");
p=new Properties();
fi=new FileInputStream("C:\\aprilmrngbatch\\Automation_Frameworks\\PropertyFile\\OR.properties");
p.load(fi);
System.setProperty("webdriver.chrome.driver", "C:\\aprilmrngbatch\\Automation_Frameworks\\Drivers\\chromedriver.exe");
driver=new ChromeDriver();

}
@Test
public void verifyRegister()throws Throwable
{
//access excelfileutil methods
	ExcelFileUtil xl=new ExcelFileUtil(inputpath);
//count no of rows in a sheet
	int rc=xl.rowCount("Register");
	//count no of columns in row
	int cc=xl.colCount("Register");
	Reporter.log("no of rows are::"+rc+"   "+"No of columns are::"+cc,true);
	for(int i=1;i<=rc;i++)
	{
	test=report.startTest("Validate Register");
	test.assignAuthor("Ranga Senior Manager");
	test.assignCategory("Data Driven Testing");
	driver.get(p.getProperty("url"));
	driver.manage().window().maximize();
	driver.findElement(By.xpath(p.getProperty("Objreg"))).click();
	//read all cell from Register sheet
	String fname=xl.getCellData("Register", i, 0);
	String lname=xl.getCellData("Register", i, 1);
	String phone=xl.getCellData("Register", i, 2);
	String email=xl.getCellData("Register", i, 3);
	String add1=xl.getCellData("Register", i, 4);
	String add2=xl.getCellData("Register", i, 5);
	String city=xl.getCellData("Register", i, 6);
	String state=xl.getCellData("Register", i, 7);
	String pcode=xl.getCellData("Register", i, 8);
	String country=xl.getCellData("Register", i, 9);
	String username=xl.getCellData("Register", i, 10);
	String password=xl.getCellData("Register", i, 11);
	String cpassword=xl.getCellData("Register", i, 12);
	//fill register form
	driver.findElement(By.xpath(p.getProperty("Objfname"))).sendKeys(fname);
	driver.findElement(By.xpath(p.getProperty("Objlname"))).sendKeys(lname);
	driver.findElement(By.xpath(p.getProperty("Objph"))).sendKeys(phone);
	driver.findElement(By.xpath(p.getProperty("Objmail"))).sendKeys(email);
	driver.findElement(By.xpath(p.getProperty("Objadd1"))).sendKeys(add1);
	driver.findElement(By.xpath(p.getProperty("Objadd2"))).sendKeys(add2);
	driver.findElement(By.xpath(p.getProperty("Objcity"))).sendKeys(city);
	driver.findElement(By.xpath(p.getProperty("Objstate"))).sendKeys(state);
	driver.findElement(By.xpath(p.getProperty("Objpcode"))).sendKeys(pcode);
new Select(driver.findElement(By.xpath(p.getProperty("Objcountry")))).selectByVisibleText(country);
driver.findElement(By.xpath(p.getProperty("Objuser"))).sendKeys(username);
driver.findElement(By.xpath(p.getProperty("Objpass"))).sendKeys(password);
driver.findElement(By.xpath(p.getProperty("Objcpass"))).sendKeys(cpassword);
driver.findElement(By.xpath(p.getProperty("Objsubmit"))).click();
if(password.equals(cpassword))
{
	//get message 
String message=driver.findElement(By.xpath("//font[contains(text(),'Thank you for registering.')]")).getText();
Reporter.log(message,true);
//write into results
xl.setCellData("Register", i, 13, message, outputpath);
//write as pass into status column
xl.setCellData("Register", i, 14, "Pass", outputpath);
test.log(LogStatus.PASS, message);
}
else
{
Reporter.log("password and cpassword are not equal",true);
//write into results
xl.setCellData("Register", i, 13, "password and cpassword are not equal", outputpath);
//write as pass into status column
xl.setCellData("Register", i, 14, "Fail", outputpath);
test.log(LogStatus.FAIL, "password and cpassword are not equal");
}
report.endTest(test);
report.flush();
	}
}
@AfterTest
public void tearDown()
{
	driver.close();
}
}















