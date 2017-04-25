package output;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import gui.IButtonApp;
import handlers.FileHandler;

/**
 * A file in the data folder to save which iButton is assign to which site. The
 * name of the file is %user%_sites.
 * 
 * 
 * (Site:iButton_address): f6148a49-d286-4057-9f26-fe11988828b2:8100000033B9B041
 * 
 * @author Justin Havely
 *
 */
public class SiteData {
	public static final File SITE_DATA = new File(FileHandler.DATA_FOLDER + "/" + IButtonApp.user + "_sites.txt");

	/**
	 * Adds a new site to the siteData file with an iButton device's address
	 * value pair with it.
	 * 
	 * @param siteID
	 *            The site's ID
	 * @param deviceID
	 *            The iButton device's address
	 * @return True if site was added, or false if the site was not added.
	 */
	public static boolean addSite(String siteID, String deviceID) {
		try {
			if (findSiteBySite(siteID) == null) {
				FileHandler.writeToFile(siteID + ":" + deviceID + System.lineSeparator(), SITE_DATA, true);
			}
			return true;
		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return false;
		}
	}

	/**
	 * Updates a site's iButton device.
	 * 
	 * @param siteID
	 *            The ID of the site to be updated.
	 * @param deviceID
	 *            The new iButton device address.
	 * @return True if site was updated, or false if the site was not updated.
	 */
	public static boolean updateSite(String siteID, String deviceID) {
		if (removeSite(siteID) && addSite(siteID, deviceID)) {
			return true;
		}
		return false;
	}

	/**
	 * Removes a site from the siteData file.
	 * 
	 * @param siteID
	 *            The ID of the site to be removed.
	 * @return True if the site was removed. False if the site was not removed.
	 */
	public static boolean removeSite(String siteID) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(SITE_DATA));
			String site;
			String temp = "";

			// Searches the siteData file for all sites that do not match siteID
			// and appends them to the temp string.
			while ((site = br.readLine()) != null) {
				String[] siteinfo = site.split(":");
				if (!siteinfo[0].equals(siteID)) {
					temp += site + System.lineSeparator();
				}
			}
			br.close();

			// Writes over old siteData file
			FileHandler.writeToFile(temp, SITE_DATA, false);
			return true;
		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return false;
		}
	}

	/**
	 * Checks if a site is in the siteData file.
	 * 
	 * @param siteID
	 *            The site's ID.
	 * @return A String array with index zero being the ID of the site and index
	 *         one being the device address assigned to it. Null, if no site is
	 *         found.
	 */
	public static String[] findSiteBySite(String siteID) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(SITE_DATA));
			String site;
			// Searches the siteData file for siteID
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
			Logger.writeErrorToLog(e);
			return null;
		}

	}

	/**
	 * Checks if a site is in the siteData file.
	 * 
	 * @param deviceAdress
	 *            The device Address connected to the site.
	 * @return A String array with index zero being the ID of the site and index
	 *         one being the device address assigned to it.
	 */
	public static String[] findSiteByDevice(String deviceAddress) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(SITE_DATA));
			String site;
			while ((site = br.readLine()) != null) {
				String[] siteinfo = site.split(":");
				if (siteinfo[1].equals(deviceAddress)) {
					br.close();
					return siteinfo;
				}
			}
			br.close();
			return null;
		} catch (IOException e) {
			Logger.writeErrorToLog(e);
			return null;
		}

	}

}
