package Classes;
import java.util.HashMap;
import java.util.Map;

public class ChatChannelManager {
	private static Map<String, ChatChannel> channels = new HashMap<>();
	
	public static void createChannel(String channelName) {
		channels.put(channelName, new ChatChannel(channelName));
	}
	
	public static ChatChannel getChannel(String channelName) {
		return channels.get(channelName);
	}
	
	public static Map<String, ChatChannel> getAllChannels() {
		return channels;
	}
}
