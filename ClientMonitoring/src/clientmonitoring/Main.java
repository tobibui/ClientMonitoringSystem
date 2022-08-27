/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientmonitoring;

import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author tuyen
 */
public class Main {
        public static void main(String[] args) throws IOException {
        // TODO code application logic here
        runApp();
    }

 
    private static void runApp() throws IOException {
        JFrame frame = new JFrame("Client Monitoring");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainGUI app = new MainGUI();
        frame.setContentPane(app.getRootPane());
        frame.pack();
        frame.setVisible(true);
    }
}
