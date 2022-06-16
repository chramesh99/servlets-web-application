package com.flipkart;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html");
		PrintWriter pWriter = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));
		
		HttpSession session = request.getSession(false);
		if(session != null) {
			try {
				String tableNameString = LoginServlet.usernameString;
				String selectQueryString = "select * from "+ tableNameString +"cart where pid = ?" ;
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ecommerce", "root", "root");
				PreparedStatement statement = connection.prepareStatement(selectQueryString);
				statement.setInt(1, id);
				ResultSet rSet = statement.executeQuery();
				String dbimageString = "";
				String dbpnameString = "";
				int dbpprice = 0;
				int dbpid = 0;
				int dbquantity = 0;
				while(rSet.next()) {
					dbpid = rSet.getInt(1);
					dbimageString = rSet.getString(2);
					dbpnameString = rSet.getString(3);
					dbpprice = rSet.getInt(4);
					dbquantity = rSet.getInt(5);
				}
				String insertQueryString = "insert into "+ tableNameString +"order (pid,image,pname,pprice,pquantity) values (?, ?, ?, ?, ?)";
				PreparedStatement insertStatement = connection.prepareStatement(insertQueryString);
				insertStatement.setInt(1, dbpid);
				insertStatement.setString(2, dbimageString);
				insertStatement.setString(3, dbpnameString);
				insertStatement.setInt(4, dbpprice);
				insertStatement.setInt(5, dbquantity);
				insertStatement.executeUpdate();
				insertStatement.close();
				String deleteQueryString = "delete from "+ tableNameString +"cart where pid = ?";
				PreparedStatement deleteStatement = connection.prepareStatement(deleteQueryString);
				deleteStatement.setInt(1, id);
				deleteStatement.executeUpdate();
				response.sendRedirect("PaymentServlet");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			pWriter.print("Login required");
		}
		
	}
	
}
