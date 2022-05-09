<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Text Quest!</title>
		<style type="text/css">
		.error {
			color: red;
			font-size: 24px;
		}
		
		td.label {
			text-align: right;
		}
		</style>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
		<%@ include file="header.jsp" %>
	</head>

	<body>
		<form action="${pageContext.servletContext.contextPath}/login" method="post">
			<!-- List of instructions -->
			<div id="instructions">   
				<!-- Sign Up Table-->
				<center>
				<h3>Login</h3>
				<c:if test="${! empty errorMessage}">
					<div class="error">${errorMessage}</div>
				</c:if>
				</center>
				<table class="table">
					<tbody>
						<!-- User Username -->
					    <tr>
					      <th scope="row"></th>
					      <td class="tdPrompt">Username:</td>
					      <td class="tdPrompt"><input type="text" name="username" size="20" ></td>
					    </tr>
					    <!-- User Password -->
					    <tr>
					      <th scope="row"></th>
					      <td class="tdPrompt">Password:</td>
						  <td class="tdPrompt"><input type="password" name="password" size="20" ></td>
					    </tr>
					    <!-- Sign Up Button -->
					    <tr>
					      <th scope="row"></th>
					      <td></td>
						  <td style="padding-left:24%;"><input class="btn btn-success" type="Submit" name="login" value="login"></td>
					    </tr>
			    	</tbody>
				</table>
			</div>
		</form>
	</body>
</html>