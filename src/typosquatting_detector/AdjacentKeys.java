package typosquatting_detector;

import java.util.HashMap;
import java.util.Map;

public class AdjacentKeys {
	
	private Map<String, String[]> map = new HashMap<>();
	
	public AdjacentKeys() {
		this.createMap();
	}
	
	public Map<String, String[]> getMap() {
		return this.map;
	}
	
	private void createMap() {
		String [] arr = {"q", "w", "s", "z"};					this.map.put("a", arr);
		String [] arr2 = {"v", "g", "h", "n"};					this.map.put("b", arr2);
		String [] arr3 = {"x", "d" , "f", "v"};					this.map.put("c", arr3);
		String [] arr4 = {"s", "e", "r", "f", "c", "x"};		this.map.put("d", arr4);
		String [] arr5 = { "w", "s", "d", "r", "3", "4"};		this.map.put("e", arr5);
		String [] arr6 = {"d", "r", "t", "g", "v", "c"};		this.map.put("f", arr6);
		String [] arr7 = { "f", "t", "y", "h", "b", "v"};		this.map.put("g", arr7);
		String [] arr8 = { "g", "y", "u", "j", "n", "b"};		this.map.put("h", arr8);
		String [] arr9 = { "u", "j", "k", "o", "9", "8"};		this.map.put("i", arr9);
		String [] arr10 = { "u", "i", "k", "m", "n", "h"};		this.map.put("j", arr10);
		String [] arr11 = { "j", "i", "o", "l", ",", "m"};		this.map.put("k", arr11);
		String [] arr12 = {"k", "o", "p", ";", ".", ","};		this.map.put("l", arr12);
		String [] arr13 = {"n", "j", "k", ","};					this.map.put("m", arr13);
		String [] arr14 = {"b", "h", "j", "m"};					this.map.put("n", arr14);
		String [] arr15 = {"i", "9", "0", "p", "l", "k"};		this.map.put("o", arr15);
		String [] arr16 = {"o", "0", "-", "[", ";", "l"};		this.map.put("p", arr16);
		String [] arr17 = {"1", "2", "w", "a"};					this.map.put("q", arr17);
		String [] arr18 = {"e", "4", "5", "t", "f", "d"};		this.map.put("r", arr18);
		String [] arr19 = {"a", "w", "e", "d", "x", "z"};		this.map.put("s", arr19);
		String [] arr20 = {"r", "5", "6", "y", "g", "f"};		this.map.put("t", arr20);
		String [] arr21 = {"y", "7", "8", "i", "j", "h"};		this.map.put("u", arr21);
		String [] arr22 = {"c", "f", "g", "b"};					this.map.put("v", arr22);
		String [] arr23 = {"q", "2", "3", "e", "s", "a"};		this.map.put("w", arr23);
		String [] arr24 = {"z", "s", "d", "c"};					this.map.put("x", arr24);
		String [] arr25 = {"t", "6", "7", "u", "h", "g"};		this.map.put("y", arr25);
		String [] arr26 = {"a", "s", "x"};						this.map.put("z", arr26);
		String [] arr27 = { "q", "2"};							this.map.put("1", arr27);
		String [] arr28 = {"1", "q", "w", "3"};					this.map.put("2", arr28);
		String [] arr29 = {"2", "w", "e", "4"};					this.map.put("3", arr29);
		String [] arr30 = {"3", "e", "r", "5"};					this.map.put("4", arr30);
		String [] arr31 = {"4", "r", "t", "6"};					this.map.put("5", arr31);
		String [] arr32 = {"5", "t", "y", "7"};					this.map.put("6", arr32);
		String [] arr33 = {"6", "y", "u", "8"};					this.map.put("7", arr33);
		String [] arr34 = {"7", "u", "i", "9"};					this.map.put("8", arr34);
		String [] arr35 = {"8", "i", "o", "0"};					this.map.put("9", arr35);
		String [] arr36 = {"9", "o", "p", "-"};					this.map.put("0", arr36);
		String [] arr37 = {"m", "k", "l", "."};					this.map.put(",", arr37);
		String [] arr38 = {"m", "k", "l", ">"};					this.map.put("<", arr38);
		String [] arr39 = {"<", "l", ":", "?"};					this.map.put(">", arr39);
		String [] arr40 = {",", "l", ";", "/"};					this.map.put(".", arr40);
		String [] arr41 = {".", ";", "'"};						this.map.put("/", arr41);
		String [] arr42 = {">", ":", "'"};						this.map.put("?", arr42);
		String [] arr43 = {",", "l", ";", "/"};					this.map.put(".", arr43);
		String [] arr44 = {"l", "p", "[", "'", "/", "."};		this.map.put(";", arr44);
		String [] arr45 = {"l", "p", "{", "'", "?", ">"};		this.map.put(":", arr45);
		String [] arr46 = {"/", ";", "[", "]"};					this.map.put("'", arr46);
		String [] arr47 = { ";", "p", "-", "=", "]", "'"};		this.map.put("[", arr47);
		String [] arr48 = {":", "p", "_", "+", "}", "'"};		this.map.put("{", arr48);
		String [] arr49 = {"'", "[", "="};						this.map.put("]", arr49);
		String [] arr50 = {",", "{", "+"};						this.map.put("}", arr50);
		String [] arr51 = {"0", "p", "[", "="};					this.map.put("-", arr51);
		String [] arr52 = {")", "p", "{", "+"};					this.map.put("_", arr52);
		String [] arr53 = {"-", "[", "]"};						this.map.put("=", arr53);
		String [] arr54 = {"_", "{", "}"};						this.map.put("+", arr54);
		String [] arr55 = {"q", "@"};							this.map.put("!", arr55);
		String [] arr56 = {"!", "q", "w", "#"};					this.map.put("@", arr56);
		String [] arr57 = {"@", "w", "e", "$"};					this.map.put("#", arr57);
		String [] arr58 = {"#", "e", "r", "%"};					this.map.put("$", arr58);
		String [] arr59 = {"$", "r", "t", "^"};					this.map.put("%", arr59);
		String [] arr60 = {"%", "t", "y", "&"};					this.map.put("^", arr60);
		String [] arr61 = {"^", "y", "u", "*"};					this.map.put("&", arr61);
		String [] arr62 = {"&", "u", "i", "("};					this.map.put("*", arr62);
		String [] arr63 = {"*", "i", "o", ")"};					this.map.put("(", arr63);
		String [] arr64 = {"(", "o", "p", "_"};					this.map.put(")", arr64);
	}
	
}
