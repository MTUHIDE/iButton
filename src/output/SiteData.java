package output;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import gui.IButtonApp;
import handlers.FileHandler;

public class SiteData {
	public static final File SITE_DATA = new File(FileHandler.DATA_FOLDER + "/" + IButtonApp.user + "_sites.txt");

	public static void addSite(String siteID, String deviceID) {
		try {
			if (findSite(siteID) == null)
				FileHandler.writeToFile(siteID + ":" + deviceID + System.lineSeparator(), SITE_DATA, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void updateSite(String siteID, String deviceID) {
		removeSite(siteID);
		addSite(siteID, deviceID);
	}

	public static void removeSite(String siteID) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(SITE_DATA));
			String site;
			String temp = "";
			while ((site = br.readLine()) != null) {

				String[] siteinfo = site.split(":");
				if (!siteinfo[0].equals(siteID)) {
					temp += site + System.lineSeparator();
				}
			}
			br.close();
			FileHandler.writeToFile(temp, SITE_DATA, false);
		} catch (IOException e) {

		}
	}

	public static String[] findSite(String siteID) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(SITE_DATA));
			String site;
			while ((site = br.readLine()) != null) {
				String[] siteinfo = site.split(":");
				if (siteinfo[0].equals(siteID)) {
					br.close();
					return siteinfo;
				}
			}
			br.close();
			return null;
		} catch (IOException e) {
			return null;
		}

	}

}
