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

<head>
<meta charset="UTF-8">
<title>Login</title>
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

	<form action="login" method="post">

		<h1>Log in</h1>

		<div>
			<div>
				<span class="formLabel">Nick</span><input type="text" name="nick"
					value='<c:out value="${zapis.nick}"/>' size="30">
			</div>
			<c:if test="${zapis.imaPogresku('nick')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('nick')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Password</span><input type="password"
					name="passwordHash" value='<c:out value="${zapis.passwordHash}"/>'
					size="30">
			</div>
			<c:if test="${zapis.imaPogresku('passwordHash')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('passwordHash')}" />
				</div>
			</c:if>
		</div>
		
		<div>
			<c:if test="${zapis.imaPogresku('user')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('user')}" />
				</div>
			</c:if>
		</div>
		
		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="metoda" value="LOGIN">
		</div>

	</form>
	
	<br>
	
	<%
		if (currentUser != null){ %>
			<a href="logout">LOGOUT</a>
			<% 
		}
	%>
		
	<h2>New to this blog?</h2>
	<a href="register">Sign in</a>
	<br>

	<h3>List of registered authors.</h3>
	<c:choose>
		<c:when test="${zapisi.isEmpty()}">
			<p>Currently there are no registered authors.</p>
		</c:when>
		<c:otherwise>
			<ol>
				<c:forEach var="zapis" items="${zapisi}">
					<li><a href="author/<c:out value="${zapis.nick}"/>"><c:out
								value="${zapis.nick}" /> </a>
					</li>
				</c:forEach>
			</ol>
		</c:otherwise>
	</c:choose>

</body>
</html>