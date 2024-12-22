package com.servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/AddCategoryServlet")
public class AddCategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
   
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext context = getServletContext();
		
		String dbUrl = context.getInitParameter("dbUrl");
		String dbDriver = context.getInitParameter("dbDriver");
		String dbUsername = context.getInitParameter("dbUsername");
		String dbPassword = context.getInitParameter("dbPassword");
		
		try {
			Class.forName(dbDriver);
			
			try(Connection connection = DriverManager.getConnection(dbUrl,dbUsername, dbPassword);
					PreparedStatement ps = connection.prepareStatement("insert into category (categoryId, categoryName, categoryDesc) values (?,?,?)");
					PrintWriter out = response.getWriter())
			{
			
				String categoryId = request.getParameter("categoryId");
				String categoryName = request.getParameter("categoryName");
				String categoryDesc = request.getParameter("categoryDesc");
				
				ps.setString(1, categoryId);
				ps.setString(2, categoryName);
				ps.setString(3, categoryDesc);
				
				int res = ps.executeUpdate();
				
				if(res > 0) {
					out.println("Data Added");
				}else {
					out.println("Error");
				}
				
				
			}catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			
			
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			
		}		
	}

}
