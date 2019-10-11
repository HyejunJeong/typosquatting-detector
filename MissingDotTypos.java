package typoGeneration;

import java.util.Scanner;

public class MissingDotTypos {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String url = input.nextLine();	
		
		String typoURL = null;

		boolean containsWww = url.startsWith("www.");

		if(containsWww) {
			int indexOfDot = url.indexOf('.');
			//typoURL = url.replace(".", "");
			typoURL = url.substring(0, indexOfDot);
			typoURL += url.substring(indexOfDot+1);
			
			//System.out.println(typoURL);
		}
		System.out.println(typoURL);

	}
}
