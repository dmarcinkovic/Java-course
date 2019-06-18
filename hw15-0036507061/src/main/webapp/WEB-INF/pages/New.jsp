<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add new blog entry</title>
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
	
	<form action="new" method="post">
		<h1>Add new blog entries.</h1>
		
		<div>
			<div>
				<span class="formLabel">Title</span><input type="text" name="nick"
					value='<c:out value="${zapis.title}"/>' size="30">
			</div>
			<c:if test="${zapis.imaPogresku('title')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('title')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Text</span><input type="password"
					name="passwordHash" value='<c:out value="${zapis.text}"/>'
					size="30">
			</div>
			<c:if test="${zapis.imaPogresku('text')}">
				<div class="greska">
					<c:out value="${zapis.dohvatiPogresku('text')}" />
				</div>
			</c:if>
		</div>
		
		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="metoda" value="ADD NEW ENTRY">
		</div>
		
	</form>

</body>
</html>