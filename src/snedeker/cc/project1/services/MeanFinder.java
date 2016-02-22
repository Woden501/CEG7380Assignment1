package snedeker.cc.project1.services;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

public class MeanFinder {

	final int WINDOW_SIZE = 3;
	
	TreeMap<String, TreeMap<Date, Double>> companies;
	
	public MeanFinder() {
		companies = new TreeMap<>();
	}
	
	public void run() throws IOException, ParseException {
		String text = FileUtils.readFileToString(new File(MeanFinder.class.getResource("/snedeker/cc/project1/resources/sample.txt").getPath()));
		
		String[] entries = text.split("\n");
		for (String entry : entries) {
			// [0] - company, [1] - date, [2] - value
			String[] values = entry.split(",");
			TreeMap<Date, Double> dateValues = companies.get(values[0]);
			
			if (dateValues != null) {
				// Add date and value pair to company's entry
				addDateValue(values, dateValues);
			}
			else {
				// Create new company entry.  Add date value pair to it.
				dateValues = new TreeMap<>();
				addDateValue(values, dateValues);
				companies.put(values[0], dateValues);
			}
		}
	}

	private void addDateValue(String[] values, TreeMap<Date, Double> dateValues) throws ParseException {
		DateFormat format = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
		Date date = format.parse(values[1]);
		dateValues.put(date, Double.parseDouble(values[2]));
	}

	public void displayValues() {
				
	}
}
