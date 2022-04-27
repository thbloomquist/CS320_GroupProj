<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Winner Winner</title>
		<style type="text/css">
		.error {
			color: red;
		}
		
		td.label {
			text-align: right;
		}
		</style>
		<%@ include file="header.jsp" %>
	</head>

	<body>
		<h1> You found the treasure! All the riches in the world are yours! </h1>
		<h2 style="color:gold"> You win! </h2>
		<a href = "/TextQuest/start">
		<button> Play Again? </button>
		</a>
	</body>
</html>