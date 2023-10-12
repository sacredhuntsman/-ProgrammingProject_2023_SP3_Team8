package Classes;

import java.util.ArrayList;
import java.util.List;

public class ChatChannel {
	private String name;
	private List<String> messages;
	
	public ChatChannel(String name) {
		this.name = name;
		this.messages = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getMessages() {
		return messages;
	}
	
	public void addMessage(String message) {
		messages.add(message);
	}
}
