package media.service;

import java.io.IOException;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import media.interfaces.SocketEventListener;
import media.model.MessageSocketModel;
import media.model.SocketEventModel;
import media.model.SystemCommandMessageSocketModel;
import media.model.SystemCommands;

public class SystemCommandService implements SocketEventListener {

	private Stage primaryStage;

	public SystemCommandService(Stage primaryStage) {
		super();
		this.primaryStage = primaryStage;
	}

	@Override
	public void messageReceived(MessageSocketModel message) {
		// TODO Auto-generated method stub
		if (message instanceof SystemCommandMessageSocketModel) {
			processCommand((SystemCommandMessageSocketModel) message);
		}
	}

	@Override
	public void connectionStatusChanged(SocketEventModel event) {
		// TODO Auto-generated method stub

	}

	private void processCommand(SystemCommandMessageSocketModel command) {
		if (command.getCommand() == SystemCommands.OFF) {
			try {
				Runtime.getRuntime().exec("shutdown -s -t 60");
				primaryStage.fireEvent(new WindowEvent(primaryStage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
