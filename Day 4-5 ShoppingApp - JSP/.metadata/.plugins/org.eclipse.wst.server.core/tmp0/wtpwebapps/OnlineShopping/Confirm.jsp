<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<jsp:useBean id="newObjUser" class="com.pojos.NewUser" scope="session"/>

<jsp:setProperty property="*" name="newObjUser"/>

User Name : <jsp:getProperty property="username" name="newObjUser"/> <br/>

Name : <jsp:getProperty property="name" name="newObjUser"/>
<br/>

Email : <jsp:getProperty property="email" name="newObjUser"/>

<a href = 'Register.jsp'>Register </a>

</body>
</html>