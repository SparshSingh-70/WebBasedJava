package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		try {
			
			ServletContext context = getServletContext();
			String dbUrl = context.getInitParameter("dbUrl");
			String dbDriver = context.getInitParameter("dbDriver");
			String dbUsername = context.getInitParameter("dbUsername");
			String dbPassword = context.getInitParameter("dbPassword");
			
			
			
			Class.forName(dbDriver);
			try(Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
					PreparedStatement ps = connection.prepareStatement("select * from users where username=? and password=?");
					PrintWriter out = resp.getWriter()){
				
			
					ps.setString(1, username);
					ps.setString(2, password);
					
					ResultSet resultSet = ps.executeQuery();
					
					if(resultSet.next()) {
						if(username.equals("admin")) {
							resp.sendRedirect("Admin/AdminPage.html");
						}else {						
							resp.sendRedirect("CategoryServlet");
						}
					}else {
						out.println("Failed");
					}
				
					
			}catch (SQLException e) {
				System.err.println("Error");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	

}
