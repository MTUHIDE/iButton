package handlers;

import java.sql.Date;

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
		Sample oldest = samples[0];
		for (Sample s : samples) {
			if (s.getTime() > oldest.getTime()) {
				oldest = s;
			}
		}
		return oldest;
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
			CSV += s.toString() + "\n";
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
		 * column: Temperature to two decimal places. Second column: Time the
		 * sample was taken in milliseconds since 1970, UTC.
		 * 
		 * @return CSV formatted string of a sample.
		 */
		public String toString() {
			return new Date(time) + "," + String.format("%.2f", temperature);
		}

	}

}
