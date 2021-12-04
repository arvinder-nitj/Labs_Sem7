import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AcrTestSuite {
	
	/*
	 * By cssSelector - driver.findElement(By.className("btn.btn-default"))
	 * By xPath - driver.findElement(By.xpath("//td/p/button"))
	 * driver.findElement(By.cssSelector("button.btn.btn-default"));
	 * 
	 * For anybody facing same issue, relative xpath can be used.
	 * (By.xpath("//button[contains(@class,'btn btn-default')]")
	 * Just make always sure, there are no other WebElements fitting on the relative xpath. Otherwise find for all WebElements and get the desired one by index.
	*/
	
	static String driverType = "webdriver.chrome.driver";
	static String driverLoc = "C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Resources\\chromedriver_new.exe";
	static String baseURL = "https://acr-node.herokuapp.com/login";
	static String targetPageURL = "https://acr-node.herokuapp.com/index";

	public static void main(String[] args) throws InterruptedException {
		System.setProperty(driverType,driverLoc);
		WebDriver driver = new ChromeDriver();	
		driver.get(baseURL);
		WebElement username=null, password = null, Login=null;		
		Thread.sleep(2000);
		try {
			username = driver.findElement(By.name("username"));
			password = driver.findElement(By.name("password"));
			Login = driver.findElement(By.cssSelector("button.btn.btn-primary"));   
		  } catch (Exception e) {
			  System.out.println("TEST CASE FAILED...");
			  System.out.println("Login fields not found on webpage");
			  driver.close();
		  }
		
		username.sendKeys(getFbUname());
		password.sendKeys(getFbPass());
		Login.click();
		 
		 Thread.sleep(2000);
		 try {
			//@SuppressWarnings("unused")
			//WebElement navBar = driver.findElement(By.cssSelector("div.page-container")); //login_form
			//or get target page URL
			if(driver.getCurrentUrl().equals(targetPageURL) == false) {
				throw new Exception("Login failed");
			}
			System.out.println("TEST CASE PASS..");
			System.out.println("Login function tested Successfully");
			driver.close();   
		  } catch (Exception e) {
			  System.out.println("TEST CASE FAILED...");
			  driver.close();
		  }
		   
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static String getFbUname() {
		return "nifahob684@mom2kid.com";
	}
	
	public static String getFbPass() {
		return "@password";
	}
}







//	driver.get("https://accounts.google.com/AccountChooser/signinchooser?service=mail&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&flowName=GlifWebSignIn&flowEntry=AccountChooser");
//
//// gmail login
//driver.findElement(By.id("identifierId")).sendKeys("acr.nitj@gmail.com");   // enter username
//driver.findElement(By.id("identifierNext")).click();    // click next
//Thread.sleep(5000);
//driver.findElement(By.name("password")).sendKeys("@acrReport");    // enter password
//driver.findElement(By.id("passwordNext")).click();   // click sign in
//
//String title = driver.getTitle();
//
//if(title.equals("Google Account")) 
//{
// System.out.println("LOGIN SUCCESSFUL...");
//}
//else
//{
// System.out.println("LOGIN FAILED");
//}

