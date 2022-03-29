package edu.ycp.cs320.groupProj.model;


// model class for TextQuest
// keeps track of the moves, totaling North-South and East-West


public class PlayerModel {
	public int hp;
	public int upLoc;
	public int sideLoc;
	public ObjectModel[] inventory;
	int invenIndex = 0;
	public Room currentRoom;
	
	public PlayerModel() {
		hp = 100;
		upLoc = 1;
		sideLoc = 2;
		inventory = new ObjectModel[10];
		invenIndex = 0;
		
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
	
	
	
}
