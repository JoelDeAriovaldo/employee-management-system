package main.java;

import javax.swing.SwingUtilities;
import main.java.com.rh.ui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        // Set up logging (if needed)
        // Logger setup code here

        // Launch the main UI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}