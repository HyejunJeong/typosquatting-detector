import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WorkerNode {
	
	private static String url = "www.yahoo.co";
	
	public static void main(String[] args) throws IOException{

		/** This path is a local path. */
		String chromeDriverPath = "/usr/local/bin/chromedriver" ;
	        
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors", "--silent");
		WebDriver driver = new ChromeDriver(options);
        
		// get the url
		driver.get("https://"+url);
        
		// Get the source code of the page and save it to a file
		File source = new File(url+"/"+url+".txt");
		FileUtils.writeStringToFile(source, driver.getPageSource(), StandardCharsets.UTF_8.name());        
        
		// Take a screenshot of the current page
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File(url+"/"+url+".png"));
        
		driver.quit();
	}
}
