package gui;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.gson.Gson;

import network.Authentication;
import network.Site;

public class IButtonApp extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final float version = 1.1f;
	public static JPanel activePanel;

	public IButtonApp() {
		try {
			activePanel = new DashBoard(getSites());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(854, 480));
		add(activePanel);
		pack();
		setVisible(true);
		this.setResizable(false);
	}

	public static void main(String[] args) {
		new IButtonApp();
	}

	public static Site[] getSites() throws IOException {
		InputStream response;
		response = Authentication.loginAuthentication("jusbmx", "notagoodpass",
				new URL("https://cocotemp.herokuapp.com/dashboard/sites.json"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(response));
		Gson gson = new Gson();
		Site[] sites = gson.fromJson(reader, Site[].class);
		reader.close();

		return sites;
	}

	public static void login() {

	}

}
