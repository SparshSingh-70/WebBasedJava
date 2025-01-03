package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.pojos.CartItem;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	Connection connection;
	PreparedStatement ps;
	PreparedStatement pCart; 
	PreparedStatement pDelete; 

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {

			ServletContext context = getServletContext();
			String dbUrl = context.getInitParameter("dbUrl");
			String dbDriver = context.getInitParameter("dbDriver");
			String dbUsername = context.getInitParameter("dbUsername");
			String dbPassword = context.getInitParameter("dbPassword");

			Class.forName(dbDriver);

			connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			ps = connection.prepareStatement("select * from users where username=? and password=?");
			pCart = connection.prepareStatement("select * from cartitems where username=?"); 
			pDelete = connection.prepareStatement("delete from cartitems where username=?");
			
			
		} catch (SQLException | ClassNotFoundException e) {
			throw new ServletException(e.getMessage());
		}
	}
 
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		PrintWriter out = resp.getWriter();
		
				
			
					try {
						ps.setString(1, username);
						ps.setString(2, password);
						
						ResultSet resultSet = ps.executeQuery();
						
						if(resultSet.next()) {
							
							HttpSession session = req.getSession();
							session.setAttribute("username", username);
							
							if(username.equals("admin")) {
								resp.sendRedirect("Admin/AdminPage.html");
							}else {	
								
								ArrayList<CartItem> cart = new ArrayList<CartItem>();
								pCart.setString(1, username);
								resultSet = pCart.executeQuery();
								while(resultSet.next()) {
									
									int categoryId = resultSet.getInt("categoryId");
									int productId = resultSet.getInt("productId");
									float productPrice = resultSet.getFloat("productPrice");
									
									CartItem cartItem = new CartItem(categoryId, productId, productPrice);
									
									cart.add(cartItem);
								}
								
								session.setAttribute("cart", cart);
								
								pDelete.setString(1, username);
								pDelete.executeUpdate();
								
								resp.sendRedirect("CategoryServlet");
							}
						}else {
							out.println("Auth Failed");
						}
					} catch (SQLException e) {
						System.err.println(e.getMessage());
					}
					
				
					
	}

	
	@Override
	public void destroy() {
		
		try {
			if(ps != null) {
				ps.close();
			}
			if(connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

	}


}
