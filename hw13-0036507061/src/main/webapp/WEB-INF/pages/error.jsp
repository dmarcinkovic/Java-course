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
<title>Error message</title>
</head>
<body bgcolor=<%out.write(color);%>>

	<p>
		<%
		out.write((String)session.getAttribute("error"));
		
		%>
	</p>

</body>
</html>