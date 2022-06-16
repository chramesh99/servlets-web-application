package com.flipkart;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html");
		PrintWriter pWriter = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);
		try {
			String tableNameString = LoginServlet.usernameString;
			String deleteQueryString = "delete from "+ tableNameString +"cart where pid = ?";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ecommerce", "root", "root");
			PreparedStatement statement = connection.prepareStatement(deleteQueryString);
			statement.setInt(1, id);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("DisplayCart");
	}
	
}
