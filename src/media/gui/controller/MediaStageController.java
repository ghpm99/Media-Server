package media.gui.controller;

import static media.global.Instances.getMediaService;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import media.global.Instances;
import media.interfaces.MediaEventListener;
import media.interfaces.SocketEventListener;
import media.model.ChangeStageSocketModel;
import media.model.CommandMediaMessageSocketModel;
import media.model.MediaMessageSocketModel;
import media.model.MessageSocketModel;
import media.model.SocketEventModel;

public class MediaStageController implements SocketEventListener, MediaEventListener {

	@FXML
	private MediaView mediaView;

	@FXML
	private BarChart<String, Number> barChart;

	private boolean active;

	private Stage primaryStage;

	@FXML
	private ListView<MediaMessageSocketModel> playlistView;

	@FXML
	private Label labelTimeMedia;

	@FXML
	private AnchorPane controls;

	@FXML
	private ProgressBar barTimeMedia;

	@FXML
	private Label mediaName;

	public MediaStageController(Stage primaryStage) {
		// TODO Auto-generated constructor stub
		this.primaryStage = primaryStage;
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		System.out.println("iniciou media");
		active = true;

		barChart.getData().add(getMediaService().getPositiveSeries());
		barChart.getData().add(getMediaService().getNegativeSeries());

		playlistView.setItems(Instances.getMediaService().getPlayList());

		mediaView.setFitHeight(primaryStage.getHeight());
		mediaView.setFitWidth(primaryStage.getWidth());

	}

	@Override
	public void messageReceived(MessageSocketModel message) {

		Platform.runLater(() -> {
			if (message instanceof MediaMessageSocketModel) {
				MediaMessageSocketModel messageMedia = (MediaMessageSocketModel) message;
				Instances.getMediaService().runMedia(messageMedia);
			}

			if (message instanceof CommandMediaMessageSocketModel) {

				if (!active)
					return;

				switch (((CommandMediaMessageSocketModel) message).getCommand()) {

				case RUN:

					break;
				case PLAY:
					getMediaService().playMedia();
					break;
				case STOP:
					getMediaService().stopMedia();
					break;
				case MUTE:
					getMediaService().muteMedia();
					break;
				case NEXT:
					getMediaService().nextMedia();
					break;
				case NULL:

					break;
				default:
					break;

				}

			}
			if (message instanceof ChangeStageSocketModel) {
				if (active && ((ChangeStageSocketModel) message).getStage() != 2) {
					getMediaService().stopMedia();
					active = false;
				}

			}
		});

	}

	@Override
	public void connectionStatusChanged(SocketEventModel event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
		// TODO Auto-generated method stub
		barTimeMedia.setProgress(newValue.toMillis() / getMediaService().getTotalDuration().toMillis());
		labelTimeMedia.setText(processTimeValue(newValue.toSeconds()) + " / "
				+ processTimeValue(getMediaService().getTotalDuration().toSeconds()));

	}

	private String processTimeValue(double duration) {

		String seconds = String.format("%02d", (int) (duration % 60));

		String minutes = String.format("%02d", (int) ((duration / 60) % 60));

		String hours = String.format("%02d", (int) ((duration / 3600) % 60));

		return hours + ":" + minutes + ":" + seconds;
	}

	@Override
	public void onReady() {
		// TODO Auto-generated method stub
		mediaName.setText(getMediaService().getNameMedia());

		System.out.println(getMediaService().isAudio());

		mediaView.setVisible(!getMediaService().isAudio());
		barChart.setVisible(getMediaService().isAudio());
		controls.setVisible(getMediaService().isAudio());

		mediaView.setMediaPlayer(getMediaService().getMedia());

	}

	@Override
	public void onError() {
		// TODO Auto-generated method stub
		mediaName.setText("ERROR");
	}

}
