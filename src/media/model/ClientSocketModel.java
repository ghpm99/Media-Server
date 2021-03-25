package media.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import media.global.Instances;
import media.interfaces.SocketEventListener;
import media.model.SocketEventModel.STATUS;

public class ClientSocketModel {

	private BufferedWriter output;

	private BufferedReader input;

	private Socket socket;

	private Thread clientMessageThread;

	private SocketEventListener eventListener;

	private boolean alive;

	public ClientSocketModel(Socket socket, SocketEventListener listener) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		this.eventListener = listener;

		try {
			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			alive = true;

			clientMessageThread = new Thread(() -> {
				waitMessage();
			});

			clientMessageThread.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void waitMessage() {
		try {
			System.out.println("Ready " + input.ready());
			int code = 0;
			HeadMessageSocketModel head = null;

			while (alive) {
				System.out.println("Aguardando msg");
				code = input.read();
				System.out.println("Codigo: " + code);
				if (code == -1) {
					close();
				}
				head = new HeadMessageSocketModel();

				head.setHostAddress(socket.getInetAddress().getHostAddress());
				head.setHostName(socket.getInetAddress().getHostName());

				ProcessedMessageSocketModel processedMessage = Instances.messageService.processMessageReceived(code,
						head, input);

				processMessage(processedMessage);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendOkMessage() {

		sendMessage((output) -> {
			try {
				output.write(1);
				output.write("OK");
				output.write("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	public void close() {
		try {
			alive = false;
			clientMessageThread.interrupt();
			socket.close();
			connectionStatusChanged(new SocketEventModel(STATUS.DISCONNECT, socket.getInetAddress().getHostAddress(),
					socket.getInetAddress().getHostName()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void processMessage(ProcessedMessageSocketModel message) {
		if (message == null)
			return;
		messageReceived(message.getMessage());
		if (message.isNeedResponse()) {
			sendMessage(message.getResponse());
		}
	}

	private void sendMessage(ResponseSocketModel message) {
		try {
			message.sendResponse(output);
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void messageReceived(MessageSocketModel message) {
		// TODO Auto-generated method stub
		eventListener.messageReceived(message);
	}

	public void connectionStatusChanged(SocketEventModel event) {
		// TODO Auto-generated method stub
		eventListener.connectionStatusChanged(event);
	}

}
