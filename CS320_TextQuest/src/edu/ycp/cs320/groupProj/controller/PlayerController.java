package edu.ycp.cs320.groupProj.controller;

import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.groupProj.model.ObjectModel;

public class PlayerController {
	private PlayerModel model;
	
	public void setModel(PlayerModel nModel) {
		this.model = nModel;
	}
	public void damage(int num) {
		model.setHP(model.getHP() - num);
	}
	public void heal(int num)  {
		model.setHP(model.getHP() + num);
	}
	public void grab(ObjectModel thing) {
		model.addInventory(thing);
	}
}
