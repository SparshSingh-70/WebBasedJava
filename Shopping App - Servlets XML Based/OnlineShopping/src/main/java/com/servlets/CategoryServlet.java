package com.servlets;

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
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
		}
		
		
		try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/advjava", "root", "root24");
				PreparedStatement ps = connection.prepareStatement("select * from category");
				PrintWriter out = response.getWriter();){
			
			
			ResultSet result = ps.executeQuery();
			
			out.println("<html>");
			out.println("<body>");
			
			out.println("<table border='2'>");
			out.println("<tr>");
			out.println("<th>Category Id</th>");
			out.println("<th>Category Name</th>");
			out.println("<th>Description</th>");
			out.println("<th>Image</th>");
			out.println("</tr>");
			
			
			while(result.next()) {
				out.println("<tr>");
					out.println("<td>"+ result.getInt("categoryId") + "</td>");
					out.println("<td> <a href='products?categoryId="+ result.getInt("categoryId")+ "'>" + result.getString("categoryName") +  "</a></td>");
					out.println("<td>"+ result.getString("categoryDesc") + "</td>");
					out.println("<td> <img width='90px' height='90px' src='Images/"+ result.getString("categoryImageUrl") + "' /></td>");
				out.println("</tr>");
			}
			
			out.println("</body>");
			out.println("</html>");
			
		}catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		
		
		
	}

	
	
}
