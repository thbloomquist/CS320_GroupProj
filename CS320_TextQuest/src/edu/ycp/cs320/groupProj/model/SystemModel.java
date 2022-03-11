package edu.ycp.cs320.groupProj.model;

// System model class for TextQuest  
// indicates whether a prompt is needed for "move" or "grab" etc.

 

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
