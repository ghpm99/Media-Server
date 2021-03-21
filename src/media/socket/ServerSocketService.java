package media.socket;

import java.io.IOException;
import java.net.ServerSocket;



public class ServerSocketService {

	private ServerSocket socket;

    public void init() {
        try {
            socket = new ServerSocket(33325);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
    }
	
}
