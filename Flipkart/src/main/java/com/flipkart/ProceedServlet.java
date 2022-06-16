package com.flipkart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/ProceedServlet")
public class ProceedServlet extends HttpServlet {
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		PrintWriter pWriter = response.getWriter();
		pWriter.print("<h1>order placed......</h1>");
		
	}
	
}
