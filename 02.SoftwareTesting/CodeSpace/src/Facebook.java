import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Facebook {
	static String driverType = "webdriver.chrome.driver";
	static String driverLoc = "C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Resources\\chromedriver_96.exe";
	static String baseURL = "https://www.facebook.com";
	
	public static void main(String[] args) throws InterruptedException {
		System.setProperty(driverType, driverLoc);
		WebDriver driver = new ChromeDriver();      
		driver.get(baseURL);
		WebElement username = driver.findElement(By.id("email"));
		WebElement password = driver.findElement(By.id("pass"));
		WebElement Login = driver.findElement(By.name("login"));
		username.sendKeys(getFbUname());
		password.sendKeys(getFbPass());
		Login.click();	
		Thread.sleep(2000);
		try {
			@SuppressWarnings("unused")
			WebElement navBar = driver.findElement(By.id("ssrb_root_start"));
			System.out.println("TEST CASE PASS..");
			driver.close();   
		} catch (Exception e) {
			System.out.println("TEST CASE FAILED...");
			//driver.close();
		}
		   
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	static String uname_val = "nifahob684@mom2kid.com";
//	static String password_val = "@password";
	
	
	
	
	//dummy account 
	public static String getFbUname() { return "nifahob684@mom2kid.com";}
	public static String getFbPass() { return "@password";}
}