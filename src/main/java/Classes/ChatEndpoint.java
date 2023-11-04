package Classes;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/chat")
public class ChatEndpoint {
	private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
	
	@OnOpen
	public void onOpen(Session session) {
		sessions.add(session);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		for (Session s : sessions) {
			s.getBasicRemote().sendText(message);
		}
	}
	
	@OnClose
	public void onClose(Session session) {
		sessions.remove(session);
	}
}