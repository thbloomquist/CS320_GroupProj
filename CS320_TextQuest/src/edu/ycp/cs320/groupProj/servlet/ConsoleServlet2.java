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
	Boolean movement = false;
	private static final long serialVersionUID = 1L;
	SystemModel model = new SystemModel();
	PlayerModel pModel = new PlayerModel();
	ObjectModel oModel = new ObjectModel();
	ObjectModel currentObj = null;
	Map map = new Map();
	String errorMessage = "Current health == " + pModel.getHP();
	String firstW = null;
	String secondW = null;

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

		// prints map - for testing purposes
//		 for (int i = 0; i < 10; i++) { 
//			 for (int j = 0; j < 10; j++) {
//			 if(map.getRoom(j, i).getEnter()) { 
//				 	System.out.print(" [O] "); 
//				 } else
//					 System.out.print(" [X] "); 
//			 } System.out.println(); 
//		 }

		// splits input into a two String array
		
//		//////// Move for DB ////////
//		DBController DBController = new DBController(); // Database controller
//		HttpSession session = req.getSession(false); // get current session
//		session.setAttribute("playerModel", pModel);
//		Player player = (Player) session.getAttribute("player"); // get player from session
//		DBController.getPlayerByUsernameAndPassword(player.getUsername(), player.getPassword()); // get current player model
//		DBController.InsertNewMove(player.getPlayerId(), 1, userInput);
//		System.out.println("Player entered : " + action);
//		//////// Move for DB ////////
		
		for (int i = 0; i < 2; i++) {
			action = userInput.split(" ");
		}
		
		
		//checks if theres only one word to remove null pointer exception errors
		if (action.length == 0) {
			result = "Please enter a command! Don't know what to enter? Press the \"stuck\" button for hints!";
		} else if (action.length == 1) {
			if(action[0].equals("fight") && currentR.hasMonster() && !pModel.getHardy()) {
				//FIGHT
				result = "The " + currentR.getMonster().getName() + " manages to claw you before you fell the beast.";
				pModel.setHP(pModel.getHP()-currentR.getMonster().getDMG());
				UpdatePlayerModel(health, xLoc, yLoc, score, matches, req); //sends updated info to db
				currentR.deadMonster();
				if(pModel.isLit()) {
					result += " During the scuffle your torch went out.";
					pModel.setLit(false);
				} 
				else if(action[0].equals("fight") && currentR.hasMonster() && pModel.getHardy()) {
					//FIGHT WITH HARDY MODE ENABLED
					Random rand = new Random();
					int upperBound = 4 ;
					int hardyChance = rand.nextInt(upperBound);
					if(hardyChance >= 3)
					{
						result = "You go to attack the " + currentR.getMonster().getName() + ", but Hardy shoves you towards the creature and you miss."
								+ "The monster manages to get you with its claws.";
						pModel.setHP(pModel.getHP()-currentR.getMonster().getDMG());
						UpdatePlayerModel(health, xLoc, yLoc, score, matches, req); //sends updated info to db
					}
					else
					{
						result = "The " + currentR.getMonster().getName() + " manages to claw you before you fell the beast.";
						pModel.setHP(pModel.getHP()-currentR.getMonster().getDMG());
						UpdatePlayerModel(health, xLoc, yLoc, score, matches, req); //sends updated info to db
						currentR.deadMonster();
						if(pModel.isLit()) {
							result += " During the scuffle your torch went out.";
							pModel.setLit(false);
						} 
					}
				}
			} else if(action[0].equals("fight") && !currentR.hasMonster() && !pModel.getHardy()) {
				result = "There's no monster here, unless you're counting yourself.";
			} 
			else if(action[0].equals("fight") && !currentR.hasMonster() && pModel.getHardy())
			{
				result = "You cleave Hardy's skull in half and kill him instantly. Hardy Mode has been disabled. The blood is on your hands.";
				pModel.setHardy(false);
			}
			else if (action[0].equals("where")) {
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
							if(pModel.getHardy())
							{
								result = "You and Hardy moved " + action[1]+ ".";
								Random rand = new Random();
								int upperBound = 4 ;
								int hardyChance = rand.nextInt(upperBound);
								if(hardyChance == 4 && pModel.isLit())
								{
									result += " Hardy sneezed on the torch and it went out.";
									pModel.setLit(false);
								}
							}
							else
							{
								result = "You moved " + action[1]+ ".";
								if(map.getRoom(7, 3).searchObject(true, "totem"))
								{
									map.getRoom(7, 3).getInven()[map.getRoom(7, 3).getObjectIndex(true, "totem")] = null;
								}
							}
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
							if(pModel.getHardy())
							{
								result = "You and Hardy moved " + action[1]+ ".";
								Random rand = new Random();
								int upperBound = 4 ;
								int hardyChance = rand.nextInt(upperBound);
								if(hardyChance == 4 && pModel.isLit())
								{
									result += " Hardy sneezed on the torch and it went out.";
									pModel.setLit(false);
								}
							}
							else
							{
								result = "You moved " + action[1]+ ".";
								if(map.getRoom(7, 3).searchObject(true, "totem"))
								{
									map.getRoom(7, 3).getInven()[map.getRoom(7, 3).getObjectIndex(true, "totem")] = null;
								}
							}
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
							if(pModel.getHardy())
							{
								result = "You and Hardy moved " + action[1]+ ".";
								Random rand = new Random();
								int upperBound = 4 ;
								int hardyChance = rand.nextInt(upperBound);
								if(hardyChance == 4 && pModel.isLit())
								{
									result += " Hardy sneezed on the torch and it went out.";
									pModel.setLit(false);
								}
							}
							else
							{
								result = "You moved " + action[1]+ ".";
								if(map.getRoom(7, 3).searchObject(true, "totem"))
								{
									map.getRoom(7, 3).getInven()[map.getRoom(7, 3).getObjectIndex(true, "totem")] = null;
								}
							}
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
							if(pModel.getHardy())
							{
								result = "You and Hardy moved " + action[1]+ ".";
								Random rand = new Random();
								int upperBound = 4 ;
								int hardyChance = rand.nextInt(upperBound);
								if(hardyChance == 4 && pModel.isLit())
								{
									result += " Hardy sneezed on the torch and it went out.";
									pModel.setLit(false);
								}
							}
							else
							{
								result = "You moved " + action[1]+ ".";
								if(map.getRoom(7, 3).searchObject(true, "totem"))
								{
									map.getRoom(7, 3).getInven()[map.getRoom(7, 3).getObjectIndex(true, "totem")] = null;
								}
							}
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
					UpdatePlayerModel(health, xLoc, yLoc, score, matches, req); //sends updated info to db
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
	
			// deals with GRAB command
			if (action[0].equals("grab")) {
				if (pModel.getiNum() == 9 && !pModel.getHardy()) {
					result = "Your inventory is full.";
				}
				else if(pModel.getiNum() == 9 && pModel.getHardy())
				{
					result = "Your inventory is full and Hardy refuses to help you carry anything.";
				}
				else if (currentR.isDark() && !pModel.isLit()) {
					result = "It's too dark to try to grab anything";
					if (currentR.hasMonster()) {
						result += ", but you hear a strange hissing coming from directly in front of you. You should light your torch fast!";
					}
				} else if (currentR.hasMonster()) {
					result = "The " + currentR.getMonster().getName() + " blocks your way.";
				} else {
					if (rController.contains(action[1]) && !pModel.getHardy() && action[1] != ("crate") && action[1] != ("totem")) 
					{
						Boolean thing = pController.grabItem(action[1].toString(), currentR); // good function great function
						if (thing) 
						{
							result = "You grab the " + action[1];
							int NEWINUM = pController.sortInven(pModel.getInvenFULL());
							pModel.setiNum(NEWINUM);
							//PLAYERINVEN DB STUFF
							//inven = currentR.getInven().toString();
//							InsertIntoRoomInven(pModel.getSide(), pModel.getUp(), inven, req);
							
//							//ROOMINVEN DB STUFF
//							for (int i = 0; i < pModel.getInvenFULL().length; i++) {
//								if (currentR.getInven()[i] != null) {
//									inven += pModel.getInvenFULL()[i].getTag().getName() + " ";
//								}
//							}
//							System.out.println("PlayerInven : " + inven);
//							InsertIntoRoomInven(inven, req);
//							//ROOMINVEN DB STUFF 
							String inven = "";
							for (int i = 0; i < pModel.getInvenFULL().length; i++) 
							{
								if (pModel.getInventory(i) != null) 
								{
									inven += pModel.getInvenFULL()[i].getName() + " ";
								}	
							}
						}
						if(rController.contains(action[1]) && !pModel.getHardy() && action[1] != ("crate") && action[1] != ("totem"))
						{
							Random rand = new Random();
							int upperBound = 4;
							int hardyChance = rand.nextInt(upperBound);
							Boolean thing1 = pController.grabItem(action[1], currentR);
							if(hardyChance > 4 && thing1 || action[1]=="key" && thing1)
							{
								result = "You grab the " + action[1];
								int NEWINUM = pController.sortInven(pModel.getInvenFULL());
								pModel.setiNum(NEWINUM);
								String inven = "";
								for (int i = 0; i < pModel.getInvenFULL().length; i++) 
								{
									if (pModel.getInventory(i) != null) 
									{
										inven += pModel.getInvenFULL()[i].getName() + " ";
									}
								}
							System.out.println("PlayerInven : " + inven);
							InsertIntoPlayerInven(inven, req);
							//PLAYERINVEN DB STUFF
//							inven = currentR.getInven().toString();
//							InsertIntoRoomInven(pModel.getSide(), pModel.getUp(), inven, req);
							
//							//ROOMINVEN DB STUFF
//							for (int i = 0; i < pModel.getInvenFULL().length; i++) {
//								if (currentR.getInven()[i] != null) {
//									inven += pModel.getInvenFULL()[i].getTag().getName() + " ";
//								}
//							}
//							System.out.println("PlayerInven : " + inven);
//							InsertIntoRoomInven(inven, req);
//							//ROOMINVEN DB STUFF 
							
							}
							else if(thing1 && hardyChance == 4)
							{
								result = "You go to grab the " + action[1] +", but Hardy takes it first";
								pController.useItem(action[1], currentR);
							}
						}
						else 
						{
							result = "Your hand slips grabbing the " + action[1] + " and it falls back onto the floor.";
					
						}
					}
					else if (action[1].equals("inventory")) {
						String s = "You rifle through your pack and find: ";
						int r = 0;
						for (int i = 0; i < pModel.getInvenFULL().length; i++) {
							if (pModel.getInvenFULL()[i] != null) {
								s += pModel.getInvenFULL()[i].getTag().getName() + ", ";
								r++;
							}
						}
						if (r == 0) {
							s += "it's empty.";
						}
						result = s;
						pController.upScore(action[0]);
					} 
					}
			}
					else 
					{
						result = "There's no " + action[1] + " around.";
					}	
	
			// deals with PLACE command
			if (action[0].equals("place")) {
				if (currentR.isDark() && !pModel.isLit()) {
					result = "It's too dark to see where to place anything";
					if (currentR.hasMonster()) {
						result += ", but you hear a strange hissing come from directly in front of you.  You should light your torch fast!";
					}
				} else {
					if (pController.contains(action[1])) {
						result = pController.placeItem(action[1], currentR);
						pController.upScore(action[0]);
						//PLAYERINVEN DB STUFF
						String inven = "";
						for (int i = 0; i < pModel.getInvenFULL().length; i++) {
							if (pModel.getInventory(i) != null) {
								inven += pModel.getInvenFULL()[i].getTag().getName() + " ";
							}
						}
						InsertIntoPlayerInven(inven, req);
						//PLAYERINVEN DB STUFF
//						inven = currentR.getInven().toString();
//						InsertIntoRoomInven(pModel.getSide(), pModel.getUp(), inven, req);
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
					if (pController.contains(action[1])) {
						result = pController.useItem(action[1], currentR);
						errorMessage = "Current health == " + health;
						pController.upScore(action[0]);
						UpdatePlayerModel(health, xLoc, yLoc, score, matches, req); //sends updated info to db
						//PLAYERINVEN DB STUFF
						String inven = "";
						for (int i = 0; i < pModel.getInvenFULL().length; i++) {
							if (pModel.getInventory(i) != null) {
								inven += pModel.getInvenFULL()[i].getTag().getName() + " ";
							}
						}
						InsertIntoPlayerInven(inven, req);
						//PLAYERINVEN DB STUFF
					} else {
						result = "You don't have any " + action[1];
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
						for (int i = 0; i < currentR.getInven().length; i++) {
							if (currentR.getInven()[i] != null) {
								s += currentR.getInven()[i].getName() + " and ";
							}
						}
						if (currentR.hasMonster()) {
							s += currentR.getMonster().getName() + " and ";
						}
						if (s.toLowerCase().compareTo(" the room contains: ") == 0) {
							result = currentR.getDesc();
						} else {
							s += "that's all";
							result = currentR.getDesc() + s;
						}
					} else if (action[1].equals("inventory")) {
						String s = "You rifle through your pack and find: ";
						int r = 0;
						for (int i = 0; i < pModel.getInvenFULL().length; i++) {
							if (pModel.getInvenFULL()[i] != null) {
								s += pModel.getInvenFULL()[i].getTag().getName() + ", ";
								r++;
							}
						}
						if (r == 0) {
							s += "it's empty.";
						}
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
								if(pController.contains("Onyx") && pController.contains("Emerald") && pController.contains("Sapphire")
										&& pController.contains("Diamond") && pController.contains("Ruby")) 
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
					}
					else if(action[1].equals("crate"))
					{
						if(currentR.searchObject(true, "Crate"))
						{
							Random rand = new Random();
							int upperBound = 3;
							int crateChance = rand.nextInt(upperBound);
							if(crateChance == 0) 
							{
								pModel.setMatches(pModel.getMatches() + 1);
								currentR.getInven()[currentR.getObjectIndex(true, "Crate")] = null;
								result = "You smash open the crate and find a match. You now have "+pModel.getMatches()+" matches.";
							}
							if(crateChance == 1)
							{
								currentR.getInven()[currentR.getObjectIndex(true, "Crate")] = new ObjectModel(new NameTag("banana", "a yellow banana you found in a box. "
										+ "You don't know where it's been but beggers can't be choosers."), 5,false);
								result = "You smash open the crate and find a banana inside.";
							}
							if(crateChance > 1)
							{
								currentR.getInven()[currentR.getObjectIndex(true, "Crate")] = null;
								result = "You smash open the crate and find nothing.";
							}	
						}
						else
						{
							result = "There is no crate in this room";
						}
					}
					else if(action[1].equals("totem"))
					{
						if(currentR.searchObject(true, "Totem"))
						{
							currentR.getInven()[currentR.getObjectIndex(true, "Totem")] = null;
							pModel.setHardy(true);
							result = "You touch the totem and it crumbles into dust. You feel a presence in the room. You turn around and see a man in the room with you, and that man's name is Hardy.";
						}
						else
						{
							result = "There is no totem in this room";
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
		DBController.InsertNewPlayerModel(player.getPlayerId(), health, xLoc, yLoc, score, matches);
		System.out.println("PlayerModel updated with : health = " + health + " x = " + xLoc + " y = " + yLoc + " score = " + score + " matches = " + matches);
	}
	private void InsertIntoPlayerInven(String insert, HttpServletRequest req) {
		DBController DBController = new DBController(); // Database controller
		HttpSession session = req.getSession(false); // get current session
		session.setAttribute("playerModel", pModel);
		Player player = (Player) session.getAttribute("player"); // get player from session
		DBController.getPlayerByUsernameAndPassword(player.getUsername(), player.getPassword()); // get current player model
		DBController.UpdatePlayerInven(player.getPlayerId(), insert); 
		System.out.println("PlayerInventory updated with: " + insert);
	}
	private void InsertIntoRoomInven(int xLoc, int yLoc, String thing, HttpServletRequest req) {
		DBController DBController = new DBController(); // Database controller
		HttpSession session = req.getSession(false); // get current session
		session.setAttribute("playerModel", pModel);
		Player player = (Player) session.getAttribute("player"); // get player from session
		DBController.getPlayerByUsernameAndPassword(player.getUsername(), player.getPassword()); // get current player model
		DBController.UpdateRoomInven(player.getPlayerId(), xLoc, yLoc, thing); 
		System.out.println("PlayerInventory updated with: " + thing);
		}
}