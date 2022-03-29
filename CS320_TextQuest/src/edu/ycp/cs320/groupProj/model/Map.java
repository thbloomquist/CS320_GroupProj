package edu.ycp.cs320.groupProj.model;
public class Map {
	public Room[][] map;
	public int currentUp;
	public int currentSide;
	
	
	//This part of the code initializes the map
	public Map() {
		map = new Room[5][5];
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				map[i][j] = new Room(false, null, null);
			}
		}
		
		
		this.map[1][2] = new Room(true, new NameTag("Start Room", "plain."), new ObjectModel[10], false, false);
		this.map[2][2] = new Room(true, new NameTag("Item Room", "open."), new ObjectModel[10], false, true);
		this.map[2][1] = new Room(true, new NameTag("Chest Room", "decadent."), new ObjectModel[10], false, false);
		this.map[2][3] = new Room(true, new NameTag("Key Room", "desc."), new ObjectModel[10], false, false);
		this.map[3][2] = new Room(true, new NameTag("Monster Room", "scary."), new ObjectModel[10], true, true);
	}
	
	
	public Map(Room[][] map) {
		this.map = map;
		
		//This for loop fills the entire map with empty rooms
		//These rooms cannot be entered and act as empty space
		//These rooms also act as borders, preventing players from going out of bounds
		//Basically, instead of the test map being 3x3, it's actually 5x5 for the border
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				this.map[i][j] = new Room(false, null, null);
			}
		}
		//Insert placement of rooms here
		//Remember to draw a map before placing rooms
		//Consider the border referred to in the previous comments
		//Example:
		//this.map[1][2] = new Room(true, new NameTag("name", "desc"), new Item[10]);
		this.map[1][2] = new Room(true, new NameTag("Start Room", "plain"), new ObjectModel[10]);
		this.map[2][2] = new Room(true, new NameTag("Center Room", "open"), new ObjectModel[10]);
		this.map[2][1] = new Room(true, new NameTag("Chest Room", "decadent"), new ObjectModel[10]);
		this.map[2][3] = new Room(true, new NameTag("Key Room", "desc"), new ObjectModel[10]);
		this.map[3][2] = new Room(true, new NameTag("Monster Room", "scary"), new ObjectModel[10]);
	}
	
	
	public Room[][] getMap() {
		return map;
	}
	public Room getRoom(int up, int side) {
		return map[up][side];
	}
	
	public void reset() {
		map = new Room[5][5];
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				map[i][j] = new Room(false, null, null);
			}
		}
		
		
		this.map[1][2] = new Room(true, new NameTag("Start Room", "plain."), new ObjectModel[10], false, false);
		this.map[2][2] = new Room(true, new NameTag("Item Room", "open."), new ObjectModel[10], false, true);
		this.map[2][1] = new Room(true, new NameTag("Chest Room", "decadent."), new ObjectModel[10], false, false);
		this.map[2][3] = new Room(true, new NameTag("Key Room", "desc."), new ObjectModel[10], false, false);
		this.map[3][2] = new Room(true, new NameTag("Monster Room", "scary."), new ObjectModel[10], true, true);
	}
	
	
}