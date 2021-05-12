package media.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import media.model.ChangeStageSocketModel;
import media.model.CommandMediaMessageSocketModel;
import media.model.CommandsMedia;
import media.model.FileMediaMessageSocketModel;
import media.model.HeadMessageSocketModel;
import media.model.MediaMessageSocketModel;
import media.model.ProcessedMessageSocketModel;
import media.model.StatusMessageSocketModel;
import media.model.SystemCommandMessageSocketModel;
import media.model.SystemCommands;
import media.model.YoutubeMessageSocketModel;
import media.model.YoutubeMessageSocketModel.Commands;
import media.util.FileToolkit;

public class MessageSocketService {

	public ProcessedMessageSocketModel processMessageReceived(int code, HeadMessageSocketModel head,
			BufferedReader input) {

		switch (code) {
		case 1:
			return messageStatus(head, input);

		case 2:
			return messageYoutube(head, input);

		case 3:
			return messageMediaFile(head, input);

		case 4:
			return messageMediaCommand(head, input);

		case 5:
			return systemCommand(head, input);

		case 6:
			return requestChangeStageCommand(head, input);

		case 7:
			return mediaPlayerCommand(head, input);

		case 8:
			return requestFileListMediaCommand(head, input);
			
		default:
			return null;
		}

	}

	private ProcessedMessageSocketModel messageStatus(HeadMessageSocketModel head, BufferedReader input) {
		ProcessedMessageSocketModel processedMessage = new ProcessedMessageSocketModel();

		processedMessage.setHead(head);

		processedMessage.setNeedResponse(false);

		StatusMessageSocketModel messageStatus = new StatusMessageSocketModel();

		try {
			messageStatus.setStatus(input.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messageStatus.setStatus("Error");
		}

		System.out.println(messageStatus.getStatus());

		processedMessage.setMessage(messageStatus);

		return processedMessage;
	}

	private ProcessedMessageSocketModel messageYoutube(HeadMessageSocketModel head, BufferedReader input) {
		ProcessedMessageSocketModel processedMessage = new ProcessedMessageSocketModel();

		processedMessage.setHead(head);
		processedMessage.setNeedResponse(true);

		YoutubeMessageSocketModel messageYoutube = new YoutubeMessageSocketModel();

		try {
			messageYoutube.setLink(input.readLine());
			messageYoutube.setEmbed(input.read() == 1);
			messageYoutube.setCommand(Commands.getCommandByIndex(input.read()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Embed: " + messageYoutube.isEmbed());

		processedMessage.setMessage(messageYoutube);

		processedMessage.setResponse((output) -> {
			try {
				output.write(messageYoutube.getCode());
				output.write(messageYoutube.getLink());
				output.write("\n");
				output.write(messageYoutube.isEmbed() ? 1 : 0);
				output.write(messageYoutube.getCommand().getIndex());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		return processedMessage;
	}

	private ProcessedMessageSocketModel messageMediaFile(HeadMessageSocketModel head, BufferedReader input) {
		ProcessedMessageSocketModel processedMessage = new ProcessedMessageSocketModel();

		processedMessage.setHead(head);

		processedMessage.setNeedResponse(false);

		FileMediaMessageSocketModel messageMediaFile = new FileMediaMessageSocketModel();

		try {
			messageMediaFile.setPath(input.readLine());
			messageMediaFile.setFileName(input.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		processedMessage.setMessage(messageMediaFile);

		return processedMessage;
	}

	private ProcessedMessageSocketModel messageMediaCommand(HeadMessageSocketModel head, BufferedReader input) {
		ProcessedMessageSocketModel processedMessage = new ProcessedMessageSocketModel();

		processedMessage.setHead(head);

		processedMessage.setNeedResponse(false);

		MediaMessageSocketModel mediaMessage = new MediaMessageSocketModel();

		try {
			mediaMessage.setPath(input.readLine());
			mediaMessage.setFileName(input.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		processedMessage.setMessage(mediaMessage);

		return processedMessage;
	}

	private ProcessedMessageSocketModel systemCommand(HeadMessageSocketModel head, BufferedReader input) {
		ProcessedMessageSocketModel processedMessage = new ProcessedMessageSocketModel();

		processedMessage.setHead(head);

		processedMessage.setNeedResponse(false);

		SystemCommandMessageSocketModel systemMessage = new SystemCommandMessageSocketModel();

		try {
			systemMessage.setCommand(SystemCommands.getCommandById(input.read()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			systemMessage.setCommand(SystemCommands.NULL);
		}

		processedMessage.setMessage(systemMessage);

		return processedMessage;
	}

	private ProcessedMessageSocketModel requestChangeStageCommand(HeadMessageSocketModel head, BufferedReader input) {
		ProcessedMessageSocketModel processedMessage = new ProcessedMessageSocketModel();

		processedMessage.setHead(head);

		processedMessage.setNeedResponse(false);

		ChangeStageSocketModel changeStageMessage = new ChangeStageSocketModel();

		try {
			changeStageMessage.setStage(input.read());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			changeStageMessage.setStage(0);
		}

		processedMessage.setMessage(changeStageMessage);

		return processedMessage;
	}

	private ProcessedMessageSocketModel mediaPlayerCommand(HeadMessageSocketModel head, BufferedReader input) {
		ProcessedMessageSocketModel processedMessage = new ProcessedMessageSocketModel();

		processedMessage.setHead(head);

		processedMessage.setNeedResponse(false);

		CommandMediaMessageSocketModel commandMediaMessage = new CommandMediaMessageSocketModel();

		try {
			commandMediaMessage.setCommand(CommandsMedia.getCommandById(input.read()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		processedMessage.setMessage(commandMediaMessage);

		return processedMessage;
	}

	private ProcessedMessageSocketModel requestFileListMediaCommand(HeadMessageSocketModel head, BufferedReader input) {
		ProcessedMessageSocketModel processedMessage = new ProcessedMessageSocketModel();
		processedMessage.setHead(head);

		processedMessage.setNeedResponse(true);

		ArrayList<FileMediaMessageSocketModel> fileMediaArray = new ArrayList<>();

		for (File mediaFile : FileToolkit.listMediaFiles()) {
			if (FileToolkit.checkIsCompatibleMedia(mediaFile)) {
				fileMediaArray.add(FileToolkit.fileToMediaFileSocket(mediaFile));
			}
		}

		// mudar
		processedMessage.setResponse((output) -> {
			fileMediaArray.forEach((fileMedia) -> {
				try {
					output.write(fileMedia.getCode());
					output.write(fileMedia.getPath());
					output.write("\n");
					output.write(fileMedia.getFileName());
					output.write("\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		});

		return processedMessage;

	}

}
