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
	<br>
	<%
		if (currentUser != null){ %>
			<a href="${pageContext.request.contextPath}/servleti/logout">Logout</a>
			<% 
		}
	%>
<head>
<meta charset="UTF-8">
<title>Blog entry</title>
</head>
<body>

	<h1>Blog entry.</h1>
	
	<h2>Title : "${entry.title}"</h2>
	<h2>Text : "${entry.text}"</h2>
	
	<ol>
				<c:forEach var="zapis" items="${entry.comments}">
					<li><p>"${zapis}"</p>
					</li>
				</c:forEach>
	</ol>
	
	<form action="comment" method="get">
	<div>
			<span class="formLabel">Post your comment here.</span><input type="text"
					name="comment" value='<c:out value="${result}"
					/>'
			size="30">
	</div>
	
	<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="metoda" value="SUBMIT">
	</div>
	
	</form>

</body>
</html>