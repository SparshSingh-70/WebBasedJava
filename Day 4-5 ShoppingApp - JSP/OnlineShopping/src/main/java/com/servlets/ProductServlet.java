package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try(Connection connection  =  DriverManager.getConnection("jdbc:mysql://localhost:3306/advjava", "root", "cdac");
					PreparedStatement ps = connection.prepareStatement("select * from products where categoryId = ?");
					PrintWriter out = resp.getWriter()){
				
				HttpSession session = req.getSession(false);
				if(session == null) {
					resp.sendRedirect("login.html");
					return;
				}
				
				
				String str = req.getParameter("categoryId");
				int cid = Integer.parseInt(str);
				
				ps.setInt(1, cid);
				
				ResultSet result = ps.executeQuery();
				out.println("<html>");
				out.println("<body>");
				
				out.println("<table border='2'>");
				out.println("<tr>");
				out.println("<th>Product Id</th>");
				out.println("<th>Product Name</th>");
				out.println("<th>Product Description</th>");
				out.println("<th>ProductPrice</th>");
				out.println("<th>Image</th>");
				out.println("<th>Add to Cart</th>");
				out.println("</tr>");
				
				
				while(result.next()) {
					out.println("<tr>");
						out.println("<td>"+ result.getInt("productId") + "</td>");
						out.println("<td> " + result.getString("productName") +"</td>");
						out.println("<td>"+ result.getString("productDesc") + "</td>");
						out.println("<td>"+ result.getFloat("productPrice") + "</td>");
						out.println("<td> <img width='90px' height='90px' src='Images/"+ result.getString("productImageUrl") + "' /></td>");
						out.println("<td><a href='AddCart?categoryId=" + result.getInt("categoryId") + "&productId=" +  result.getInt("productId") + "&productPrice=" + result.getFloat("productPrice")  +"'> Add to cart </a></td>");
						out.println("</tr>");
				}
				
				out.println("</body>");
				out.println("</html>");
			}catch (SQLException e) {
				System.err.println(e.getMessage());
			}
			
		} catch (ClassNotFoundException e) {
			
			System.err.println(e.getMessage());
			
		}
		
		
	}
	
	
	
	

}
