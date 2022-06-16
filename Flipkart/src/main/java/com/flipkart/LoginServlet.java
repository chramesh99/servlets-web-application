package com.flipkart;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	static String usernameString = "";
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter pWriter = response.getWriter();
		usernameString = request.getParameter("userName");
		String passwordString = request.getParameter("password");
		
		try {
			String selectQueryString = "select name,mobilenum,password from details"; 
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ecommerce", "root", "root");
			Statement statement = connection.createStatement();
			ResultSet rSet = statement.executeQuery(selectQueryString);
			while(rSet.next()) {
				String dbnameString = rSet.getString(1);
				String dbmobilenumString = rSet.getString(2);
				String dbpasswordString = rSet.getString(3);
				if((dbnameString.equals(usernameString) || (dbmobilenumString.equals(usernameString))) && (dbpasswordString.equals(passwordString))) {
					pWriter.print("<script>");
					pWriter.print("alert('Hello! I am an alert box!')");
					pWriter.print("</script>");
					HttpSession session = request.getSession();
					session.setAttribute("username", usernameString);
					response.sendRedirect("homepage.html");
					return;
				}
			}
			pWriter.println("Login failed");
			request.getRequestDispatcher("login.html").include(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}