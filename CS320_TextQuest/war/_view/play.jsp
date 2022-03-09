<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Guessing Game</title>
	</head>

	<body>
		<form action="/TextQuest/dead" method="post">
		<label for="input"> Type your action here: </label>
		<input type="text" name="action" id="input"> 
		
		</form>
	</body>
</html>