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
			text-align: right;
		}
		</style>
	</head>

	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
	
		<form action="${pageContext.servletContext.contextPath}/main" method="post">
			<label for="input"> Type your action here: </label>
			<input type="text" name="action" id="input"> 
			<br>
			<br>
			<table>
				<tr>
					<td class="label">Result:</td>
					<td>${result}</td>
				</tr>
			</table>
		</form>
				
		<button type="button" class="btn btn-outline-info" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">Stuck?</button>

		<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
		  <div class="offcanvas-header">
		    <h5 id="offcanvasRightLabel">Need a hint?</h5>
		    <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
		  </div>
		  <div class="offcanvas-body">
		    <ul>
		    	<li>Move - enter this to move from room to room (followed by N/S/E/W)</li>
		    	<li>Inspect - look at room or inventory</li>
		    	<li>Use - use an item in inventory or the room</li>
		    	<li>Grab - grab an item in the room</li>
		    	<li>Anything else thats needed</li>
		    	
		    </ul>
		  </div>
		</div>
		
		<a href = "/TextQuest/start"><button type="button" class="btn btn-outline-danger">Back to Main Menu</button></a>
		
	</body>
</html>