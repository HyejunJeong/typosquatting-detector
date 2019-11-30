package typosquatting_detector;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class ClientImpl extends UnicastRemoteObject implements Client {
	
	private static final long serialVersionUID = 1L;
	
	private static String chromeDriverPath;
	
	public static void main(String args[]) {
		// Print message for the users
		System.out.println("Starting Client...");
		
		// Ask for IP addresses and local chrome driver path
		Scanner scanner = new Scanner(System.in);
		System.out.print("\nPlease Enter Your Local IP Address: ");
		String caddr = scanner.nextLine();
		System.out.print("Please Enter the Hosted Server's Local IP Address: ");
		String saddr = scanner.nextLine();
		System.out.print("Please Enter the Path to Chrome Driver: ");
		chromeDriverPath = scanner.nextLine();
		scanner.close();
		
		try {
			// Get remote server object
			System.setProperty("java.rmi.server.hostname", caddr);
			Registry registry = LocateRegistry.getRegistry(saddr, 1099);
			Server nserver = (Server) registry.lookup("Server");
			
			// Create new client object
			String nid = UUID.randomUUID().toString();
			Client nclient = new ClientImpl(nserver, nid);
			nserver.registerClient(nid, nclient);
			
			// Add shutdown hook
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		        public void run() {
		        	try {
						nserver.deregisterClient(nid);
					} 
		        	catch (RemoteException e) {
		        		System.err.println("ERROR: Could Not Deregister From Server");
						e.printStackTrace();
					}
		        }
		    }));
			
			// Print message for the users
			System.out.println("\nClient Successfully Started!");
		}
		catch (RemoteException | NotBoundException e) {
			System.err.println("ERROR: Server Not Found!");
			e.printStackTrace();
		}
	}
	
	private Server server;		// Remote server object
	private String id;			// Unique client ID
	
	public ClientImpl(Server iserver, String iid) throws RemoteException {
		server = iserver;
		id = iid;
	}
	
	@Override
	public synchronized void crawl() throws RemoteException {	
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors", "--silent","--remote-debugging-port=8081");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		LoggingPreferences logPrefs = new LoggingPreferences();
		logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
		capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		
		WebDriver driver = new ChromeDriver(options);
		
		String fileName = "";
		
		// Print message for the users
		System.out.println("\nStarting to Crawl URLs From the Queue...");
		
		int counter = 0;
		
		while (!server.URLQueueIsEmpty()) {
			// Get the URL
			String url = server.pollURLQueue();
			counter++;
			
			// Kludgy way of checking if site actually exists
			try {
				HttpURLConnection connection = (HttpURLConnection) new URL("https://" + url).openConnection();
				connection.setRequestMethod("GET");
				if (connection.getResponseCode() == 404) {
					System.out.println("Address Not Found " + url);
					continue;
				}
			}
			catch (IOException e) {
				System.out.println("Address Not Found " + url);
				continue;
			}
			
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			try {
				driver.get("https://" + url);
			}
			catch (TimeoutException e) {
				System.out.println("Address Not Found " + url);
				continue;
			}
			
			fileName = "sites/" + url + ".txt";
			
			// Print message for the users
			System.out.println("Started Crawling " + url);

			// Report in txt
			File txtFileReport = new File(fileName);
			
			// Converting screenshot
			String screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
			
			// Get the source code of the page
			String source = driver.getPageSource();
			
			//Assemble imageString and Source
			String txtReport = "";

			txtReport += url + "\n" + screenshot + "\n" + source;
			
			
			try {
				FileUtils.writeStringToFile(txtFileReport, txtReport, StandardCharsets.UTF_8.name());
			}
			catch (IOException e) {
				System.err.printf("ERROR: Failed to Get Report for %s%n", url);
			}
			
			SimpleRemoteInputStream istream = null;
			try {
				istream = new SimpleRemoteInputStream(new FileInputStream(fileName));
				server.sendFile(istream.export());
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				if (istream != null)
					istream.close();
			}
			// Print message for the users
			System.out.println("Done Crawling " + url);
			
		}
		
		// Print message for the users
		System.out.println("Done Crawling All URLs From the Queue");
		System.out.println("URLs crawled: " + counter);
	}

}
