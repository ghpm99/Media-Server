package media.gui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import media.gui.controller.LoadingStageController;
import media.util.UiToolkit;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		LoadingStageController controller = new LoadingStageController(primaryStage);	

		Parent root = UiToolkit.loadingFXML(getClass().getResource("fxml/LoadingStage.fxml"), controller);
		
		Scene scene = new Scene(root);

		primaryStage.setTitle("Media Server");
		
		primaryStage.setMaximized(true);

		primaryStage.setScene(scene);
		
		primaryStage.show();

		primaryStage.setFullScreen(true);

	}

}
