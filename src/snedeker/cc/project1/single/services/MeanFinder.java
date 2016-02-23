package snedeker.cc.project1.single.services;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

/**
 * The MeanFinder class reads the values into self-sorting TreeMaps then computes the mean with the given window size.
 * 
 * @author Colby Snedeker
 *
 */
public class MeanFinder {
	
	TreeMap<String, TreeMap<Date, Double>> companies;
	
	/**
	 * Default constructor.  Simply instantiates the companies TreeMap.
	 */
	public MeanFinder() {
		companies = new TreeMap<>();
	}
	
	/**
	 * Parses the values out of the sample.txt file. Checks if the company is already present in
	 * the top TreeMap. If it is not present create a new TreeMap for the new companies date/price
	 * pairs.  Pass the date/price pair, and value TreeMap to the addDateValue function.
	 * 
	 * @throws IOException The sample.txt file was unable to be read.
	 * @throws ParseException The addDateValue function through a ParseException.
	 */
	public void readValues() throws IOException, ParseException {
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

	/**
	 * Converts the date and price from Strings to Java Date and Double.  Puts those values
	 * into the provided TreeMap with date as the key and price as the value.
	 * 
	 * @param values A String array that contains the company code, date, and price.
	 * @param dateValues The TreeMap that contains a company's date/price pairs.
	 * @throws ParseException The date String is not valid.
	 */
	private void addDateValue(String[] values, TreeMap<Date, Double> dateValues) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		format.setLenient(false);
		Date date = format.parse(values[1]);
		dateValues.put(date, Double.parseDouble(values[2]));
	}

	/**
	 * Calculates the mean for the specified window size, and prints it to System.out.
	 * 
	 * @param windowSize The number of past dates to take the average of.
	 */
	public void calculateMeans(int windowSize) {
		for (Map.Entry<String, TreeMap<Date, Double>> entry : companies.entrySet()) {
			int entryCount = 0;
			double total = 0;
			
			for (Map.Entry<Date, Double> dateValue : entry.getValue().descendingMap().entrySet()) {
				if (entryCount < windowSize) {
					++entryCount;
					total += dateValue.getValue();
				}
			}
			
			double mean = total / (double) entryCount;
			
			DecimalFormat df = new DecimalFormat("#.00");
			
			System.out.println("Company: " + entry.getKey() + ", Mean Value: " + df.format(mean));
		}
	}
}
