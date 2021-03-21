package media.gui.controller;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import media.global.Instances;
import media.gui.Main;
import media.interfaces.LoadingInstanceListener;
import media.util.UiToolkit;

public class LoadingStageController implements LoadingInstanceListener {

	@FXML
	private ProgressBar loadingProgressBar;
	@FXML
	private Label statusLoadingLabel;

	private Stage primaryStage;

	private double progressValue;

	public LoadingStageController(Stage primaryStage) {
		// TODO Auto-generated constructor stub
		this.primaryStage = primaryStage;
	}

	@FXML
	private void initialize() {
		statusLoadingLabel.setText("Carregando");
		progressValue = 0;
		Instances.addListener(this);
		loadingTask();
	}

	private void loadingTask() {
		Service<Void> loading = new Service<Void>() {

			@Override
			protected Task<Void> createTask() {
				// TODO Auto-generated method stub
				return new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						// TODO Auto-generated method stub
						Instances.init();
						return null;
					}
				};
			}
		};

		loading.start();
	}

	@Override
	public void notify(String msg) {
		// TODO Auto-generated method stub
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				statusLoadingLabel.setText(msg);
				progressValue += 0.4;
				loadingProgressBar.setProgress(progressValue);
			}
		});

		
	}

	@Override
	public void completed() {
		// TODO Auto-generated method stub
		loadingProgressBar.setProgress(1);
		changeIdleStage();

	}

	private void changeIdleStage() {

		IdleStageController controller = new IdleStageController(primaryStage);
		Parent root = UiToolkit.loadingFXML(Main.class.getResource("fxml/IdleStage.fxml"), controller);

		primaryStage.getScene().setRoot(root);

	}

}
