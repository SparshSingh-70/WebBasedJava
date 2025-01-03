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
import java.util.Iterator;

import com.pojos.CartItem;

/**
 * Servlet implementation class ListCart
 */
@WebServlet("/ListCart")
public class ListCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if(session == null) {
			response.sendRedirect("login.html");
			return;
		}
		
		PrintWriter out = response.getWriter();
		double total = 0.0;
		ArrayList<CartItem> cart = (ArrayList<CartItem>)session.getAttribute("cart");
		
		out.println("<html>");
		out.println("<body>");
		out.println("Welcome <b>" + session.getAttribute("username") + "</b> <br/>");
		out.println("<table border='2'>");
		out.println("<tr>");
		out.println("<th>Category Id</th>");
		out.println("<th>Product Id</th>");
		out.println("<th>Product Price</th>");
		out.println("<th>Action</th>");
		
		out.println("</tr>");
		
		if(cart == null || cart.size()<=0) {
			out.println("Cart is empty!!");
		}else {
			Iterator<CartItem> iter = cart.iterator();
			
			while(iter.hasNext()) {
				CartItem cartItem = iter.next();
				out.println("<tr>");
				out.println("<td>" + cartItem.getCategoryId() + "</td>");
				out.println("<td>" + cartItem.getProductId()+ "</td>");
				out.println("<td>" + cartItem.getProductPrice() + "</td>");
				out.println("<td><a href = 'RemoveItemServlet?categoryId=" + cartItem.getCategoryId() + "&productId=" + cartItem.getProductId() + "'>Remove Item</a><br/></td>");
				out.println("</tr>");
				
				total += cartItem.getProductPrice();				
						
			}//while
						
			out.println("</table>");
			out.println("Total : " + "<b>" + total + "</b><br/>");
			out.println("<a href = 'CategoryServlet'> Continue Shopping </a><br/>");
			out.println("<a href='UserCardDetails.html'> Checkout </a> <br/>");
			
			out.println("<a href ='LogoutServlet'>Logout</a><br/>");
			
			out.println("</body>");
			out.println("</html>");
			
			session.setAttribute("total", total);
			
			
		}
	}

	

}
