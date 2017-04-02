package handlers;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Stores a list of mission samples containing temperature and time readings.
 * 
 * @author Justin Havely
 *
 */
public class MissionSamples {

	private Sample[] samples;
	private int count = 0;

	/**
	 * Stores a list of mission samples containing temperature and time
	 * readings.
	 * 
	 * @param maxNumOfSamples
	 *            The maximum number of samples to be stored.
	 */
	public MissionSamples(int maxNumOfSamples) {
		samples = new Sample[maxNumOfSamples];
	}

	/**
	 * Adds a new sample.
	 * 
	 * @param temperature
	 *            The temperature reading.
	 * @param time
	 *            The time the temperature reading was taken since 1970 UTC in
	 *            milliseconds.
	 */
	public void addSample(double temperature, long time) {
		samples[count++] = new Sample(temperature, time);
	}

	/**
	 * Returns the oldest sample taken in the current set of samples.
	 * 
	 * @return The oldest sample.
	 */
	public Sample getOldestSample() {
		if (samples.length < 1) {
			return null;
		}
		Sample oldest = samples[0];
		for (Sample s : samples) {
			if (s.getTime() < oldest.getTime()) {
				oldest = s;
			}
		}
		return oldest;
	}

	/**
	 * Returns the newest sample taken in the current set of samples.
	 * 
	 * @return The newest sample.
	 */
	public Sample getNewestSample() {
		if (samples.length < 1) {
			return null;
		}
		Sample newest = samples[0];
		for (Sample s : samples) {
			if (s.getTime() > newest.getTime()) {
				newest = s;
			}
		}
		return newest;
	}

	/**
	 * A text representation of all the added samples. Formatted for a CSV file.
	 * First column: Temperatures to two decimal places. Second column: Time the
	 * sample was taken in milliseconds since 1970, UTC. Each newline is a new
	 * sample.
	 * 
	 * @return CSV formatted string of all samples.
	 */
	public String toString() {
		String CSV = "";
		for (Sample s : samples) {
			CSV += s.toString() + System.lineSeparator();
		}
		return CSV;
	}

	/**
	 * Total number of samples added.
	 * 
	 * @return The number of samples.
	 */
	public int getLength() {
		return samples.length;
	}

	/**
	 * Used to Create a connection between a time and temperature.
	 * 
	 * @author Justin Havely
	 *
	 */
	public class Sample {
		private double temperature;
		private long time;
		private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		/**
		 * Creates a connection between a time and temperature.
		 * 
		 * @param temperature
		 *            The temperature reading.
		 * @param time
		 *            The time the temperature reading was taken since 1970 UTC
		 *            in milliseconds.
		 */
		public Sample(double temperature, long time) {
			this.temperature = temperature;
			this.time = time;
		}

		/**
		 * The temperature of this sample.
		 * 
		 * @return The temperature of this sample.
		 */
		public double getTemperature() {
			return temperature;
		}

		/**
		 * The time of this sample.
		 * 
		 * @return The time of this sample.
		 */
		public long getTime() {
			return time;
		}

		/**
		 * A text representation of a sample. Formatted for a CSV file. First
		 * column: Temperature to zero decimal places. Second column: Time the
		 * sample was taken in milliseconds since 1970, UTC.
		 * 
		 * EX: 2017-04-1 04:45:31,25
		 * 
		 * @return CSV formatted string of a sample.
		 */
		public String toString() {
			Date date = new Date(time);
			return format.format(date) + "," + String.format("%.0f", temperature);
		}

	}

}
