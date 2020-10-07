package gui;

import app.IButtonApp;
import com.dalsemi.onewire.OneWireException;
import com.dalsemi.onewire.container.OneWireContainer;
import com.dalsemi.onewire.container.OneWireContainer21;
import ibutton.IButtonHandler;
import ibutton.MissionHandler;
import ibutton.MissionSamples;
import output.Logger;
import output.TemperatureData;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class MissionControl extends GUI implements ActionListener {
    private JTextArea log;
    private JButton startMission;
    private JButton stopMission;
    private JButton readData;
    private JButton back;
    private JScrollPane scrollPane;
    private List<OneWireContainer> devices; // All connected iButtons
    private OneWireContainer21 activeDevice;

    public MissionControl() throws IOException {
        super("MissionControl");
        setBackground(new Color(0,0,205));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 60, 0, 0, 0, 0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gbl_contentPane);

        BufferedImage myPicture = ImageIO.read(new File("res/logo.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.gridx = 4;
        gbc_lblNewLabel.gridy = 1;
        add(picLabel, gbc_lblNewLabel);

        startMission = new JButton("New Mission");
        GridBagConstraints gbc_btnStart = new GridBagConstraints();
        gbc_btnStart.insets = new Insets(0, 0, 5, 5);
        gbc_btnStart.gridx = 4;
        gbc_btnStart.gridy = 3;
        add(startMission, gbc_btnStart);

        stopMission = new JButton("Stop Misson");
        GridBagConstraints gbc_btnStopMisson = new GridBagConstraints();
        gbc_btnStopMisson.insets = new Insets(0, 0, 5, 5);
        gbc_btnStopMisson.gridx = 4;
        gbc_btnStopMisson.gridy = 4;
        add(stopMission, gbc_btnStopMisson);

        readData = new JButton("Read Data");
        GridBagConstraints gbc_btnRead = new GridBagConstraints();
        gbc_btnRead.insets = new Insets(0, 0, 5, 5);
        gbc_btnRead.gridx = 4;
        gbc_btnRead.gridy = 5;
        add(readData, gbc_btnRead);

        log = new JTextArea(10,40);
        log.setBackground(new Color(192, 192, 192));

        scrollPane = new JScrollPane(log);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(new Color(0,0,205));
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 4;
        gbc_scrollPane.gridy = 6;
        add(scrollPane, gbc_scrollPane);

        back = new JButton("Back");
        GridBagConstraints gbc_btnBack = new GridBagConstraints();
        gbc_btnBack.insets = new Insets(0, 0, 5, 5);
        gbc_btnBack.gridx = 0;
        gbc_btnBack.gridy = 8;
        add(back, gbc_btnBack);

//        GridBagLayout gbl_contentPane = new GridBagLayout();
//        gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 64, 0, 0, 0, 0, 0};
//        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
//        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
//        setLayout(gbl_contentPane);
//
//        //Logo
//        BufferedImage logo = ImageIO.read(new File("C:\\Users\\wilma\\testGUI\\gui\\src\\images\\logo.png"));
//        JLabel picLabel = new JLabel(new ImageIcon(logo));
//        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
//        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
//        gbc_lblNewLabel.gridx = 4;
//        gbc_lblNewLabel.gridy = 1;
//        add(picLabel, gbc_lblNewLabel);
//
//        //Start mission button
//        startMission = new JButton("Start Mission");
//        GridBagConstraints gbc_btnSrtButton = new GridBagConstraints();
//        gbc_btnSrtButton.insets = new Insets(0, 0, 5, 5);
//        gbc_btnSrtButton.gridx = 4;
//        gbc_btnSrtButton.gridy = 3;
//        add(startMission, gbc_btnSrtButton);
//
//        //Stop mission button
//        stopMission = new JButton("Stop Mission");
//        GridBagConstraints gbc_btnStopMisson = new GridBagConstraints();
//        gbc_btnStopMisson.insets = new Insets(0, 0, 5, 5);
//        gbc_btnStopMisson.gridx = 4;
//        gbc_btnStopMisson.gridy = 4;
//        add(stopMission, gbc_btnStopMisson);
//
//        //Read Data Button
//        readData = new JButton("Read Data");
//        GridBagConstraints gbc_btnRead = new GridBagConstraints();
//        gbc_btnRead.insets = new Insets(0, 0, 5, 5);
//        gbc_btnRead.gridx = 4;
//        gbc_btnRead.gridy = 5;
//        add(readData, gbc_btnRead);
//
//        //Log area
//        log = new JTextArea(10, 40); // Displays helpful information
//        log.setBackground(new Color(0, 0, 205));
//        log.setEditable(false);
//        scrollPane = new JScrollPane(log);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.getViewport().setBackground(new Color(0,0,205));
//        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
//        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
//        gbc_scrollPane.fill = GridBagConstraints.BOTH;
//        gbc_scrollPane.gridx = 4;
//        gbc_scrollPane.gridy = 6;
//        add(scrollPane, gbc_scrollPane);
//
//        //Back button
//        back = new JButton("Back");
//        GridBagConstraints gbc_btnBack = new GridBagConstraints();
//        gbc_btnBack.insets = new Insets(0, 0, 0, 5);
//        gbc_btnBack.gridx = 0;
//        gbc_btnBack.gridy = 8;
//        add(back, gbc_btnBack);
//        scrollV = new JScrollPane(log);
//        scrollV.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        //Add Listeners
        startMission.addActionListener(this);
        stopMission.addActionListener(this);
        readData.addActionListener(this);
        back.addActionListener(this);

        setVisible(true);
        this.load();
    }
    private void load() {
        try {
            devices = IButtonHandler.getIButtons(IButtonHandler.DEVICE_DEFAULT_NAMES, IButtonHandler.ADAPTER_DEFAULT_NAME);
            if(devices.size() != 0) {
                // Change the value '0' to set a different iButton as active, if multiple iButtons are plugged in
                activeDevice = (OneWireContainer21) devices.get(0);

                log.append("Found Adapters\n");
            } else {
                log.append("No iButtons plugged in\n");
            }
        } catch (OneWireException e) {
            log.append("Could not Find Adapters!\n");
        }
    }
    @Override
    public void actionPerformed(ActionEvent a) {
        // Starts a new mission with default settings
        if (a.getSource() == startMission) {
            try {
                MissionHandler.startMission(activeDevice);
                log.append("Mission Started\n");
            } catch (OneWireException e) {
                log.append("Failed to start mission!\n");
            }
        }
        // Stops the current mission
        else if (a.getSource() == stopMission) {
            try {
                MissionHandler.stopMission(activeDevice);
                log.append("Mission ended\n");
            } catch (OneWireException e) {
                log.append("Failed to stop mission!\n");
            }
        }
        else if(a.getSource()==back){
            log.setText(null);
            IButtonApp.showPreviousCard();
        }
        // Writes mission samples into the data folder(C://Users/{user}/AppData/Roaming/iButtonData)
        else if (a.getSource() == readData) {

            log.append("Loading Mission...\n");

            byte[] state = null;
            try {
                state = activeDevice.readDevice();
            } catch (OneWireException e) {
                Logger.writeErrorToLog(e);
            }
            try {
                // Gets temperature readings
                byte[] tempLog = activeDevice.getTemperatureLog(state);
                MissionSamples samples = new MissionSamples(tempLog.length);
                Calendar time_stamp = activeDevice.getMissionTimeStamp(state);
                int sample_rate = activeDevice.getSampleRate(state);
                long time = time_stamp.getTime().getTime() + activeDevice.getFirstLogOffset(state);
                char temp_standard = 'C';
                for (int i = 0; i < tempLog.length; i++) {
                    samples.addSample(activeDevice.decodeTemperature(tempLog[i]),temp_standard, time);
                    time += sample_rate * 60 * 1000;
                }
                if(samples.getLength()>0) {
                    log.append("Writing to file...\n");
                    new TemperatureData(activeDevice.getAddressAsString(), samples).writeDataFile();
                    log.append("Done! File location: C://user/AppData/Roaming/iButtonData\n");
                }
                else{
                    log.append("No data available yet\n");
                }
            } catch (IOException | OneWireException e) {
                log.append("Failed to read data!\n");
            }
        }
    }
}

