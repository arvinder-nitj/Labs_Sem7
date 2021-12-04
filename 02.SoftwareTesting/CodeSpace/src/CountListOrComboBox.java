import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class CountListOrComboBox {
	
	static String driverType = "webdriver.chrome.driver";
	static String driverLoc = "C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Resources\\chromedriver_new.exe";
	static String baseURL = "http://demo.guru99.com/test/newtours/register.php";

	public static void main(String[] args) {
		System.setProperty(driverType, driverLoc);
		WebDriver driver = new ChromeDriver();      
		driver.get(baseURL);
		Select drpCountry = new Select(driver.findElement(By.name("country")));
		List <WebElement> mylist = drpCountry.getOptions();		        	        
		System.out.println("Number of countries in option = "+mylist.size());
		
		/*
		drpCountry.selectByVisibleText("ANTARCTICA");

		//Selecting Items in a Multiple SELECT elements
		driver.get("http://jsbin.com/osebed/2");
		Select fruits = new Select(driver.findElement(By.id("fruits")));
		fruits.selectByVisibleText("Banana");
		fruits.selectByIndex(1);
		*/
	}

}
