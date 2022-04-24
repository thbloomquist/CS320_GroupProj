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
			text-align: right;
		}
		</style>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
	</head>

	<body>
		<form action="${pageContext.servletContext.contextPath}/signup" method="post">
			<!-- List of instructions -->
			<div id="instructions">   
				<!-- Sign Up Table-->
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
						  <td style="padding-left:24%;"><input class="btn btn-success" type="Submit" name="login" value="sign-up"></td>
					    </tr>
			    	</tbody>
				</table>
			</div>
			<input name="studentModel" type="hidden" value="${sinfo}" />
		</form>
	</body>
</html>