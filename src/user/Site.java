package user;


/**
 * Created by jmh62 on 7/10/2017.
 */
public class Site {

    public String id, siteName, siteDescription;
    public float siteLatitude, siteLongitude;

    /**
     * @return The site's name given by the user.
     */
    public String toString() {
        return siteName;
    }

    /**
     *
     * @return A block of text that contains the site's name, description,
     *         location, and the iButton assigned to it.
     */
    public String getInfo() {
        return "Site ID: " + id + "\nSite Name: " + siteName + "\nDescription: " + siteDescription + "\nLatitude: "
                + siteLatitude + "\nLongitude: " + siteLongitude;
    }

}
