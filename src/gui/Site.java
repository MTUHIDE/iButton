package gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Site extends JPanel{
	public Site() {
		setLayout(null);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.LIGHT_GRAY);
		btnBack.setBounds(351, 266, 89, 23);
		add(btnBack);
		
		textField = new JTextField();
		textField.setBounds(182, 61, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblSiteName = new JLabel("Site Name");
		lblSiteName.setBounds(182, 36, 64, 14);
		add(lblSiteName);
		
		textField_1 = new JTextField();
		textField_1.setBounds(182, 107, 86, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(182, 154, 86, 20);
		add(textField_2);
		textField_2.setColumns(10);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(95, 193, 261, 45);
		add(textArea);
		
		JLabel lblLat = new JLabel("Lat");
		lblLat.setBounds(180, 92, 46, 14);
		add(lblLat);
		
		JLabel lblLon = new JLabel("Lon");
		lblLon.setBounds(182, 138, 46, 14);
		add(lblLon);
		
		JLabel lblDesrcpiton = new JLabel("Desrcpiton");
		lblDesrcpiton.setBounds(95, 170, 64, 14);
		add(lblDesrcpiton);

	}
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
}
