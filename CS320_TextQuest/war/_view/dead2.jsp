<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Text Quest!</title>
		<style type="text/css">
		.error {
			color: red;
		}
		
		td.label {
			text-align: center;
		}
		</style>
		<%@ include file="header.jsp" %>
	</head>

	<body>
		<h1> You are dead! Better luck next time! </h1>
		<h2> Your final score was: ${score}</h2>
		<h2> Welcome to the bone zone. </h2>
		<a href = "/TextQuest/start">
		<button> Try again?</button>
		</a>
	</body>
</html>