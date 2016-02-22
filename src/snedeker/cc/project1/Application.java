package snedeker.cc.project1;

import java.io.IOException;
import java.text.ParseException;

import snedeker.cc.project1.services.MeanFinder;

public class Application {

	private static MeanFinder meanFinder;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		meanFinder = new MeanFinder();
		
		try {
			meanFinder.run();
		} catch (IOException | ParseException e) {
			System.out.println("Unable to run MeanFinder: " + e);
		}
		
		meanFinder.displayValues();
	}

}
