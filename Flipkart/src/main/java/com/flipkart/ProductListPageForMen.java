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
@WebServlet("/ProductListPageForMen")
public class ProductListPageForMen extends HttpServlet {
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pWriter = response.getWriter();
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		HttpSession session = request.getSession(false);
		if(session != null) {
		try {
			String selectQueryString = "select * from addproducts";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ecommerce", "root", "root");
			Statement statement = connection.createStatement();
			ResultSet rSet = statement.executeQuery(selectQueryString);
			pWriter.print("<html><head>");
			pWriter.print("<link rel=\"stylesheet\" href=\"testing.css\"></head>");
			pWriter.print("<body>");
			
			while(rSet.next()) {
				String dbpNameString = rSet.getString(2);
				String dbpDescriptionString = rSet.getString(3);
				int dbppriceString = rSet.getInt(4);
				String dbpimageString = rSet.getString(5);
				pWriter.print("");
				pWriter.print("<div class=\"content\" style=\"float: left;\">");
				pWriter.print("<a href='ProductDescriptionPage?id='" + rSet.getInt(1) + "'>");
				pWriter.print("<a href="+ "ProductDescriptionPage" +"?id="+ rSet.getInt(1) +">");
				pWriter.print("<img src = '"+dbpimageString+"'>");
				pWriter.print("<p>"+dbpNameString+"</p>");
				pWriter.print("<p>"+dbpDescriptionString+"</p>");
				pWriter.print("<p>Price: "+dbppriceString+"</p>");
				pWriter.print("<div class='btn'>");
				pWriter.print("<a href= 'CartServlet?id="+rSet.getInt(1)+"'><button class='cart'>Add Cart</button></a>&nbsp;&nbsp;");
				pWriter.print("<a href= 'CartServlet?id="+rSet.getInt(1)+"'><button class='buy' style='margin-top:-18px;'>Buy Now</button></a>");
				pWriter.print("</div>");
				pWriter.print("</a>");
				pWriter.print("</div>");
			}
			pWriter.print("</body>");
			pWriter.print("</html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else {
			pWriter.print("Login requried");
			response.sendRedirect("login.html");
		}
	}
}
