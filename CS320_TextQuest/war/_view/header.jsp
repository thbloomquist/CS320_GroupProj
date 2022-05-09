<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">TextQuest!</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
    <div class="navbar-nav">
      <a class="nav-item nav-link active" href="/TextQuest/">Home</a>
      <a class="nav-item nav-link" href="/TextQuest/new.game">Start New Game</a>
      <a class="nav-item nav-link" href="/TextQuest/continue.game">Continue Game</a>
      <a class="nav-item nav-link" href="/TextQuest/instructions">Instructions</a>
      <a class="nav-item nav-link" href = "/TextQuest/about">About</a>
      <a class="nav-item nav-link" href = "/TextQuest/leaderboard">Leaderboard</a>
      </div>
      </div>
      <ul class="nav justify-content-end">
		  <li class="nav-item">
		    <button type="button" onclick="window.location='${pageContext.request.contextPath}/login'" class="btn btn-success" data-dismiss="modal">Login</button>
		  </li>
		  <li class="nav-item">
		    <button type="button" onclick="window.location='${pageContext.request.contextPath}/signup'" class="btn btn-secondary">SignUp</button>
		  </li>
		  <li class="nav-item">
		 	<form action="${pageContext.servletContext.contextPath}/logout" method="post">
		    	<button type="submit" onclick="window.location='${pageContext.request.contextPath}/logout'" class="btn btn-danger">Logout</button>
		    </form>
		  </li>
	  </ul>
  
</nav>