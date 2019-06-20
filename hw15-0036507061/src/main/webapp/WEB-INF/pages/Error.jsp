<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>
<%
	String error = (String)session.getAttribute("error");
%>
<h1><%out.write(error); %></h1> 
	     
</body>
</html>