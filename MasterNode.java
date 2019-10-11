// Merge codes here

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.lang.StringBuilder;

public class MasterNode {

	// Name
	// Typo Type 1
	

	// Name
	// Typo Type 2


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


	// Henry Crain
	// Typo Type 5
	private List<String> characterReplacement(String url) {
		List<String> typos = new ArrayList<>();
		Map<String, String[]> adjacencyMap = adjacentMap("Adjacent.json");

		for (int i = 0; i < url.length(); i++) {
			StringBuilder typoUrl = new StringBuilder(url);

			String c = url.substring(i, i+1);
			String[] adj = adjacencyMap.get(c);

			for (String typo: adj) {
				typoUrl.setCharAt(i, typo.charAt(0));
				typos.add(typoUrl.toString());
			}
		}

		return typos;
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
}