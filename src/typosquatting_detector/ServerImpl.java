package typosquatting_detector;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.StringBuilder;

public class ServerImpl extends UnicastRemoteObject implements Server {

	private static final long serialVersionUID = 1L;
	
	private static Server server;
	
	public static void init() {
		// Print message for the users
		System.out.println("Starting Server...");
		
		// Ask for local IP address to host the server
		Scanner scanner = new Scanner(System.in);
		System.out.print("\nPlease Enter Your Local IP Address to Host the Server: ");
		String address = scanner.nextLine();
		scanner.close();
		
		try {
			// Bind remote server object
			System.setProperty("java.rmi.server.hostname", address);
			Registry registry = LocateRegistry.createRegistry(1099);
			server = new ServerImpl();
			registry.rebind("Server", server);
			
			// Add shutdown hook
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		        public void run() {
		        	try {
		        		registry.unbind("Server");
						UnicastRemoteObject.unexportObject(registry, true);
					} 
		        	catch (RemoteException | NotBoundException e) {
		        		System.err.println("ERROR: Server Program Abnormally Terminated!");
						e.printStackTrace();
					}
		        }
		    }));
			
			// Print message for the users
			System.out.println("\nServer Successfully Started!");
		}
		catch (RemoteException e) {
			System.err.println("ERROR: Could Not Launch Server");
			e.printStackTrace();
		}
	}
	
	public static Server getServer() {
		return server;
	}
	
	private ConcurrentHashMap<String, Client> clientMap;	// Map of clients keyed with their IDs
	private ConcurrentLinkedQueue<String> urlQueue;			// Queue of URLs
	
	public ServerImpl() throws RemoteException {
		clientMap = new ConcurrentHashMap<String, Client>();
		urlQueue = new ConcurrentLinkedQueue<String>();
	}
	
	@Override
	public void registerClient(String ikey, Client iclient) throws RemoteException {
		clientMap.put(ikey, iclient);
	}
	
	@Override
	public void deregisterClient(String ikey) throws RemoteException {
		// TODO: Not implemented yet
	}

	@Override
	public ConcurrentHashMap<String, Client> getClientMap() throws RemoteException {
		return clientMap;
	}

	@Override
	public ConcurrentLinkedQueue<String> getURLQueue() throws RemoteException {
		return urlQueue;
	}
		
	
	@Override
	public void sendFile(RemoteInputStream ristream) throws IOException {
		InputStream istream = RemoteInputStreamClient.wrap(ristream);
		FileOutputStream ostream = null;
		
		try {
			File tempFile = File.createTempFile("receivedFile_", ".txt");
			
			ostream = new FileOutputStream(tempFile);
			System.out.println("Writing file ...");
			
			byte[] buf = new byte[1024];
			int bytesRead = 0;
			while((bytesRead = istream.read(buf)) >= 0)
				ostream.write(buf, 0, bytesRead);
			ostream.flush();
			
			System.out.println("Finished writing file " + tempFile);
		} finally {
			try {
				if(istream != null)
					istream.close();
			} finally {
				if(ostream != null)
					ostream.close();
			}
		}
	}
	
	public void createReport() {
		
	}
	
	@Override
	public void assignWork(String iurl) throws RemoteException {
		if (!clientMap.isEmpty()) {
			// Generate typos and add them to queue
			getTyposType1(iurl);
			getTyposType2(iurl);
			getTyposType3(iurl);
			getTyposType4(iurl);
			getTyposType5(iurl);
			
			// Assign work to all clients registered in the map
			for (Client c : clientMap.values()) {
				c.crawl();
			}
		}
		else {
			System.out.println("Clients Not Found! Please Re-Enter When Clients Are Registered");
		}
	}
	
	// Type 1 Typos
	// June Jeong
	private void getTyposType1(String iurl) {
		String typoURL = null;

		boolean containsWww = iurl.startsWith("www.");
		
		if(containsWww) {
			int indexOfDot = iurl.indexOf('.');
			typoURL = iurl.substring(0, indexOfDot);
			typoURL += iurl.substring(indexOfDot+1);
			urlQueue.add(typoURL);
		}
	}
	
	// Type 2 Typos
	// June Jeong
	private void getTyposType2(String iurl) {
		int lengthOfURL = iurl.length();
		boolean containsWww = iurl.startsWith("www.");
				
		String typoURL = "";
		int index = 0;
		
		while(index != lengthOfURL) {
			if(containsWww && (index == 0)) 
				index = index + 2;
			if(index < lengthOfURL-2 && iurl.charAt(index) == iurl.charAt(index+1)) 
				index ++;
			if(iurl.charAt(index) == '.') 
				index ++;
			
			typoURL = iurl.substring(0, index);
			typoURL += iurl.substring(index+1);
			
			urlQueue.add(typoURL);
			index ++;
		}
	}

	// Type 3 Typos
	// Jay Moon
	private void getTyposType3(String iurl) {
		// Iterate through input string and generate typos
		for(int i = 0; i < iurl.length()-1; i++) {
			char currChar = iurl.charAt(i);
			char nextChar = iurl.charAt(i+1);

			// If two characters are not same
			if(currChar != nextChar) {
				// Initialize new string to swap characters
				StringBuilder sb = new StringBuilder(iurl);
				sb.setCharAt(i, nextChar);
				sb.setCharAt(i+1, currChar);

				// Add new typo to the list
				urlQueue.add(sb.toString());
			}
			// Else do not do anything and continue loop
		}
	}
	
	// Type 4 Typos
	// Henry Crain
	private void getTyposType4(String iurl) {
		Map<String, String[]> adjacencyMap = new AdjacentKeys().getMap();

		for (int i = 0; i < iurl.length(); i++) {
			StringBuilder typoUrl = new StringBuilder(iurl);

			String c = iurl.substring(i, i+1);
			String[] adj = adjacencyMap.get(c);

			for (String typo: adj) {
				typoUrl.setCharAt(i, typo.charAt(0));
				urlQueue.add(typoUrl.toString());
			}
		}
	}

	// Type 5 Typos
	// Nick Reimer
	private void getTyposType5(String iurl) {
		Map<String, String[]> map = new AdjacentKeys().getMap();
		int i = 0;
		int j;
		List<String> list = new ArrayList<String>();
		while (i < iurl.length()) {
			j = 0;
			String[] adjacent = map.get(iurl.substring(i, i + 1));
			while (j < adjacent.length) {
				String urlTypo = iurl.substring(0, i) + adjacent[j] + iurl.substring(i, iurl.length());
				if (!list.contains(adjacent[j])) {
					list.add(adjacent[j]);
					urlQueue.add(urlTypo);
				}
				j++;
			}
			j = 0;
			list = new ArrayList<String>();
			while (j < adjacent.length) {
				String urlTypo = iurl.substring(0, i + 1) + adjacent[j] + iurl.substring(i + 1, iurl.length());
				if (!list.contains(adjacent[j])) {
					list.add(adjacent[j]);
					urlQueue.add(urlTypo);
				}
				j++;
			}
			i++;
		}
	}

	
	
}
