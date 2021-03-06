package edu.ycp.cs320.groupProj.model;

// System model class for TextQuest  
// indicates whether a prompt is needed for "move" or "grab" etc.
public class SystemModel {

	private Boolean movement;
	private Boolean use;
	private Boolean grab;
	private Boolean inspect;
	private Boolean place;
	//shouldnt this be private?
	
	public SystemModel() {
		movement = false;
		use = false;
		grab = false;
		inspect = false;
		place = false;
	}
	
	public void reset() {
		movement = false;
		use = false;
		grab = false;
		inspect = false;
		place = false;
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
	public Boolean getUse() {
		return use;
	}
	public void setUse() {
		if(use) {
			use = false;
		} else {
			use = true;
		}
	}
	public void indicateUse(Boolean t) {
		use = t;
	}
	public Boolean getGrab() {
		return grab;
	}
	public void indicateGrab(Boolean t) {
		grab = t;
	}
	public void setGrab() {
		if(grab) {
			grab = false;
		} else {
			grab = true;
		}
	}
	public Boolean getInspect() {
		return inspect;
	}
	public void indicateInspect(Boolean t) {
		inspect = t;
	}
	public void setInspect() {
		if(inspect) {
			inspect = false;
		} else {
			inspect = true;
		}
	}
	public Boolean getPlace() {
		return place;
	}
	public void indicatePlace(Boolean t) {
		place = t;
	}
	public void setPlace() {
		if(place) {
			place = false;
		} else {
			place = true;
		}
	}
}
