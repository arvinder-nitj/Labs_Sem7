package random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Current {
	static String driverType = "webdriver.chrome.driver";
	static String driverLoc = "C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Resources\\chromedriver_96.exe";
	static String baseURL = "https://www.facebook.com";

	
	static String uname_val = "arvinders.it.18@nitj.ac.in";
	static String password_val = "RollNo_18124004";
	
	public static void main(String[] args) throws InterruptedException {
		System.setProperty(driverType, driverLoc);
		WebDriver driver = new ChromeDriver();      
		driver.get(baseURL);
		WebElement username = driver.findElement(By.id("email"));
		WebElement password = driver.findElement(By.id("pass"));
		WebElement Login = driver.findElement(By.name("login"));
		username.sendKeys(uname_val);
		password.sendKeys(password_val);
		Login.click();
		Thread.sleep(2000);
		try {
			@SuppressWarnings("unused")
			WebElement e= driver.findElement(By.id("ssrb_root_start"));
			System.out.println("TEST CASE PASS..");
			driver.close();
		} catch (Exception e) {System.out.println("TEST CASE FAILED...");
			
			WebDriver driver2 = new ChromeDriver();
			driver2.get(driver.getCurrentUrl());
			
			driver.close();
		}
	}
}

