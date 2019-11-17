package typosquatting_detector;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ClientWorkImpl implements Runnable, ClientWork {

	private String url;

	// Notify when worker is finished
	private final Queue<ClientWorkImpl> queue;
	
	public ClientWorkImpl() {
		this.queue = new LinkedList<ClientWorkImpl>();
	}

	public ClientWorkImpl(Queue<ClientWorkImpl> queue) {
		this.queue = queue;
	}

	public void assignURL(String url) {
		this.url = url;
	}
	
	public String communicate() {
		return "Hello from worker";
	}

	@Override
	public void run() {
		// This path is a local path.
		String chromeDriverPath = "/usr/local/bin/chromedriver";
	        
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors", "--silent");
		WebDriver driver = new ChromeDriver(options);
        
		// Get the url
		driver.get("https://" + url);
        
		// Get the source code of the page and save it to a file
		try {
			File source = new File("sites/" + url + "/" + url + ".html");
			FileUtils.writeStringToFile(source, driver.getPageSource(), StandardCharsets.UTF_8.name());
		} catch (IOException e) {
			System.err.printf("Failed to get page source for %s%n", url);
		}

		// Take a screenshot of the current page
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File("sites/" + url + "/" + url + ".png"));
		} catch (IOException e) {
			System.err.printf("Failed to get screenshot for %s%n", url);
		}

		synchronized (queue) {
			queue.add(this);
			queue.notify();
		}

		driver.quit();
	}
}
