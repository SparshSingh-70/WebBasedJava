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
import java.util.ArrayList;
import java.util.Iterator;

import com.pojos.CartItem;


@WebServlet("/RemoveItemServlet")
public class RemoveItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if(session == null ) { //null session check
			response.sendRedirect("login.html");
			return;
		}
		
		
		ArrayList<CartItem> cart = (ArrayList<CartItem>)session.getAttribute("cart");
		
		Iterator<CartItem> iter = cart.iterator();
		CartItem cartItem = null;
		while(iter.hasNext()) {
			cartItem = iter.next();
			
			if(cartItem.getCategoryId() == Integer.parseInt(request.getParameter("categoryId"))
				
			&& cartItem.getProductId() == Integer.parseInt(request.getParameter("productId"))) {
				break;
			}
				
				
					
		}//while ends
		cart.remove(cartItem);
	
		
		session.setAttribute("cart", cart);

		response.sendRedirect("ListCart");
		
	}
		
}

	

