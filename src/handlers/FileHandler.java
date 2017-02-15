package handlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Used to write files into the data folder.
 * (%user%/Appdata/Roaming/iButtonData)
 * 
 * @author Justin Havely
 *
 */
public class FileHandler {
	public static final String DATA_FOLDER = System.getProperty("user.home") + "/AppData/Roaming/iButtonData";
	public static final File LogFile = new File(DATA_FOLDER + "/log.txt");

	/**
	 * 
	 * Check for the data folder exists and make one if it does not. Writes the
	 * file to the data folder.(%user%/Appdata/Roaming/iButtonData)
	 * 
	 * @param text
	 *            The text to be written into the file.
	 * @param file
	 *            The file to be written into the data folder.
	 * @throws IOException
	 *             If the file could not be written.
	 */
	public static void writeToFile(String text, File file) throws IOException {

		File f = new File(DATA_FOLDER);
		if (!f.exists()) {
			f.mkdir();
		}

		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(text);
		bw.close();
		fw.close();

	}

}
