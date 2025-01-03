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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.pojos.CartItem;


@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
 

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		HttpSession session = request.getSession(false);
		if(session == null ) { //null session check
			response.sendRedirect("login.html");
			return;
		}
		
		String userName = (String) session.getAttribute("username");
		
		
		ServletContext context = getServletContext();
		
		String dbUrl = context.getInitParameter("dbUrl");
		String dbDriver = context.getInitParameter("dbDriver");
		String dbUsername = context.getInitParameter("dbUsername");
		String dbPassword = context.getInitParameter("dbPassword");
		
		try {
			Class.forName(dbDriver);
			
			
			
			
			connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			pStatement = connection.prepareStatement("Insert into cartitems values ( ?, ?, ?, ?) ");
			
		    
			ArrayList<CartItem> cart = (ArrayList<CartItem>) session.getAttribute("cart");
			
			Iterator<CartItem> iter = cart.iterator();
			while(iter.hasNext()) {
				CartItem cartItem = iter.next();
				int categoryId = cartItem.getCategoryId();
				int productId = cartItem.getProductId();
				float productPrice = cartItem.getProductPrice();
				
				pStatement.setString(1, userName);
				pStatement.setInt(2, categoryId);
				pStatement.setInt(3, productId);
				pStatement.setFloat(4, productPrice);
				
				pStatement.executeUpdate();
				
				
			}
				
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
				try {
					if(pStatement != null) {
						pStatement.close();
					}
					if(connection != null) {
					connection.close();
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
		
		session.invalidate();
		
		PrintWriter out = response.getWriter();
		out.println("You have been successfully Logged Out!! <br/>");
		
		out.println("<a href = 'login.html'> Login Again </a>");
		
	}

	

}
