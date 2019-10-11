import java.io.FileReader;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Typo {

    public static void main(String []args) {
        Map<String, String[]> map = adjacentMap("./src/Adjacent.json");
        String url = "dacb";
        ArrayList<String> list = generateTypo(url, map);
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static ArrayList<String> generateTypo(String url, Map<String, String[]> map) {
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

    //Replace arr with adjacent characters data structures
    //System.out.println => push urlTypo onto queue

    public static Map<String, String[]> adjacentMap(String filename) {
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
            while(i.hasNext()) {
                o = (JSONObject) i.next();
                jsonChars = (JSONArray) o.get("adjacent");
                String [] chars = new String[jsonChars.size()];
                for(int j = 0; j < jsonChars.size(); j++) {
                    chars[j] = (String) jsonChars.get(j);
                }
                map.put((String) o.get("key"), chars);
            }
        } catch(java.io.FileNotFoundException e) {
            e.printStackTrace();
        } catch(java.io.IOException e) {
            e.printStackTrace();
        } catch(org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        return map;
    }
}