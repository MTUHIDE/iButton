package gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import app.IButtonApp;
import com.dalsemi.onewire.OneWireException;

import com.dalsemi.onewire.container.OneWireContainer21;
import ibutton.MissionHandler;
import ibutton.MissionSamples;
import network.DeviceController;
import network.LoginController;
import network.NetworkController;
import network.UploadController;
import output.TemperatureData;
import output.Logger;
import user.Device;
import user.Site;

import java.awt.*;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * The JPanel for showing the users sites and upload, edit site, and add site
 * functions. This GUI is the main hub for the user.
 * 
 * @author Justin Havely
 *
 */
public class DashBoard extends GUI implements ListSelectionListener {
	private static final long serialVersionUID = 1L;

	private JList<Device> list; // All of the user's sites
	private JTextArea info; // Displays information about the selected site
	private JButton btnUpload, btnAbout, btnLogOut, btnEditSite,btnMission;

	/**
	 * Creates and adds all of the components to the JPanel.
	 */
	public DashBoard() {
		super("Dashboard");
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setBackground(new Color(0, 0, 205));

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{98, 174, 96, 0};
		gbl_contentPane.rowHeights = new int[]{0, 121, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gbl_contentPane);


		// The list of the user's sites
		list = new JList<Device>();
//		list.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, SystemColor.activeCaption));
////		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
////		list.setBackground(Color.WHITE);
////		list.setBounds(10, 28, 177, 400);
		list.setBackground(new Color(192, 192, 192));
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.insets = new Insets(0, 0, 0, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 0;
		gbc_list.gridy = 1;
		list.addListSelectionListener(this);
		add(list, gbc_list);

		//Main text area panel
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);

		info = new JTextArea();
		info.setBackground(new Color(192, 192, 192));
		info.setEditable(false);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 0;
		gbc_textArea.gridy = 0;
		panel.add(info, gbc_textArea);




		//Control Panel
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(new Color(0, 0, 255));
		GridBagConstraints gbc_controlPanel = new GridBagConstraints();
		gbc_controlPanel.fill = GridBagConstraints.BOTH;
		gbc_controlPanel.gridx = 2;
		gbc_controlPanel.gridy = 1;
		add(controlPanel, gbc_controlPanel);
		GridBagLayout gbl_controlPanel = new GridBagLayout();
		gbl_controlPanel.columnWidths = new int[]{65, 0, 0};
		gbl_controlPanel.rowHeights = new int[]{23, 0, 0, 0};
		gbl_controlPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_controlPanel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		controlPanel.setLayout(gbl_controlPanel);


		// Setting Button
//		btnAbout = new JButton("About");
//		btnAbout.addActionListener(this);
//		btnAbout.setBackground(Color.LIGHT_GRAY);
//		btnAbout.setBounds(755, 338, 89, 23);
//		add(btnAbout);
		btnAbout = new JButton("About");
		GridBagConstraints gbc_about = new GridBagConstraints();
		gbc_about.insets = new Insets(0, 0, 5, 0);
		gbc_about.gridx = 1;
		gbc_about.gridy = 0;
		btnAbout.addActionListener(this);
		btnAbout.setBackground(Color.LIGHT_GRAY);
		controlPanel.add(btnAbout, gbc_about);


		//Mission
//		btnMission = new JButton("Mission");
//		btnMission.addActionListener(this);
//		btnMission.setBackground(Color.LIGHT_GRAY);
//		btnMission.setBounds(755, 372, 100, 23);
//		add(btnMission);
		btnMission = new JButton("Mission");
		GridBagConstraints gbc_mission = new GridBagConstraints();
		gbc_mission.insets = new Insets(0, 0, 5, 0);
		gbc_mission.gridx = 1;
		gbc_mission.gridy = 1;
		btnMission.addActionListener(this);
		btnMission.setBackground(Color.LIGHT_GRAY);
		controlPanel.add(btnMission, gbc_mission);

		// Logout Button
//		btnLogOut = new JButton("Logout");
//		btnLogOut.addActionListener(this);
//		btnLogOut.setBackground(Color.LIGHT_GRAY);
//		btnLogOut.setBounds(755, 406, 89, 23);
//		add(btnLogOut);
		btnLogOut = new JButton("Logout");
		GridBagConstraints gbc_logout = new GridBagConstraints();
		gbc_logout.gridx = 1;
		gbc_logout.gridy = 2;
		btnLogOut.addActionListener(this);
		btnLogOut.setBackground(Color.LIGHT_GRAY);
		controlPanel.add(btnLogOut, gbc_logout);

		// The subJPanel that contains site information, edit site, and upload
		// components. Mainly for looks.
//		JPanel panel = new JPanel();
//		panel.setBounds(197, 28, 548, 400);
//		add(panel);
//		panel.setLayout(null);

		// Text box that displays site information
//		info = new JTextArea();
//		info.setBounds(10, 11, 409, 378);
//		info.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, SystemColor.activeCaption));
//		info.setEditable(false);
//		info.setBackground(Color.WHITE);
//		panel.add(info);

		// UploadController Button
//		btnUpload = new JButton("Upload");
//		btnUpload.setBounds(429, 12, 109, 23);
//		btnUpload.setEnabled(false);
//		btnUpload.addActionListener(this);
//		btnUpload.setBackground(Color.LIGHT_GRAY);
//		panel.add(btnUpload);
		btnUpload = new JButton("Upload");
		GridBagConstraints gbc_btnUpload = new GridBagConstraints();
		gbc_btnUpload.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpload.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnUpload.gridx = 0;
		gbc_btnUpload.gridy = 0;
		btnUpload.addActionListener(this);
		btnUpload.setEnabled(false);
		btnUpload.setBackground(Color.LIGHT_GRAY);
		controlPanel.add(btnUpload, gbc_btnUpload);

		// Edit SiteController Button
//		btnEditSite = new JButton("Edit Site");
//		btnEditSite.addActionListener(this);
//		btnEditSite.setEnabled(false);
//		btnEditSite.setBackground(Color.LIGHT_GRAY);
//		btnEditSite.setBounds(429, 46, 109, 23);
//		panel.add(btnEditSite);
		btnEditSite = new JButton("Assign Site");
		GridBagConstraints gbc_btnEditSites = new GridBagConstraints();
		gbc_btnEditSites.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditSites.gridx = 0;
		gbc_btnEditSites.gridy = 1;
		btnEditSite.addActionListener(this);
		btnEditSite.setEnabled(false);
		btnEditSite.setBackground(Color.LIGHT_GRAY);
		controlPanel.add(btnEditSite, gbc_btnEditSites);
	}

	/**
	 * Updates the siteControllers list in the dashboard. This should be call when ever a
	 * site is added or edited.
	 *
	 */
	public void update() {
		DefaultListModel<Device> model = new DefaultListModel<Device>();
		List<Device> devices = NetworkController.currentLoggedInUser.getDevices();

		for (Device d : devices) {
			model.addElement(d);
		}

		list.setModel(model);
	}

	/**
	 * Listens for when the selected site is changed. Sets the edit and upload
	 * buttons to be active or not.
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
        info.setText("");
		if (list.getSelectedValue() == null) {
			btnEditSite.setEnabled(false);
			btnUpload.setEnabled(false);

		} else {
            btnEditSite.setEnabled(true);

            Site site = NetworkController.currentLoggedInUser.getSite(list.getSelectedValue().siteID);

			if(site != null){
                info.setText(site.getInfo());

                // Enables the upload button if device has an site
                if (list.getSelectedValue().iButton != null) {
                    btnUpload.setEnabled(true);
                } else {
                    btnUpload.setEnabled(false);
                }
            }
		}
	}

	/**
	 * Listen for mouse clicks and contains the logic for uploading data from an
	 * iButton iButton.
	 */
	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand().equals("Upload")) {
			// Gets the siteController, iButton, and the iButton mission container.
			Site site = NetworkController.currentLoggedInUser.getSite(list.getSelectedValue().siteID);
			Device device = list.getSelectedValue();
			byte [] state =null;
			OneWireContainer21 ms = (OneWireContainer21) device.iButton;
			try {
				state = ms.readDevice();
			} catch (OneWireException e) {
				Logger.writeErrorToLog(e);
			}
			try {
				try {
					// Gets temperature readings
					byte [] tempLog = ms.getTemperatureLog(state);
					MissionSamples samples = new MissionSamples(tempLog.length);
					Calendar time_stamp = ms.getMissionTimeStamp(state);
					int sample_rate = ms.getSampleRate(state);
					long time = time_stamp.getTime().getTime()+ms.getFirstLogOffset(state);
					char temp_standard= 'C';
					for(int i=0;i<tempLog.length;i++){
						samples.addSample(ms.decodeTemperature(tempLog[i]),temp_standard,time);
						time+=sample_rate*60*1000;
					}


					if (samples.getLength() < 1) {
						// No readings to upload.
						JOptionPane.showMessageDialog(this, "No temperatures taken. Please wait an hour.");
						return;
					}

					// Creates local CSV file with temperature data.
					TemperatureData tempFile = new TemperatureData(device.manufacture_num, samples);
					tempFile.writeDataFile();

					// Uploads file to server and logs the action.
					if (UploadController.uploadFile(site, new File(tempFile.location))) {
						JOptionPane.showMessageDialog(this, "Upload Successful");
						Logger.writeToLog("Wrote data to:" + site.getInfo() + "from: " + device.manufacture_num);
					} else {
						JOptionPane.showMessageDialog(this, "Upload Failed");
					}

				} catch (IOException e) {
					Logger.writeErrorToLog(e);
				}

			} catch (OneWireException e) {
				Logger.writeErrorToLog(e);
			}

		}
		// Shows settings JPanel
		if (action.getActionCommand().equals("About")) {
			IButtonApp.showCard(GUI.about);
		}
		if(action.getActionCommand().equals("Mission")){
			IButtonApp.showCard(GUI.missionControl);
		}
		// Shows login JPanel
		if (action.getActionCommand().equals("Logout")) {
			LoginController.logout();
			IButtonApp.showCard(GUI.login);
		}
		// Shows edit site JPanel
		if (action.getActionCommand().equals("Assign Site")) {

            Device device = list.getSelectedValue();
            OneWireContainer21 owc = (OneWireContainer21) device.iButton;
            List<Site> retrievedSites = new ArrayList<>();

			retrievedSites = NetworkController.currentLoggedInUser.getSites();
			if(retrievedSites.size()==0)
			{
				JOptionPane.showMessageDialog(null,"You have no sites " +
						"please go to the website and create one");
				return;
			}

            Site site = (Site) JOptionPane.showInputDialog(null, "Pick a site",
                    "", JOptionPane.QUESTION_MESSAGE, null,
                    NetworkController.currentLoggedInUser.getSites().toArray(),
			 		 retrievedSites.get(0));
            if(site == null){
                return;
            }

            if(device.id != null) {
				OneWireContainer21 ms = (OneWireContainer21) device.iButton;
				try {
					MissionHandler.startMission(ms);
					DeviceController.editDevice(device.id, site.id, device.deviceType, device.manufacture_num,device.shelterType);
				} catch (OneWireException e) {
                    Logger.writeErrorToLog(e);
                }
            } else {
				OneWireContainer21 ms = (OneWireContainer21) device.iButton;
                try {
                    MissionHandler.startMission(ms);
                    DeviceController.addDevice(site.id, device.deviceType, device.manufacture_num, device.shelterType);
                } catch (OneWireException e) {
                    Logger.writeErrorToLog(e);
                }
            }
            update();
		}
	}
}
