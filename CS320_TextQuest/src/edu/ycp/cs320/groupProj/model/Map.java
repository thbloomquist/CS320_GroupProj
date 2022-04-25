package edu.ycp.cs320.groupProj.model;

public class Map {
	public Room[][] map;
	public int currentUp;
	public int currentSide;
	
	
	//This part of the code initializes the map
	public Map() 
	{
		this.reset();
	}
	
	public Room[][] getMap() 
	{
		return map;
	}
	public Room getRoom(int up, int side) 
	{
		return map[up][side];
	}
	
	public void reset() {
		map = new Room[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				map[i][j] = new Room(false, null, null, false);
			}
		}

		this.map[1][1] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false);
		this.map[1][2] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false);
		this.map[1][3] = new Room(true, new NameTag("Hallway", "Bla"), new ObjectModel[10], false);
		this.map[1][4] = new Room(true, new NameTag("Hallway", "Bla"), new ObjectModel[10], false);
		this.map[1][5] = new Room(true, new NameTag("Hallway", "Bla"), new ObjectModel[10], false);
		this.map[1][6] = new Room(true, new NameTag("Chest Room!", "Golden Arches"), new ObjectModel[10], false);
		// [1][7] is a wall
		this.map[1][8] = new Room(true, new NameTag("Monster/Hallway", "Claustrophobic"), new ObjectModel[10], true);
		// [1][9] is outside the dungeon

		// [2][0] is outside of dungeon
		this.map[2][1] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false);
		this.map[2][2] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false);
		// [2][3] is a wall
		// [2][4] is a wall
		// [2][5] is a wall
		// [2][6] is a wall
		// [2][7] is a wall
		this.map[2][8] = new Room(true, new NameTag("Hallway", "Dark"), new ObjectModel[10], false);
		// [2][9] is outside of dungeon

		// [3][0] is outside of dungeon
		this.map[3][1] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false);
		this.map[3][2] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false);
		this.map[3][3] = new Room(true, new NameTag("Hallway", "Enclosed"), new ObjectModel[10], false);
		this.map[3][4] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false);
		this.map[3][5] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false);
		this.map[3][6] = new Room(true, new NameTag("Hallway", "Restricting"), new ObjectModel[10], false);
		this.map[3][7] = new Room(true, new NameTag("Hallway", "Claustrophobic"), new ObjectModel[10], false);
		this.map[3][8] = new Room(true, new NameTag("Turn in Hallway", "Scary"), new ObjectModel[10], false);
		// [3][9] is outside of dungeon

		this.map[4][1] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false);
		this.map[4][2] = new Room(true, new NameTag("Key Room", "Shiny shiny shiny"), new ObjectModel[10], false);
		// [4][3] is a wall
		this.map[4][4] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false);
		this.map[4][5] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false);
		// [4][6] is a wall
		// [4][7] is a wall
		// [4][8] is a wall
		// [4][9] is outside of dungeon

		// [5][0] is outside of dungeon
		this.map[5][1] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false);
		this.map[5][2] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false);
		// [5][3] is a wall
		this.map[5][4] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false);
		this.map[5][5] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false);
		// [5][6] is a wall
		this.map[5][7] = new Room(true, new NameTag("Dark", "The fog blocks your vision"), new ObjectModel[10], false);
		this.map[5][8] = new Room(true, new NameTag("Dark", "The fog blocks your vision"), new ObjectModel[10], true);
		// [5][9] is outside of dungeon

		this.map[6][1] = new Room(true, new NameTag("Open", "Bla"), new ObjectModel[10], false);
		// [6][2] is a wall
		// [6][3] is a wall
		// [6][4] is a wall
		this.map[6][5] = new Room(true, new NameTag("Hallway", "Enclosed"), new ObjectModel[10], false);
		// [6][6] is a wall
		this.map[6][7] = new Room(true, new NameTag("Dark", "It's dark as FUCK"), new ObjectModel[10], true);
		this.map[6][8] = new Room(true, new NameTag("Dark", "Too dark to see"), new ObjectModel[10], false);

		// [7][0] is outside of dungeon
		this.map[7][1] = new Room(true, new NameTag("Tunnel", "Bla"), new ObjectModel[10], false);
		this.map[7][2] = new Room(true, new NameTag("Empty", "Bla"), new ObjectModel[10], false);
		this.map[7][3] = new Room(true, new NameTag("Starting Area", "dark & dreary"), new ObjectModel[10], false);
		// [7][4] is a wall
		this.map[7][5] = new Room(true, new NameTag("Thing", "Thing"), new ObjectModel[10], true);
		this.map[7][6] = new Room(true, new NameTag("Misty", "Thick ass fog"), new ObjectModel[10], false);
		this.map[7][7] = new Room(true, new NameTag("Misty", "Thick ass fog"), new ObjectModel[10], false);
		this.map[7][8] = new Room(true, new NameTag("Misty", "Thick ass fog"), new ObjectModel[10], false);
		// [7][9] is outside the dungeon

		// [8][1] is outside the dungeon
		this.map[8][1] = new Room(true, new NameTag("Monster", "Scary scary scary"), new ObjectModel[10], true);
		this.map[8][2] = new Room(true, new NameTag("Open", "Regular"), new ObjectModel[10], false);
		this.map[8][3] = new Room(true, new NameTag("Open", "Regular"), new ObjectModel[10], false);
		this.map[8][4] = new Room(true, new NameTag("Open", "Regular"), new ObjectModel[10], false);
		this.map[8][5] = new Room(true, new NameTag("Open", "Regular"), new ObjectModel[10], false);
		this.map[8][6] = new Room(true, new NameTag("Dark", "Foggy damn"), new ObjectModel[10], false);
		this.map[8][7] = new Room(true, new NameTag("Dark", "Foggy damn"), new ObjectModel[10], false);
		this.map[8][8] = new Room(true, new NameTag("Dark", "Foggy as hell"), new ObjectModel[10], false);
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

		// The Chest, 1000 represents the score the player will receive from opening the chest. 
		// TODO
		// Make the player get 1000 points from opening the chest.
		this.map[1][6].getInven()[1] = new ObjectModel(new NameTag("Chest", "Ornate, golden, and locked tight"), 1000, false);
		
		//The Gems, the player gets 200 points from picking them up for the first time
		//Once the player picks them up, the description changes to also say that the player's finger prints are on them
		//This description is used as the check to confirm if the player has picked them up before
		//The player gets a bonus to their score at the end of the game for collecting all 5 gems
		this.map[1][8].getInven()[1] = new ObjectModel(new NameTag("Red Gem", "Bright and shiny"), 200, true);
		this.map[7][5].getInven()[1] = new ObjectModel(new NameTag("Blue Gem", "Bright and shiny"), 200, true);
		this.map[1][1].getInven()[1] = new ObjectModel(new NameTag("Yellow Gem", "Bright and shiny"), 200, true);
		this.map[5][2].getInven()[1] = new ObjectModel(new NameTag("Green Gem", "Bright and shiny"), 200, true);
		this.map[5][7].getInven()[1] = new ObjectModel(new NameTag("White Gem", "Bright and shiny"), 200, true);
		
		//The key
		this.map[4][2].getInven()[1] = new ObjectModel(new NameTag("Key", "Ornate, golden, and looks like it could unlock something tight"), 0, true);
		
		// If the player tries to attack Kevin, he dodges and slaps them for 3 damage
		// Kevin is an NPC. This object in is here just so that you can inspect Kevin.
		// Kevin's Dialogue is handled in the Console Servlet.
		this.map[1][6].getInven()[2] = new ObjectModel(new NameTag("Kevin, The Raft Wizard", "Wearing a set of red robes and blue sunglasses"), 3, false);
		
		//Sets the starting room to be discovered at the beginning of the game.
		this.map[7][3].setDiscovered();
		
		// TODO
		// Add the key, crates, and gems to the proper rooms. 
		

	}

}