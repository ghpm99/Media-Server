package media.gui.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import media.interfaces.SocketEventListener;
import media.model.MediaMessageSocketModel;
import media.model.MessageSocketModel;
import media.model.SocketEventModel;
import media.model.StatusMessageSocketModel;

public class MediaStageController implements SocketEventListener {

	@FXML
	private MediaView mediaView;

	private MediaPlayer mediaPlay;

	private boolean active;

	private Stage primaryStage;

	public MediaStageController(Stage primaryStage) {
		// TODO Auto-generated constructor stub
		this.primaryStage = primaryStage;
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		active = true;

	}

	@Override
	public void messageReceived(MessageSocketModel message) {
		Platform.runLater(() -> {
			if (message instanceof MediaMessageSocketModel) {

				MediaMessageSocketModel messageMedia = (MediaMessageSocketModel) message;
				switch (messageMedia.getCommand()) {
				case RUN:
					runMedia(messageMedia.getPath());
					break;
				case PLAY:
					playMedia();
					break;
				case STOP:
					stopMedia();
					break;
				case MUTE:
					muteMedia();
					break;
				case NULL:

					break;
				default:
					break;

				}

			}
			if (message instanceof StatusMessageSocketModel) {
				if (active && mediaView.getMediaPlayer() != null) {
					mediaView.getMediaPlayer().stop();
					mediaView.setMediaPlayer(null);
				}

			}
		});

	}

	private void runMedia(String file) {
		if (mediaView.getMediaPlayer() != null)
			mediaView.getMediaPlayer().stop();

		Media media = new Media(file);
		mediaPlay = new MediaPlayer(media);
		mediaPlay.setAutoPlay(true);
		mediaPlay.setOnError(() -> {
			System.out.println("ERROR: " + mediaPlay.getError().getMessage());
		});
		
		mediaPlay.setOnReady(() -> {
			System.out.println("Height: " + primaryStage.getHeight() + " Media Height: " + media.getHeight() + " width: "
					+ primaryStage.getWidth() + " Media Width: " + media.getWidth());
		});

		mediaView.setFitHeight(primaryStage.getHeight());		
		mediaView.setFitWidth(primaryStage.getWidth());

		mediaView.setMediaPlayer(mediaPlay);
		mediaView.autosize();

	}

	private void playMedia() {
		if (mediaView.getMediaPlayer() != null)
			mediaView.getMediaPlayer().play();
	}

	private void stopMedia() {
		if (mediaView.getMediaPlayer() != null)
			mediaView.getMediaPlayer().pause();
	}

	private void muteMedia() {
		if (mediaView.getMediaPlayer() != null)
			mediaView.getMediaPlayer().setMute(!mediaView.getMediaPlayer().isMute());

	}

	@Override
	public void connectionStatusChanged(SocketEventModel event) {
		// TODO Auto-generated method stub

	}

}
