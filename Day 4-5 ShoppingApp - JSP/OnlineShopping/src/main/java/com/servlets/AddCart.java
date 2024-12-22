package com.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.pojos.CartItem;


@WebServlet("/AddCart")
public class AddCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.sendRedirect("login.html");
			return;
		}
		
		PrintWriter out = response.getWriter();
		
		String temp = request.getParameter("categoryId");
		int categoryId = Integer.parseInt(temp);
		
		temp = request.getParameter("productId");
		int productId = Integer.parseInt(temp);
		
		temp = request.getParameter("productPrice");
		float productPrice = Float.parseFloat(temp);
		
		
		CartItem item = new CartItem(categoryId, productId, productPrice);
		
		ArrayList<CartItem> cart = (ArrayList<CartItem>) session.getAttribute("cart");
		
		if(cart == null) {
			cart = new ArrayList<CartItem>();
			session.setAttribute("cart", cart);
		}
		cart.add(item);
		
		response.sendRedirect("ListCart");
		
		
	}



}
