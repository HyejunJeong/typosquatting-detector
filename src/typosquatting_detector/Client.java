package typosquatting_detector;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;

public class Client {
	
	private static ServerInterface si;
	
	public static void main(String args[]) throws MalformedURLException, RemoteException, NotBoundException {
		// Get remote server object
		si = (ServerInterface) Naming.lookup("//localhost/Server");
		
		// Debug
		LinkedList<String> queue = si.getQueue();
		for (String s : queue) {
			System.out.println(s);
		}
		
		// Crawl URL from here...
		String url = si.pollURL();
		
		// Debug
		System.out.println("Polled URL: " + url);
	}

}
