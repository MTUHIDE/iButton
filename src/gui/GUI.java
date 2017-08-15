package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Justin Havely
 */
public class GUI extends JPanel implements ActionListener {
    public final String cardName;

    public static Login login = new Login();
    public static About about = new About();
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
