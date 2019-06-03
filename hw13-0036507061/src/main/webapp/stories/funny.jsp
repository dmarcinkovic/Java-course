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
	<%
		String[] fonts = { "Arial", "Helvetica", "Times New Roman", "Times", "Courier New", "Courier", "Verdana",
				"Georgia", "Palatino", "Garamond", "Bookman", "Comic Sans MS", "Trebuchet MS", "Arial Black",
				"Impact" };

		int index = (int) (Math.random() * (fonts.length - 1));
		String font = fonts[index];
	%>

	<p>
		<font face=<%out.write("\"" + font + "\"");%>> Prolazi Mujo
			pored prosjaka, ovaj mu jadan pruža <br> ruku i kaže: <br>
			-Brate, nisam jeo tri dana. <br> A Mujo odgovara: <br>
			-Moraš, brate, makar na silu, moraaaš. <br>

		</font>
	</p>

</body>
</html>