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
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client extends UnicastRemoteObject implements RemoteInterface {
	
	private static final long serialVersionUID = 1L;
	
	private RemoteInterface server;		// Remote server object
	private String id;					// Unique client ID
	
	public Client(RemoteInterface iserver, String iid) throws RemoteException {
		server = iserver;
		id = iid;
	}
	
	@Override
	public void addClient(RemoteInterface iclient) throws RemoteException {}
	
	@Override
	public String pollURL() throws RemoteException { return null; }

	@Override
	public LinkedList<RemoteInterface> getClientList() throws RemoteException { return null; }

	@Override
	public ConcurrentLinkedQueue<String> getURLQueue() throws RemoteException { return null; }
	
	@Override
	public String getClientID() throws RemoteException {
		return id;
	}
	
	private void printClientList() throws RemoteException {
		LinkedList<RemoteInterface> list = server.getClientList();
		System.out.println("\nRegistered Clients:");
		for (RemoteInterface ri : list) {
			System.out.println("Client ID " + ri.getClientID());
		}
	}
	
	private void crawl() throws RemoteException {
		// Ask for local chrome driver path
		Scanner input = new Scanner(System.in);
		System.out.print("\nPlease Enter the Path to Chrome Driver: ");
		String chromeDriverPath = input.nextLine();
		
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors", "--silent");
		WebDriver driver = new ChromeDriver(options);
		
		// Get URL Queue
		ConcurrentLinkedQueue<String> urlQueue = server.getURLQueue();
		
		// Debug
		for (String s : urlQueue) {
			System.out.println(s);
		}
		
		// Print message for the users
		System.out.println("\nStarting to Crawl URLs From the Queue...");
		
		while(!urlQueue.isEmpty()) {
			// Get the URL
			String url = urlQueue.poll();
			driver.get("https://" + url);
			
			// Print message for the users
			System.out.println("Started Crawling " + url);

			// Get the source code of the page and save it to a file
			try {
				File source = new File("sites/" + url + "/" + url + ".html");
				FileUtils.writeStringToFile(source, driver.getPageSource(), StandardCharsets.UTF_8.name());
			} 
			catch (IOException e) {
				System.err.printf("Failed to get page source for %s%n", url);
			}

			// Take a screenshot of the current page
			try {
				File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(screenshot, new File("sites/" + url + "/" + url + ".png"));
			} 
			catch (IOException e) {
				System.err.printf("Failed to get screenshot for %s%n", url);
			}
			
			// Print message for the users
			System.out.println("Done Crawling " + url);
		}
		
		// Print message for the users
		System.out.println("Done Crawling All URLs From the Queue");
	}
	
	public static void main(String args[]) {
		// Print message for the users
		System.out.println("Starting Client...");
		
		try {
			// Get remote server object
			RemoteInterface nserver = (RemoteInterface) Naming.lookup("//localhost/Server");
			
			// Create new client object
			Client nclient = new Client(nserver, UUID.randomUUID().toString());
			nserver.addClient(nclient);
			
			// Print message for the users
			System.out.println("Client Successfully Started!");
			
			// Print all clients in the list
			nclient.printClientList();
			
			// Print the current client ID
			System.out.println("\nYou Are Client ID " + nclient.getClientID());
			
			// Begin crawling
			nclient.crawl();
		} 
		catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

}
