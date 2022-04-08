package edu.ycp.cs320.groupProj.model;

public class Room {
	public boolean canEnter;
	public NameTag roomDesc;
	public ObjectModel[] contents = new ObjectModel[10];
	public Boolean hasMonster;
	public Monster m;
	public Boolean empty;
	
	//The boolean value refers to whether or not this room can be entered or not.
	//If it's true, you can enter this room
	//If it's false, you cannot enter this room
	public Room(boolean canEnter, NameTag room, ObjectModel[] contents) {
		this.canEnter = canEnter;
		roomDesc = room;
		this.contents = contents;
	}
	
	//Second boolean is if room has monsters
	//Third boolean is if room has items
	public Room(boolean canEnter, NameTag room, ObjectModel[] contents, Boolean t, Boolean f) {
		this.canEnter = canEnter;
		roomDesc = room;
		this.contents = contents;
		hasMonster = t;
		empty = true;
		if(hasMonster) {
			makeMonster();
		}
		if(f) {
			createContents1();
			empty = false;
		}
		// if f, room has items in it
		
	}
	public Boolean hasMonster() {
		return this.hasMonster;
	}
	public void makeMonster() {
		m = new Monster();
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
	//Returns the room's inventory
	public ObjectModel[] getInven() {
		return contents;
	}
	public String printInven() {
		String s = "";
		for(int i = 0; i < contents.length; i++) {
			if(contents[i] != null) {
				s += contents.toString() + ", ";
			}
		}
		return s;
	}
	public Monster getMonster() {
		return m;
	}
	
	public void createContents1() {
		NameTag n = new NameTag("banana", "a yellow banana");
		for(int i = 0; i < 10; i++) {
			if(i == 0 || i == 3) {
				contents[i] = new ObjectModel(n, 5, false);
			}
		}
	}
	public Boolean isEmpty() {
		return empty;
	}
	public void checkEmpty() {
		boolean n = false;
		for(int i = 0; i < contents.length; i++) {
			if(contents[i] != null) {
				n = true;
			}
		}
		if(n) {
			empty = false;
		} else {
			empty = true;
		}
	}
}