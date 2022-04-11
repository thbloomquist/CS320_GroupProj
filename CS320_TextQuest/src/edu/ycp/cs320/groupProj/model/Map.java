package edu.ycp.cs320.groupProj.model;
public class Map {
	public Room[][] map;
	public int currentUp;
	public int currentSide;
	
	
	//This part of the code initializes the map
	public Map() {
		map = new Room[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				map[i][j] = new Room(false, null, null);
			}
		}
		
		this.map[1][1] = new Room(true, new NameTag("Monster","plain"), new ObjectModel[10], true, false);
		this.map[2][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[3][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[4][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[5][1] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, true);
		this.map[6][1] = new Room(true, new NameTag("Chest", "plain"), new ObjectModel[10], false, false);
		this.map[8][1] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, true);
		
		this.map[2][2] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[8][2] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		
		this.map[1][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[2][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[3][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[4][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[5][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[6][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[7][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[8][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		
		this.map[2][4] = new Room(true, new NameTag("Key", "plain"), new ObjectModel[10], false, false);
		this.map[4][4] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[5][4] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, false);

		this.map[1][5] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[2][5] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[4][5] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[5][5] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, false);
		this.map[7][5] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[8][5] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, false);
		
		this.map[1][6] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[5][6] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[7][6] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, false);
		this.map[8][6] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		
		this.map[1][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[2][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[3][7] = new Room(true, new NameTag("Player Start", "plain"), new ObjectModel[10], false, false);
		this.map[5][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[6][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[7][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[8][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		
		this.map[1][8] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, false);
		this.map[2][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[3][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[4][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[5][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[8][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		
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
		this.map[1][1] = new Room(true, new NameTag("Monster","plain"), new ObjectModel[10]);
		this.map[2][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[3][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[4][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[5][1] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10]);
		this.map[6][1] = new Room(true, new NameTag("Chest", "plain"), new ObjectModel[10]);
		this.map[8][1] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10]);
		
		this.map[2][2] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[8][2] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		
		this.map[1][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[2][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[3][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[4][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[5][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[6][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[7][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[8][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		
		this.map[2][4] = new Room(true, new NameTag("Key", "plain"), new ObjectModel[10]);
		this.map[4][4] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[5][4] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10]);

		this.map[1][5] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[2][5] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[4][5] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[5][5] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10]);
		this.map[7][5] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[8][5] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10]);
		
		this.map[1][6] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[5][6] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[7][6] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10]);
		this.map[8][6] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		
		this.map[1][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[2][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[3][7] = new Room(true, new NameTag("Player Start", "plain"), new ObjectModel[10]);
		this.map[5][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[6][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[7][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[8][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		
		this.map[1][8] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10]);
		this.map[2][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[3][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[4][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[5][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
		this.map[8][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10]);
	}
	
	
	public Room[][] getMap() {
		return map;
	}
	public Room getRoom(int up, int side) {
		return map[up][side];
	}
	
	public void reset() {
		map = new Room[10][10];
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				map[i][j] = new Room(false, null, null);
			}
		}
		
		
		this.map[1][1] = new Room(true, new NameTag("Monster","plain"), new ObjectModel[10], true, false);
		this.map[2][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[3][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[4][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[5][1] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, true);
		this.map[6][1] = new Room(true, new NameTag("Chest", "plain"), new ObjectModel[10], false, false);
		this.map[8][1] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, true);
		
		this.map[2][2] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[8][2] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		
		this.map[1][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[2][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[3][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[4][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[5][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[6][1] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[7][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		this.map[8][3] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, true);
		
		this.map[2][4] = new Room(true, new NameTag("Key", "plain"), new ObjectModel[10], false, false);
		this.map[4][4] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[5][4] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, false);

		this.map[1][5] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[2][5] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[4][5] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[5][5] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, false);
		this.map[7][5] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[8][5] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, false);
		
		this.map[1][6] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[5][6] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[7][6] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, false);
		this.map[8][6] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		
		this.map[1][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[2][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[3][7] = new Room(true, new NameTag("Player Start", "plain"), new ObjectModel[10], false, false);
		this.map[5][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[6][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[7][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[8][7] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		
		this.map[1][8] = new Room(true, new NameTag("Monster", "plain"), new ObjectModel[10], true, false);
		this.map[2][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[3][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[4][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[5][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
		this.map[8][8] = new Room(true, new NameTag("Empty", "plain"), new ObjectModel[10], false, false);
	}
	
	
}