package edu.ycp.cs320.groupProj.model;

public class Map {
	public Room[][] map;
	public int currentUp;
	public int currentSide;

	// need to add setDark(true) to the parts that are dark
	// and setChest(true) to the chest room
	// and setKey(true) to the key room
	// This part of the code initializes the map
	public Map() {
		map = new Room[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				map[i][j] = new Room(false, null, null, false, false);
			}
		}

		// ok so i messed up and basically it goes map[playerUp][playerSide] so its
		// actually map[y][x]
		// and i looked over the logic within the Servlet and it'd actually take more
		// work to change that entirely so i just re-did the map
		// sooorrry
		// [1][0] is outside of dungeon
		this.map[1][1] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false, false);
		this.map[1][2] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false, false);
		this.map[1][3] = new Room(true, new NameTag("Hallway", "Bla"), new ObjectModel[10], false, false);
		this.map[1][4] = new Room(true, new NameTag("Hallway", "Bla"), new ObjectModel[10], false, false);
		this.map[1][5] = new Room(true, new NameTag("Hallway", "Bla"), new ObjectModel[10], false, false);
		this.map[1][6] = new Room(true, new NameTag("Chest Room!", "Golden Arches"), new ObjectModel[10], false, false);
		// [1][7] is a wall
		this.map[1][8] = new Room(true, new NameTag("Monster/Hallway", "Claustrophobia"), new ObjectModel[10], true,
				true);
		// [1][9] is outside the dungeon

		// [2][0] is outside of dungeon
		this.map[2][1] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false, false);
		this.map[2][2] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false, false);
		// [2][3] is a wall
		// [2][4] is a wall
		// [2][5] is a wall
		// [2][6] is a wall
		// [2][7] is a wall
		this.map[2][8] = new Room(true, new NameTag("Hallway", "Dark"), new ObjectModel[10], false, false);
		// [2][9] is outside of dungeon

		// [3][0] is outside of dungeon
		this.map[3][1] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[3][2] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[3][3] = new Room(true, new NameTag("Hallway", "Enclosed"), new ObjectModel[10], false, false);
		this.map[3][4] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[3][5] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[3][6] = new Room(true, new NameTag("Hallway", "Restricting"), new ObjectModel[10], false, false);
		this.map[3][7] = new Room(true, new NameTag("Hallway", "Claustrophobic"), new ObjectModel[10], false, false);
		this.map[3][8] = new Room(true, new NameTag("Turn in Hallway", "Scary"), new ObjectModel[10], false, false);
		// [3][9] is outside of dungeon

		this.map[4][1] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[4][2] = new Room(true, new NameTag("Key Room", "Shiny shiny shiny"), new ObjectModel[10], false,
				false);
		// [4][3] is a wall
		this.map[4][4] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[4][5] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		// [4][6] is a wall
		// [4][7] is a wall
		// [4][8] is a wall
		// [4][9] is outside of dungeon

		// [5][0] is outside of dungeon
		this.map[5][1] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[5][2] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		// [5][3] is a wall
		this.map[5][4] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[5][5] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		// [5][6] is a wall
		this.map[5][7] = new Room(true, new NameTag("Dark", "The fog blocks your vision"), new ObjectModel[10], false,
				false);
		this.map[5][8] = new Room(true, new NameTag("Dark", "The fog blocks your vision"), new ObjectModel[10], true,
				true);
		// [5][9] is outside of dungeon

		this.map[6][1] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		// [6][2] is a wall
		// [6][3] is a wall
		// [6][4] is a wall
		this.map[6][5] = new Room(true, new NameTag("Hallway", "Enclosed"), new ObjectModel[10], false, false);
		// [6][6] is a wall
		this.map[6][7] = new Room(true, new NameTag("Dark", "It's dark as FUCK"), new ObjectModel[10], true, true);
		this.map[6][8] = new Room(true, new NameTag("Dark", "Too dark to see"), new ObjectModel[10], false, true);

		// [7][0] is outside of dungeon
		this.map[7][1] = new Room(true, new NameTag("Tunnel", "Bla"), new ObjectModel[10], false, true);
		this.map[7][2] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false, true);
		this.map[7][3] = new Room(true, new NameTag("Starting Area", "dark & dreary"), new ObjectModel[10], false,
				false);
		// [7][4] is a wall
		this.map[7][5] = new Room(true, new NameTag("Thing", "Thing"), new ObjectModel[10], true, true);
		this.map[7][6] = new Room(true, new NameTag("Misty", "Thick ass fog"), new ObjectModel[10], false, true);
		this.map[7][7] = new Room(true, new NameTag("Misty", "Thick ass fog"), new ObjectModel[10], false, true);
		this.map[7][8] = new Room(true, new NameTag("Misty", "Thick ass fog"), new ObjectModel[10], false, true);
		// [7][9] is outside the dungeon

		// [8][1] is outside the dungeon
		this.map[8][1] = new Room(true, new NameTag("Monster", "Scary scary scary"), new ObjectModel[10], true, true);
		this.map[8][2] = new Room(true, new NameTag("Open", "Regular"), new ObjectModel[10], false, true);
		this.map[8][3] = new Room(true, new NameTag("Open", "Regular"), new ObjectModel[10], false, true);
		this.map[8][4] = new Room(true, new NameTag("Open", "Regular"), new ObjectModel[10], false, true);
		this.map[8][5] = new Room(true, new NameTag("Open", "Regular"), new ObjectModel[10], false, true);
		this.map[8][6] = new Room(true, new NameTag("Dark", "Foggy damn"), new ObjectModel[10], false, true);
		this.map[8][7] = new Room(true, new NameTag("Dark", "Foggy damn"), new ObjectModel[10], false, true);
		this.map[8][8] = new Room(true, new NameTag("Dark", "Foggy as hell"), new ObjectModel[10], false, true);
		// [8][9] is outside the dungeon

		// top dark section ==
		this.map[1][8].setDark(true);
		this.map[2][8].setDark(true);
		this.map[3][8].setDark(true);
		this.map[3][7].setDark(true);
		this.map[3][6].setDark(true);

		// bottom dark section ==
		this.map[5][7].setDark(true);
		this.map[5][8].setDark(true);
		this.map[6][7].setDark(true);
		this.map[6][8].setDark(true);
		this.map[7][6].setDark(true);
		this.map[7][7].setDark(true);
		this.map[7][8].setDark(true);
		this.map[8][6].setDark(true);
		this.map[8][7].setDark(true);
		this.map[8][8].setDark(true);
	}

	public Room[][] getMap() {
		return map;
	}

	public Room getRoom(int up, int side) {
		return map[up][side];
	}

	public void reset() {
		map = new Room[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				map[i][j] = new Room(false, null, null);
			}
		}

		this.map[1][1] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false, false);
		this.map[1][2] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false, false);
		this.map[1][3] = new Room(true, new NameTag("Hallway", "Bla"), new ObjectModel[10], false, false);
		this.map[1][4] = new Room(true, new NameTag("Hallway", "Bla"), new ObjectModel[10], false, false);
		this.map[1][5] = new Room(true, new NameTag("Hallway", "Bla"), new ObjectModel[10], false, false);
		this.map[1][6] = new Room(true, new NameTag("Chest Room!", "Golden Arches"), new ObjectModel[10], false, false);
		// [1][7] is a wall
		this.map[1][8] = new Room(true, new NameTag("Monster/Hallway", "Claustrophobia"), new ObjectModel[10], true,
				true);
		// [1][9] is outside the dungeon

		// [2][0] is outside of dungeon
		this.map[2][1] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false, false);
		this.map[2][2] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false, false);
		// [2][3] is a wall
		// [2][4] is a wall
		// [2][5] is a wall
		// [2][6] is a wall
		// [2][7] is a wall
		this.map[2][8] = new Room(true, new NameTag("Hallway", "Dark"), new ObjectModel[10], false, false);
		// [2][9] is outside of dungeon

		// [3][0] is outside of dungeon
		this.map[3][1] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[3][2] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[3][3] = new Room(true, new NameTag("Hallway", "Enclosed"), new ObjectModel[10], false, false);
		this.map[3][4] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[3][5] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[3][6] = new Room(true, new NameTag("Hallway", "Restricting"), new ObjectModel[10], false, false);
		this.map[3][7] = new Room(true, new NameTag("Hallway", "Claustrophobic"), new ObjectModel[10], false, false);
		this.map[3][8] = new Room(true, new NameTag("Turn in Hallway", "Scary"), new ObjectModel[10], false, false);
		// [3][9] is outside of dungeon

		this.map[4][1] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[4][2] = new Room(true, new NameTag("Key Room", "Shiny shiny shiny"), new ObjectModel[10], false,
				false);
		// [4][3] is a wall
		this.map[4][4] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[4][5] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		// [4][6] is a wall
		// [4][7] is a wall
		// [4][8] is a wall
		// [4][9] is outside of dungeon

		// [5][0] is outside of dungeon
		this.map[5][1] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[5][2] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		// [5][3] is a wall
		this.map[5][4] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		this.map[5][5] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		// [5][6] is a wall
		this.map[5][7] = new Room(true, new NameTag("Dark", "The fog blocks your vision"), new ObjectModel[10], false,
				false);
		this.map[5][8] = new Room(true, new NameTag("Dark", "The fog blocks your vision"), new ObjectModel[10], true,
				true);
		// [5][9] is outside of dungeon

		this.map[6][1] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false, false);
		// [6][2] is a wall
		// [6][3] is a wall
		// [6][4] is a wall
		this.map[6][5] = new Room(true, new NameTag("Hallway", "Enclosed"), new ObjectModel[10], false, false);
		// [6][6] is a wall
		this.map[6][7] = new Room(true, new NameTag("Dark", "It's dark as FUCK"), new ObjectModel[10], true, true);
		this.map[6][8] = new Room(true, new NameTag("Dark", "Too dark to see"), new ObjectModel[10], false, true);

		// [7][0] is outside of dungeon
		this.map[7][1] = new Room(true, new NameTag("Tunnel", "Bla"), new ObjectModel[10], false, true);
		this.map[7][2] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false, true);
		this.map[7][3] = new Room(true, new NameTag("Starting Area", "dark & dreary"), new ObjectModel[10], false,
				false);
		// [7][4] is a wall
		this.map[7][5] = new Room(true, new NameTag("Thing", "Thing"), new ObjectModel[10], true, true);
		this.map[7][6] = new Room(true, new NameTag("Misty", "Thick ass fog"), new ObjectModel[10], false, true);
		this.map[7][7] = new Room(true, new NameTag("Misty", "Thick ass fog"), new ObjectModel[10], false, true);
		this.map[7][8] = new Room(true, new NameTag("Misty", "Thick ass fog"), new ObjectModel[10], false, true);
		// [7][9] is outside the dungeon

		// [8][1] is outside the dungeon
		this.map[8][1] = new Room(true, new NameTag("Monster", "Scary scary scary"), new ObjectModel[10], true, true);
		this.map[8][2] = new Room(true, new NameTag("Open", "Regular"), new ObjectModel[10], false, true);
		this.map[8][3] = new Room(true, new NameTag("Open", "Regular"), new ObjectModel[10], false, true);
		this.map[8][4] = new Room(true, new NameTag("Open", "Regular"), new ObjectModel[10], false, true);
		this.map[8][5] = new Room(true, new NameTag("Open", "Regular"), new ObjectModel[10], false, true);
		this.map[8][6] = new Room(true, new NameTag("Dark", "Foggy damn"), new ObjectModel[10], false, true);
		this.map[8][7] = new Room(true, new NameTag("Dark", "Foggy damn"), new ObjectModel[10], false, true);
		this.map[8][8] = new Room(true, new NameTag("Dark", "Foggy as hell"), new ObjectModel[10], false, true);
		// [8][9] is outside the dungeon

		// top dark section ==
		this.map[1][8].setDark(true);
		this.map[2][8].setDark(true);
		this.map[3][8].setDark(true);
		this.map[3][7].setDark(true);
		this.map[3][6].setDark(true);

		// bottom dark section ==
		this.map[5][7].setDark(true);
		this.map[5][8].setDark(true);
		this.map[6][7].setDark(true);
		this.map[6][8].setDark(true);
		this.map[7][6].setDark(true);
		this.map[7][7].setDark(true);
		this.map[7][8].setDark(true);
		this.map[8][6].setDark(true);
		this.map[8][7].setDark(true);
		this.map[8][8].setDark(true);
		
		
		// chest :_)
		this.map[1][6].setChest(true);
	}

}