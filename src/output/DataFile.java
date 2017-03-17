package output;

import java.io.File;
import java.io.IOException;

import handlers.FileHandler;
import handlers.MissionSamples;

/**
 * Creates a CSV formatted file containing a header and mission samples, located
 * the data folder. The Header contains column names (Temperatures and Time) and
 * the address of the iButton.
 * 
 * @author Justin Havely
 *
 */
public class DataFile {

	public final MissionSamples samples;
	public final String address;
	private File datFile;

	public final String location;

	/**
	 * Creates a new file for storing mission data and other valuable
	 * information, such as the address. An Address is the unique identification
	 * number each iButton has. The name of this file is
	 * address_timeOfLastSample_dat.csv.
	 * 
	 * @param address
	 *            The address of the iButton that the mission samples came from.
	 * @param samples
	 *            The Mission's temperature and time readings.
	 */
	public DataFile(String address, MissionSamples samples) {
		this.samples = samples;
		this.address = address;
		location = FileHandler.DATA_FOLDER + "/" + address + "_" + samples.getNewestSample().getTime() + "_dat.csv";
		datFile = new File(location);
	}

	/**
	 * Writes the data file in the data folder.
	 * 
	 * @throws IOException
	 *             If file could not be written.
	 */
	public void writeDataFile() throws IOException {
		String head = "dateTime,temperature" + System.lineSeparator();
		FileHandler.writeToFile(head + samples.toString(), datFile, false);
	}

}
