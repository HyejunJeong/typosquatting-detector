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
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements RemoteInterface {
	
	private static final long serialVersionUID = 1L;
	
	private RemoteInterface server;
	private String url;
	
	public Client(RemoteInterface iserver, String iurl) throws RemoteException {
		server = iserver;
		url = iurl;
		System.out.println(server.getClientList().toString());
		crawl();
	}
	
	@Override
	public void addClient(RemoteInterface iclient) throws RemoteException {}

	@Override
	public String pollURL() throws RemoteException { return null; }

	@Override
	public LinkedList<RemoteInterface> getClientList() throws RemoteException { return null; }

	@Override
	public LinkedList<String> getURLQueue() throws RemoteException { return null; }
	
	public void crawl() {
		// Ask for local chrome driver path
		Scanner input = new Scanner(System.in);
		System.out.print("Enter Path to Chrome Driver: ");
		String chromeDriverPath = input.nextLine();
		
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
	
	public static void main(String args[]) {
		try {
			// Get remote server object
			RemoteInterface nserver = (RemoteInterface) Naming.lookup("//localhost/Server");
			
			// Debug
			LinkedList<String> queue = nserver.getURLQueue();
			for (String s : queue) {
				System.out.println(s);
			}
			
			// Create new client object
			nserver.addClient(new Client(nserver, nserver.pollURL()));
		} 
		catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

}
