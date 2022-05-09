<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<html>
	<head>
		<title>TextQuest!</title>
		<style type="text/css">
		.error {
			color: red;
		}
		
		h1 {
			text-align: center;
		}
		p {
			text-align: center;
		}
		</style>
		
	</head>

	<body>
		<center>
		<h1> Text Quest! </h1>
		<h2>Enter! If you dare</h2>
		<p>You're on the hunt for a treasure chest filled with unimaginable riches</p>
		<p>It is said to be a myth, but you are determine to find this treasure</p>
		<p>You find yourself in a dungeon, it's dark and spooky and you're not quite sure which direction to go...</p>
		<p>You hear a lot of growls and snarls coming from the depths of the hallways</p>
		<p>To find the treasure, navigate your way through the maze, fight off the monsters, and use the items you find to your advantage to find the key and open the chest!</p>
		<p> You grab your trusty torch and set out to score unimaginable riches. </p>
		<form action="${pageContext.servletContext.contextPath}/main" method="post">
			<button type="submit" class="btn btn-success">Begin Game</button>
			<input name="action" id="action" type="hidden" value="welcome" />
		</form>
		</center>
	</body>
</html>