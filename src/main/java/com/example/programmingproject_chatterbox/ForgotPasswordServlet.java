package com.example.programmingproject_chatterbox;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "forgotPassword", value = "/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
	private String message;
	
	public void init() {
		message = "Forgot Password!";
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		
		// Hello
		PrintWriter out = response.getWriter();
		out.println("<html><body>");
		out.println("<h1>" + message + "</h1>");
		out.println("</body></html>");
	}
	
	public void destroy() {
	}
}