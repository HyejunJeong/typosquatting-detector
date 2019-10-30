package typosquatting_detector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.lang.StringBuilder;

public class MasterNode {

	// Store generated typos here
	LinkedList<String> typos;

	// User input
	String input;

	public MasterNode(String input) {
		// Initialize typos queue
		this.typos = new LinkedList<String>();

		// Get user input
		this.input = input;

		// Generate typos
		getType1Typos(input);
		getType2Typos(input);
		getType3Typos(input);
//		getType4Typos(input);
//		getType5Typos(input);
	}
	
//	// Debug
//	public String getInput() {
//		// Initialize scanner
//		Scanner sc = new Scanner(System.in);
//
//		// Ask user for input
//		System.out.print("Enter Address: ");
//
//		// Pass the input to getTypo3
//		return sc.nextLine();
//	}
//	
//	// Debug
//	public void foo() {
//		System.out.println("foo");
//	}

	// Type 1 Typos
	// June Jeong
	public void getType1Typos(String input) {
		String typoURL = null;

		boolean containsWww = input.startsWith("www.");
		
		if(containsWww) {
			int indexOfDot = input.indexOf('.');
			typoURL = input.substring(0, indexOfDot);
			typoURL += input.substring(indexOfDot+1);
			typos.add(typoURL);
		}
	}
	
	// Type 2 Typos
	// June Jeong
	public void getType2Typos(String input) {
		int lengthOfURL = input.length();
		boolean containsWww = input.startsWith("www.");
				
		String typoURL = "";
		int index = 0;
		
		while(index != lengthOfURL) {
			if(containsWww && (index == 0)) 
				index = index + 2;
			if(index < lengthOfURL-2 && input.charAt(index) == input.charAt(index+1)) 
				index ++;
			if(input.charAt(index) == '.') 
				index ++;
			
			typoURL = input.substring(0, index);
			typoURL += input.substring(index+1);
			
			typos.add(typoURL);
			index ++;
		}
	}

	// Type 3 Typos
	// Jay Moon
	public void getType3Typos(String input) {

		// Iterate through input string and generate typos
		for(int i = 0; i < input.length()-1; i++) {
			char currChar = input.charAt(i);
			char nextChar = input.charAt(i+1);

			// If two characters are not same
			if(currChar != nextChar) {
				// Initialize new string to swap characters
				StringBuilder sb = new StringBuilder(input);
				sb.setCharAt(i, nextChar);
				sb.setCharAt(i+1, currChar);

				// Add new typo to the list
				typos.add(sb.toString());
			}

			// Else do not do anything and continue loop
		}
	}
	
//	// Type 4 Typos
//	// Henry Crain
//	private void getType4Typos(String url) {
//		Map<String, String[]> adjacencyMap = adjacentMap("Adjacent.json");
//
//		for (int i = 0; i < url.length(); i++) {
//			StringBuilder typoUrl = new StringBuilder(url);
//
//			String c = url.substring(i, i+1);
//			String[] adj = adjacencyMap.get(c);
//
//			for (String typo: adj) {
//				typoUrl.setCharAt(i, typo.charAt(0));
//				typos.add(typoUrl.toString());
//			}
//		}
//	}
//
//	// Type 5 Typos
//	// Nick Reimer
//	public void getType5Typos(String url) {
//		Map<String, String[]> map = adjacentMap("Adjacent.json");
//		int i = 0;
//		int j;
//		List<String> list = new ArrayList<String>();
//		while (i < url.length()) {
//			j = 0;
//			String[] adjacent = map.get(url.substring(i, i + 1));
//			while (j < adjacent.length) {
//				String urlTypo = url.substring(0, i) + adjacent[j] + url.substring(i, url.length());
//				if (!list.contains(adjacent[j])) {
//					list.add(adjacent[j]);
//					typos.add(urlTypo);
//				}
//				j++;
//			}
//			j = 0;
//			list = new ArrayList<String>();
//			while (j < adjacent.length) {
//				String urlTypo = url.substring(0, i + 1) + adjacent[j] + url.substring(i + 1, url.length());
//				if (!list.contains(adjacent[j])) {
//					list.add(adjacent[j]);
//					typos.add(urlTypo);
//				}
//				j++;
//			}
//			i++;
//		}
//	}
//
//	private Map<String, String[]> adjacentMap(String filename) {
//		JSONParser parser = new JSONParser();
//		Map<String, String[]> map = new HashMap<>();
//
//		try {
//			JSONObject obj = (JSONObject) parser.parse(new FileReader(filename));
//			JSONArray arr = (JSONArray) obj.get("keys");
//
//			for (Object value : arr) {
//				JSONObject o = (JSONObject) value;
//				JSONArray jsonChars = (JSONArray) o.get("adjacent");
//				String[] chars = new String[jsonChars.size()];
//				for (int j = 0; j < jsonChars.size(); j++) {
//					chars[j] = (String) jsonChars.get(j);
//				}
//				map.put((String) o.get("key"), chars);
//			}
//		} catch(IOException | ParseException e) {
//			e.printStackTrace();
//		}
//		return map;
//	}
	
	public String getTypos() {
		return typos.toString();
	}
	
}