<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit blog entries</title>
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
						<c:out value="${zapis.title}"></c:out>
					<a href="edit">Edit</a>
					</li>
				</c:forEach>
			</ol>
		</c:otherwise>
	</c:choose>

</body>
</html>