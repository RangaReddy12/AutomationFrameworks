package DriverFactory;

import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.BranchCreationPage;
import CommonFunLibrary.BranchUpdationPage;
import CommonFunLibrary.BranchesPage;
import CommonFunLibrary.LoginPage;
import CommonFunLibrary.LogoutPage;
import Constant.PBConstant;
import Utilities.ExcelFileUtil;
public class DriverScript extends PBConstant{
String inputpath="C:\\aprilmrngbatch\\Automation_Frameworks\\TestInput\\Controller.xlsx";
String outputpath="C:\\aprilmrngbatch\\Automation_Frameworks\\TestOutput\\KeyWordResults.xlsx";
String TCSheet="TestCases";
String TSSheet="TestSteps";
ExtentReports report;
ExtentTest test;
@Test
public void startTest()throws Throwable
{
	//generate html report to specified path
	report=new ExtentReports("./ExtentReports/KeywordReports.html");
	//call all page classes 
	LoginPage login=PageFactory.initElements(driver, LoginPage.class);
	BranchesPage bdetailspage=PageFactory.initElements(driver, BranchesPage.class);
	BranchCreationPage newbranch=PageFactory.initElements(driver, BranchCreationPage.class);
	BranchUpdationPage branchupdate=PageFactory.initElements(driver, BranchUpdationPage.class);
	LogoutPage logout=PageFactory.initElements(driver, LogoutPage.class);
	boolean res=false;
	String tcres=null;
//access excel util methods
	ExcelFileUtil xl=new ExcelFileUtil(inputpath);
	//count no of rows in TCsheet
	int TCCount=xl.rowCount(TCSheet);
	//count no of rows in TSSheet
	int TSCount=xl.rowCount(TSSheet);
	Reporter.log("No of row are::"+TCCount+"    "+"No of rows ::"+TSCount,true);
	//iterate all rows in TCSheet
	for(int i=1;i<=TCCount;i++)
	{
		test=report.startTest("Keyword Framework");
		test.assignAuthor("Ranga Senior QA");
		test.assignCategory("Keyword");
		//read execute column from TCSheet
		String execute=xl.getCellData(TCSheet, i, 2);
		if(execute.equalsIgnoreCase("Y"))
		{
			//read tcid column from TSsheet
		String tcid=xl.getCellData(TCSheet, i, 0);
		//iterate all rows in TSSheet
		for(int j=1;j<=TSCount;j++)
		{
			//read description column
			String Description=xl.getCellData(TSSheet, j, 2);
			//read tsid column from TSsheet
			String tsid=xl.getCellData(TSSheet, j, 0);
			if(tcid.equalsIgnoreCase(tsid))
			{
				//read keyword column from TSsheet
				String keyword=xl.getCellData(TSSheet, j, 3);
				if(keyword.equalsIgnoreCase("AdminLogin"))
				{
				res=login.verifyLogin("Admin", "Admin");
				test.log(LogStatus.INFO, Description);
				}
				else if(keyword.equalsIgnoreCase("NewBranchCreation"))
				{
					bdetailspage.VerifyNavigateBranches();
					res=newbranch.verifynewBranch("Ameerpet", "Kadiri", "madanapalli", "anantapur", "Kadiri2", "12345", 1, 1, 1);
					test.log(LogStatus.INFO, Description);
				}
				else if(keyword.equalsIgnoreCase("UpdateBranch"))
				{
					bdetailspage.VerifyNavigateBranches();
					res=branchupdate.verifyBranchupdate("kukapalii", "hyderabad", "13456");
					test.log(LogStatus.INFO, Description);
				}
				else if(keyword.equalsIgnoreCase("AdminLogout"))
				{
					res=logout.verifyLogout();
					test.log(LogStatus.INFO, Description);
				}
				String tsres;
				if(res)
				{
					tsres="pass";
				//if it true means pass write into TSSheet results column
				xl.setCellData(TSSheet, j, 4, tsres, outputpath);	
				test.log(LogStatus.PASS, Description);
				}
				else
				{
					tsres="Fail";
					//if it false means fail write into TSSheet results column
					xl.setCellData(TSSheet, j, 4, tsres, outputpath);
					test.log(LogStatus.FAIL, Description);
				}
				if(!tsres.equalsIgnoreCase("Fail"))
				{
					tcres=tsres;
				}
			}
			report.endTest(test);
			report.flush();
		}
		//write tcres into results column in TCSheet
		xl.setCellData(TCSheet, i, 3, tcres, outputpath);
		}
		else
		{
			//write as blocked into results column in TCSheet when tc are Flaged to N
		xl.setCellData(TCSheet, i, 3, "Not Executed", outputpath);	
		}
	}
}
}
