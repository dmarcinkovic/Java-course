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

	<form action="setcolor?pickedBgCol=white">
		<a href="setcolor?pickedBgCol=white">WHITE</a>
	</form>

	<form action="setcolor?pickedBgCol=red">
		<a href="setcolor?pickedBgCol=red">RED</a>
	</form>

	<form action="setcolor?pickedBgCol=green">
		<a href="setcolor?pickedBgCol=green">GREEN</a>
	</form>

	<form action="setcolor?pickedBgCol=cyan">
		<a href="setcolor?pickedBgCol=cyan">CYAN</a>
	</form>


</body>
</html>