package typosquatting_detector;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Scanner;

public class Client {
	
	private static ServerInterface si;
	
	public static void main(String args[]) throws MalformedURLException, RemoteException, NotBoundException {
		// Get remote server object
		si = (ServerInterface) Naming.lookup("//localhost/Server");

		Scanner input = new Scanner(System.in);
		System.out.print("Enter path to Chrome Driver: ");
		String chromeDriverPath = input.nextLine();
		
		// Debug
		LinkedList<String> queue = si.getQueue();
		for (String s : queue) {
			System.out.println(s);
		}
		
		// Crawl URL from here...
		String url = si.pollURL();

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
	}
}
