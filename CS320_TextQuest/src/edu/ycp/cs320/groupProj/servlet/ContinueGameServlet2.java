package edu.ycp.cs320.groupProj.servlet;

import java.io.IOException;

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

public class ContinueGameServlet2 extends HttpServlet {
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
		Room roomNorth = map.getRoom(pModel.getUp()-1, pModel.getSide());
		Room roomSouth = map.getRoom(pModel.getUp()-1, pModel.getSide());
		Room roomWest = map.getRoom(pModel.getUp(), pModel.getSide()-1);
		Room roomEast = map.getRoom(pModel.getUp(), pModel.getSide()+1);
		
		// TO DO
		// IMPLEMENT GRABBING AND PLACING ITEMS, ALONG WITH THEIR USES!!!!!!!
		// TO DO
		
		//prints map - for testing purposes
//		 for (int i = 0; i < 10; i++) { 
//			 for (int j = 0; j < 10; j++) {
//			 if(map.getRoom(j, i).getEnter()) { 
//				 	System.out.print(" [O] "); 
//				 } else
//					 System.out.print(" [X] "); 
//			 } System.out.println(); 
//		 }
		
		//splits input into a two String array
		
//		//////// Move for DB ////////
//		DBController DBController = new DBController(); // Database controller
//		HttpSession session = req.getSession(false); // get current session
//		session.setAttribute("playerModel", pModel);
//		Player player = (Player) session.getAttribute("player"); // get player from session
//		DBController.getPlayerByUsernameAndPassword(player.getUsername(), player.getPassword()); // get current player model
//		DBController.InsertNewMove(player.getPlayerId(), 1, action); //deal with later
//		System.out.println("Player entered : " + action);
//		//////// Move for DB ////////
		
		 for(int i = 0; i < 2; i++)
		 {
			 action = userInput.split(" ");
		 }
		 
		 
		 if(action[0].contentEquals(null)) {
			 errorMessage = "Please specify an action or click 'Stuck?' for a list of commands.";
			 }
		 
		 //deals with MOVE command - done I think - no DB stuff
		 if (action[0].contentEquals("move")) {
			 controller.setMovement(true);
			 movement = model.getMovement();
			 if(action[1].contentEquals("north")) {
				 if (map.getRoom(pModel.getUp() - 1, pModel.getSide()).getEnter()) {
						pModel.setUpLoc(pModel.getUp() - 1); // updates player location
						result = "You moved north";
					} else {
						result = "The path is blocked.";// isn't traversable- no change
					}
			 } else if(action[1].contentEquals("south")) {
				 if (map.getRoom(pModel.getUp() + 1, pModel.getSide()).getEnter()) {
						pModel.setUpLoc(pModel.getUp() + 1); // updates player location
						result = "You moved south";
					} else {
						result = "The path is blocked";// isn't traversable- no change
					}
			 } else if(action[1].contentEquals("east")) {
				 if (map.getRoom(pModel.getUp(), pModel.getSide() + 1).getEnter()) {
						pModel.setSideLoc(pModel.getSide() + 1); // updates player location
						result = "You moved east";
					} else {
						result = "The path is blocked"; // isn't traversable- no change
					}
			 } else if(action[1].contentEquals("west")) {
				 if (map.getRoom(pModel.getUp(), pModel.getSide() - 1).getEnter()) {
						pModel.setSideLoc(pModel.getSide() - 1);
						result = "You moved west"; // updates player location
					} else {
						result = "The path is blocked"; // isn't traversable- no change
					}
				} else {
					result = "That isn't a valid direction";
				}
		 }
		 
		 if (currentR.hasMonster()) {
			 result = "The " + currentR.getMonster().getNameTag().getName()
						+ " blocks your way. You'll have to kill it to loot this room.";
		 }
		 
		 //deals with LOOK command - done I think - no DB stuff
		 if (action[0].contentEquals("look")) {
			 if (currentR.isDark() && !pModel.isLit()) {
					result = "It's too dark to see anything.";
			 } else if((action[1].contentEquals("north") && roomNorth.getEnter())
					 ||(action[1].contentEquals("south") && roomSouth.getEnter())
					 |(action[1].contentEquals("east") && roomEast.getEnter())
					 |(action[1].contentEquals("west") && roomWest.getEnter())){
				 
				 result = "You look into the room to your " + action[1] + " and see "; 
				 if (currentR.isDark()) {
					result += "nothing, the room is encased in shadows";
				 }
				 else if (currentR.hasMonster()) {
					 result+= "something shuffling within the room. It makes you feel uneasy";
				 }
				 else if (currentR.checkEmpty()) {
					 result += "an empty room";
				 }
				 else if (!currentR.checkEmpty()) {
					 result += "something o the floor, though you can't discern what it is from here";
				 }
			 }
			 else if (action[1].contentEquals("down")) {
				 result = "You look downward and see a stone-brick flooring, it's got patches of grass growing through the cracks";
			 }
			 else if (action[1].contentEquals("up")) {
					result = "You look upwards and see a tile-stone ceiling, it's got signs of aging that lead you to believe this dungeon is very old.";
			 }
			 else if (action[1].contentEquals(null)){
				 result = "Please enter a valid direction to look";
			 }
			 else {
				 result = "You see a wall";
			 }
		 }
		 
		 //deals with GRAB command - update PLAYER and ROOM INVENTORY DB
		 if (action[0].contentEquals("grab")) {
			 model.indicateGrab(true);
			 if (currentR.hasMonster()) {
					result = "The " + currentR.getMonster().getNameTag().getName()
							+ " blocks your way. You'll have to kill it to loot this room.";
				} else if (pModel.getiNum() == 9) {
					result = "Your inventory is full.";
				} else if (currentR.isDark() && !pModel.isLit()) {
					result = "It's too dark to try to grab anything";
				} else if (action[1].contentEquals("key")) {
					//check if room actually has key
					//add key to inventory
					pModel.setHasKey(true);
				}
					else {
						result = "There is no key here";
					}
				} else if (rController.contains(action[1])) {
					ObjectModel[] temp = new ObjectModel[10];
					int num = 0;
					for (int i = 0; i < currentR.getInven().length; i++) {
						if (currentR.getInven()[i] != null) {
							temp[num] = currentR.getInven()[i];
							num++;
						}
				}
					//UPDATE INVENTORY DB FOR ROOM + PLAYER
			 
		 }
		 
		 //deals with PLACE command - done I think - update PLAYER and ROOM INVENTORY DB 
		 if (action[0].contentEquals("place")){
			 model.indicatePlace(true);
			 if (action[1].contentEquals(null)) {
				 result = "Place what?";
			 } else if (pController.contains(action[1])) {
				 ObjectModel tempr = null;
					Boolean only1 = true;
					for (int l = 0; l < pModel.getInvenFULL().length; l++) {
						if (pModel.getInventory(l) != null) {
							if (pModel.getInventory(l).getTag().getName().toLowerCase()
									.equals(secondW) && only1) {
								tempr = pModel.getInventory(l);
								only1 = false;
								pModel.getInvenFULL()[l] = null;
								// this should return a reference to the first index of an item that contains
								// the same name as the input
							}
						}
					}
					int temp = -1;
					Boolean firstI = true;
					for (int i = 0; i < currentR.getInven().length; i++) {
						if (currentR.getInven()[i] == null && firstI) {
							temp = i;
							firstI = false;
							// this should return the first index that is "open" for an item input
						}
					}
					if (temp == -1) {
						result = "The room is full.";
					} else {
						currentR.getInven()[temp] = tempr;
						int NEWINUM = pController.sortInven(pModel.getInvenFULL());
						pModel.setiNum(NEWINUM);
						result = "You placed the " + action + " on the floor.";
					}
				} else {
					result = "You don't have any " + action[1];
				}
			 //UPDATE INVENTORY FOR ROOM AND PLAYER
			 }
		 
		 
		 //deals with USE command - done I think - update PLAYER INVENTORY and HEALTH
		 if (action[0].contentEquals("use")) {
			 model.indicateUse(true);
			 if(action[1].contentEquals(null)) {
				 result = "Use what?";
			 } else if (pController.contains(action[1])){
				 Boolean j1 = true;
					ObjectModel temp = null;
					for (int i = 0; i < pModel.getInvenFULL().length; i++) {
						if (pModel.getInvenFULL()[i] != null) {
							if (pModel.getInventory(i).getTag().getName().toLowerCase()
									.equals(secondW) && j1) {
								temp = pModel.getInventory(i);
								pModel.removeItem(i);
								j1 = false;
								//UPDATE DATABASE HERE - REMOVE ITEM FROM PLAYER INVENTORY
							}
						}
					}
					int NEWINUM = pController.sortInven(pModel.getInvenFULL());
					pModel.setiNum(NEWINUM);
					pModel.setHP(pModel.getHP() + temp.getThing());
					result = "You used the " + temp.getTag().getName();
					errorMessage = "Current health == " + pModel.getHP();
					//UPDATE HEALTH DB
			 } else {
				 result = "You dont have any " + action[1];
			 }
		 }
		 
		 //deals with INSPECT command - done I think - no DB stuff
		 if (action[0].contentEquals("inspect")) {
			 model.indicateInspect(true);
			 if (action[1].contentEquals(null)) {
				 result = "Inspect what?";
			 }
			 else if (currentR.isDark() && !pModel.isLit()) {
					result = "The room is entirely dark, you look down and notice you can't even see your feet.";
			}
			 else if (action[1].contentEquals("room")) {
				 result = "The room contains: ";
				 for (int i = 0; i < currentR.getInven().length; i++) {
						if (currentR.getInven()[i] != null) {
							result += currentR.getInven()[i].getTag().getName() + " and ";
						}
				 }
				 if (currentR.hasMonster()) {
						result += currentR.getMonster().getNameTag().getName() + " and ";
				}else {
						result += "that's all";
						result = currentR.getTag().getDesc() + result;
					}
			 }
			 else if (action[1].contentEquals("inventory")){
				 result = "You rifle through your pack and find: ";
				 int r = 0;
					for (int i = 0; i < pModel.getInvenFULL().length; i++) {
						if (pModel.getInvenFULL()[i] != null) {
							result += pModel.getInvenFULL()[i].getTag().getName() + ", ";
							r++;
						}
					}
					if (r == 0) {
						result += "it's empty.";
					} else {
					result = "You fail to inspect the " + action[1];
					}
			 }
			 else {
				 result = "That is not a command I recognize";
			 }
	}
		 
		 
		 //deals with LIGHT command - done I think - DB stuff but idk what yet
		 if (action[0].contentEquals("light")) {
			 if (action[1].contentEquals("torch")) {
				 if (pModel.getMatches() > 0) {
					 pModel.setMatches(pModel.getMatches() - 1);
					 pModel.setLit(true);
					 result = "You take out your pack of matches and strike one, lighting the torch and throwing out the now-burnt match.";
				 }
				 else {
					 result = "Uh oh, you are out of matches";
				 } 
			 }
			 else {
				 result = "You are unable to light " + action[1] + " on fire";
			 }
		 }
		 
		 //deals with OPEN command - almost done [NEED WIN SCREEN]
		 if (action[0].contentEquals("open")){
			 if (action[1].contentEquals("chest")) {
				 if (currentR.hasChest()) {
					 if (pModel.getHasKey()) {
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
				 result = "You fail to open " + action[1];
			 }
		 }
		 
		 
		req.setAttribute("action", req.getParameter("action"));

		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("result", result);

		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/main.jsp").forward(req, resp);
	}
}
