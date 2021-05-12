package media.gui;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import media.global.Instances;
import media.gui.controller.LoadingStageController;
import media.util.UiToolkit;

public class Main {

	public void start(Stage primaryStage) {

		LoadingStageController controller = new LoadingStageController(primaryStage);

		Parent root = UiToolkit.loadingFXML(getClass().getResource("fxml/LoadingStage.fxml"), controller);

		Scene scene = new Scene(root);

		primaryStage.setTitle("Media Server");

		primaryStage.setMaximized(true);

		primaryStage.setScene(scene);	
		
		primaryStage.setOnCloseRequest((s) -> {
			Platform.exit();
			Instances.exit();
			System.exit(0);
		});
		
		primaryStage.show();

		primaryStage.setFullScreen(true);

	}

}
