// Merge codes here

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.*;
import java.lang.StringBuilder;

public class MasterNode {

	// June
	// Typo Type 1
	public void getTypo1(String input) {
		String typoURL = null;

		boolean containsWww = input.startsWith("www.");
		
		LinkedList<String> typoList = new LinkedList<>();

		if(containsWww) {
			int indexOfDot = input.indexOf('.');
			//typoURL = url.replace(".", "");
			typoURL = input.substring(0, indexOfDot);
			typoURL += input.substring(indexOfDot+1);
			typoList.add(typoURL);
		}
	}
	

	// June
	// Typo Type 2
	public void getTypo2(String input) {
		int lengthOfURL = input.length();
		boolean containsWww = input.startsWith("www.");
		
		LinkedList<String> typoList = new LinkedList<>();
		
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
			
			typoList.add(typoURL);
			//System.out.println("[" + typoList.size() + "] " + typoURL);
			//System.out.println(typoList.size());
			index ++;
		}
		//System.out.println("typolist.size(): " + typoList.size());

	}


	// Jay Moon
	// Typo Type 3
	public void getTypo3(String input) {

		// Store generated typos here
		LinkedList<String> typos = new LinkedList<String>();

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

		// Name
		// Typo Type 4


		// Nick Reimer
		// Typo Type 5
		//Part 5
		public ArrayList<String> generateTypo(String url, Map<String, String[]> map) {
			int i = 0;
			int j;
			List<String> list = new ArrayList<String>();
			ArrayList<String> typos = new ArrayList<String>();
			while(i < url.length()) {
				j = 0;
				String [] adjacent = map.get(url.substring(i, i+1));
				while(j < adjacent.length) {
					String urlTypo = url.substring(0, i) + adjacent[j] + url.substring(i, url.length());
					if(!list.contains(adjacent[j])) {
						list.add(adjacent[j]);
						typos.add(urlTypo);
					}
					j++;
				}
				j=0;
				list = new ArrayList<String>();
				while(j < adjacent.length) {
					String urlTypo = url.substring(0, i + 1) + adjacent[j] + url.substring(i + 1, url.length());
					if(!list.contains(adjacent[j])) {
						list.add(adjacent[j]);
						typos.add(urlTypo);
					}
					j++;
				}
				i++;
			}
			return typos;
		}

		public Map<String, String[]> adjacentMap(String filename){
			JSONParser parser = new JSONParser();
			JSONObject o;
			JSONArray jsonChars;
			org.json.simple.JSONObject obj;
			org.json.simple.JSONArray arr;
			Map<String, String[]> map = new HashMap<>();
			try {
				obj = (org.json.simple.JSONObject) parser.parse(new FileReader(filename));
				arr = (org.json.simple.JSONArray) obj.get("keys");
				Iterator i = arr.iterator();
				while (i.hasNext()) {
					o = (JSONObject) i.next();
					jsonChars = (JSONArray) o.get("adjacent");
					String[] chars = new String[jsonChars.size()];
					for (int j = 0; j < jsonChars.size(); j++) {
						chars[j] = (String) jsonChars.get(j);
					}
					map.put((String) o.get("key"), chars);
				}
			} catch (java.io.FileNotFoundException e) {
				e.printStackTrace();
			} catch (java.io.IOException e) {
				e.printStackTrace();
			} catch (org.json.simple.parser.ParseException e) {
				e.printStackTrace();
			}
			return map;
		}


}