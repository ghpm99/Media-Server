/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package media.server.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ghpm9
 */
public class ServerSocketService {

    private ServerSocket socket;

    public void init() {
        try {
            socket = new ServerSocket(33325);

        } catch (IOException ex) {
            Logger.getLogger(ServerSocketService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerSocketService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
