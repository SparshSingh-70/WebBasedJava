package com.servlets;


import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/AddCard")
public class AddCard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Connection connection;
	PreparedStatement ps;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		out.println("<h1>Add Card</h1>");
		
		ServletContext context = getServletContext();
		
		String dbUrl = context.getInitParameter("dbUrl");
		String dbDriver = context.getInitParameter("dbDriver");
		String dbUsername = context.getInitParameter("dbUsername");
		String dbPassword = context.getInitParameter("dbPassword");
		
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			ps = connection.prepareStatement("insert into cards values(?,?,?)");
			
			String temp = request.getParameter("cardNumber");
			int cardNumber = Integer.parseInt(temp);
			
			temp = request.getParameter("balance");
			double balance = Double.parseDouble(temp);
			
			temp = request.getParameter("expiryDate");
			String expiryDate = temp;
			
			ps.setInt(1, cardNumber);
			ps.setDouble(2, balance);
			ps.setString(3, expiryDate);
			
			int res = ps.executeUpdate();
			
			if(res > 0) {
				out.println("Card Added Successfully!");
				out.println("<a href = 'Admin/AdminPage.html'>Back</a>");
				
			} 
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.sendRedirect("login.html");
			return;
		}
		
		
	}

}
