/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package media.server.GUI;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import media.server.global.Instances;

/**
 *
 * @author ghpm9
 */
public class MainFrame extends javax.swing.JFrame {

    private STATUS status;
    
    public static enum STATUS {
        LOADING, IDLE, ACTIVE, CLOSING;
    };

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }

    public void init() {        
        getContentPane().add(new LoadingPanel());
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Media Server");
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            status = STATUS.CLOSING;
            exit();
        }
    }//GEN-LAST:event_formKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    public void setPanel(JPanel newPanel, String alignment, STATUS status) {
        this.status = status;
        updatePanel(newPanel, alignment);
    }

    private void updatePanel(JPanel newPanel, String alignment) {
        getContentPane().removeAll();
        getContentPane().add(newPanel);
        revalidate();
        
    }

    public STATUS getStatus() {
        return status;
    }

    private void exit() {
        Instances.exit();
        System.exit(0);
    }
}