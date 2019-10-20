package etc;

// Jay Moon

import java.util.Scanner;

public class TestTypo3 {

	public static void main(String args[]) {

		// Initialize scanner
		Scanner sc = new Scanner(System.in);

		// Ask user for input
		System.out.print("Enter Address: ");

		// Pass the input to getTypo3
		Typo3 t3 = new Typo3();
		t3.getTypo3(sc.nextLine());
	}
}
