package edu.ycp.cs320.groupProj.model;


// model class for TextQuest
// keeps track of the moves, totaling North-South and East-West


public class Monster {
	private int hp;
	private Room currentRoom;
	private int damage;
	private NameTag n;
	private Boolean alive;
	private int score;
	
	//"hp" and "damage" refers to the monster's stats
	//The boolean refers to whether or not the monster is alive
	
	//"score" refers to the amount of points the player earns from killing the monster
	//At the moment score is equal to the monster's damage * their health
	
	public Monster() 
	{
		hp = 100;
		damage = 10;
		n = new NameTag("Purple People Eater", "It's big, purple, and scary!");
		alive = true;
		score = hp * damage;
	}
	public Monster(int i, int j, NameTag n1) 
	{
		hp = i;
		damage = j;
		n = n1;
	}
	
	public void setHP(int n) 
	{
		hp = n;
	}
	public int getHP() 
	{
		return hp;
	}
	
	public void setRoom(Room r) 
	{
		currentRoom = r;
	}
	public Room getRoom() 
	{
		return currentRoom;
	}
	public NameTag getTag() 
	{
		return n;
	}
	//Returns name of monster
	public String getName()
	{
		return this.getTag().getName();
	}
	//Returns description of monster
	public String getDesc()
	{
		return this.getTag().getName();
	}
	public void setAlive(Boolean t) 
	{
		alive = t;
	}
	public Boolean getAlive() 
	{
		return alive;
	}
	public int getDMG() 
	{
		return damage;
	}
	public int getScore() 
	{
		return score;
	}
	
	
	
}
