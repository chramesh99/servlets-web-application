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
@WebServlet("/DisplayOrders")
public class DisplayOrders extends HttpServlet {
	
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException {
		
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		if(session != null) {
			try {
				String tableNameString = LoginServlet.usernameString;
				String selectQueryString = "select * from "+ tableNameString +"order"; 
				System.out.println(tableNameString+ ": table name");
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ecommerce", "root", "root");
				Statement statement = connection.createStatement();
				ResultSet rSet = statement.executeQuery(selectQueryString);
				pw.print("<html><head>");
				pw.print("<link rel='stylesheet' href='cart.css'></head><body>");
				pw.print("<table>");
				pw.print("<tr>");
				pw.print("<th></th>");
				pw.print("<th>Product Name</th>");
				pw.print("<th>price</th>");
				pw.print("<th>quantity</th>");
				pw.print("<th>Total</th>");
				pw.print("<th>Cancel(or)Order</th>");
				pw.print("</tr>");
				while(rSet.next()) {
					String dbimageString = rSet.getString(2);
					String dbpnameString = rSet.getString(3);
					int dbpprice = rSet.getInt(4);
					int dbquantity = rSet.getInt(5);
					pw.print("<tr>");
					pw.print("<td><img src='"+ dbimageString +"' alt=''></td>");
					pw.print("<td>"+ dbpnameString +"</td>");
					pw.print("<td>"+ dbpprice +"</td>");
					pw.print("<td>"+ dbquantity +"</td>");
					pw.print("<td>"+ dbquantity*dbpprice +"</td>");
					pw.print("<th><a href='DeleteOrders?id="+rSet.getInt(1)+"'><button class='cancel'>Cancel</button></a></th>");
					pw.print("</tr>");
				}
				pw.print("</table>");
				pw.print("</body>");
				pw.print("</html>");
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			}
			else {
				pw.print("Login requried");
			}
		}	
	}
