package edu.ycp.cs320.groupProj.controller;

import edu.ycp.cs320.groupProj.model.SystemModel;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.model.Map;
import edu.ycp.cs320.groupProj.model.NameTag;
import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.groupProj.model.Room;


public class GameController {
	private SystemModel model;
	private ObjectModel object;
	private PlayerModel player;
	private Room room;
	private Map map;
	
	private Room roomNorth = map.getRoom(player.getUp()-1, player.getSide());
	private Room roomSouth = map.getRoom(player.getUp()-1, player.getSide());
	private Room roomWest = map.getRoom(player.getUp(), player.getSide()-1);
	private Room roomEast = map.getRoom(player.getUp(), player.getSide()+1);
	
	//SYSTEM MODEL
	public void setSystemModel(SystemModel model) {
		this.model = model;
	} 

	public Boolean getMove() {
		return model.getMovement();
	}
	public void setMove() {
		model.setMovement();
	}
	public void setMovement(Boolean t) {
		model.indicateMove(t);
		
	}
	public Boolean getPlace() {
		return model.getPlace();
	}
	public void setPlace() {
		model.setPlace();
	}
	public Boolean getGrab() {
		return model.getGrab();
	}
	public void setGrab() {
		model.setGrab();
	}
	public Boolean getInspect() {
		return model.getInspect();
	}
	public void setInspect() {
		model.setInspect();
	}
	
	public Boolean checkMove(String dir){
		boolean canMove = false;
		if (dir.equals("north")) {
			if (roomNorth.getEnter()) {
				canMove = true;
				player.setUpLoc(player.getUp()-1);
			}
		} else if (dir.equals("south")) {
			if (roomSouth.getEnter()) {
				canMove = true;
				player.setUpLoc(player.getUp()+1);
			}
		} else if (dir.equals("east")) {
			if (roomEast.getEnter()) {
				canMove = true;
				player.setSideLoc(player.getSide()+1);
			}
		} else if (dir.equals("west")) {
			if (roomWest.getEnter()) {
				canMove = true;
				player.setSideLoc(player.getSide()+1);
			}
		}
		return canMove;
	}
	
	public String Look(String dir) {
		String see = "You look " + dir + " and see ";
		if (room.isDark() && !player.isLit()) {
			see = "The room is pitch black, try using your torch!";
		} else if (dir.equals("north") && roomNorth.getEnter()) {
			 if(roomNorth.hasMonster()) {
				 see = "Something shuffling around in the room, it makes you feel uneasy.";
			 }
			 else if(!roomNorth.isEmpty()) {
				 see = " Something on the floor, you can't discern what it is from here.";
			 }
			 if(roomNorth.isDark()) {
				 see = "The room is encased in shadows, you can't see anything.";
			 }
		} else if (dir.equals("south") && roomSouth.getEnter()) {
			 if(roomSouth.hasMonster()) {
				 see = "Something shuffling around in the room, it makes you feel uneasy.";
			 }
			 else if(!roomSouth.isEmpty()) {
				 see = " Something on the floor, you can't discern what it is from here.";
			 }
			 if(roomSouth.isDark()) {
				 see = "The room is encased in shadows, you can't see anything.";
			 }
		} else if (dir.equals("east") && roomEast.getEnter()) {
			 if(roomEast.hasMonster()) {
				 see = "Something shuffling around in the room, it makes you feel uneasy.";
			 }
			 else if(!roomEast.isEmpty()) {
				 see = " Something on the floor, you can't discern what it is from here.";
			 }
			 if(roomEast.isDark()) {
				 see = "The room is encased in shadows, you can't see anything.";
			 }
		} else if (dir.equals("west") && roomWest.getEnter()) {
			 if(roomWest.hasMonster()) {
				 see = "Something shuffling around in the room, it makes you feel uneasy.";
			 }
			 else if(!roomWest.isEmpty()) {
				 see = " Something on the floor, you can't discern what it is from here.";
			 }
			 if(roomWest.isDark()) {
				 see = "The room is encased in shadows, you can't see anything.";
			 }
		} else if (dir.equals("down")) {
			 see = "You look downward and see a stone-brick flooring, it's got patches of grass growing through the cracks";
		 }
		 else if (dir.equals("up")) {
				see = "You look upwards and see a tile-stone ceiling, it's got signs of aging that lead you to believe this dungeon is very old.";
		 }
		 else if (dir.equals("")){
			 see = "Please enter a valid direction to look";
		 }
		 else {
			 see = "You see a wall";
		 }
		return see;
	}
	
	//OBJECT MODEL
	public void setObjectModel(ObjectModel nModel) {
		object = nModel;
	}
	public void setAtt(String d, int num, Boolean f) {
		object.setDesc(d);
		object.setThing(num);
		object.setKey(f);
	}
	public ObjectModel banana() {
		NameTag n = new NameTag("Banana", "It's a yellow banana");
		int num = 5;
		boolean f = false;
		ObjectModel thing = new ObjectModel(n, num, f);
		return thing;
	}
	public ObjectModel key() {
		NameTag n = new NameTag("Key", "It's a very shiny key");
		int num = 1;
		boolean t = true;
		ObjectModel thing = new ObjectModel(n, num, t);
		return thing;
	}
	public ObjectModel getThis() {
		return object;
	}
	
	
	//PLAYER MODEL
	public void setPlayerModel(PlayerModel nModel) {
		player = nModel;
	}
	public void damage(int num) {
		player.setHP(player.getHP() - num);
	}
	public void heal(int num)  {
		player.setHP(player.getHP() + num);
	}
	
	public String grab(String thing) {
		String result = null;
		object = room.hasInven(thing, room.getInven().length, room.getInven());
		if (room.hasMonster()) {
			result = "The " + room.getMonster().getNameTag().getName()
					+ " blocks your way. You'll have to kill it to loot this room.";
		} else if (player.getiNum() == 9) {
			result = "Your inventory is full";
		} else if (room.isDark() && !player.isLit()) {
			result = "It's too dark to try to grab anything";
		} else if (thing.toString().equals("key")) {
			//check if room actually has key
			//add key to inventory
			player.setHasKey(true);
		} else if (object != null) {
			 player.addInventory(object);
			 result = "You grabbed " + thing;
		 } else {
			 result = "There is no " + thing + " in here";
		 }
		
		return result;
	}
	
	public String place(String thing) {
		String result = null;
		object = player.hasInven(thing, room.getInven().length, room.getInven());
		if (room.getInven().length == 9) {
			result = "The room is full";
		} else if (object != null) {
			 room.addInventory(object);
			 result = "You placed " + thing;
		 } else {
			 result = "There is no " + thing + " to place";
		 }
		
		return result;
	}
	
	public String inspect(String lookAt) {
		String result = null;
		if (lookAt.equals("")) {
			result = "Inspect what?";
		} else if (room.isDark() && !player.isLit()) {
			result = "The room is entirely dark, you look down and notice you can't even see your feet.";
		} else if (lookAt.equals("room")) {
			result = "The room contains: ";
			 for (int i = 0; i < room.getInven().length; i++) {
					if (room.getInven()[i] != null) {
						result += room.getInven()[i].getTag().getName() + " and ";
					}
			 } 	 if (room.hasMonster()) {
					result += room.getMonster().getNameTag().getName() + " and ";
				}else {
						result += "that's all";
						result = room.getTag().getDesc() + result;
						}
		} else if (lookAt.equals("inventory")) {
			result = "You rifle through your pack and find: ";
			 int r = 0;
				for (int i = 0; i < player.getInvenFULL().length; i++) {
					if (player.getInvenFULL()[i] != null) {
						result += player.getInvenFULL()[i].getTag().getName() + ", ";
						r++;
					}
				}
				if (r == 0) {
					result += "it's empty.";
				} else {
				result = "You fail to inspect the " + lookAt;
				}
		}
		return result;
	}
	
	public String use(String thing) {
		String result = null;
		object = room.hasInven(thing, room.getInven().length, room.getInven());
		if(thing.equals("")) {
			 result = "Use what?";
		 } else if (object != null){
			 Boolean j1 = true;
				ObjectModel temp = null;
				for (int i = 0; i < player.getInvenFULL().length; i++) {
					if (player.getInvenFULL()[i] != null) {
						if (player.getInventory(i).getTag().getName().toLowerCase()
								.equals(thing) && j1) {
							temp = player.getInventory(i);
							player.removeItem(i);
							j1 = false;
							//UPDATE DATABASE HERE - REMOVE ITEM FROM PLAYER INVENTORY
						}
					}
				}
				int NEWINUM = player.getInvenFULL().length;
				player.setiNum(NEWINUM);
				player.setHP(player.getHP() + temp.getThing());
				result = "You used the " + temp.getTag().getName();
				//UPDATE HEALTH DB
		 } else {
			 result = "You dont have any " + thing;
		 }
		return result;
	}
	
	public Boolean playerContains(String item) {
		Boolean t  = false;
		for(int i = 0; i < player.getInvenFULL().length; i++) {
			if(player.getInvenFULL()[i] != null) {
				if(player.getInvenFULL()[i].getTag().getName().equals(item)) {
					t = true;
				}
			}
		}
		return t;
	}
	
	public String light(String thing) {
		String result = null;
		if (thing.equals("")) {
			result = "What do you want to light?";
		} else if (thing.equals("torch")) {
			if (player.getMatches() > 0) {
				player.setMatches(player.getMatches() - 1);
				 player.setLit(true);
				 result = "You take out your pack of matches and strike one, lighting the torch and throwing out the now-burnt match.";
			} else {
				result = "You are out of matches";
			}
		} else {
			result = "You can't light that.";
		}
		return result;
	}
	
	public String open(String thing) {
		String result = "";
		if (thing.equals("")) {
			result = "Open what?";
		} else if (thing.equals("chest")) {
			 if (room.hasChest()) {
				 if (player.getHasKey()) {
					 //LINK TO WIN SCREEN HERE
				 } 
				 else {
					 result = "You don't have a key, how did you plan on opening it - brute strength?";
				 }
			 }
			 else {
				 result = "There is no chest in this room, you should get your eyes checked";
			 }
		 }
		 else {
			 result = "You fail to open " + thing;
		 }
		return result;
	}
	
	public int sortInven(ObjectModel[] inven) {
		// this function serves two purposes - returning the new iNum & sorting the players inventory so the iNum is accurate.
		int num = 0; 
		ObjectModel[] tempr = new ObjectModel[inven.length];
		for(int i = 0; i < inven.length; i++) {
			if(inven[i] != null) {
				tempr[num] = inven[i];
				num++;
			}
		}
		player.setInvenFULL(tempr);
		
		return num;
		
	}
	
	//ROOM MODEL
	public void setRoomModel(Room cRoom) {
		room = cRoom;
	} 
	
	public Room getRoom() {
		return room; 
	}

	public Boolean roomContains(String item) {
		Boolean thing = false;
		for (int i = 0; i < room.getInven().length; i++) {
			if (room.getInven()[i] != null) {
				if (room.getInven()[i].getTag().getName().equals(item)) {
					thing = true;
				}
			}
		}
		return thing;
	}
	
	//MAP MODEL
	public void setMap(Map map) {
		this.map = map;
	}
	public Map getMap() {
		return map;
	}
}
