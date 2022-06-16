package com.flipkart;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
@MultipartConfig
@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {
	
	public void service(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		
		String pNameString = request.getParameter("pName");
		String pDescriptionString = request.getParameter("pDescription");
		String pPriceString = request.getParameter("pPrice");
//		Part filePart = request.getPart("pImage");
//		String imageFileNameString = filePart.getSubmittedFileName();
		String imageurlString = request.getParameter("pimageurl");
		String genderString = request.getParameter("gender");
		String servletString = request.getParameter("pfullpage");
		System.out.println(pNameString);
		System.out.println(pDescriptionString);
		System.out.println(pPriceString);
		System.out.println(imageurlString);
//		System.out.println(imageFileNameString);
		
//		String uploadFileString = "C:/Users/d1/eclipse-workspace/Flipkart/src/main/webapp/" + imageFileNameString;
//		FileOutputStream fileOutputStream = new FileOutputStream(uploadFileString);
//		InputStream iStream = filePart.getInputStream();
//		
//		byte[] data = new byte[iStream.available()];
//		iStream.read(data);
//		fileOutputStream.write(data);
//		fileOutputStream.close();
		
		try {
			String insertQueryString = "insert into addproducts (pname,pdescription,pprice,pimage,gender) values (?, ?, ?, ?, ?)";
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ecommerce", "root", "root");
			PreparedStatement statement = connection.prepareStatement(insertQueryString);
			statement.setString(1, pNameString);
			statement.setString(2, pDescriptionString);
			statement.setString(3, pPriceString);
			statement.setString(4, imageurlString);
			statement.setString(5, genderString);
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
