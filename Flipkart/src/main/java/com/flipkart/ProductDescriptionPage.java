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
@WebServlet("/ProductDescriptionPage")
public class ProductDescriptionPage extends HttpServlet {
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println(id);
		
		try {
			String selectQueryString = "select * from addproducts where pid = ?";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ecommerce", "root", "root");
			PreparedStatement statement = connection.prepareStatement(selectQueryString);
			statement.setInt(1, id);
			ResultSet rSet = statement.executeQuery();
			pw.print("<html><head>");
			pw.print("<link rel=\"stylesheet\" href=\"productdescription.css\"></head><body>");
			while(rSet.next()) {
				String dbpname = rSet.getString(2);
				String dbpimageString = rSet.getString(5);
				pw.print("<div class='container'>");
				pw.print("<div class='content'>");
				pw.print("<img src='"+ dbpimageString +"' alt='"+dbpname+"'></div>");
				pw.print("<div class='info'>");
				pw.print("<p>"+ rSet.getString(2) +"</p><p><b>Price:"+ rSet.getInt(4) +"</b></p>");
				pw.print("<p><b>Coupons for you</b>");
				pw.print("<p>Special PriceGet extra 30% off upto 50/- on 1 item(s) (price inclusive of discount)TandC</p>");
				pw.print("<input type='num' placeholder='Enter delivery pincode'> <a href='#' style='text-decoration: none; color: black;'>Check</a>");
				pw.print("</p></div></div>");
				pw.print("<div class='description'>");
				pw.print("<p>"+ rSet.getString(3) +"</p>");
				pw.print("<div class='btn'>");
				pw.print("<a href= 'CartServlet?id="+rSet.getInt(1)+"'><button class='cart'>Add Cart</button></a>&nbsp;&nbsp;");
				pw.print("<a href= 'CartServlet?id="+rSet.getInt(1)+"'><button class='buy'>Buy Now</button></a>");
				pw.print("</div></div>");
			}
			pw.print("</body></html>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
