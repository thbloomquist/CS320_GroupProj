package edu.ycp.cs320.groupProj.model;

// model class for GuessingGame
// only the controller should be allowed to call the set methods
// the JSP will call the "get" and "is" methods implicitly
// when the JSP specifies game.min, that gets converted to
//    a call to model.getMin()
// when the JSP specifies if(game.done), that gets converted to
//    a call to model.isDone()
public class SystemModel {
	public Boolean movement;
	
	public SystemModel() {
		movement = false;
	}
	
	public Boolean getMovement() {
		return movement;
	}
	public void setMovement() {
		if(movement) {
			movement = false;
		} else {
			movement = true;
		}
	}
	public void indicateMove(Boolean t) {
		movement = t;
	}
}
