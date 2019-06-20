<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit blog entry.</title>
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
	<h2>${form.title}</h2>
		<h3>${form.text}</h3>
	
	<form action="edit?ID=${form.id}" method="post">
		
		<div>
			<div>
				<span class="formLabel">Change title</span><input type="text" name="title"
					value='<c:out value="${form.title}"/>' size="30">
			</div>
			
			<c:if test="${form.hasError('title')}">
				<div class="greska">
					<c:out value="${form.getError('title')}" />
				</div>
			</c:if>
		</div>

		<div>
			<div>
				<span class="formLabel">Change text</span><input type="text"
					name="text" value='<c:out value="${form.text}"/>'
					size="30">
			</div>
			
			<c:if test="${form.hasError('text')}">
				<div class="greska">
					<c:out value="${form.getError('text')}" />
				</div>
			</c:if>
		</div>
		
		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				name="metoda" value="EDIT ENTRY">
		</div>
		
	</form>
	
	
</body>
</html>