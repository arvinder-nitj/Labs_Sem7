import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CountCheckedUncheckedCheckBoxes {
	
	static String driverType = "webdriver.chrome.driver";
	static String driverLoc = "C:\\Users\\arvindersingh\\Desktop\\Labs\\02.SoftwareTesting\\Resources\\chromedriver_new.exe";
	static String baseURL = "http://uitestpractice.com/Students/Form";
	static String baseURL2 = "file:///C:/Users/arvindersingh/Desktop/Labs/02.SoftwareTesting/CodeSpace/src/Lab8.html";

	public static void main(String[] args) throws InterruptedException {
		System.setProperty(driverType, driverLoc);
		WebDriver driver = new ChromeDriver();      
		driver.get(baseURL);
		
		 
		driver.findElement(By.xpath("//input[@value='dance']")).click();
		driver.findElement(By.xpath("//input[@value='cricket']")).click();
		List <WebElement> webElements = driver.findElements(By.xpath("//input[@type='checkbox']"));
		int checkedCount = 0;
		int uncheckedCount = 0;
		for (int i=0;i < webElements.size();i++){
			if ( webElements.get(i).isSelected() == true) {
				checkedCount++;
			}else {
				uncheckedCount++;
			}
		 }
		 System.out.println("Number of checked checkboxes are " +checkedCount);
		 System.out.println("Number of unchecked checkboxes are " + uncheckedCount);
		 driver.quit();

	}
	
	public static void alt() {
		System.setProperty(driverType, driverLoc);
		WebDriver driver = new ChromeDriver();      
		driver.get(baseURL2);
        int chk = 0;
        int unchk = 0;
        List <WebElement> els = driver.findElements(By.xpath("//input[@type='radio']"));
        for(WebElement el : els){
        	if(el.isSelected()) { chk++;}
        	else { unchk++;}        	
        }
        System.out.println("Total checked items are " + chk);
    	System.out.println("Total unchecked items are " + unchk);
    	driver.close();
	}

}
