package media.model;

public enum CommandsMedia {
	
	RUN(0), PLAY(1), STOP(2), MUTE(3),NEXT(4), NULL(-1);

	private int id;

	private CommandsMedia(int id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public static CommandsMedia getCommandById(int id) {
		for (CommandsMedia command : values()) {
			if (command.getId() == id) {
				return command;
			}
		}

		return CommandsMedia.NULL;
	}

}
