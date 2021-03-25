package media.interfaces;

import media.model.MessageSocketModel;
import media.model.SocketEventModel;

public interface SocketEventListener {
	
	public void messageReceived(MessageSocketModel message);
	
	public void connectionStatusChanged(SocketEventModel event);
	
	
}
