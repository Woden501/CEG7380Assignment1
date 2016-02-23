package snedeker.cc.project1.single;

import java.io.IOException;
import java.text.ParseException;

import snedeker.cc.project1.single.services.MeanFinder;

public class Application {

	private static MeanFinder meanFinder;
	
	public static void main(String[] args) {
		
		// Creating the MeanFinder Service
		meanFinder = new MeanFinder();
		
		// Attempt to have the service read the values from the input file.
		try {
			meanFinder.readValues();
		} catch (IOException | ParseException e) {
			System.out.println("Unable to run MeanFinder: " + e);
		}
		
		// Output the means for window sizes of both 3 and 4 to System.out
		System.out.println("Window size 3:");
		meanFinder.calculateMeans(3);
		System.out.println("\nWindow size 4:");
		meanFinder.calculateMeans(4);
	}

}
