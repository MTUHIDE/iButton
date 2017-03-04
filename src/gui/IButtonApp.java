package gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class IButtonApp extends JFrame {
	private static final long serialVersionUID = 1L;
	public static JPanel activePanel;

	public IButtonApp() {
		activePanel = new Login();
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
	

}
