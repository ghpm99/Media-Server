package media.model;

public class SocketEventModel {

	public static enum STATUS {
		
		CONNECTED("Connected"), DISCONNECT("Disconnect");
		
		private String status;
		
		private STATUS(String status) {
			// TODO Auto-generated constructor stub
			this.status = status;
		}
		
		public String getText() {
			return status;
		}
		
	};
	
	private STATUS status;
	
	private String hostAddress;
	
	private String hostName;
	
	public SocketEventModel(STATUS status, String hostAddress, String hostName) {
		super();
		this.status = status;
		this.hostAddress = hostAddress;
		this.hostName = hostName;
	}

	public STATUS getStatus() {
		return status;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	
	
	
}
