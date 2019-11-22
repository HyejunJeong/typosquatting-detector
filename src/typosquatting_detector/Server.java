package typosquatting_detector;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.StringBuilder;

public class Server extends UnicastRemoteObject implements ServerInterface {

	private static final long serialVersionUID = 1L;
	
	private LinkedList<String> queue;
	
	public Server(LinkedList<String> iqueue) throws RemoteException {
		super();
		queue = iqueue;
	}
	
	@Override
	public LinkedList<String> getQueue() {
		return queue;
	}
	
	@Override
	public String pollURL() throws RemoteException {
		return queue.poll();
	}
	
	public void sendFile(String fileName, RemoteInputStream remoteFileData) throws IOException {
		InputStream fileData = RemoteInputStreamClient.wrap(remoteFileData);
	}
	
	public static void main(String args[]) {
		// Initialize squeue
		LinkedList<String> squeue = new LinkedList<String>();
		
		// Generate typos and add them to queue
		getTyposType1(squeue, args[0]);
		getTyposType2(squeue, args[0]);
		getTyposType3(squeue, args[0]);
		getTyposType4(squeue, args[0]);
		getTyposType5(squeue, args[0]);
		
		try {
			// Bind remote server object with queue
			Naming.rebind("//localhost/Server", new Server(squeue));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Type 1 Typos
	// June Jeong
	private static void getTyposType1(LinkedList<String> iqueue, String iurl) {
		String typoURL = null;

		boolean containsWww = iurl.startsWith("www.");
		
		if(containsWww) {
			int indexOfDot = iurl.indexOf('.');
			typoURL = iurl.substring(0, indexOfDot);
			typoURL += iurl.substring(indexOfDot+1);
			iqueue.add(typoURL);
		}
	}
	
	// Type 2 Typos
	// June Jeong
	private static void getTyposType2(LinkedList<String> iqueue, String iurl) {
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
			
			iqueue.add(typoURL);
			index ++;
		}
	}

	// Type 3 Typos
	// Jay Moon
	private static void getTyposType3(LinkedList<String> iqueue, String iurl) {

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
				iqueue.add(sb.toString());
			}
			// Else do not do anything and continue loop
		}
	}
	
	// Type 4 Typos
	// Henry Crain
	private static void getTyposType4(LinkedList<String> iqueue, String iurl) {
		Map<String, String[]> adjacencyMap = new Adjacent().getMap();

		for (int i = 0; i < iurl.length(); i++) {
			StringBuilder typoUrl = new StringBuilder(iurl);

			String c = iurl.substring(i, i+1);
			String[] adj = adjacencyMap.get(c);

			for (String typo: adj) {
				typoUrl.setCharAt(i, typo.charAt(0));
				iqueue.add(typoUrl.toString());
			}
		}
	}

	// Type 5 Typos
	// Nick Reimer
	private static void getTyposType5(LinkedList<String> iqueue, String iurl) {
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
					iqueue.add(urlTypo);
				}
				j++;
			}
			j = 0;
			list = new ArrayList<String>();
			while (j < adjacent.length) {
				String urlTypo = iurl.substring(0, i + 1) + adjacent[j] + iurl.substring(i + 1, iurl.length());
				if (!list.contains(adjacent[j])) {
					list.add(adjacent[j]);
					iqueue.add(urlTypo);
				}
				j++;
			}
			i++;
		}
	}

	/*private static Map<String, String[]> adjacentMap(String filename) {
		JSONParser parser = new JSONParser();
		Map<String, String[]> map = new HashMap<>();
		

		try {
			JSONObject obj = (JSONObject) parser.parse(new FileReader(filename));
			JSONArray arr = (JSONArray) obj.get("keys");

			for (Object value : arr) {
				JSONObject o = (JSONObject) value;
				JSONArray jsonChars = (JSONArray) o.get("adjacent");
				String[] chars = new String[jsonChars.size()];
				for (int j = 0; j < jsonChars.size(); j++) {
					chars[j] = (String) jsonChars.get(j);
				}
				map.put((String) o.get("key"), chars);
			}
		} catch(IOException | ParseException e) {
			e.printStackTrace();
		}
		return map;
	}*/
}
