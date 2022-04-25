<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Text Quest!</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<%@ include file="header.jsp" %>
	</head>

	<body>
	
		<h1 class="display-1">Text Quest!</h1>
		<c:if test="${! empty player }">
			<h1>Welcome ${player.username}!</h1>
		</c:if>
		
		<br>
		<a href = "instructions">
		<button> Instructions </button>
		</a>
		<br>
		
		
	</body>
</html>
