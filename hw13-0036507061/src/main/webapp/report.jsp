<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page session="true"%>
<%
	String color = (String) session.getAttribute("pickedBgCol");
	if (color == null) {
		color = "white";
	}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body bgcolor=<%out.write(color);%>>

	<h1>OS usage</h1>
	<p>Here are the results of OS usage in survey that we completed</p>

	<img src="reportImage" />

</body>
</html>