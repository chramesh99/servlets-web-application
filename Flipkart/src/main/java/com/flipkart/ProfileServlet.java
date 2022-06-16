package com.flipkart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pWriter = response.getWriter();
		HttpSession session = request.getSession(false);
		if(session != null) {
			String nameString = LoginServlet.usernameString;
			pWriter.print("profile page of " + nameString);
		}else {
			pWriter.print("login required");
		}
		
	}

}
