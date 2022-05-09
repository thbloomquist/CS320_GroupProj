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
		
		td.label {
			text-align: center;
		}
		</style>
		<%@ include file="header.jsp" %>
	</head>

	<body>
		<h1 align="center"> Instructions </h1>
		<br />
		<p align="center">You are on a quest to find the hidden treasure in the dungeon!</p>
		<p align="center">Enter commands to explore, find/use objects, fight monsters, and find the hidden treasure!</p>
		<p align="center">Most commands are two words, an action followed by a description (ex. move south, light torch, use banana...)</p>
		<p align="center">Find the hidden treasure before it's too late!</p>
		<p align="center">Don't worry too much about getting stuck, there's hints if you need them</p>
		<p align="center">Have fun on your quest!</p>
		<a class="nav-item nav-link" href="/TextQuest/new.game">Start New Game</a>
	</body> 
</html>