
<%@page import="com.pojos.CartItem"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Date"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="jakarta.servlet.ServletContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		ServletContext context = getServletContext();
	
		String dbUrl = context.getInitParameter("dbUrl");
		String dbDriver = context.getInitParameter("dbDriver");
		String dbUsername = context.getInitParameter("dbUsername");
		String dbPassword = context.getInitParameter("dbPassword");
		
		Class.forName(dbDriver);
		
		try(Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
				PreparedStatement psCardInfo = connection.prepareStatement("select * from cards where cardNo = ? and expiryDate = ?");
				PreparedStatement psUpdateBal = connection.prepareStatement("update cards set balance = ? where cardNo = ?");
				PreparedStatement psUpdateTrans = connection.prepareStatement("insert into transaction (username, cardNo, amount, txDate, status) values (?,?,?,?,?)")){
			
			
			String temp = request.getParameter("cardNumber");
			int cardNumber = Integer.parseInt(temp);
			
			String expiryDate = request.getParameter("expiryDate");
			
			
			String username = (String) session.getAttribute("username");
			
			
			ResultSet resultCard = null;
			psCardInfo.setInt(1, cardNumber);
			psCardInfo.setString(2, expiryDate);
			resultCard = psCardInfo.executeQuery();
			
			
			
			double cartTotal = (double)session.getAttribute("total");
			int status = 0;
			if(resultCard.next()){
				
				double bal = resultCard.getDouble("balance");
				String expDbDate = resultCard.getString("expiryDate");
				
				if(bal >= cartTotal && expiryDate.equals(expDbDate)){
					
					double updateBal = bal - cartTotal;
					psUpdateBal.setDouble(1, updateBal);
					psUpdateBal.setDouble(2, cardNumber);
					psUpdateBal.executeUpdate();
					status = 1;
				}
				else{
					%>
					
					<h3 style="color: red;" >Not Enough Balance ? Can Add Another Card</h3>
					<jsp:include page="UserCardDetails.html"/>
					<%
				}
				
				psUpdateTrans.setString(1, username);
				psUpdateTrans.setInt(2, cardNumber);
				psUpdateTrans.setDouble(3, cartTotal);
				psUpdateTrans.setDate(4, new Date(System.currentTimeMillis()));
				psUpdateTrans.setInt(5, status);
				
				psUpdateTrans.executeUpdate();
				
				if(status == 1){
				%>
					<h3 style="color: green;">Payment Successful</h3>
					<a href="ListCart">Cart</a>
				<%
				
				ArrayList<CartItem> cart = (ArrayList<CartItem>)session.getAttribute("cart");
				cart.removeAll(cart);
				
				}
			}else{
				
				%>
				
				<h3 style="color: red;" >Card Not Found</h3>
					<jsp:include page="UserCardDetails.html"/>
				
				<%
				
				
			}
			
			
			
		}
	
	
	%>

</body>
</html>