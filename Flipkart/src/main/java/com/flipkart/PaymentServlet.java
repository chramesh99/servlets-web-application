package com.flipkart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html");
		PrintWriter pWriter = response.getWriter();
		pWriter.print("<html><head>");
		pWriter.print("<link rel='stylesheet' href='cart.css'></head><body>");
		pWriter.print("<div class='container'>");
		pWriter.print("<div class='content'>");
		pWriter.print("<a href='#'><button class='cart'>Select Address</button></a><br><br>");
		pWriter.print("<h1>Payment</h1>");
		pWriter.print("<input type='radio' name='Payment' value='UPI'><b>UPI</b><br><br>");
		pWriter.print("<input type='radio' name='Payment' value='card'><b>Dedit/Credit Card</b><br><br>");
		pWriter.print("<input type='radio' name='Payment' value='netbanking'><b>Net Banking</b><br><br>");
		pWriter.print("<input type='radio' name='Payment' value='cash'><b>Cash on Delivery</b><br><br>");
		pWriter.print("<a href='ProceedServlet'><button class='buy'>Proceed to pay</button></a>");
		pWriter.print("</div></div>");
		pWriter.print("</body></html>");
		
	}
	
}
