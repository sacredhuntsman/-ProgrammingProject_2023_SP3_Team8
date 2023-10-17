package Classes;

public class Channel {
	
	private int channelID;
	private String channelName;
	private int GroupID;
	public Channel() {
	}
	public Channel(int GroupID, int channelID, String channelName) {
		this.channelID = channelID;
		this.channelName = channelName;
		this.GroupID = GroupID;
	}
	
	public int getChannelId() {
		return channelID;
	}
	
	public void setId(int channelID) {
		this.channelID = channelID;
	}
	
	public String getChannelName() {
		return channelName;
	}
	
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public int getGroupId() {
		return GroupID;
	}
	
	public void setGroupId(int GroupID) {
		this.GroupID = GroupID;
	}
}
