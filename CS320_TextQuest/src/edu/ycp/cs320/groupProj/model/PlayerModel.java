package edu.ycp.cs320.groupProj.model;

import java.util.ArrayList;

// model class for TextQuest
// keeps track of the moves, totaling North-South and East-West


public class PlayerModel {
	private int hp;
	private int upLoc;
	private int sideLoc;
	private ArrayList<ObjectModel> inventory; // arraylist for dynamically allocated size
	private int invenIndex;
	private Room currentRoom;
	private int score;
	private int matches;
	private Boolean isLit;
	private Boolean hasKey;
	private Boolean hardyMode; //TODO implement Hardy mode, currently just here for score multiplier
	//The NPC will have different dialogue based on whether or not the player has talked to them before
	
	public PlayerModel() {
		this.reset();
	}
	public void reset() {
		hp = 100;
		upLoc = 7; // replace these with intial values in 2d Array
		sideLoc = 3;
		inventory = new ArrayList<ObjectModel>(); 
		invenIndex = 0;
		score = 0;
		matches = 10;
		isLit = false;
		hasKey = false;
		hardyMode = false;
		createTorch();
		// fully resets values of current Player Model to their intended original states.
		// i.e. score starts at 0, health starts at 100, etc.
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
	
	/**
	 * Adds an item to player inventory
	 * @param thing		Item to add to inventory
	 */
	public void addItemToInventory(ObjectModel thing) {
		inventory.add(thing);
	}
	
	/**
	 * Removes an item at index 
	 * @param item		name of them to remove
	 * @return			item
	 */
	public ObjectModel removeItem(String item) {
		for(int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).getName().equals(item.toLowerCase())) {
				System.out.println("ITEM BEING REMOVED: " + inventory.get(i).getName());
				return inventory.remove(i);
			}
		}
		return null;
	}
	
	/**
	 * get Item from inventory based on name
	 * @param item
	 * @return
	 */
	public ObjectModel getItemFromInventory(String item) {
		for(int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).getName().equals(item.toLowerCase())) {
				System.out.println("ITEM BEING REMOVED: " + inventory.get(i).getName());
				return inventory.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Gets an item at index
	 * @param index		Value of item
	 * @return			Item at index
	 */
	public ObjectModel getItemFromInventory(int index) {
		return inventory.get(index);
	}
	
	/**
	 * Returns players inventory
	 * @return		Arraylist of objects in inventory
	 */
	public ArrayList<ObjectModel> getInventory() {
		return inventory;
		// returns a reference to the players entire inventory
	}
	
	public void setInventory(ArrayList<ObjectModel> inventory) {
		this.inventory = inventory;
	}
	public void setRoom(Room r) {
		currentRoom = r;
	}
	public Room getRoom() {
		return currentRoom;
	}
	public int getMatches() {
		return matches;
	}
	public void setMatches(int num) {
		matches = num;
	}
	public Boolean isLit() {
		return isLit;
	}
	public void setLit(Boolean t) {
		isLit = t;
	}
	public void setLit2() {
		isLit = true;
	}
	public void createTorch() {
		NameTag n = new NameTag("torch", "It's a piece of wood with oil-covered cloth wrapped on the tip.");
		ObjectModel torch = new ObjectModel(n, false);
		addItemToInventory(torch);
	}
	public int getScore() {
		return score;
	}
	public void setScore(int n) {
		score = n;
	}
	public void incrementScore(int n) {
		if(hardyMode)
		{
			score += n*2;
		}
		else
		{
			score += n;
		}
	}
	public Boolean getHasKey() {
		return hasKey;
	}
	public void setHasKey(Boolean hasKey) {
		this.hasKey = hasKey;
	}

	
	public Boolean getHardy()
	{
		return hardyMode;
	}
	public void setHardy(boolean hardy)
	{
		this.hardyMode = hardy;
	}
}
