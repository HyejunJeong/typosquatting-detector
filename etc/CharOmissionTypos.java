package etc;

import java.util.Scanner;

public class CharOmissionTypos {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String url = input.nextLine();	
		int lengthOfURL = url.length();
		
		int sizeOfTypoArr = 0;
		String[] typoURLs;
		
		boolean containsWww = url.startsWith("www.");
		
		// if url contains "www.", max # case = url.length()-2
		// else, max # case = url.length()-1
		
		// google.com case
		if(!containsWww)
			sizeOfTypoArr = lengthOfURL - 1;
		
		// www.google.com case
		if(containsWww) 
			sizeOfTypoArr = lengthOfURL - 2;
		
		// array size (sizeOfTypoArr) is the maximum possible # possibilities.
		typoURLs = new String[sizeOfTypoArr];
		
		int index = 0;
		int i = 0;

		while(index != lengthOfURL) {
			if(containsWww && (index == 0)) 
				index = index + 2;
			if(index < lengthOfURL-2 && url.charAt(index) == url.charAt(index+1)) 
				index ++;
			if(url.charAt(index) == '.') 
				index ++;
			
			typoURLs[i] = url.substring(0, index);
			typoURLs[i] += url.substring(index+1);
			System.out.println(typoURLs[i]);
			index ++;
			i ++;
		}
	}
}
