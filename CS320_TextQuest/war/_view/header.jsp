<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">TextQuest!</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav">
      <a class="nav-item nav-link active" href="#">Home <span class="sr-only">(current)</span></a>
      <a class="nav-item nav-link" href="/TextQuest/main">Start New Game</a>
      <a class="nav-item nav-link" href="/TextQuest/continue">Continue Game</a>
      <a class="nav-item nav-link" href = "/TextQuest/about">About</a>
      </div>
      </div>
      <ul class="nav justify-content-end">
		  <li class="nav-item">
		    <a class="nav-link active btn btn-success" href = "/TextQuest/login">Login</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link btn btn-primary" href = "/TextQuest/signup">Sign-Up</a>
		  </li>
		  <li class="nav-item">
		  	<form action="${pageContext.servletContext.contextPath}/logout" method="post">
		    <input class="btn btn-warning" style="margin-right: 0%; ;" type="Submit" name="Logout" value="Logout">
		    </form>
		  </li>
		  
	  </ul>
  
</nav>