package com.flipkart;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.valves.rewrite.Substitution.StaticElement;

@WebServlet("/signupservlet")
public class SignupServlet extends HttpServlet{
	static String usernameString = "";
	public void service(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {

		response.setContentType("text/html");
		PrintWriter pWriter = response.getWriter();
		usernameString = request.getParameter("userName");
		long mobileNum = Long.parseLong(request.getParameter("mobile_Num"));
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("conPassword");
		if(password.equals(confirmPassword)) {
			try {
				int count = 0;
				String queryString = "select * from details";
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ecommerce", "root", "root");
				Statement statement = connection.createStatement();
				ResultSet rSet = statement.executeQuery(queryString);
				while(rSet.next()) {
					//					String dbnum = rSet.getString(3);
					String dbnameString = rSet.getString(2);
					if(dbnameString.equals(usernameString)) {
						count ++;
					}
				}
				if (count < 1) {
					String insertQueryString = "insert into details (name, mobilenum, password, confirmpassword) values (?, ?, ?, ?)";
					PreparedStatement preparedStatement = connection.prepareStatement(insertQueryString);
					preparedStatement.setString(1, usernameString);
					preparedStatement.setLong(2, mobileNum);
					preparedStatement.setString(3, password);
					preparedStatement.setString(4, confirmPassword);
					int countStatement = preparedStatement.executeUpdate();
					if(countStatement > 0) {
						String createCartString = "create table "+usernameString+"cart (pid int not null,image varchar(500) not null,pname varchar(100) not null,pprice int not null,pquantity int not null)";
						String createOrderString = "create table "+usernameString+"order (pid int not null,image varchar(500) not null,pname varchar(100) not null,pprice int not null,pquantity int not null)";
						statement.executeUpdate(createCartString);
						statement.executeUpdate(createOrderString);
						response.sendRedirect("login.html");
					}

				}
				else {
					pWriter.print("<script>");
					pWriter.print("alert('UserName already exists you can login')");
					pWriter.print("</script>");

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			pWriter.print("password and confirm password are mismatch");
			request.getRequestDispatcher("sign_up_page.html").include(request, response);
		}
	}
}