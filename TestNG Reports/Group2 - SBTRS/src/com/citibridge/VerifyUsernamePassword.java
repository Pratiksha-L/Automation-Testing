package com.citibridge;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class VerifyUsernamePassword 
{
	// declaration and instantiation of objects/variables  
	String driverPath =  "C:\\Users\\HP\\Downloads\\chromedriver_win32\\chromedriver.exe";  
	public WebDriver driver;  
	public String baseUrl = "http://localhost:4200/login" ;

	@BeforeTest
	public void launchBrowser() 
	{
		System.out.println("Launching google chrome browser"); 
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver() ;
		driver.manage().window().maximize();
		driver.get(baseUrl);
	}

	@Test(priority = 0)
	public void verifyLoginPageTitle() throws InterruptedException 
	{
		Reporter.log("Login page header contains 'Login'");
		String expectedTitle = "Login";
        String actualTitle = driver.findElement(By.xpath("//div[@class = 'p-card-title ng-star-inserted']")).getText();
        Thread.sleep(3000) ;
        System.out.println("Page Title : "+actualTitle);
        Assert.assertEquals(actualTitle, expectedTitle);
	}
	
	@Test(priority = 1, dependsOnMethods = "verifyLoginPageTitle")
	public void verifyIncorrectCredentials() throws InterruptedException
	{
		Reporter.log("Login with incorrect username and password throws error message & does not login.");
		String userName = "rdthtytft" ;
		String password = "pfiddfhd$#4576" ;
		int count  = 0 ;
		boolean check = false ;
		
		driver.findElement(By.xpath("//input[@id = 'float-input']")).click() ;
		driver.findElement(By.xpath("//input[@id = 'float-input']")).sendKeys(userName) ;
		Thread.sleep(3000) ;
		
		driver.findElement(By.xpath("//input[@id = 'float-password']")).click() ;
		driver.findElement(By.xpath("//input[@id = 'float-password']")).sendKeys(password) ;
		Thread.sleep(3000) ;
		
		driver.findElement(By.xpath("//div[@class = 'p-card-title ng-star-inserted']")).click() ;
		driver.findElement(By.xpath("//p-button")).click() ;
		
		while(count < 5)
		{
			check = driver.findElement(By.xpath("//p-toast")).isDisplayed() ;
			if(check == true)
			{
				break ;
			}
			Thread.sleep(1000) ;
			count++ ;
		}
		
		if(check != false)
		{
			System.out.println("Pop up error message displayed") ;
		}
		else
		{
			System.out.println("Pop up error NOT message displayed") ;
		}
		Assert.assertEquals(check, true, "Pop up error message should be displayed") ;
		
	}
	
	@Test(priority = 2, dependsOnMethods = "verifyIncorrectCredentials")
	public void verifyEmptyCredentials() throws InterruptedException
	{
		Reporter.log("Login with invalid & empty crendentials throws error message asking user to provide username and password compulsorily.") ;
		String expectedErrorMssg = "*Username and Password are compulsary" ;
		
		driver.get(baseUrl);
		Thread.sleep(2000) ;
		
		driver.findElement(By.xpath("//p-button")).click() ;
		
		Thread.sleep(1000) ;
		String actualErrorMessage= driver.findElement(By.xpath("//p")).getText() ;
		System.out.println("Error message : "+actualErrorMessage);
		
		Thread.sleep(2000) ;
		if(actualErrorMessage.equals(expectedErrorMssg))
		{
			System.out.println("Correct Error Message Displayed !");
		
		}
		else
		{
			System.out.println("Incorrect Error Message Displayed !");
		}
		
		Assert.assertEquals(actualErrorMessage, expectedErrorMssg) ;
	}
	
	@Test(priority = 3, dependsOnMethods = "verifyEmptyCredentials")
	public void verifyServerDown() throws InterruptedException
	{
		Reporter.log("If server down, server down error message displayed & does not allow user to login") ;
		String userName = "rdthtytft" ;
		String password = "pfiddfhd$#4576" ;
		int count = 0 ;
		boolean check = false ;
		
		driver.findElement(By.xpath("//input[@id = 'float-input']")).click() ;
		driver.findElement(By.xpath("//input[@id = 'float-input']")).sendKeys(userName) ;
		Thread.sleep(3000) ;
		
		driver.findElement(By.xpath("//input[@id = 'float-password']")).click() ;
		driver.findElement(By.xpath("//input[@id = 'float-password']")).sendKeys(password) ;
		Thread.sleep(3000) ;
		
		driver.findElement(By.xpath("//div[@class = 'p-card-title ng-star-inserted']")).click() ;
		driver.findElement(By.xpath("//p-button")).click() ;
		
		Thread.sleep(1000) ;
		while(count < 5)
		{
			check = driver.findElement(By.xpath("//div[@class= 'ng-tns-c121-0 p-toast p-component p-toast-bottom-right']")).isDisplayed() ;
			if(check == true)
			{
				break ;
			}
			Thread.sleep(1000) ;
			count++ ;
		}
		
		if(check != false)
		{
			System.out.println("Pop up server down error message displayed") ;
		}
		else
		{
			System.out.println("Pop up server down error NOT message displayed") ;
		}
		Assert.assertEquals(check, true, "Pop up server down error message should be displayed") ;
		
	}
	
	@Test(priority = 4, dependsOnMethods = "verifyServerDown")
	public void verifySuccessfulLogin() throws InterruptedException
	{
		Reporter.log("Login with correct credentials navigate user to Dashboard");
		String userName = "Elon" ;
		String password = "musKeloN*123" ;
		boolean check = false ;
		
		driver.findElement(By.xpath("//input[@id = 'float-input']")).click() ;
		driver.findElement(By.xpath("//input[@id = 'float-input']")).clear() ;
		driver.findElement(By.xpath("//input[@id = 'float-input']")).sendKeys(userName) ;
		Thread.sleep(3000) ;
		
		driver.findElement(By.xpath("//input[@id = 'float-password']")).click() ;
		driver.findElement(By.xpath("//input[@id = 'float-password']")).clear() ;
		driver.findElement(By.xpath("//input[@id = 'float-password']")).sendKeys(password) ;
		Thread.sleep(3000) ;
		
		driver.findElement(By.xpath("//div[@class = 'p-card-title ng-star-inserted']")).click() ;
		driver.findElement(By.xpath("//p-button")).click() ;
		
		Thread.sleep(3000) ;
		String dashboardTitle = driver.findElement(By.xpath("//h1[@class = 'topleft']")).getText() ;
		
		if(dashboardTitle.contains("NSE Top Gainer:"))
		{
			check = true ;
			System.out.println("Correct Dashboard title present");
		}
		else
		{
			check = false ;
			System.out.println("Incorrect Dashboard title present");
		}
		
		Assert.assertEquals(check, true) ;		
	}
	
	@Test(priority = 6 , dependsOnMethods = "verifySuccessfulLogin")
	public void logout() throws InterruptedException
	{
		Reporter.log("Logout navigates user to Login Page");
		
		driver.findElements(By.xpath("//i[@class='now-ui-icons users_single-02']")).get(1).click() ;
		Thread.sleep(2000) ;
		
		String expectedTitle = "Login";
        String actualTitle = driver.findElement(By.xpath("//div[@class = 'p-card-title ng-star-inserted']")).getText();
        System.out.println("Page Title : "+actualTitle);
        Assert.assertEquals(actualTitle, expectedTitle);
	}
	
	@AfterTest
    public void terminateBrowser()
	{
        driver.close();
    }
}

/*								OUTPUT (Console)
 * 
 * [RemoteTestNG] detected TestNG version 7.4.0
Launching google chrome browser
Starting ChromeDriver 92.0.4515.107 (87a818b10553a07434ea9e2b6dccf3cbe7895134-refs/branch-heads/4515@{#1634}) on port 36986
Only local connections are allowed.
Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
ChromeDriver was started successfully.
Aug 24, 2021 9:56:22 AM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
Page Title : Login
Pop up error message displayed
Error message : *Username and Password are compulsary
Correct Error Message Displayed !
Pop up server down error message displayed
Correct Dashboard title present
Page Title : Login
PASSED: verifyEmptyCredentials
PASSED: verifySuccessfulLogin
PASSED: verifyServerDown
PASSED: verifyIncorrectCredentials
PASSED: logout
PASSED: verifyLoginPageTitle

===============================================
    Default test
    Tests run: 6, Failures: 0, Skips: 0
===============================================


===============================================
Default suite
Total tests run: 6, Passes: 6, Failures: 0, Skips: 0
===============================================


 * 
 */
