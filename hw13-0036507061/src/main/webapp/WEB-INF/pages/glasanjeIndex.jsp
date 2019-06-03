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

	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na
		link kako biste glasali!</p>
	<ol>
		<li><a href="glasanje-glasaj?id=1">The Beatles</a></li>
		<li><a href="glasanje-glasaj?id=2">The Platters</a></li>
		<li><a href="glasanje-glasaj?id=3">The Beach Boys</a></li>
		<li><a href="glasanje-glasaj?id=4">The Four Seasons</a></li>
		<li><a href="glasanje-glasaj?id=5">The Marcels</a></li>
		<li><a href="glasanje-glasaj?id=6">The Everly Brothers</a></li>
		<li><a href="glasanje-glasaj?id=7">The Mamas And The Papas</a></li>
	</ol>

</body>
</html>