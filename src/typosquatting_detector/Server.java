package typosquatting_detector;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.lang.StringBuilder;
import java.net.MalformedURLException;

public class Server extends UnicastRemoteObject implements RemoteInterface {

	private static final long serialVersionUID = 1L;
	
	private LinkedList<RemoteInterface> clientList;		// List of clients
	private ConcurrentLinkedQueue<String> urlQueue;		// Queue of URLs
	
	public Server(ConcurrentLinkedQueue<String> iurlQueue) throws RemoteException {
		urlQueue = iurlQueue;
		clientList = new LinkedList<RemoteInterface>();
	}
	
	@Override
	public void addClient(RemoteInterface iclient) throws RemoteException {
		clientList.add(iclient);
	}
	
	@Override
	public String pollURL() throws RemoteException {
		return urlQueue.poll();
	}
	
	@Override
	public LinkedList<RemoteInterface> getClientList() throws RemoteException {
		return clientList;
	}
	
	@Override
	public ConcurrentLinkedQueue<String> getURLQueue() throws RemoteException {
		return urlQueue;
	}
	
	@Override
	public String getClientID() throws RemoteException { return null; }
	
	public static void main(String args[]) {
		// Print message for the users
		System.out.println("Starting Server...");
		
		// Initialize nurlQueue
		ConcurrentLinkedQueue<String> nurlQueue = new ConcurrentLinkedQueue<String>();
		
		// Generate typos and add them to queue
		getTyposType1(nurlQueue, args[0]);
		getTyposType2(nurlQueue, args[0]);
		getTyposType3(nurlQueue, args[0]);
		getTyposType4(nurlQueue, args[0]);
		getTyposType5(nurlQueue, args[0]);
		
		// Bind remote server object with queue
		try {
			Naming.rebind("//localhost/Server", new Server(nurlQueue));
			
			// Print message for the users
			System.out.println("Server Successfully Started!");
		} 
		catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	// Type 1 Typos
	// June Jeong
	private static void getTyposType1(ConcurrentLinkedQueue<String> iurlQueue, String iurl) {
		String typoURL = null;

		boolean containsWww = iurl.startsWith("www.");
		
		if(containsWww) {
			int indexOfDot = iurl.indexOf('.');
			typoURL = iurl.substring(0, indexOfDot);
			typoURL += iurl.substring(indexOfDot+1);
			iurlQueue.add(typoURL);
		}
	}
	
	// Type 2 Typos
	// June Jeong
	private static void getTyposType2(ConcurrentLinkedQueue<String> iurlQueue, String iurl) {
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
			
			iurlQueue.add(typoURL);
			index ++;
		}
	}

	// Type 3 Typos
	// Jay Moon
	private static void getTyposType3(ConcurrentLinkedQueue<String> iurlQueue, String iurl) {
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
				iurlQueue.add(sb.toString());
			}
			// Else do not do anything and continue loop
		}
	}
	
	// Type 4 Typos
	// Henry Crain
	private static void getTyposType4(ConcurrentLinkedQueue<String> iurlQueue, String iurl) {
		Map<String, String[]> adjacencyMap = new Adjacent().getMap();

		for (int i = 0; i < iurl.length(); i++) {
			StringBuilder typoUrl = new StringBuilder(iurl);

			String c = iurl.substring(i, i+1);
			String[] adj = adjacencyMap.get(c);

			for (String typo: adj) {
				typoUrl.setCharAt(i, typo.charAt(0));
				iurlQueue.add(typoUrl.toString());
			}
		}
	}

	// Type 5 Typos
	// Nick Reimer
	private static void getTyposType5(ConcurrentLinkedQueue<String> iurlQueue, String iurl) {
		Map<String, String[]> map = new Adjacent().getMap();
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
					iurlQueue.add(urlTypo);
				}
				j++;
			}
			j = 0;
			list = new ArrayList<String>();
			while (j < adjacent.length) {
				String urlTypo = iurl.substring(0, i + 1) + adjacent[j] + iurl.substring(i + 1, iurl.length());
				if (!list.contains(adjacent[j])) {
					list.add(adjacent[j]);
					iurlQueue.add(urlTypo);
				}
				j++;
			}
			i++;
		}
	}
	
}
