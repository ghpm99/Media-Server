package media.util;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import media.global.Instances;

public class UiToolkit {

	public static <T> T loadingFXML(URL fxml, Object controller) {

		FXMLLoader fxmlLoader = new FXMLLoader(fxml);

		fxmlLoader.setController(controller);
		
		try {
			return fxmlLoader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Instances.exit();
			System.exit(0);
			return null;
		}
	}

}
