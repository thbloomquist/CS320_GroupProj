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
//		Boolean t  = false;
//		for(int i = 0; i < model.getInvenFULL().length; i++) {
//			if(model.getInvenFULL()[i] != null) {
//				if(model.getInvenFULL()[i].getTag().getName().toLowerCase().equals(item.toLowerCase())) {
//					t = true;
//				}
//			}
//		}
//		return t;
//	}
//	public int sortInven(ObjectModel[] inven) {
//		// this function serves two purposes - returning the new iNum & sorting the players inventory so the iNum is accurate.
//		int num = 0; 
//		ObjectModel[] tempr = new ObjectModel[inven.length];
//		for(int i = 0; i < inven.length; i++) {
//			if(inven[i] != null) {
//				tempr[num] = inven[i];
//				num++;
//			}
//		}
//		model.setInvenFULL(tempr);
//		
//		return num;
//		
//	}
	
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
//		int num = 0;
//		for (int i = 0; i < currentR.getInven().length; i++) {
//			if (currentR.getInven()[i] != null) {
//				temp[num] = currentR.getInven()[i];
//				num++;
//			}
//		}
//		Boolean only1 = true;
//		for (int i = 0; i < num; i++) {
//			if (secondW.toLowerCase().compareTo(temp[i].getTag().getName().toLowerCase()) == 0 && only1) {
//				model.addInventory(temp[i]);
//				t = true;
//				temp[i] = null;
//				only1 = false;
//			}
//		}
//		for (int i = 0; i < currentR.getInven().length; i++) {
//			currentR.getInven()[i] = temp[i];
//		}
//		return t;
	}
	
//	public String placeItem(String secondW, Room currentR) {
//		String retSult = "";
//		ObjectModel tempr = null;
//		Boolean only1 = true;
//		for (int l = 0; l < model.getInventory().length; l++) {
//			if (model.getItemFromInventory(l) != null) {
//				if (model.getItemFromInventory(l).getName().toLowerCase()
//						.equals(secondW) && only1) {
//					tempr = model.getItemFromInventory(l);
//					only1 = false;
//					model.getInventory()[l] = null;
//					// this should return a reference to the first index of an item that contains
//					// the same name as the input
//				}
//			}
//		}
//		int temp = -1;
//		Boolean firstI = true;
//		for (int i = 0; i < currentR.getInven().size(); i++) {
//			if (currentR.getInven().get(i) == null && firstI) {
//				temp = i;
//				firstI = false;
//				// this should return the first index that is "open" for an item input
//			}
//		}
//		if (temp == -1) {
//			retSult = "The room is full.";
//		} else {
//			currentR.getInven().set(temp, tempr);
////			int NEWINUM = sortInven(model.getInvenFULL());
////			model.setiNum(NEWINUM);
//			retSult = "You placed the " + secondW + " on the floor.";
//		}
//		return retSult;
//	}
//	public String useItem(String secondW, Room CurrentR) {
//		String result = "";
//		Boolean j1 = true;
//		ObjectModel temp = null;
//		for (int i = 0; i < model.getInventory().length; i++) {
//			if (model.getInventory()[i] != null) {
//				if (model.getItemFromInventory(i).getTag().getName().toLowerCase()
//						.equals(secondW) && j1) {
//					temp = model.getItemFromInventory(i);
//					model.removeItem(i);
//					j1 = false;
//					//UPDATE DATABASE HERE - REMOVE ITEM FROM DB
//				}
//			}
//		}
//		int NEWINUM = sortInven(model.getInvenFULL());
//		model.setiNum(NEWINUM);
//		model.setHP(model.getHP() + temp.getThing());
//		result = "You used the " + temp.getTag().getName();
//		return result;
//	}
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
