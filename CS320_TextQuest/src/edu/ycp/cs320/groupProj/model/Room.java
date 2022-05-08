package edu.ycp.cs320.groupProj.model;

import java.util.ArrayList;

public class Room {
	private boolean canEnter;
	private NameTag roomDesc;
	private ArrayList<ObjectModel> contents = new ArrayList<ObjectModel>();
	private Boolean hasMonster;
	private Monster m;
	private Boolean hasChest;
	private Boolean isDark = true; //set to false for testing
	private String ceiling = null;
	private String floor = null;
	private Boolean discovered = false; //Begins false, sets to true when a room is discovered
	
	//The boolean value refers to whether or not this room can be entered or not.
	//If it's true, you can enter this room
	//If it's false, you cannot enter this room
	public Room(boolean canEnter, NameTag room, ArrayList<ObjectModel> contents) {
		this.canEnter = canEnter;
		roomDesc = room;
		this.contents = contents;
	}
	
	//Second boolean is if room has monsters
	//Third boolean is if room has items
	public Room(boolean canEnter, NameTag room, ArrayList<ObjectModel> contents, Boolean t, Boolean f) {
		this.canEnter = canEnter;
		roomDesc = room;
		this.contents = contents;
		hasMonster = t;
		if(hasMonster) {
			makeMonster();
		}
		if(f) {
			createContents1();
			System.out.println(contents.get(0).getName());
		}
		// if f, room has items in it
		hasChest = false;
		isDark = false;
	}
	public Boolean hasMonster() {
		return hasMonster;
	}
	public void makeMonster() {
		m = new Monster();
		checkMonster();
	}
	public void checkMonster() {
		if(m.getAlive()) {
			hasMonster = true;
		} else {
			hasMonster = false;
		}
	}
	
	//where does this get called?
	public void deadMonster() {
		hasMonster = false;
		m.setAlive(false);
		m.setHP(0);
	}
	//Returns the boolean
	public boolean getEnter() {
		return canEnter;
	}
	//Returns the room's nametag
	public NameTag getTag() {
		return roomDesc;
	}
	/**
	 * Get ArrayList of Inventory
	 * @return	rooms inventory
	 */
	public ArrayList<ObjectModel> getInven() {
		return contents;
	}
	/**
	 * Sets the contents of the room
	 * @param contents
	 */
	public void setInventory(ArrayList<ObjectModel> contents) {
		this.contents = contents;
	}
	
	/**
	 * retrieves an item from the inventory if its there
	 * @param item	The item you're searching for
	 * @return
	 */
	public ObjectModel getItemFromInventory(String item) {
		for(int i = 0; i < contents.size(); i++) {
			if(contents.get(i).getName().equals(item.toLowerCase())) {
				return contents.get(i);
			}
		}
		return null;
	}
	
	
	
	public String printInven() {
		String s = "";
		for(int i = 0; i < contents.size(); i++) {
			if(contents.get(i) != null) {
				s += contents.toString() + ", ";
			}
		}
		return s;
	}
	public Monster getMonster() {
		return m;
	}
	
	public void createContents1() {
		NameTag n = new NameTag("crate", "an old wooden crate");
		contents.add(new ObjectModel(n, false));
		
//		for(int i = 0; i < contents.size(); i++) {
//			if(contents.get(i) == null) {
//				contents.set(i, new ObjectModel(n, 5, false)); 
//				i = contents.size() + 1;
//			}
//		}
	}
	public boolean checkEmpty() {
		for(int i = 0; i < contents.size(); i++) {
			if(contents.get(i) != null) {
				return false;
			}
		}
		return true;
	}
	public Boolean hasChest() {
		return hasChest;
	}
	public void setChest(Boolean t) {
		hasChest = t;
	}
	public Boolean isDark() {
		return isDark;
	}
	public void setDark(Boolean t) {
		isDark = t;
	}
	public void createKey() {
		NameTag tempr = new NameTag("Key", "It's a shiny golden key, damn it's sparkly.");
		ObjectModel thing = new ObjectModel(tempr, true);
		contents.set(7, thing);
	}
	public void setCel(String thing) {
		ceiling = thing;
	}
	public String getCel() {
		return ceiling;
	}
	public void setFloor(String thing) {
		floor = thing;
	}
	public String getFloor() {
		return floor;
	}
	public String getName()
	{
		return this.getTag().getName();
	}
	public String getDesc()
	{
		return this.getTag().getName();
	}
	public boolean getDiscovered()
	{
		return this.discovered;
	}
	public void setDiscovered(PlayerModel pModel)
	{
		pModel.incrementScore(10);
		this.discovered = true;
	}
	//Checks if the room contains a specific item
	//Set isName to true to search by name, and isName to false to search by description.
	public Boolean searchObject(boolean isName, String string)
	{
		for(int i = 0; i < contents.size(); i++) {
			if(contents.get(i) != null) {
				if(contents.get(i).getName() == string && isName) {
					return true;
				}
				if(contents.get(i).getDesc() == string && isName == false) {
					return true;
				}
			}
		}
		return false;
	}
	public int getObjectIndex(boolean isName, String string)
	{
		for(int i = 0; i < contents.size(); i++) {
			if(contents.get(i) != null) {
				if(contents.get(i).getName() == string && isName) {
					return i;
				}
				if(contents.get(i).getDesc() == string && isName == false) {
					return i;
				}
			}
		}
		return 999;
	}
}