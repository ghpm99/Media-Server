package media.global;

import java.util.ArrayList;

import javafx.stage.Stage;
import media.gui.controller.IdleStageController;
import media.gui.controller.MediaStageController;
import media.gui.controller.YoutubePlayerController;
import media.interfaces.LoadingInstanceListener;
import media.service.MessageSocketService;
import media.socket.ServerSocketService;

public class Instances {

	private static final ArrayList<LoadingInstanceListener> listeners = new ArrayList<>();

	public static MessageSocketService messageService = new MessageSocketService();

	private static ServerSocketService socket;

	private static Stage primaryStage;

	private static IdleStageController idleStageController;

	private static YoutubePlayerController youtubePlayerController;
	
	private static MediaStageController mediaStageController;

	public static void init(Stage primaryStage) {

		Instances.primaryStage = primaryStage;
		initSocket();
		initControllers();
		notifyListenersCompletes();

	}

	private static void initSocket() {
		socket = new ServerSocketService();
		socket.init();
		notifyListeners("Socket iniciado");
	}

	private static void initControllers() {
		idleStageController = new IdleStageController(primaryStage);
		socket.addListener(idleStageController);

		youtubePlayerController = new YoutubePlayerController();
		socket.addListener(youtubePlayerController);
		
		mediaStageController = new MediaStageController(primaryStage);
		socket.addListener(mediaStageController);

		notifyListeners("Controllers criados");
	}

	public static void addListener(LoadingInstanceListener listener) {
		listeners.add(listener);
	}

	public static void removeListener(LoadingInstanceListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	private static void notifyListeners(String msg) {
		listeners.forEach((s) -> {
			s.notify(msg);
		});
	}

	private static void notifyListenersCompletes() {
		listeners.forEach((s) -> {
			s.completed();
		});
	}

	public synchronized static ServerSocketService getSocket() {
		if (socket == null) {
			throw new NullPointerException("Uninitialized instance");
		} else {
			return socket;
		}
	}

	public static void exit() {
		if (socket != null) {
			socket.close();
		}
	}

	public static IdleStageController getIdleStageController() {
		return idleStageController;
	}	

	public static YoutubePlayerController getYoutubePlayerController() {		
		return youtubePlayerController;
	}
	
	public static MediaStageController getMediaStageController() {
		return mediaStageController;
	}

}
