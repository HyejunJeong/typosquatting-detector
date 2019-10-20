package etc;

import java.util.LinkedList;
import java.util.Scanner;

public class ListCharOmissionTypos {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String url = input.nextLine();
		int lengthOfURL = url.length();
		boolean containsWww = url.startsWith("www.");
		
		LinkedList<String> typoList = new LinkedList<>();
		
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
			
			typoList.add(typoURL);
			System.out.println("[" + typoList.size() + "] " + typoURL);
			//System.out.println(typoList.size());
			index ++;
		}
		System.out.println("typolist.size(): " + typoList.size());
	}
}
