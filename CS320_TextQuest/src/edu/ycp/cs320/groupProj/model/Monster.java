package edu.ycp.cs320.groupProj.model;


// model class for TextQuest
// keeps track of the moves, totaling North-South and East-West


public class Monster {
	public int hp;
	public Room currentRoom;
	public int damage;
	public NameTag n;
	public Boolean alive;
	
	public Monster() {
		hp = 100;
		damage = 10;
		n = new NameTag("Purple People Eater", "It's big, purple, and scary!");
		alive = true;
	}
	public Monster(int i, int j, NameTag n1) {
		hp = i;
		damage = j;
		n = n1;
	}
	
	public void setHP(int n) {
		hp = n;
	}
	public int getHP() {
		return hp;
	}
	
	public void setRoom(Room r) {
		currentRoom = r;
	}
	public Room getRoom() {
		return currentRoom;
	}
	public NameTag getNameTag() {
		return n;
	}
	public void setAlive(Boolean t) {
		alive = t;
	}
	public int getDMG() {
		return damage;
	}
	
	
	
}
