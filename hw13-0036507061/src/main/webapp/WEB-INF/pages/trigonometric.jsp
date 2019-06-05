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
<title>Trigonometric</title>
</head>
<body bgcolor=<%out.write(color);%>>

	<table border="1">

		<thead>
			<tr>
				<td>sin(x)</td>
				<td>cos(x)</td>
			</tr>
		</thead>

		<%
			int a = (Integer) session.getAttribute("a");
			int b = (Integer) session.getAttribute("b");
			for (int i = a; i <= b; i++) {
				Double sin = Math.sin(i);
				Double cos = Math.cos(i);
		%><tr>
			<td>
				<%
					out.write(sin.toString());
				%>
			</td>
			<td>
				<%
					out.write(cos.toString());
				%>
			</td>
		</tr>
		<%
			}
		%>

	</table>

</body>
</html>