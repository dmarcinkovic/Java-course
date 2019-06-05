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
<title>Main page</title>
</head>
<body bgcolor=<%out.write(color);%>>

	<a href="colors.jsp">Background color chooser</a>
	<br>
	<a href="trigonometric?a=0&b=90">Trigonometric</a>
	<br>
	<a href="stories/funny.jsp">Funny story</a>
	<br>
	<a href="powers?a=1&b=100&n=3">Microsoft Excel</a>
	<br>
	<a href="appinfo.jsp">App informations</a>
	<br>
	<br>

	<form action="trigonometric" method="GET">
		Početni kut:<br> <input type="number" name="a" min="0" max="360"
			step="1" value="0"><br> Završni kut:<br> <input
			type="number" name="b" min="0" max="360" step="1" value="360"><br>
		<input type="submit" value="Tabeliraj"><input type="reset"
			value="Reset">
	</form>

</body>
</html>