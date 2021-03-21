package media.global;

import java.util.ArrayList;

import media.interfaces.LoadingInstanceListener;
import media.socket.ServerSocketService;

public class Instances {

	private static final ArrayList<LoadingInstanceListener> listeners = new ArrayList<>();

	private static ServerSocketService socket;

	public static void init() {

		initSocket();
		
		initIdlePanel();
		
		notifyListenersCompletes();

	}

	private static void initSocket() {
		socket = new ServerSocketService();
		socket.init();
		notifyListeners("Socket iniciado");
	}

	private static void initIdlePanel() {
		
		notifyListeners("Painel criado");
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

	public static ServerSocketService getSocket() {
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

}
