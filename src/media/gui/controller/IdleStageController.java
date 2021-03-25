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
import javafx.stage.Stage;
import media.global.Instances;
import media.gui.Main;
import media.interfaces.SocketEventListener;
import media.model.MediaMessageSocketModel;
import media.model.MessageSocketModel;
import media.model.SocketEventModel;
import media.model.StatusMessageSocketModel;
import media.model.YoutubeMessageSocketModel;
import media.util.SystemInfoService;
import media.util.UiToolkit;

public class IdleStageController implements SocketEventListener {
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

	@FXML
	private Label statusConnectionLabel;

	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

	private Timer systemInfoUpdateTimer;

	private Stage primaryStage;

	private boolean active;

	public IdleStageController(Stage primaryStage) {
		// TODO Auto-generated constructor stub
		this.primaryStage = primaryStage;
	}

	@FXML
	private void initialize() {
		updateUI();
		initializeThreadUpdateUi();
		active = true;
	}

	private void initializeThreadUpdateUi() {
		systemInfoUpdateTimer = new Timer();
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

	private void changeView(String fxmlPath, Object controller) {
		Platform.runLater(() -> {

			systemInfoUpdateTimer.cancel();
			Parent root = UiToolkit.loadingFXML(Main.class.getResource(fxmlPath), controller);

			primaryStage.getScene().setRoot(root);

		});

	}

	@Override
	public void messageReceived(MessageSocketModel message) {
		// TODO Auto-generated method stub
		if (message instanceof YoutubeMessageSocketModel) {
			if (active) {
				changeView("fxml/YoutubePlayerStage.fxml", Instances.getYoutubePlayerController());
				active = false;
			}
		}
		if (message instanceof StatusMessageSocketModel) {
			if (!active) {
				changeView("fxml/IdleStage.fxml", this);
			}
		}
		
		if(message instanceof MediaMessageSocketModel) {
			if(active) {
				changeView("fxml/MediaStage.fxml", Instances.getMediaStageController());
				active = false;
			}
		}
	}

	@Override
	public void connectionStatusChanged(SocketEventModel event) {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			statusConnectionLabel.setText(event.getHostName() + " : " + event.getStatus().getText());
		});

	}

}
