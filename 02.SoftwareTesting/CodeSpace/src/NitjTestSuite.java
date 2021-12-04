import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class NitjTestSuite {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Resources\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();	
		driver.get("https://www.nitj.ac.in/");
		Thread.sleep(2000);
		 try {
			@SuppressWarnings("unused")
			WebElement clock = driver.findElement(By.id("time"));
			System.out.println("RESULT : TEST CASE PASS..\nClock is present and working on website");
			driver.close();   
		  } catch (Exception e) {
			System.out.println("RESULT : TEST CASE FAILED...\\nClock is not loading on website");	
			driver.close();
		  }				   
	}
	
}


//public static void checkLink(WebElement cur,WebDriver driver) throws InterruptedException {
//
//List<WebElement> linksList = driver.findElements(By.tagName("a"));
//System.out.println(linksList.size());
//linksList.forEach((cur) -> {
//	try {
//		checkLink(cur,driver);
//	} catch (InterruptedException e1) {
//		// TODO Auto-generated catch block
//		//e1.printStackTrace();
//		System.out.println("TEST CASE FAILED...");
//	}
//});
//
//System.out.println("TEST CASE PASS..");
//
//System.out.println( cur.getAttribute("href"));
//driver.get(cur.getAttribute("href"));
////driver.get("https://www.nitj.ac.in/");
////Thread.sleep(2000);
////cur.click();
////Thread.sleep(2000);			
//}
