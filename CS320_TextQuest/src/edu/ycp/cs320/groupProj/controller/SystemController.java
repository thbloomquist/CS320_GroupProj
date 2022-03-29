package edu.ycp.cs320.groupProj.controller;

import edu.ycp.cs320.groupProj.model.SystemModel;

public class SystemController {
	private SystemModel model;
	
	public void setModel(SystemModel model) {
		this.model = model;
	}
	public Boolean getMove() {
		return model.getMovement();
	}
	public void setMove() {
		model.setMovement();
	}
	public void setMovement(Boolean t) {
		model.indicateMove(t);
		
	}
}
