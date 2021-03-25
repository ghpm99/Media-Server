package media.model;

public class MediaMessageSocketModel extends MessageSocketModel{

	
	private String path;
	
	private String fileName;
	
	private CommandsMedia command;
	
	public MediaMessageSocketModel() {
		// TODO Auto-generated constructor stub
		setCode(4);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public CommandsMedia getCommand() {
		return command;
	}

	public void setCommand(CommandsMedia command) {
		this.command = command;
	}
	
	
	
}
