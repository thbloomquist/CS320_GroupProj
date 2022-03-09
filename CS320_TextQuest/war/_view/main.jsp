<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
		
		<a href="/TextQuest/start">
		<button>Index</button>
		</a>
	</body>
</html>