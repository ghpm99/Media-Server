package media;

import javafx.application.Application;
import javafx.stage.Stage;
import media.gui.Main;

public class MediaServer extends Application {
		
	Main main = new Main();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		main.start(arg0);
	}

}
