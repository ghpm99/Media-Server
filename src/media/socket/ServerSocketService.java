package media.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import media.interfaces.SocketEventListener;
import media.model.ClientSocketModel;
import media.model.MessageSocketModel;
import media.model.SocketEventModel;
import media.model.SocketEventModel.STATUS;

public class ServerSocketService implements SocketEventListener {

	private ServerSocket socket;

	private int port = 33325;

	private Thread threadServerSocket;

	private boolean alive;

	private ArrayList<ClientSocketModel> clientArray = new ArrayList<>();

	private ArrayList<SocketEventListener> socketEventListeners = new ArrayList<>();

	public void init() {
		try {
			alive = true;

			socket = new ServerSocket(port);

			threadServerSocket = new Thread(() -> {
				System.out.println("Iniciando ");
				
				Socket clientSocket = null;
				ClientSocketModel client = null;
				
				while (alive) {
					try {
						clientSocket = socket.accept();
						System.out.println("Cliente " + clientSocket.getInetAddress().getHostAddress() + " conectou");
						connectionStatusChanged(
								new SocketEventModel(STATUS.CONNECTED, clientSocket.getInetAddress().getHostAddress(),
										clientSocket.getInetAddress().getHostName()));

						client = new ClientSocketModel(clientSocket,this);
						System.out.println("Enviando Ok msg");
						client.sendOkMessage();

						System.out.println("Conexao realizada com sucesso");
						clientArray.add(client);

					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});

			threadServerSocket.start();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public void close() {
		try {
			alive = false;
			threadServerSocket.interrupt();
			closeAllSocket();
			socket.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void closeAllSocket() {
		clientArray.forEach((s) -> {
			s.close();
		});
	}

	public void addListener(SocketEventListener listener) {
		socketEventListeners.add(listener);
	}

	public void removeListener(SocketEventListener listener) {
		if (socketEventListeners.contains(listener)) {
			socketEventListeners.remove(listener);
		}
	}

	@Override
	public void messageReceived(MessageSocketModel message) {
		socketEventListeners.forEach((s) -> {
			s.messageReceived(message);
		});
	}

	@Override
	public void connectionStatusChanged(SocketEventModel socketEvent) {
		socketEventListeners.forEach((s) -> {
			s.connectionStatusChanged(socketEvent);
		});
	}

}
