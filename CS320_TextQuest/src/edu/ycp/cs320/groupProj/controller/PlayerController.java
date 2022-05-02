package edu.ycp.cs320.groupProj.controller;

import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.model.Room;

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
	public int sortInven(ObjectModel[] inven) {
		// this function serves two purposes - returning the new iNum & sorting the players inventory so the iNum is accurate.
		int num = 0; 
		ObjectModel[] tempr = new ObjectModel[inven.length];
		for(int i = 0; i < inven.length; i++) {
			if(inven[i] != null) {
				tempr[num] = inven[i];
				num++;
			}
		}
		model.setInvenFULL(tempr);
		
		return num;
		
	}
	public Boolean grabItem(String secondW, Room currentR) {
		Boolean t = false;
		ObjectModel[] temp = new ObjectModel[10];
		int num = 0;
		for (int i = 0; i < currentR.getInven().length; i++) {
			if (currentR.getInven()[i] != null) {
				temp[num] = currentR.getInven()[i];
				num++;
			}
		}
		Boolean only1 = true;
		for (int i = 0; i < num; i++) {
			if (secondW.compareTo(temp[i].getTag().getName()) == 0 && only1) {
				model.addInventory(temp[i]);
				t = true;
				temp[i] = null;
				only1 = false;
			}
		}
		for (int i = 0; i < currentR.getInven().length; i++) {
			currentR.getInven()[i] = temp[i];
		}
		return t;
	}
}
