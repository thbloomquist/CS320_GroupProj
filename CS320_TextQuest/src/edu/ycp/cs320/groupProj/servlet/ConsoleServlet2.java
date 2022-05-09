package edu.ycp.cs320.groupProj.servlet;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.groupProj.controller.SystemController;
import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.groupProj.controller.DBController;
import edu.ycp.cs320.groupProj.controller.ObjectController;
import edu.ycp.cs320.groupProj.model.NameTag;
import edu.ycp.cs320.groupProj.model.Map;
import edu.ycp.cs320.groupProj.model.Room;
import edu.ycp.cs320.groupProj.model.SystemModel;
import edu.ycp.cs320.groupProj.controller.RoomController;
import edu.ycp.cs320.groupProj.controller.PlayerController;

public class ConsoleServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PlayerModel pModel = null;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("Console: doGet");
		HttpSession session = req.getSession(false);
		
		//redirects to login page if not logged in
		if(session == null) {
			LoginServlet serve = new LoginServlet();
			serve.doGet(req, resp);
			return;
		}

		System.out.println(session);

		Player player = (Player) session.getAttribute("player");

		DBController controller = new DBController();
		controller.InsertNewGame(player.getPlayerId());
		System.out.println("New game created");

		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/main.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		

		DBController DBController = new DBController(); // Database controller
		HttpSession session = req.getSession(false); // get current session
		session.setAttribute("playerModel", pModel);
		Player player = (Player) session.getAttribute("player"); // get player from session
		
		pModel = DBController.LoadPlayerModel(player.getPlayerId());
		
		System.out.println("Up: " + pModel.getUp() + " Side: " + pModel.getSide());
		
		Boolean movement = false;
		SystemModel model = new SystemModel();
		ObjectModel oModel = new ObjectModel();
		ObjectModel currentObj = null;
		Map map = (Map) session.getAttribute("map");
		String errorMessage = "Current health == " + pModel.getHP();
		String firstW = null;
		String secondW = null;

		System.out.println("Console Servlet: doPost");
		SystemController controller = new SystemController();
		ObjectController oController = new ObjectController();
		RoomController rController = new RoomController();
		PlayerController pController = new PlayerController();
		pController.setModel(pModel);
		controller.setModel(model);
		oController.setModel(oModel);
		String userInput = req.getParameter("action").toLowerCase();
		String[] action = null;
		String result = "";
		Room currentR = map.getRoom(pModel.getUp(), pModel.getSide());
		Room roomNorth = map.getRoom(pModel.getUp() - 1, pModel.getSide());
		Room roomSouth = map.getRoom(pModel.getUp() + 1, pModel.getSide());
		Room roomWest = map.getRoom(pModel.getUp(), pModel.getSide() - 1);
		Room roomEast = map.getRoom(pModel.getUp(), pModel.getSide() + 1);
		
		int health = pModel.getHP();
		int xLoc = pModel.getSide();
		int yLoc = pModel.getUp();
		int score = pModel.getScore();
		int matches = pModel.getMatches();
		
		rController.setModel(currentR);
		
		// set the player and room inventory from DB
		///////////////////////LOADING FROM DATABASE//////////////////////////
		pModel.setInventory(DBController.getPlayerInventory(player.getPlayerId()));
		currentR.setInventory(DBController.getRoomInventory(player.getPlayerId(), xLoc, yLoc));
		//////////////////////LOADING FROM DATABASE///////////////////////////
		
		
		//Needed to remove null elements from the room inventory
		for(int i = 0; i < currentR.getInven().size(); i++) {
			if(currentR.getInven().get(i).getName().equals("REMOVE")) {
				currentR.getInven().remove(i);
				System.out.println("\tRemoving null elements from room");
			}
		}
		for(int i = 0; i < pModel.getInventory().size(); i++) {
			if(pModel.getInventory().get(i).getName().equals("REMOVE")) {
				pModel.getInventory().remove(i);
				System.out.println("\tRemoving null elements from room");
			}
		}
		
		// TO DO
		// IMPLEMENT GRABBING AND PLACING ITEMS, ALONG WITH THEIR USES!!!!!!!
		// TO DO

		// prints map - for testing purposes
//		 for (int i = 0; i < 10; i++) { 
//			 for (int j = 0; j < 10; j++) {
//			 if(map.getRoom(j, i).getEnter()) { 
//				 	System.out.print(" [O] "); 
//				 } else
//					 System.out.print(" [X] "); 
//			 } System.out.println(); 
//		 }
		
//		for (int y = 1; y < 9; y++) {
//			for (int x = 1; x < 9; x++) {
//				String insert = "";
//				if (map.getRoom(y, x).getEnter()) {
//					for (int i = 0; i < map.getRoom(y, x).getInven().length; i++) {
//						if (currentR.getInven()[i] != null) {
//							insert += map.getRoom(y, x).getInven()[i].getTag().getName() + " ";
//						}
//						InsertIntoRoomInven(x, y, insert, req);
//					}
//				}
//			}
//		}

		// splits input into a two String array
		
	
		//////// Move for DB ////////
		DBController.getPlayerByUsernameAndPassword(player.getUsername(), player.getPassword()); // get current player model
		DBController.InsertNewMove(player.getPlayerId(), 1, userInput);
		System.out.println("Player entered : " + action);
		//////// Move for DB ////////
		
		for (int i = 0; i < 2; i++) {
			action = userInput.split(" ");
		}
		
		
		//checks if theres only one word to remove null pointer exception errors
		if (action.length == 0) {
			result = "Please enter a command! Don't know what to enter? Press the \"stuck\" button for hints!";
		} else if (action.length == 1) {
			if(action[0].equals("fight") && currentR.hasMonster()) {
				//FIGHT
				result = "The " + currentR.getMonster().getNameTag().getName() + " manages to claw you before you fell the beast.";
				pModel.setHP(health-currentR.getMonster().getDMG());
				UpdatePlayerModel(health, xLoc, yLoc, score, matches, req); //sends updated info to db
				currentR.deadMonster();
				if(pModel.isLit()) {
					result += " During the scuffle your torch went out.";
					pModel.setLit(false);
				} 
			} else if(action[0].equals("fight") && !currentR.hasMonster()) {
				result = "There's no monster here, unless you're counting yourself.";
			} else if (action[0].equals("where")) {
				result = "UpPlace ==" + pModel.getUp() + " sidePlace == " + pModel.getSide();
			} else if (action[0].equals("move") || action[0].equals("look")) {
				result = action[0] + " where?";
			} else if (action[0].equals("grab") || action[0].equals("place") || action[0].equals("use") || action[0].equals("inspect") || action[0].equals("light") || action[0].equals("open")) {
				result = action[0] + " what?";
			} else {
				result = "I don't recognize that command. Click 'Stuck?' for more options";
			}
		} else if (action.length == 2){
		// move done

			if (action[0].equals("move")) {
				Boolean did = false;
				if (currentR.isDark() && !pModel.isLit()) {
					result = "It's too dark to see where to go";
					if (currentR.hasMonster()) {
						result += ", but you hear a strange hissing coming from directly in front of you. You should light your torch fast!";
					}
				} else {
					if (action[1].equals("north")) {
						if (map.getRoom(pModel.getUp() - 1, pModel.getSide()).getEnter()) {
							pModel.setUpLoc(pModel.getUp() - 1); // updates player location
							result = "You moved " + action[1]+ ".";
							did = true;
							if(currentR.getDiscovered() == false)
							{
								currentR.setDiscovered(pModel);
							}
						} else {
							result = "The path is blocked.";// isn't traversable- no change
						}
					} else if (action[1].equals("south")) {
						if (map.getRoom(pModel.getUp() + 1, pModel.getSide()).getEnter()) {
							pModel.setUpLoc(pModel.getUp() + 1); // updates player location
							result = "You moved " + action[1]+ ".";
							did = true;
							if(currentR.getDiscovered() == false)
							{
								currentR.setDiscovered(pModel);
							}
						} else {
							result = "The path is blocked";// isn't traversable- no change
						}
					} else if (action[1].equals("east")) {
						if (map.getRoom(pModel.getUp(), pModel.getSide() + 1).getEnter()) {
							pModel.setSideLoc(pModel.getSide() + 1); // updates player location
							result = "You moved " + action[1]+ ".";
							did = true;
							if(currentR.getDiscovered() == false)
							{
								currentR.setDiscovered(pModel);
							}
						} else {
							result = "The path is blocked"; // isn't traversable- no change
						}
					} else if (action[1].equals("west")) {
						if (map.getRoom(pModel.getUp(), pModel.getSide() - 1).getEnter()) {
							pModel.setSideLoc(pModel.getSide() - 1);
							result = "You moved " + action[1]+ "."; // updates player location
							did = true;
							if(currentR.getDiscovered() == false)
							{
								currentR.setDiscovered(pModel);
							}
						} else {
							result = "The path is blocked"; // isn't traversable- no change
						}
					} else {
						result = "That isn't a valid direction.";
					}
				}
				if (did) {
					pController.upScore(action[0]);
					UpdatePlayerModel(health, pModel.getSide(), pModel.getUp(), score, matches, req); //sends updated info to db
				}
			}
	
			// deals with LOOK command - done I think, no db things
			if (action[0].equals("look")) {
				if (currentR.isDark() && !pModel.isLit()) {
					result = "It's too dark to see anything";
					if (currentR.hasMonster()) {
						result += ", but you hear a strange hissing coming from directly in front of you. You should light your torch fast!";
					}
				} else if (action[1].equals("north") && roomNorth.getEnter()) {
					result = "You look north and see";
					if (roomNorth.hasMonster()) {
						result += " something shuffling around in the room, it makes you feel uneasy.";
					} else if (!roomNorth.checkEmpty()) {
						result += " something on the floor, you can't discern what it is from here.";
					}
					if (roomNorth.isDark()) {
						result = " a room encased in shadows, you can't see anything.";
					} else if(roomNorth.checkEmpty()) {
						result = "You see an empty room, it's eerily similar to the one you're standing in now.";
					}
				} else if (action[1].equals("south") && roomSouth.getEnter()) {
					if (roomSouth.hasMonster()) {
						result += " something shuffling around in the room, it makes you feel uneasy.";
					} else if (!roomSouth.checkEmpty()) {
						result += " something on the floor, you can't discern what it is from here.";
					}
					if (roomSouth.isDark()) {
						result = " a room encased in shadows, you can't see anything.";
					} else if(roomSouth.checkEmpty()) {
						result = "You see an empty room, it's eerily similar to the one you're standing in now.";
					}
				} else if (action[1].equals("east") && roomEast.getEnter()) {
					if (roomEast.hasMonster()) {
						result += " something shuffling around in the room, it makes you feel uneasy.";
					} else if (!roomEast.checkEmpty()) {
						result += " something on the floor, you can't discern what it is from here.";
					}
					if (roomEast.isDark()) {
						result = " a room encased in shadows, you can't see anything.";
					} else if(roomEast.checkEmpty()) {
						result = "You see an empty room, it's eerily similar to the one you're standing in now.";
					}
				} else if (action[1].equals("west") && roomWest.getEnter()) {
					if (roomWest.hasMonster()) {
						result += " something shuffling around in the room, it makes you feel uneasy.";
					} else if (!roomWest.checkEmpty()) {
						result += " something on the floor, you can't discern what it is from here.";
					}
					if (roomWest.isDark()) {
						result = " a room encased in shadows, you can't see anything.";
					} else if(roomWest.checkEmpty()) {
						result = "You see an empty room, it's eerily similar to the one you're standing in now.";
					}
				} else if (action[1].equals("down")) {
					result = "You look downward and see " + currentR.getFloor();
				} else if (action[1].equals("up")) {
					result = "You look upwards and see " + currentR.getCel();
				} else if (action[1].equals(null)) {
					result = "Please enter a valid direction to look";
				} else {
					result = "You see a wall";
				}
				pController.upScore(action[0]);
			}
	
			// deals with GRAB command - update ROOMINVEN and PLAYERINVEN
			if (action[0].equals("grab")) {
				currentR = map.getRoom(pModel.getUp(), pModel.getSide());
				if(currentR.getInven().size() >= 1)
				if (pModel.getInventory().size() == 9) {
					result = "Your inventory is full.";
				} else if (currentR.isDark() && !pModel.isLit()) {
					result = "It's too dark to try to grab anything";
					if (currentR.hasMonster()) {
						result += ", but you hear a strange hissing coming from directly in front of you. You should light your torch fast!";
					}
				} else if (currentR.hasMonster()) {
					result = "The " + currentR.getMonster().getNameTag().getName() + " blocks your way, you'll have to fight it before you can loot the room.";
				} else {
					if (rController.contains(action[1].toString())) {
						// thing = item was picked up and placed in player inventory
						Boolean thing = pController.grabItem(action[1].toString().toLowerCase(), currentR); // good function great function
						if (thing) {
							result = "You grab the " + action[1];
							String inven = "";
							for(ObjectModel items: pModel.getInventory()) {
								inven += items.getName() + " ";
							}
							
							DBController.UpdatePlayerInven(player.getPlayerId(), inven);
							
							currentR.checkEmpty();
						
							DBController.UpdatePlayerInven(player.getPlayerId(), inven);
							//PLAYERINVEN DB STUFF
							inven = currentR.getInven().toString();
							DBController.UpdateRoomInven(player.getPlayerId(), pModel.getSide(), pModel.getUp(), inven);
							inven = "";
//							//ROOMINVEN DB STUFF							
							for (int i = 0; i < currentR.getInven().size(); i++) {
								if (currentR.getInven().get(i) != null) {
									inven += currentR.getInven().get(i).getName().toLowerCase() + " ";
								}
							}
							
							DBController.UpdateRoomInven(player.getPlayerId(), pModel.getSide(), pModel.getUp(), inven);
							//ROOMINVEN DB STUFF
							
							// if the last item in the room is taken room.isEmpty = true
							// isEmpty matters for the look command tho its not important for this one
						} else {
							result = "Your hand slips grabbing the " + action[1] + " and it falls back onto the floor.";
						}
					} else {
						result = "There's no " + action[1] + " around.";
					}
				}
	
			}
	
			// deals with PLACE command - update ROOMINVEN and PLAYERINVEN
			if (action[0].equals("place")) {
				if (currentR.isDark() && !pModel.isLit()) {
					result = "It's too dark to see where to place anything";
					if (currentR.hasMonster()) {
						result += ", but you hear a strange hissing come from directly in front of you.  You should light your torch fast!";
					}
				} else {
					if (pController.contains(action[1])) {
						result = action[1] + " has been placed into the room";
						pController.upScore(action[0]);
						//PLAYERINVEN DB STUFF
						String inven = "";
						
						// places the object from players inventory into room inventory
						Boolean insertRoom = DBController.UpdateRoomInven(player.getPlayerId(), xLoc, yLoc, pModel.removeItem(action[1]).getName());
						
						for(ObjectModel items: pModel.getInventory()) {
							inven += items.getName() + " ";
						}
						// removes item from players inventory
						DBController.UpdatePlayerInven(player.getPlayerId(), inven);
						
						UpdatePlayerModel(health, xLoc, yLoc, score, matches, req); //sends updated info to db
					} else {
						result = "You don't have any " + action[1];
					}
				}
				// check if place is valid + update inventory
				// most code is contained within controller(s)
			}
	
			// deals with USE command - update PLAYERINVEN and PLAYERMODEL
			if (action[0].equals("use")) {
				if (currentR.isDark() && !pModel.isLit()) {
					result = "It's too dark to try to grab anything from your pack";
					if (currentR.hasMonster()) {
						result += ", but you hear a strange hissing coming from directly in front of you. You should light your torch fast!";
					}
				} else {
					if(action[1].toString().equals("banana")) {
						if (pController.contains(action[1])) {
							result = "You eat a banana, and instantly feel better";
							errorMessage = "Current health == " + health+10;
							pController.upScore(action[0]);
							UpdatePlayerModel(health + 10, xLoc, yLoc, score, matches, req); //sends updated info to db
							//PLAYERINVEN DB STUFF
							pModel.removeItem(action[1].toString().toLowerCase());
							String inven = "";
							
							for(ObjectModel items: pModel.getInventory()) {
								inven += items.getName() + " ";
							}
							
							DBController.UpdatePlayerInven(player.getPlayerId(), inven);
							//PLAYERINVEN DB STUFF
						} else {
							result = "You don't have any " + action[1];
						}
					}
				}
			}
	
			// deals with INSPECT command - no db
			if (action[0].equals("inspect")) {
				if (currentR.isDark() && !pModel.isLit()) {
					result = "It's too dark to inspect anything";
					if (currentR.hasMonster()) {
						result += ", but you hear a strange hissing coming from directly in front of you. You should light your torch fast!";
					}
				} else {
					if (action[1].equals(null)) {
						result = "Inspect what?";
					} else if (action[1].equals("room")) {
						String s = " the room contains: ";
						
						System.out.println(currentR.getInven().size());
						for(ObjectModel items : currentR.getInven()) {
							s += items.getName() + " and ";
						}
						if (currentR.hasMonster()) {
							s += currentR.getMonster().getNameTag().getName() + " and ";
						}
						if (s.toLowerCase().compareTo(" the room contains: ") == 0) {
							result = currentR.getTag().getDesc();
						} else {
							s += "that's all";
							result = currentR.getTag().getDesc() + s;
						}
					} else if (action[1].equals("inventory")) {
						String s = "You rifle through your pack and find: ";
						int r = 0; // check if inventory is empty
						for(ObjectModel items : pModel.getInventory()) {
							if(items != null && !items.equals("REMOVE"))
								s += items.getName() + ", ";
							r++;
						}
						if (r == 0) {
							s += "it's empty.";
						}
						System.out.println(s);
						result = s;
						pController.upScore(action[0]);
					} else {
						result = "That is not a command I recognize";
					}
				}
			}
			// deals with LIGHT command - done I think
			if (action[0].equals("light")) {
				if (action[1].equals("torch")) {
					if (pModel.getMatches() > 0) {
						pModel.setMatches(pModel.getMatches() - 1);
						UpdatePlayerModel(health, xLoc, yLoc, score, matches, req); //sends updated info to db
						pModel.setLit(true);
						result = "You take out your pack of matches and strike one, lighting the torch and throwing out the now-burnt match.";
						pController.upScore(action[0]);
						if (currentR.hasMonster()) {
							result += " As the room fills with the light of torch, you see a "
									+ currentR.getMonster().getName()
									+ " drooling as it stares at you. Gross!";
						}
					} else {
						result = "Uh oh, you are out of matches";
					}
				} else {
					result = "You are unable to light " + action[1] + " on fire";
				}
			}
	
			// deals with OPEN command - almost done [NEED WIN SCREEN]
			if (action[0].equals("open")) {
				if (currentR.isDark() && !pModel.isLit()) {
					result = "It's too dark to see anything";
					if (currentR.hasMonster()) {
						result += ", but you hear a strange hissing coming from directly in front of you. You should light your torch fast!";
					}
				} else {
					if (action[1].equals("chest")) {
						if (currentR.hasChest()) {
							if (pController.contains("key")) {
								
								pModel.incrementScore(1000);
								
								//Checks if the player has all the gems, if they do they get another 1000 points
								if(pController.contains("Black Gem") && pController.contains("Green Gem") && pController.contains("Blue Gem")
										&& pController.contains("Yellow Gem") && pController.contains("Red Gem")) 
								{
									pModel.incrementScore(1000);
								}
								req.getSession().setAttribute("score", pModel.getScore());
								
								req.getRequestDispatcher("/_view/win.jsp").forward(req, resp);
							} else {
								result = "You don't have a key, how did you plan on opening it - brute strength?";
							}
						} else {
							result = "There is no chest in this room, you should get your eyes checked";
						}
					}else if(action[1].equals("crate")){
						// if player has a crate in their inventory
						System.out.println("CRATES!");
						if(pModel.getItemFromInventory(action[1].toString().toLowerCase()).getName().equals("crate"))
						{
							Random rand = new Random();
							int upperBound = 3;
							int crateChance = rand.nextInt(upperBound);
							
							for(int i = 0; i < 20; i++) {
								System.out.println(rand.nextInt(upperBound));
							}
							
							String inven = "";
							
							System.out.println(crateChance);
							
							if(crateChance == 1)
							{
								pModel.removeItem("crate");
								// load in players invintory with crate removed
								for(ObjectModel items: pModel.getInventory()) {
									inven += items.getName() + " ";
								}
								DBController.UpdatePlayerInven(player.getPlayerId(), inven);
								result = "You smash open the crate and find nothing.";
							}else 
							{
								pModel.removeItem("crate");
								// load in players inventory (with item removed)
								for(ObjectModel items: pModel.getInventory()) {
									inven += items.getName() + " ";
								}
								inven += "banana";
								DBController.UpdatePlayerInven(player.getPlayerId(), inven);
								pModel.addItemToInventory(new ObjectModel(new NameTag("banana", "a yellow banana you found in a box. " + "You don't know where it's been but beggers can't be choosers."),false));
								result = "You smash open the crate and find a banana inside.";
								System.out.println("GETTING BANANA");
							}	
						}
					}
	
					else {
						result = "You fail to open " + action[1];
					}
				}
			}
		}
		
		errorMessage = "Current health == " + health;
		
		req.setAttribute("action", req.getParameter("action"));

		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("result", result);

		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/main.jsp").forward(req, resp);
	
	}
	public void UpdatePlayerModel(int health, int xLoc, int yLoc, int score, int matches, HttpServletRequest req) {
		DBController DBController = new DBController(); // Database controller
		HttpSession session = req.getSession(false); // get current session
		session.setAttribute("playerModel", pModel);
		Player player = (Player) session.getAttribute("player"); // get player from session
		DBController.getPlayerByUsernameAndPassword(player.getUsername(), player.getPassword()); // get current player model
		DBController.UpdatePlayerModel(player.getPlayerId(), health, xLoc, yLoc, score, matches);
		System.out.println("PlayerModel updated with : health = " + health + " x = " + xLoc + " y = " + yLoc + " score = " + score + " matches = " + matches);
	}

}