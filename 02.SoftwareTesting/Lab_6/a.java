import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class CountListOrComboBox {
	
	static String driverType = "webdriver.chrome.driver";
	static String driverLoc = "C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Resources\\chromedriver.exe";
	static String baseURL = "http://demo.guru99.com/test/newtours/register.php";

	public static void main(String[] args) {
		System.setProperty(driverType, driverLoc);
		WebDriver driver = new ChromeDriver();      
		driver.get(baseURL);
		Select drpCountry = new Select(driver.findElement(By.name("country")));
		List <WebElement> mylist = drpCountry.getOptions();		        	        
		System.out.println("Number of items (countries in option) = "+mylist.size());
	}

}
