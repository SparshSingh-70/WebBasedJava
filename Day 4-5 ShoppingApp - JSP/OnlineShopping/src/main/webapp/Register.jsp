<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<jsp:useBean id="newObjUser" type="com.pojos.NewUser" scope="session"/>

<%
ServletContext context = getServletContext();
String dbUrl = context.getInitParameter("dbUrl");
String dbDriver = context.getInitParameter("dbDriver");
String dbUsername = context.getInitParameter("dbUsername");
String dbPassword = context.getInitParameter("dbPassword");

Class.forName(dbDriver);

Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
PreparedStatement psInsert = connection.prepareStatement("Insert into users values (?,?,?,?)");

psInsert.setString(1, newObjUser.getUsername());
psInsert.setString(2, newObjUser.getPassword());
psInsert.setString(3, newObjUser.getName());
psInsert.setString(4, newObjUser.getEmail());

psInsert.executeUpdate();


%>

<h4 style="color: green;"> New User Registered Successfully!!</h4>

<jsp:include page="login.html"></jsp:include>


</body>
</html>