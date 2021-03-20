/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package media.server.global;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import media.server.GUI.IdlePanel;
import media.server.interfaces.LoadingInstanceListener;
import media.server.socket.ServerSocketService;

/**
 *
 * @author ghpm9
 */
public class Instances {

    private static final ArrayList<LoadingInstanceListener> listeners = new ArrayList<>();

    private static ServerSocketService socket;

    private static IdlePanel idlePanel;

    public static void init() {
        
        try {
            initSocket();
            Thread.sleep(1000);
            initIdlePanel();
            Thread.sleep(1000);
            notifyListenersCompletes();
        } catch (InterruptedException ex) {
            Logger.getLogger(Instances.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void initSocket() {
        socket = new ServerSocketService();
        socket.init();
        notifyListeners("Socket iniciado");
    }

    private static void initIdlePanel() {
        idlePanel = new IdlePanel();
        notifyListeners("Painel criado");
    }

    public static void addListener(LoadingInstanceListener listener) {
        listeners.add(listener);
    }

    public static void removeListener(LoadingInstanceListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    private static void notifyListeners(String msg) {
        listeners.forEach((s) -> {
            s.notify(msg);
        });
    }
    
    private static void notifyListenersCompletes(){
        listeners.forEach((s) -> {
            s.completed();
        });
    }

    public static ServerSocketService getSocket(){
        if (socket == null) {
            throw new NullPointerException("Uninitialized instance");
        } else {
            return socket;
        }
    }

    public static IdlePanel getIdlePanel() {
        if (idlePanel == null) {
            throw new NullPointerException("Uninitialized instance");
        } else {
            return idlePanel;
        }
    }

    public static void exit(){
        if(socket != null){
            socket.close();
        }
    }
    
}
