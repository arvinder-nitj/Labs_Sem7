import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class NumberOFObjects {
	static String driverType = "webdriver.chrome.driver";
	static String driverLoc = "C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Resources\\chromedriver_96.exe";
	static String baseURL = "https://www.nitj.ac.in/";
	
	public static void main(String[] args) {
		System.setProperty(driverType, driverLoc);
		WebDriver driver = new ChromeDriver();      
		driver.get(baseURL);
		List< WebElement> mylist=driver.findElements(By.xpath("//a"));
		System.out.println("Number of links =" +mylist.size());
		List< WebElement> mylist2=driver.findElements(By.xpath("//img"));
		System.out.println("Number of images =" +mylist2.size());
		List< WebElement> mylist3=driver.findElements(By.xpath("//ul"));
		System.out.println("Number of unordered_Lists =" +mylist3.size());
		List< WebElement> mylist4=driver.findElements(By.xpath("//ol"));
		System.out.println("Number of ordered_Lists =" +mylist4.size());
		//driver.close();
		
		//System.out.println(driver.getCurrentUrl());
	}
}
