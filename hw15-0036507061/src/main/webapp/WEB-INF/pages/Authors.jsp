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
<title>Authors</title>
</head>
<body>
	
	<h1>List of blog entries.</h1>
	<c:choose>
		<c:when test="${blogEntries.isEmpty() || blogEntries == null}">
			<p>Currently there are no blog entries from <c:out value="${nick}"/></p>
		</c:when>
		<c:otherwise>
			<ol>
				<c:forEach var="zapis" items="${blogEntries}">
					<li><a href="<c:out value="${nick}"/>/<c:out value="${zapis.id}"/>"><c:out
								value="${zapis.title}" /> </a>
					</li>
				</c:forEach>
			</ol>
		</c:otherwise>
	</c:choose>
	<c:if test="${permits != null && permits == true}">
		<a href="<c:out value="${nick}"/>/new">Add new blog entry.</a> <br>
		<a href="<c:out value="${nick}"/>/edit">Edit existing entries.</a>
	</c:if>
	
</body>
</html>