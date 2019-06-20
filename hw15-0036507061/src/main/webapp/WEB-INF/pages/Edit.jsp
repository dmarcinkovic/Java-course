<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
     <%@ page session="true"%>
<!DOCTYPE html>
<html>
<% String currentUser = (String)session.getAttribute("current.user.nick");%>
	<%
		if (currentUser == null){
			out.write("Not loged in");
		}else {
			String firstName = (String)session.getAttribute("current.user.fn");
			String lastName = (String)session.getAttribute("current.user.ln");
			out.write(firstName + " " + lastName);
		}
	%>
	<%
		if (currentUser != null){ %>
			<a href="logout">Logout</a>
			<% 
		}
	%>
<head>
<meta charset="UTF-8">
<title>Edit blog entries</title>
<style type="text/css">
.greska {
	font-family: fantasy;
	font-weight: bold;
	font-size: 0.9em;
	color: #FF0000;
	padding-left: 110px;
}

.formLabel {
	display: inline-block;
	width: 100px;
	font-weight: bold;
	text-align: right;
	padding-right: 10px;
}

.formControls {
	margin-top: 10px;
}
</style>
</head>
<body>

	<h1>List of blog entries.</h1>
	
	<c:choose>
		<c:when test="${blogEntries.isEmpty() || blogEntries == null}">
			<p>Nothing to edit.</p>
		</c:when>
		<c:otherwise>
			<ol>
				<c:forEach var="zapis" items="${blogEntries}">
					<li>
						<p>${zapis.title}</p>
						
						<a href="edit?blogId=${zapis.id}">Edit</a>
					</li>
				</c:forEach>
			</ol>
		</c:otherwise>
	</c:choose>

</body>
</html>