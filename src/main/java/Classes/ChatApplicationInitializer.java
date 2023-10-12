package Classes;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ChatApplicationInitializer implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Initialize ChatChannelsManager here
		ChatChannelManager chatChannelsManager = new ChatChannelManager();
		
		// Create some initial chat channels
		ChatChannelManager.createChannel("general");
		ChatChannelManager.createChannel("random");
		
		// Store the manager in the servlet context for access from JSP and other servlets
		sce.getServletContext().setAttribute("chatChannelsManager", chatChannelsManager);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Perform cleanup or shutdown tasks if necessary
	}
}
