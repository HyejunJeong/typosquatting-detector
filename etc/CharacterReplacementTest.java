package etc;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CharacterReplacementTest {

    private static final String FILENAME = "resources/Adjacent.json";

    public static void main(String[] args) {
        String url = args[0];
        List<String> typos = characterReplacement(url);

        for (String typo: typos) {
            System.out.println(typo);
        }
    }

    private static List<String> characterReplacement(String url) {
        List<String> typos = new ArrayList<>();
        Map<String, String[]> adjacencyMap = adjacentMap(FILENAME);

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

    @SuppressWarnings("SameParameterValue")
    private static Map<String, String[]> adjacentMap(String filename) {
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
