package media.gui.controller;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;
import javafx.scene.web.WebView;

public class YoutubePlayerController {

	@FXML
	private WebView webView;

	@FXML
	private void initialize() {
		webView.getEngine().load("https://www.youtube.com/embed/yoSlGuSUBZo");

		webView.getEngine().getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				System.out.println("Carregou");
				Robot robot = new Robot();
				robot.mouseClick(MouseButton.PRIMARY);
			}
		});

	}

}
