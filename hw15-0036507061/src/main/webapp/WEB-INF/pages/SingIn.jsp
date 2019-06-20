<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<!DOCTYPE html>
<html>
<%
	String currentUser = (String) session.getAttribute("current.user.nick");
%>
<%
	if (currentUser == null) {
		out.write("Not loged in");
	} else {
		String firstName = (String) session.getAttribute("current.user.fn");
		String lastName = (String) session.getAttribute("current.user.ln");
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
<title>Sing in</title>
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

	<form action="save" method="post">

		<p>Sing in</p>

		<div>
			<div>
				<span class="formLabel">First name</span><input type="text"
					name="firstName" value='<c:out value="${zapis.firstName}"/>'
					size="50">
			</div>
			<c:if test="${zapis.imaPogresku('firstName')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('firstName')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Last name</span><input type="text"
					name="lastName" value='<c:out value="${zapis.lastName}"/>'
					size="50">
			</div>
			<c:if test="${zapis.imaPogresku('lastName')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('lastName')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Email</span><input type="text" name="email"
					value='<c:out value="${zapis.email}"/>' size="50">
			</div>
			<c:if test="${zapis.imaPogresku('email')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('email')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Nick</span><input type="text" name="nick"
					value='<c:out value="${zapis.nick}"/>' size="50">
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
					size="50">
			</div>
			<c:if test="${zapis.imaPogresku('passwordHash')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('passwordHash')}" />
				</div>
			</c:if>
		</div>

		<div>
			<c:if test="${zapis.imaPogresku('nickAlreadyExists')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('nickAlreadyExists')}" />
				</div>
			</c:if>
		</div>

		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="metoda" value="SIGN IN">
		</div>

	</form>

</body>
</html>