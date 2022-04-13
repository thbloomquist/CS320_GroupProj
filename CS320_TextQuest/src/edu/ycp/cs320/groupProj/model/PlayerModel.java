package edu.ycp.cs320.groupProj.model;


// model class for TextQuest
// keeps track of the moves, totaling North-South and East-West


public class PlayerModel {
	private int hp;
	private int upLoc;
	private int sideLoc;
	private ObjectModel[] inventory;
	private int invenIndex = 0;
	private Room currentRoom;
	private int score;
	
	//"upLoc" and "sideLoc" are used to find the player's location on the map
	
	//"score" represents the player's current score
	//TODO implement the following 
	//Score begins at 0, but increases when the player...
	//enters a new room, kills a monster, picks up treasure, and opens the chest
	//The score is then further increased based on how fast the player won
	
	public PlayerModel() {
		hp = 100;
		upLoc = 7;
		sideLoc = 3;
		inventory = new ObjectModel[10];
		invenIndex = 0;
		score = 0;
	}
	public void reset() {
		hp = 100;
		upLoc = 0; // replace these with intial values in 2d Array
		sideLoc = 0;
		for(int i = 0; i < inventory.length-1; i++) {
			inventory[i] = null;
		}
		invenIndex = 0;
		upLoc = 1;
		sideLoc = 2;
		score = 0;
		// currently initial starting room is [2][2]
	}
	
	public void setLocation(int up, int side) {
		upLoc = up;
		sideLoc = side;
	}
	public void setSideLoc(int s) {
		sideLoc = s;
	}
	public void setUpLoc(int u) {
		upLoc = u;
	}
	public void setHP(int n) {
		hp = n;
	}
	public int getHP() {
		return hp;
	}
	
	public int getUp() {
		return upLoc;
	}
	public int getSide() {
		return sideLoc;
	}
	public void addInventory(ObjectModel thing) {
		inventory[invenIndex] = thing;
		invenIndex++;
	}
	public void removeItem(int n) {
		inventory[n] = null;
	}
	public int getiNum() {
		return invenIndex;
	}
	public void setiNum(int n) {
		invenIndex = n;
	}
	public ObjectModel getInventory(int n) {
		return inventory[n];
	}
	public ObjectModel[] getInvenFULL() {
		return inventory;
	}
	public void setRoom(Room r) {
		currentRoom = r;
	}
	public Room getRoom() {
		return currentRoom;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int n) {
		score = n;
	}
	//Increases the score by this amount
	public void addScore(int n) {
		score = score + n;
	}
}
