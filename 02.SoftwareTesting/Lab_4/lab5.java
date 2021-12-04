import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class NumberOFObjects {
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Resources\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.nitj.ac.in/");
		List< WebElement> mylist=driver.findElements(By.xpath("//a"));
		System.out.println("Number of links =" +mylist.size());
		List< WebElement> mylist2=driver.findElements(By.xpath("//img"));
		System.out.println("Number of images =" +mylist2.size());
	}
}
