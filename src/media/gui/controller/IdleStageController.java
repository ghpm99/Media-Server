package media.gui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import media.gui.Main;
import media.util.SystemInfoService;
import media.util.UiToolkit;

public class IdleStageController {
	@FXML
	private ProgressBar cpuProgressBar;

	@FXML
	private ProgressBar ramProgressBar;

	@FXML
	private Label cpuLabel;

	@FXML
	private Label ramLabel;

	@FXML
	private Label timeLabel;

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	private Timer systemInfoUpdateTimer = new Timer();

	private Stage primaryStage;

	public IdleStageController(Stage primaryStage) {
		// TODO Auto-generated constructor stub
		this.primaryStage = primaryStage;
	}

	@FXML
	private void initialize() {
		systemInfoUpdateTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				updateUI();
			}
		}, 0, 1000);

	}

	private void updateUI() {
		Platform.runLater(() -> {
			cpuProgressBar.setProgress(SystemInfoService.getCpuUsage());
			cpuLabel.setText((int) (SystemInfoService.getCpuUsage() * 100) + "%");
			ramProgressBar.setProgress(SystemInfoService.getMemoryUsage());
			ramLabel.setText((int) (SystemInfoService.getMemoryUsage() * 100) + "%");
			timeLabel.setText(sdf.format(new Date()));
		});

	}

	@FXML
	void mudarTela(MouseEvent event) {
		systemInfoUpdateTimer.cancel();
		System.out.println("clicou");

		YoutubePlayerController controller = new YoutubePlayerController();
		Parent root = UiToolkit.loadingFXML(Main.class.getResource("fxml/YoutubePlayerStage.fxml"), controller);

		primaryStage.getScene().setRoot(root);

	}

}
