package output;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import handlers.FileHandler;

/**
 * 
 * Will be used for logging errors and actions.
 * 
 * @author Justin Havely
 *
 */
public class Logger {
	public static final File LogFile = new File(FileHandler.DATA_FOLDER + "/log.txt");

	public static void writeToLog(String text) {
		try {
			FileHandler.writeToFile(new Date() + "\t" + text + System.lineSeparator(), LogFile, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeErrorToLog(Exception error) {
		try {
			FileHandler.writeToFile(new Date() + "\t" + "Error: " + error.toString() + System.lineSeparator(), LogFile,
					true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
