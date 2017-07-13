package output;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 
 * Used for logging errors and user actions. The log file can be found in the
 * data folder (C://user/AppData/Roaming/iButtonData).
 * 
 * @author Justin Havely
 *
 */
public class Logger {
	public static final File LogFile = new File(FileHandler.DATA_FOLDER + "/log.txt");

	/**
	 * Logs a new line of text into the log file with the local time.
	 * 
	 * @param text
	 *            The text to be log.
	 */
	public static void writeToLog(String text) {
		try {
			FileHandler.writeToFile(new Date() + "\t" + text + System.lineSeparator(), LogFile, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Logs a print out of the exception into the log file with the local time.
	 * 
	 * @param error
	 *            The exception to be log.
	 */
	public static void writeErrorToLog(Exception error) {
		try {
			FileHandler.writeToFile(new Date() + "\t" + "Error: " + error.toString() + System.lineSeparator(), LogFile,
					true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
