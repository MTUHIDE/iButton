package gui;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.gson.Gson;

import network.Site;

public class IButtonApp extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final float version = 1.1f;
	public static JPanel activePanel;

	public IButtonApp() {
		try {
			activePanel = new DashBoard(getSites());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(activePanel);
		setPreferredSize(new Dimension(854, 480));
		pack();
		setVisible(true);
		this.setResizable(false);
	}

	public static void main(String[] args) {
		new IButtonApp();
	}

	public static Site[] getSites() throws FileNotFoundException {
		// InputStream response =
		// Authentication.loginAuthentication(username.getText(),
		// passwordField.getPassword().toString(), new URL("URL"));

		// Reader reader = new InputStreamReader(response);

		String JSON_PATH = "C:/Users/justin/Documents/Research/testjson.json";

		Gson gson = new Gson();
		BufferedReader br = new BufferedReader(new FileReader(JSON_PATH));
		Site[] sites = gson.fromJson(br, Site[].class);
		return sites;
	}

	public static void login() {

	}

}
