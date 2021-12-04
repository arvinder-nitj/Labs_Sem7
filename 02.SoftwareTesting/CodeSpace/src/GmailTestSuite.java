import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import java.util.*;
//import java.lang.*;

public class GmailTestSuite {
	
	static String driverType = "webdriver.chrome.driver";
	static String driverLoc = "C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Resources\\chromedriver_96.exe";
	static String baseURL = "https://accounts.google.com/";
	//+ "AccountChooser/signinchooser?service=mail&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&flowName=GlifWebSignIn&flowEntry=AccountChooser";

	
	public static void main(String[] args) throws InterruptedException {
		System.setProperty(driverType, driverLoc);
		WebDriver driver = new ChromeDriver();      
		driver.get(baseURL);
		
		// gmail login
		try {
			driver.findElement(By.id("identifierId")).sendKeys(getEmail());   // enter username
			driver.findElement(By.id("identifierNext")).click();    // click next
		} catch (Exception e) {
			  System.out.println("TEST CASE FAILED...");
			  System.out.println("Problem with email field and next button");
			  //driver.close();
		}		
		Thread.sleep(5000);
		try {
			driver.findElement(By.name("password")).sendKeys(getPass());    // enter password
			driver.findElement(By.id("passwordNext")).click();   // click sign in
		} catch (Exception e) {
			  System.out.println("TEST CASE FAILED...");
			  System.out.println("Problem with password field and sign in button");
			  //driver.close();
		}
		Thread.sleep(5000);
		String title = driver.getTitle();		
		if(title.equals("Google Account")){ System.out.println("LOGIN SUCCESSFUL...");}
		else { System.out.println("LOGIN FAILED"); }		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	private static CharSequence getEmail() {return "acr.nitj@gmail.com";}
	private static CharSequence getPass() {return "@acrReport";}

}
