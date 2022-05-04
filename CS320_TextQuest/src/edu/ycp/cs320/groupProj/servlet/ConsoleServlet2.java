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
		Room roomSouth = map.getRoom(pModel.getUp() - 1, pModel.getSide());
		Room roomWest = map.getRoom(pModel.getUp(), pModel.getSide() - 1);
		Room roomEast = map.getRoom(pModel.getUp(), pModel.getSide() + 1);

		
		
		rController.setModel(currentR);
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

		// splits input into a two String array
		for (int i = 0; i < 2; i++) {
			action = userInput.split(" ");
		}
		
		action[0].toLowerCase();
		action[1].toLowerCase();
		
		if(action[0].equals("fight") && currentR.hasMonster()) {
			result = "The " + currentR.getMonster().getNameTag().getName() + " manages to claw you before you fell the beast.";
			pModel.setHP(pModel.getHP()-currentR.getMonster().getDMG());
			currentR.deadMonster();
			if(pModel.isLit()) {
				result += " During the scuffle your torch went out.";
				pModel.setLit(false);
			}
		} else if(action[0].equals("fight") && !currentR.hasMonster()) {
			result = "There's no monster here, unless you're counting yourself.";
		}

		// move done
		if (action[0].equals(null)) {
			errorMessage = "Please specify an action or click 'Stuck?' for a list of commands.";
		}

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
						result = "You moved " + action[1];
						did = true;
					} else {
						result = "The path is blocked.";// isn't traversable- no change
					}
				} else if (action[1].equals("south")) {
					if (map.getRoom(pModel.getUp() + 1, pModel.getSide()).getEnter()) {
						pModel.setUpLoc(pModel.getUp() + 1); // updates player location
						result = "You moved " + action[1];
						did = true;
					} else {
						result = "The path is blocked";// isn't traversable- no change
					}
				} else if (action[1].equals("east")) {
					if (map.getRoom(pModel.getUp(), pModel.getSide() + 1).getEnter()) {
						pModel.setSideLoc(pModel.getSide() + 1); // updates player location
						result = "You moved " + action[1];
						did = true;
					} else {
						result = "The path is blocked"; // isn't traversable- no change
					}
				} else if (action[1].equals("west")) {
					if (map.getRoom(pModel.getUp(), pModel.getSide() - 1).getEnter()) {
						pModel.setSideLoc(pModel.getSide() - 1);
						result = "You moved " + action[1]; // updates player location
						did = true;
					} else {
						result = "The path is blocked"; // isn't traversable- no change
					}
				} else {
					result = "That isn't a valid direction.";
				}
			}
			if (did) {
				pController.upScore(action[0]);
			}
		}

		// deals with LOOK command - done I think
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
				} else if (!roomNorth.isEmpty()) {
					result += " something on the floor, you can't discern what it is from here.";
				}
				if (roomNorth.isDark()) {
					result = " a room encased in shadows, you can't see anything.";
				}
			} else if (action[1].equals("south") && roomSouth.getEnter()) {
				if (roomSouth.hasMonster()) {
					result += " something shuffling around in the room, it makes you feel uneasy.";
				} else if (!roomSouth.isEmpty()) {
					result += " something on the floor, you can't discern what it is from here.";
				}
				if (roomSouth.isDark()) {
					result = " a room encased in shadows, you can't see anything.";
				}
			} else if (action[1].equals("east") && roomEast.getEnter()) {
				if (roomEast.hasMonster()) {
					result += " something shuffling around in the room, it makes you feel uneasy.";
				} else if (!roomEast.isEmpty()) {
					result += " something on the floor, you can't discern what it is from here.";
				}
				if (roomEast.isDark()) {
					result = " a room encased in shadows, you can't see anything.";
				}
			} else if (action[1].equals("west") && roomWest.getEnter()) {
				if (roomWest.hasMonster()) {
					result += " something shuffling around in the room, it makes you feel uneasy.";
				} else if (!roomWest.isEmpty()) {
					result += " something on the floor, you can't discern what it is from here.";
				}
				if (roomWest.isDark()) {
					result = " a room encased in shadows, you can't see anything.";
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
			if (pModel.getiNum() == 9) {
				result = "Your inventory is full.";
			} else if (currentR.isDark() && !pModel.isLit()) {
				result = "It's too dark to try to grab anything";
				if (currentR.hasMonster()) {
					result += ", but you hear a strange hissing coming from directly in front of you. You should light your torch fast!";
				}
			} else if (currentR.hasMonster()) {
				result = "The " + currentR.getMonster().getNameTag().getName() + " blocks your way.";
			} else {
				if (rController.contains(action[1])) {
					Boolean thing = pController.grabItem(action[1], currentR); // good function great function
					if (thing) {
						result = "You grab the " + action[1];
						int NEWINUM = pController.sortInven(pModel.getInvenFULL());
						pModel.setiNum(NEWINUM);
						currentR.checkEmpty();
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
				} else {
					result = "You don't have any " + action[1];
				}
			}
			// check if place is valid + update inventory
			// most code is contained within controller(s)
		}

		// deals with USE command
		if (action[0].equals("use")) {
			if (currentR.isDark() && !pModel.isLit()) {
				result = "It's too dark to try to grab anything from your pack";
				if (currentR.hasMonster()) {
					result += ", but you hear a strange hissing coming from directly in front of you. You should light your torch fast!";
				}
			} else {
				if (pController.contains(action[1])) {
					result = pController.useItem(action[1], currentR);
					errorMessage = "Current health == " + pModel.getHP();
					pController.upScore(action[0]);
					//
					//
					// UPDATE DB - HEALTH && SCORE
					//
					//
				} else {
					result = "You don't have any " + action[1];
				}
			}
		}

		// deals with INSPECT command
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
							s += currentR.getInven()[i].getTag().getName() + " and ";
						}
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
					pModel.setLit(true);
					result = "You take out your pack of matches and strike one, lighting the torch and throwing out the now-burnt match.";
					pController.upScore(action[0]);
					if (currentR.hasMonster()) {
						result += " As the room fills with the light of torch, you see a "
								+ currentR.getMonster().getNameTag().getName()
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
							// CREATE WIN SCREEN HERE
						} else {
							result = "You don't have a key, how did you plan on opening it - brute strength?";
						}
					} else {
						result = "There is no chest in this room, you should get your eyes checked";
					}
				}

				else {
					result = "You fail to open " + action[1];
				}
			}
		}
		
		errorMessage = "Current health == " + pModel.getHP();
		
		req.setAttribute("action", req.getParameter("action"));

		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("result", result);

		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/main.jsp").forward(req, resp);
	}
}