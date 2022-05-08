package edu.ycp.cs320.groupProj.controller;

import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.model.Room;

import java.util.ArrayList;
import java.util.Random;

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
		model.addItemToInventory(thing);
		//Checks if the item is one of the gems
		if(thing.getName() == "Black Gem" || thing.getName() == "Green Gem" || thing.getName() == "Yellow Gem" 
				|| thing.getName() == "Red Gem" || thing.getName() == "Blue Gem" )
		{
			//player gets score equal to the gem's "thing" value, and then that value is set to 0
//			model.incrementScore(thing.getThing());
//			thing.setThing(0);
		}
	}
	public Boolean contains(String item) {
		Boolean thing = false;
		for(int i = 0; i < model.getInventory().size(); i++) {
			if(model.getInventory().get(i).getName().toLowerCase().equals(item)) {
				thing = true;
			}
		}
		return thing;
	}

	
	/**
	 * Grabs an item from the room and adds it to player inventory
	 * @param secondW		Item that player is trying to grab
	 * @param currentR		The room that player is in
	 * @return
	 */
	public Boolean grabItem(String secondW, Room currentR) {
		for(int i = 0; i < currentR.getInven().size(); i++) {
			if(currentR.getInven().get(i).getName().toLowerCase().equals(secondW)) {
				model.addItemToInventory(currentR.getInven().remove(i));
				return true;
			}
		}
		return false;
	}
	
	public void upScore(String thing) {
		thing = thing.toLowerCase();
		Random rand = new Random();
		int n = 0;
		switch(thing) {
		case "use":
			n = rand.nextInt(26);
			model.incrementScore(n);
		case "place":
			n = rand.nextInt(31);
			model.incrementScore(n);
		case "fight":
			n = rand.nextInt(101);
			model.incrementScore(n);
		case "light":
			model.incrementScore(1);
		case "move":
			n = rand.nextInt(6);
			model.incrementScore(n);
		case "inspect":
			n = rand.nextInt(16);
			model.incrementScore(n);
		case "look":
			n = rand.nextInt(11);
			model.incrementScore(n);
		}
	}
}
