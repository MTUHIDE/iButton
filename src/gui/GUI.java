package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Justin Havely
 */
public class GUI extends JPanel implements ActionListener {
    public final String cardName;

    public static Login login;

    static {
        try {
            login = new Login();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static About about;

    static {
        try {
            about = new About();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MissionControl missionControl;

    static {
        try {
            missionControl = new MissionControl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DashBoard dashboard = new DashBoard();

    public GUI(String cardName){
        this.cardName = cardName;
    }

    public void update(){

    }

    @Override
    public void actionPerformed(ActionEvent action) {

    }

}
