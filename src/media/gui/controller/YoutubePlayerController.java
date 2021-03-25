package media.gui.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;
import javafx.scene.web.WebView;
import media.interfaces.SocketEventListener;
import media.model.MessageSocketModel;
import media.model.SocketEventModel;
import media.model.StatusMessageSocketModel;
import media.model.YoutubeMessageSocketModel;
import media.model.YoutubeMessageSocketModel.Commands;

public class YoutubePlayerController implements SocketEventListener {

	@FXML
	private WebView webView;

	private String url;

	private Robot robot;

	private boolean embed;

	private boolean active;
	
	private ChangeListener<Worker.State> videoEmbedListener;

	@FXML
	private void initialize() {
		active = true;
		robot = new Robot();
		loadPage(url);
		createVideoListener();
	}
	
	private void createVideoListener() {
		videoEmbedListener = (obs, oldState, newState) -> {
			if (newState == Worker.State.SUCCEEDED) {
				System.out.println("Carregou");
				if (embed) {
					robot.mouseMove(200, 200);
					robot.mouseClick(MouseButton.PRIMARY);

				}
			}
		};
		webView.getEngine().getLoadWorker().stateProperty().addListener(videoEmbedListener);
	}

	private void loadPage(String url) {
		Platform.runLater(() -> {
			webView.getEngine().load(url);
		});

	}

	@Override
	public void messageReceived(MessageSocketModel message) {
		System.out.println(message.getCode());
		// TODO Auto-generated method stub
		if (message instanceof YoutubeMessageSocketModel) {
			YoutubeMessageSocketModel msg = (YoutubeMessageSocketModel) message;
			setLink(msg.getLink(), msg.isEmbed());
			if (!(msg.getCommand() == Commands.NULL)) {
				processCommand(msg.getCommand());
			}
		}
		if (message instanceof StatusMessageSocketModel) {
			Platform.runLater(() -> {
				if (active) {
					webView.getEngine().load("");
					webView.getEngine().getLoadWorker().stateProperty().removeListener(videoEmbedListener);
					active = false;
				}
			});
		}
	}

	@Override
	public void connectionStatusChanged(SocketEventModel event) {
		// TODO Auto-generated method stub

	}

	private void setLink(String link, boolean embed) {
		this.embed = embed;
		String newUrl;
		if (embed) {
			newUrl = link.replace("watch?v=", "embed/");
		} else {
			newUrl = link;
		}
		if (active) {
			if (!this.url.equals(newUrl)) {
				this.url = newUrl;
				loadPage(url);
			}
		} else {
			this.url = newUrl;
		}

	}

	private void processCommand(Commands command) {
		switch (command.getIndex()) {
		case 0:
			pressButton(KeyCode.K);
			break;
		case 1:
			pressButton(KeyCode.M);
			break;
		case 2:
			pressButton(KeyCode.T);
			break;
		case 3:
			pressButton(KeyCode.TRACK_NEXT);
			break;
		case 4:
			pressButton(KeyCode.UP);
			break;
		case 5:
			pressButton(KeyCode.DOWN);
			break;

		}
	}

	private void pressButton(KeyCode key) {
		Platform.runLater(() -> {
			robot.keyPress(key);
			robot.keyRelease(key);
		});

	}

}
