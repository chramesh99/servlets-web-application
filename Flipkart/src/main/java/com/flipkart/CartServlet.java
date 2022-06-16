package com.flipkart;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		//		System.out.println("This is cartServlet page");
		int id = Integer.parseInt(request.getParameter("id"));
		//		System.out.println(id);
		HttpSession session = request.getSession(false);
		if(session != null) {
			try {
				String selectQueryString = "select * from addproducts where pid = ?";
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ecommerce", "root", "root");
				PreparedStatement statement = connection.prepareStatement(selectQueryString);
				statement.setInt(1, id);
				ResultSet rSet = statement.executeQuery();
				String dbimageString = null;
				String dbnameString = null;
				int dbprice = 0;
				int dbpid = 0;
				while(rSet.next()) {
					dbpid = rSet.getInt(1);
					dbimageString = rSet.getString(5);
					dbnameString = rSet.getString(2);
					dbprice = rSet.getInt(4);
				}
				statement.close();
				String tableNameString = LoginServlet.usernameString;
				//inserting into cart table
				String selectQueryCartString = "select * from "+ tableNameString +"cart where pid="+id;
				Statement selectStatement = connection.createStatement();
				ResultSet resultSet = selectStatement.executeQuery(selectQueryCartString);
				int quantity = 1;
				System.out.println("before while......................");
				boolean val = false;
				while(!val) {
					val = resultSet.next();
					if(!val) {
						String insertQueryString = "insert into "+ tableNameString +"cart (pid,image,pname,pprice,pquantity) values (?, ?, ?, ?, ?)";
						PreparedStatement insertStatement = connection.prepareStatement(insertQueryString);
						insertStatement.setInt(1, dbpid);
						insertStatement.setString(2, dbimageString);
						insertStatement.setString(3, dbnameString);
						insertStatement.setInt(4, dbprice);
						insertStatement.setInt(5, quantity);
						insertStatement.executeUpdate();
						insertStatement.close();
						System.out.println("first insert completed: "+id+"............");
						break;
					}
					else {
						int dbquantity = resultSet.getInt(5);
						dbquantity++;
						System.out.println("the quantity is:"+dbquantity);
						String updateQueryString = "UPDATE "+ tableNameString +"cart SET pquantity = "+dbquantity +" where pid="+id;
						Statement updateStatement = connection.createStatement();
						updateStatement.executeUpdate(updateQueryString);
						updateStatement.close();
						System.out.println("second insert completed: "+id+"............");
						break;
					}
				}
				response.sendRedirect("DisplayCart");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			pw.print("Login requried");
		}
	}
}
