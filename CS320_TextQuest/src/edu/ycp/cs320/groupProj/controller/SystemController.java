package edu.ycp.cs320.groupProj.controller;

import edu.ycp.cs320.groupProj.model.SystemModel;

public class SystemController {
	private SystemModel model;
	
	public void setModel(SystemModel nModel) {
		this.model = nModel;
	}
	public Boolean getMove() {
		return model.getMovement();
	}
	public void setMove() {
		model.setMovement();
	}
}
