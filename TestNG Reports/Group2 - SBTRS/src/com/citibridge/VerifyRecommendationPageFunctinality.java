package com.citibridge;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class VerifyRecommendationPageFunctinality 
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
	
	@Test(priority = 1,dependsOnMethods = "verifySuccessfulLogin")
	public void verifyRecommendationTitle() throws InterruptedException
	{
		Reporter.log("Recommendation Page gives title 'Nifty Recommendations'");
		String expected = "Nifty Recommendations";
		boolean check = false ;
		
		driver.findElement(By.xpath("//a[@href =\"/recommendation\"]")).click() ;
		
		Thread.sleep(3000) ;
		String title = driver.findElement(By.xpath("//h1[@class='topleft']")).getText() ;
	
		if(title.equals(expected) != false)
		{
			check = true ;
			System.out.println("Correct title");
		}
		else
		{
			check = false ;
			System.out.println("Incorrect title");
		}
		
		Assert.assertEquals(check, true) ;
	}
	
	@Test(priority = 2,dependsOnMethods = "verifyRecommendationTitle")
	public void verifyAddSavedStocks() throws InterruptedException
	{
		Reporter.log("Saving a recommended stock shows success message");
		int count = 0 ;
		boolean check  = false ;
		driver.findElements(By.xpath("//span[@class='p-button-icon pi pi-plus']")).get(0).click() ;
		Thread.sleep(1000) ;
		driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/div[2]/app-recommendations/p-dialog/div/div/div[2]/p/p-inputnumber/span/input")).sendKeys("900") ;
		driver.findElement(By.xpath("//button[@label='Save']")).click() ;
		while(count < 5)
		{
			check = driver.findElement(By.xpath("/html/body/app-root/app-admin-layout/div/div[2]/app-recommendations/p-toast/div")).isDisplayed() ;
			if(check == true)
			{
				break ;
			}
			Thread.sleep(1000) ;
			count++ ;
		}
	Assert.assertEquals(check, true) ;
	
	}
	
	@AfterTest
    public void terminateBrowser()
	{
        driver.close();
    }

}

/*								OUTPUT
 * 
 * [RemoteTestNG] detected TestNG version 7.4.0
Launching google chrome browser
Starting ChromeDriver 92.0.4515.107 (87a818b10553a07434ea9e2b6dccf3cbe7895134-refs/branch-heads/4515@{#1634}) on port 20493
Only local connections are allowed.
Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
ChromeDriver was started successfully.
Aug 24, 2021 11:16:56 AM org.openqa.selenium.remote.ProtocolHandshake createSession
INFO: Detected dialect: W3C
Correct Dashboard title present
Correct title
PASSED: verifyRecommendationTitle
PASSED: verifySuccessfulLogin
PASSED: verifyAddSavedStocks

===============================================
    Default test
    Tests run: 3, Failures: 0, Skips: 0
===============================================


===============================================
Default suite
Total tests run: 3, Passes: 3, Failures: 0, Skips: 0
===============================================


 * 
 */
