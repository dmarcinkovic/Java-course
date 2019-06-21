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
<title>Blog entries</title>
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
	<h1>Title: ${zapis.title}</h1>
	<h2>Text: ${zapis.text}</h2>
	
	<c:if test="${permits != null && permits == true}">
		<p>Edit blogs</p>
		
		<a href="edit?blogId=${id}">Edit blog entry.</a> <br>
	</c:if>
	
	<a href=""></a>

	<c:choose>
		<c:when test="${comments.isEmpty() || comments == null}">
			<p>No comments are posted.</p>
		</c:when>
		<c:otherwise>
			<p>Comments:</p>
			<ol>
				<c:forEach var="comment" items="${comments}">
					<li>${comment.message}</li>
				</c:forEach>
			</ol>
		</c:otherwise>
	</c:choose>

	<form action="${id}"
		method="post">

		<div>
			<div>
				<span class="formLabel">Comment </span><input type="text"
					name="comment.message" value='<c:out value="${commentForm.message}"/>'
					size="30">
			</div>
			<c:if test="${commentForm.hasError('message')}">
				<div class="greska">
					<c:out value="${commentForm.getError('message')}" />
				</div>
			</c:if>
		</div>
		
		<%
		if (currentUser == null){ %>
			<div>
			<div>
				<span class="formLabel">Email </span><input type="text"
					name="usersEMail" value='<c:out value="${commentForm.usersEMail}"/>'
					size="30">
			</div>
			<c:if test="${commentForm.hasError('usersEMail')}">
				<div class="greska">
					<c:out value="${commentForm.getError('usersEMail')}" />
				</div>
			</c:if>
		</div>
			<% 
		}
	%>
		
		
	
		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="metoda" value="ADD COMMENT">
		</div>
	</form>
	

</body>
</html>