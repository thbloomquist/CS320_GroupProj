package edu.ycp.cs320.groupProj.model;


// model class for TextQuest
// keeps track of the moves, totaling North-South and East-West


public class PlayerModel {
	private int hp;
	private int upLoc;
	private int sideLoc;
	private ObjectModel[] inventory;
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
		inventory = new ObjectModel[10];
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
	public void addInventory(ObjectModel thing) {
		inventory[invenIndex] = thing;
		invenIndex++;
		// adds an item to the players inventory
	}
	public void removeItem(int n) {
		inventory[n] = null;
		// removes an item, given the index in which it resides
	}
	public int getiNum() {
		return invenIndex;
	}
	public void setiNum(int n) {
		invenIndex = n;
	}
	public ObjectModel getInventory(int n) {
		return inventory[n];
		// returns a reference to a single ObjectModel within the players inventory
	}
	public ObjectModel[] getInvenFULL() {
		return inventory;
		// returns a reference to the players entire inventory
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
	public void createTorch() {
		NameTag n = new NameTag("torch", "It's a piece of wood with oil-covered cloth wrapped on the tip.");
		ObjectModel torch = new ObjectModel(n, -1, false);
		addInventory(torch);
	}
	public void setInvenFULL(ObjectModel[] temporary) {
		inventory = temporary;
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
	//Checks if a specific item is in the player's inventory
	//Set isName to true to search by name, and isName to false to search by description.
	public Boolean searchObject(boolean isName, String string)
	{
		for(int i = 0; i < inventory.length; i++) {
			if(inventory[i] != null) {
				if(inventory[i].getName() == string && isName) {
					return true;
				}
				if(inventory[i].getDesc() == string && isName == false) {
					return true;
				}
			}
		}
		return false;
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
