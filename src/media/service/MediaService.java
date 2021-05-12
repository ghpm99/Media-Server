package media.service;

import java.util.Arrays;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import media.global.Instances;
import media.interfaces.MediaEventListener;
import media.model.MediaMessageSocketModel;

public class MediaService implements AudioSpectrumListener, ChangeListener<Duration> {

	private ObservableList<MediaMessageSocketModel> playList = FXCollections.observableArrayList();

	private MediaPlayer mediaPlay;

	private boolean playing = false;

	private boolean audio;

	private Series<String, Number> positiveSeries = new XYChart.Series<>();

	private Series<String, Number> negativeSeries = new XYChart.Series<>();

	private Data[] positiveData;

	private Data[] negativeData;

	private String mediaName = "";

	private MediaEventListener mediaListener;

	public void init() {
		positiveSeries.setName("positive");

		positiveData = new XYChart.Data[128];

		for (int i = 0; i < positiveData.length; i++) {
			positiveData[i] = new XYChart.Data<>(Integer.toString(i + 1), 50);
			positiveSeries.getData().add(positiveData[i]);
		}

		negativeSeries.setName("negative");

		negativeData = new XYChart.Data[128];

		for (int i = 0; i < negativeData.length; i++) {
			negativeData[i] = new XYChart.Data<>(Integer.toString(i + 1), 50);
			negativeSeries.getData().add(negativeData[i]);
		}
	}

	public ObservableList<MediaMessageSocketModel> getPlayList() {
		return playList;
	}

	public synchronized void runMedia(MediaMessageSocketModel file) {

		addPlayList(file);

	}

	public void nextMedia() {

		if (isPlaylistEmpty()) {
			return;
		}

		if (mediaPlay != null) {
			mediaPlay.stop();
		}
		playing = true;
		MediaMessageSocketModel nextMedia = getNextPlayList();

		mediaName = nextMedia.getFileName();

		verifyIsAudio(nextMedia.getPath());

		Media media = new Media(nextMedia.getPath());

		mediaPlay = new MediaPlayer(media);
		mediaPlay.setAutoPlay(true);
		mediaPlay.setOnError(() -> {
			playing = false;
			System.out.println("ERROR: " + mediaPlay.getError().getMessage());
			if (!isPlaylistEmpty()) {
				nextMedia();
			} else {
				Instances.getIdleStageController().requestShowIdleView();
			}
			if (mediaListener != null) {
				mediaListener.onError();
			}
		});

		mediaPlay.setOnReady(() -> {
			if (mediaListener != null) {
				mediaListener.onReady();
			}
		});

		mediaPlay.setOnEndOfMedia(() -> {
			playing = false;
			if (!isPlaylistEmpty()) {
				nextMedia();
			} else {
				Instances.getIdleStageController().requestShowIdleView();
			}
		});

		mediaPlay.setAudioSpectrumListener(this);

		mediaPlay.currentTimeProperty().addListener(this);

	}

	public void playMedia() {
		if (mediaPlay != null)
			mediaPlay.play();
	}

	public void stopMedia() {
		if (mediaPlay != null)
			mediaPlay.pause();
	}

	public void muteMedia() {
		if (mediaPlay != null)
			mediaPlay.setMute(!mediaPlay.isMute());

	}

	private void verifyIsAudio(String media) {
		List<String> audioExtensions = Arrays.asList("aif", "aiff", "mp3", "wav");
		audio = audioExtensions.contains(media.substring(media.lastIndexOf(".") + 1));
		System.out.println("Audio " + audio);
	}

	private void addPlayList(MediaMessageSocketModel media) {
		playList.add(media);
	}

	private MediaMessageSocketModel getNextPlayList() {
		MediaMessageSocketModel media = playList.get(0);
		playList.remove(0);
		return media;
	}

	private boolean isPlaylistEmpty() {
		return playList.isEmpty();
	}

	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		// TODO Auto-generated method stub
		if (!audio)
			return;

		for (int i = 0; i < magnitudes.length; i++) {

			float newValue = magnitudes[i] + 60f;

			positiveData[i].setYValue(newValue); // Top Series

			negativeData[i].setYValue(-newValue);// Bottom series
		}

	}

	public void addMediaListener(MediaEventListener listener) {
		mediaListener = listener;
	}

	public Duration getTotalDuration() {
		return mediaPlay.getTotalDuration();
	}

	public MediaPlayer getMedia() {
		return mediaPlay;
	}

	public boolean isAudio() {
		return audio;
	}

	public Series<String, Number> getPositiveSeries() {
		return positiveSeries;
	}

	public Series<String, Number> getNegativeSeries() {
		return negativeSeries;
	}

	@Override
	public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
		// TODO Auto-generated method stub
		if (mediaListener != null) {
			mediaListener.changed(observable, oldValue, newValue);
		}
	}
	
	public String getNameMedia() {
		return mediaName;
	}
}
