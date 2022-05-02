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
	public Boolean contains(String item) {
		Boolean t  = false;
		for(int i = 0; i < model.getInvenFULL().length; i++) {
			if(model.getInvenFULL()[i] != null) {
				if(model.getInvenFULL()[i].getTag().getName().equals(item)) {
					t = true;
				}
			}
		}
		return t;
	}
}
