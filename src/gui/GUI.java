package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jmh62 on 7/12/2017.
 */
public class GUI extends JPanel implements ActionListener {
    public final String cardName;

    public static Login login = new Login();
    public static AddSite Addsite = new AddSite();
    public static EditSite editSite = new EditSite();
    public static Settings settings = new Settings();
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
