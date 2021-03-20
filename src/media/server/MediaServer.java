/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package media.server;

import java.awt.Color;
import javax.swing.UIManager;
import media.server.GUI.MainFrame;

/**
 *
 * @author ghpm9
 */
public class MediaServer {

    private static MainFrame mainFrame;

    private MediaServer() {
        init();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new MediaServer();
    }

    private void init() {
        System.out.println("Iniciando");
        setStyleUI();
        initGUI();

    }

    private void setStyleUI() {        
        System.out.print("Setando Look and Feel");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    javax.swing.UIManager.put("ProgressBar.background", Color.black);
                    javax.swing.UIManager.put("ProgressBar.foreground", Color.white);
                    System.out.print(".");
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        System.out.println("");
        System.out.println("Look and Feel setado");
    }

    private void initGUI() {
        System.out.println("Carregando interface");
        mainFrame = new MainFrame();
        System.out.println("Interface carregada");
        System.out.println("Iniciando interface");
        java.awt.EventQueue.invokeLater(() -> {
            mainFrame.init();
        });
        System.out.println("Interface iniciada");
    }

    public static MainFrame getMainFrameInstance() {
        return mainFrame;
    }

}
