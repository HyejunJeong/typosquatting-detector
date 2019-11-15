package typosquatting_detector;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.lang.StringBuilder;

public class Server {
	
	LinkedList<String> typos;
	
	public Server(String url) throws NotBoundException, Exception {
		// Debug
		System.out.println("hi from server's scan");
		
		// Store generated typos here
		typos = new LinkedList<String>();
		
		// Generate all five types of typos
		getTyposType1(url);
		getTyposType2(url);
		getTyposType3(url);
//		getTyposType4(url);
//		getTyposType5(url);
		
		// Assign work to clients
		Service tester = new Service();
		Naming.rebind("Test", tester);
		Client client = new Client();
		client.go();
	}

	// Type 1 Typos
	// June Jeong
	private void getTyposType1(String url) {
		String typoURL = null;

		boolean containsWww = url.startsWith("www.");
		
		if(containsWww) {
			int indexOfDot = url.indexOf('.');
			typoURL = url.substring(0, indexOfDot);
			typoURL += url.substring(indexOfDot+1);
			typos.add(typoURL);
		}
	}
	
	// Type 2 Typos
	// June Jeong
	private void getTyposType2(String url) {
		int lengthOfURL = url.length();
		boolean containsWww = url.startsWith("www.");
				
		String typoURL = "";
		int index = 0;
		
		while(index != lengthOfURL) {
			if(containsWww && (index == 0)) 
				index = index + 2;
			if(index < lengthOfURL-2 && url.charAt(index) == url.charAt(index+1)) 
				index ++;
			if(url.charAt(index) == '.') 
				index ++;
			
			typoURL = url.substring(0, index);
			typoURL += url.substring(index+1);
			
			typos.add(typoURL);
			index ++;
		}
	}

	// Type 3 Typos
	// Jay Moon
	private void getTyposType3(String url) {

		// Iterate through input string and generate typos
		for(int i = 0; i < url.length()-1; i++) {
			char currChar = url.charAt(i);
			char nextChar = url.charAt(i+1);

			// If two characters are not same
			if(currChar != nextChar) {
				// Initialize new string to swap characters
				StringBuilder sb = new StringBuilder(url);
				sb.setCharAt(i, nextChar);
				sb.setCharAt(i+1, currChar);

				// Add new typo to the list
				typos.add(sb.toString());
			}
			// Else do not do anything and continue loop
		}
	}
	
	// Type 4 Typos
	// Henry Crain
	private void getTyposType4(String url) {
		//Map<String, String[]> adjacencyMap = adjacentMap("Adjacent.json");
		Map<String, String[]> adjacencyMap = new HashMap<String, String[]>();
		String [] list = {"1", "2", "3"};
		String [] list2 = {"4", "5", "6"};
		adjacencyMap.put("a", list);
		adjacencyMap.put("b", list);
		System.out.println(adjacencyMap.size());

		for (int i = 0; i < url.length(); i++) {
			StringBuilder typoUrl = new StringBuilder(url);

			String c = url.substring(i, i+1);
			String[] adj = adjacencyMap.get(c);

			for (String typo: adj) {
				typoUrl.setCharAt(i, typo.charAt(0));
				typos.add(typoUrl.toString());
			}
		}
	}

	// Type 5 Typos
	// Nick Reimer
	private void getTyposType5(String url) {
		//Map<String, String[]> map = adjacentMap("Adjacent.json");
		Map<String, String[]> map = new HashMap<String, String[]>();
		String [] list1 = {"1", "2", "3"};
		String [] list2 = {"4", "5", "6"};
		map.put("a", list1);
		map.put("b", list2);
		int i = 0;
		int j;
		List<String> list = new ArrayList<String>();
		while (i < url.length()) {
			j = 0;
			String[] adjacent = map.get(url.substring(i, i + 1));
			while (j < adjacent.length) {
				String urlTypo = url.substring(0, i) + adjacent[j] + url.substring(i, url.length());
				if (!list.contains(adjacent[j])) {
					list.add(adjacent[j]);
					typos.add(urlTypo);
				}
				j++;
			}
			j = 0;
			list = new ArrayList<String>();
			while (j < adjacent.length) {
				String urlTypo = url.substring(0, i + 1) + adjacent[j] + url.substring(i + 1, url.length());
				if (!list.contains(adjacent[j])) {
					list.add(adjacent[j]);
					typos.add(urlTypo);
				}
				j++;
			}
			i++;
		}
	}

	private Map<String, String[]> adjacentMap(String filename) {
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
	}
	
	public String getTypos() {
		return typos.toString();
	}
	
}
