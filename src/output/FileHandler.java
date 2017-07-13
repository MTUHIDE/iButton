package output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Used to write files into the data folder
 * (C://user/AppData/Roaming/iButtonData).
 * 
 * @author Justin Havely
 *
 */
public class FileHandler {
	public static final String DATA_FOLDER = System.getProperty("user.home") + "/AppData/Roaming/iButtonData";

	/**
	 * 
	 * Checks if the data folder exists. If it doesn't, one is made. Writes a
	 * text file to the data folder (C://user/AppData/Roaming/iButtonData). If
	 * another file exists with the same name and the parameter append is set to
	 * false the file will be over written.
	 * 
	 * @param text
	 *            The text to be written into the file.
	 * @param file
	 *            The file to be written into the data folder.
	 * @param append
	 *            False to write over old file. True to append data to old file.
	 * @throws IOException
	 *             If the file could not be written.
	 */
	public static void writeToFile(String text, File file, Boolean append) throws IOException {

		File f = new File(DATA_FOLDER);
		if (!f.exists()) {
			f.mkdir();
		}

		FileWriter fw = new FileWriter(file, append);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(text);
		bw.close();
		fw.close();

	}

}
