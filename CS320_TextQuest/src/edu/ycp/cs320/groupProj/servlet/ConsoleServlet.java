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

public class ConsoleServlet extends HttpServlet {
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
		 
		//
		String result = "";
		try {
			firstW = null;
			secondW = null;
			// firstW & secondW have to be reset b/c if they enter a one word command it'll
			// re-run the previously entered command
			String action = getStringFromParameter(req.getParameter("action")).toLowerCase();
			
			//////// Move for DB ////////
			DBController DBController = new DBController(); // Database controller
			HttpSession session = req.getSession(false); // get current session
			session.setAttribute("playerModel", pModel);
			Player player = (Player) session.getAttribute("player"); // get player from session
			DBController.getPlayerByUsernameAndPassword(player.getUsername(), player.getPassword()); // get current player model
			DBController.InsertNewMove(player.getPlayerId(), 1, action);
			System.out.println("Player entered : " + action);
			//////// Move for DB ////////
			
			if (action.indexOf(" ") != -1) {
				int firstS = action.indexOf(" ");
				firstW = action.substring(0, firstS).toLowerCase();
				secondW = action.substring(firstS + 1, action.length()).toLowerCase();
			}
			Room currentR = map.getRoom(pModel.getUp(), pModel.getSide());
			rController.setModel(currentR);
			errorMessage = "Current health == " + pModel.getHP();

			if (action == null) {
				errorMessage = "Please specify an action or click 'Stuck?' for a list of commands.";
			} else if (action.compareTo("where") == 0) {
				result = "UpPlace ==" + pModel.getUp() + " sidePlace == " + pModel.getSide();
			} else if (model.getInspect()) {
				if (action.compareTo("room") == 0) {
					String s = " the room contains: ";
					for (int i = 0; i < currentR.getInven().length; i++) {
						if (currentR.getInven()[i] != null) {
							s += currentR.getInven()[i].getTag().getDesc() + " and ";
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
				} else if (action.compareTo("inventory") == 0) {
					String s = "You rifle through your pack and find: ";
					int r = 0;
					for (int i = 0; i < pModel.getInvenFULL().length; i++) {
						if (pModel.getInvenFULL()[i] != null) {
							s += pModel.getInvenFULL()[i].getTag().getDesc() + ", ";
							r++;
						}
					}
					if (r == 0) {
						s += "it's empty.";
					}
					result = s;
				} else {
					result = "Invalid input.";
				}
				model.indicateInspect(false);
			} else if (action.compareTo("look") == 0) {
				NameTag n = map.getRoom(pModel.getUp(), pModel.getSide()).getTag();
				String s = n.getName();
				result = s;
			} else if (model.getPlace()) {
				if (pController.contains(action)) {
					ObjectModel tempr = null;
					Boolean only1 = true;
					for (int l = 0; l < pModel.getInvenFULL().length; l++) {
						if (pModel.getInventory(l) != null) {
							if (pModel.getInventory(l).getTag().getName().toLowerCase().equals(action)
									&& only1) {
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
						if (currentR.getInven()[i] == null & firstI) {
							temp = i;
							firstI = false;
							// this should return the first index that is "open" for an item input
						}
					}

					if (temp == -1) {
						result = "The room is full.";
					} else {
						currentR.getInven()[temp] = tempr;
						result = "You placed the " + action + " on the floor.";
					}
				} else {
					result = "You dont have a " + action;
				}
				model.indicatePlace(false);
			} else if (model.getUse()) {
				if (pController.contains(action)) {
					Boolean j1 = true;
					ObjectModel temp = null;
					for (int i = 0; i < pModel.getInvenFULL().length; i++) {
						if (pModel.getInvenFULL()[i] != null) {
							if (pModel.getInventory(i).getTag().getName().toLowerCase().equals(action)
									&& j1) {
								temp = pModel.getInventory(i);
								pModel.removeItem(i);
								j1 = false;
							}
						}
					}
					pModel.setHP(pModel.getHP() + temp.getThing());
					result = "You used the " + temp.getTag().getName();
					errorMessage = "Current health == " + pModel.getHP();
					//UPDATE DB - HEALTH
				} else {
					result = "You don't have a " + action;
				}

				model.indicateUse(false);
			} else if (model.getMovement()) {
				if (action.compareTo("north") == 0 || action.compareTo("west") == 0
						|| action.compareTo("south") == 0
						|| action.compareTo("east") == 0) {

					if (action.compareTo("north") == 0) {
						if (map.getRoom(pModel.getUp() - 1, pModel.getSide()).getEnter()) {
							pModel.setUpLoc(pModel.getUp() - 1);
							result = "You moved " + action;
						} else {
							result = "The path is blocked.";
						}
						controller.setMovement(false);
						movement = model.getMovement();
					} else if (action.compareTo("south") == 0) {
						if (map.getRoom(pModel.getUp() + 1, pModel.getSide()).getEnter()) {
							pModel.setUpLoc(pModel.getUp() + 1);
							result = "You moved " + action;
						} else {
							result = "The path is blocked";
						}
						controller.setMovement(false);
						movement = model.getMovement();
					} else if (action.compareTo("east") == 0) {
						if (map.getRoom(pModel.getUp(), pModel.getSide() + 1).getEnter()) {
							pModel.setSideLoc(pModel.getSide() + 1);
							result = "You moved " + action;
						} else {
							result = "The path is blocked";
						}
						controller.setMovement(false);
						movement = model.getMovement();
					} else if (action.compareTo("west") == 0) {
						if (map.getRoom(pModel.getUp(), pModel.getSide() - 1).getEnter()) {
							pModel.setSideLoc(pModel.getSide() - 1);
							result = "You moved " + action;
						} else {
							result = "The path is blocked";
						}
						controller.setMovement(false);
						movement = model.getMovement();
					}
				} else {
					result = "That is not a valid direction.";
					controller.setMovement(false);
					movement = model.getMovement();
				}
			} else if (model.getGrab()) {
				if (currentR.hasMonster()) {
					result = "The " + currentR.getMonster().getNameTag().getName()
							+ " blocks your way. You'll have to kill it to loot this room.";
				} else if (rController.contains(action)) {
					ObjectModel[] temp = new ObjectModel[10];
					int num = 0;
					for (int i = 0; i < currentR.getInven().length; i++) {
						if (currentR.getInven()[i] != null) {
							temp[num] = currentR.getInven()[i];
							num++;
						}
					}
					Boolean only1 = true;
					for (int i = 0; i < num; i++) {
						if (action.compareTo(temp[i].getTag().getName()) == 0 && only1) {
							pModel.addInventory(temp[i]);
							result = "You grabbed the " + temp[i].getTag().getName();
							temp[i] = null;
							only1 = false;
							//UPDATE DB HERE - ADD ITEM TO INVENTORY
						}
					}
					for (int i = 0; i < currentR.getInven().length; i++) {
						currentR.getInven()[i] = temp[i];
					}
				} else {
					result = "The room doesn't contain any: " + action;
				}
				model.indicateGrab(false);
			} else if (action.compareTo("move") == 0) {
				controller.setMovement(true);
				movement = model.getMovement();
				result = "Enter a direction you'd like to move.";
			} else if (action.compareTo("grab") == 0) {
				model.indicateGrab(true);
				result = "What would you like to grab?";
			} else if (action.compareTo("use") == 0) {
				model.indicateUse(true);
				result = "Use what?";
			} else if (action.compareTo("inspect") == 0) {
				model.indicateInspect(true);
				result = "Inspect what?";
			} else if (action.compareTo("place") == 0) {
				model.indicatePlace(true);
				result = "Place what?";
			} else if (action.compareTo("fight") == 0) {
				if (currentR.hasMonster()) {
					result = "The " + currentR.getMonster().getNameTag().getName()
							+ " bites you, but you manage to beat it.";
					pModel.setHP(pModel.getHP() - currentR.getMonster().getDMG());
					currentR.deadMonster();
					errorMessage = "Current health == " + pModel.getHP();
					//UPDATE DB - HEALTH
				} else {
					result = "You fight yourself, you manage to both lose and win.";
					pModel.setHP(pModel.getHP() - 25);
					errorMessage = "Current health == " + pModel.getHP();
					//UPDATE DB - HEALTH
				}

				// reset all models & controllers if player health reaches 0.
				// game can be replayed through hyperlink buttons
				if (pModel.getHP() <= 0) {
					pModel.reset();
					map.reset();
					req.getRequestDispatcher("/_view/dead2.jsp").forward(req, resp);
				}
			} else {
				result = "Invalid command.";
			}

			if (firstW != null & secondW != null) {
				if (firstW.compareTo("move") == 0) {
					if (secondW.compareTo("north") == 0) {
						// checks to see if the tile directly above is traversable
						if (map.getRoom(pModel.getUp() - 1, pModel.getSide()).getEnter()) {
							pModel.setUpLoc(pModel.getUp() - 1); // updates player location
							result = "You moved " + secondW;
						} else {
							result = "The path is blocked.";// isn't traversable- no change
						}
					} else if (secondW.compareTo("south") == 0) {
						// checks to see if the tile directly below is traversable
						if (map.getRoom(pModel.getUp() + 1, pModel.getSide()).getEnter()) {
							pModel.setUpLoc(pModel.getUp() + 1); // updates player location
							result = "You moved " + secondW;
						} else {
							result = "The path is blocked";// isn't traversable- no change
						}
					} else if (secondW.compareTo("east") == 0) {
						// checks to see if the tile directly right is traversable
						if (map.getRoom(pModel.getUp(), pModel.getSide() + 1).getEnter()) {
							pModel.setSideLoc(pModel.getSide() + 1); // updates player location
							result = "You moved " + secondW;
						} else {
							result = "The path is blocked"; // isn't traversable- no change
						}
					} else if (secondW.compareTo("west") == 0) {
						// checks to see if the tile directly left is traversable
						if (map.getRoom(pModel.getUp(), pModel.getSide() - 1).getEnter()) {
							pModel.setSideLoc(pModel.getSide() - 1);
							result = "You moved " + secondW; // updates player location
						} else {
							result = "The path is blocked"; // isn't traversable- no change
						}
					} else {
						result = "That isn't a valid direction";
					}
				} else if (firstW.compareTo("use") == 0) {
					if (pController.contains(secondW)) {
						Boolean j1 = true;
						ObjectModel temp = null;
						for (int i = 0; i < pModel.getInvenFULL().length; i++) {
							if (pModel.getInvenFULL()[i] != null) {
								if (pModel.getInventory(i).getTag().getName().toLowerCase()
										.equals(secondW) && j1) {
									temp = pModel.getInventory(i);
									pModel.removeItem(i);
									j1 = false;
									//UPDATE DATABASE HERE - REMOVE ITEM FROM DB
								}
							}
						}
						int NEWINUM = pController.sortInven(pModel.getInvenFULL());
						pModel.setiNum(NEWINUM);
						pModel.setHP(pModel.getHP() + temp.getThing());
						result = "You used the " + temp.getTag().getName();
						errorMessage = "Current health == " + pModel.getHP();
						//UPDATE DB - HEALTH
					} else {
						result = "You don't have any " + secondW;
					}
				} else if (firstW.compareTo("grab") == 0) {
					if (currentR.hasMonster()) {
						result = "The " + currentR.getMonster().getNameTag().getName()
								+ " blocks your way. You'll have to kill it to loot this room.";
					} else if (pModel.getiNum() == 9) {
						result = "Your inventory is full.";
					} else if (currentR.isDark() && !pModel.isLit()) {
						result = "It's too dark to try to grab anything";
						if (currentR.hasMonster()) {
							result += ", but you hear a strange hissing coming from directly in front of you.";
						}
					} else {
						if (secondW.compareTo("key") == 0) {
							// if room hasKey() add key
						} else if (rController.contains(secondW)) {
							ObjectModel[] temp = new ObjectModel[10];
							int num = 0;
							for (int i = 0; i < currentR.getInven().length; i++) {
								if (currentR.getInven()[i] != null) {
									temp[num] = currentR.getInven()[i];
									num++;
								}
							}
							Boolean only1 = true;
							for (int i = 0; i < num; i++) {
								if (secondW.compareTo(temp[i].getTag().getName()) == 0 && only1) {
									pModel.addInventory(temp[i]);
									result = "You grabbed the " + temp[i].getTag().getName();
									temp[i] = null;
									only1 = false;
								}
							}
							for (int i = 0; i < currentR.getInven().length; i++) {
								currentR.getInven()[i] = temp[i];
							}
							int NEWINUM = pController.sortInven(pModel.getInvenFULL());
							pModel.setiNum(NEWINUM);
							currentR.checkEmpty(); // if the last item in the room is taken room.isEmpty = true
						} else {
							result = "There aren't any " + secondW + " around";
						}
					}
				} else if (firstW.compareTo("place") == 0) {
					if (pController.contains(secondW)) {
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
						result = "You don't have any " + secondW;
					}
				} else if (firstW.compareTo("inspect") == 0) {
					if (currentR.isDark() && !pModel.isLit()) {
						result = "The room is entirely dark, you look down and notice you can't even see your feet.";
					} else {
						if (secondW.compareTo("room") == 0) {
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
						} else if (secondW.compareTo("inventory") == 0) {
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
						} else {
							result = "You fail to inspect the " + secondW;
						}
					}
				} else if (firstW.compareTo("look") == 0) {
					if (currentR.isDark() && !pModel.isLit()) {
						result = "It's too dark to see anything.";
					} else {
						if (secondW.compareTo("north") == 0) {
							if (map.getRoom(pModel.getUp() - 1, pModel.getSide()).getEnter()) {
								Room roomT = map.getRoom(pModel.getUp() - 1, pModel.getSide());
								result = "You look into the room to your north and see ";
								if (roomT.isDark()) {
									result += " nothing, the room is encased in shadow.";
								} else {
									if (roomT.hasMonster()) {
										result += " something shuffling about within the room, it makes you feel uneasy";
									} else if (!roomT.isEmpty()) {
										result += " something on the floor, though you can't discern what it is from here.";
									} else {
										result += " an exact copy of the room you're currently in, perhaps the dungeon is getting to your mind.";
									}
								}
							} else {
								result = "You look north and see a wall.";
							}
						} else if (secondW.compareTo("south") == 0) {
							if (map.getRoom(pModel.getUp() + 1, pModel.getSide()).getEnter()) {
								Room roomT = map.getRoom(pModel.getUp() + 1, pModel.getSide());
								result = "You look into the room to your south and see ";
								if (roomT.isDark()) {
									result += " nothing, the room is encased in shadow.";
								} else {
									if (roomT.hasMonster()) {
										result += " something shuffling about within the room, it makes you feel uneasy";
									} else if (!roomT.isEmpty()) {
										result += " something on the floor, though you can't discern what it is from here.";
									} else {
										result += " an exact copy of the room you're currently in, perhaps the dungeon is getting to your mind.";
									}
								}
							} else {
								result = "You look south and see a wall.";
							}
						} else if (secondW.compareTo("east") == 0) {
							if (map.getRoom(pModel.getUp(), pModel.getSide() + 1).getEnter()) {
								Room roomT = map.getRoom(pModel.getUp(), pModel.getSide() + 1);
								result = "You look into the room to your east and see ";
								if (roomT.isDark()) {
									result += " nothing, the room is encased in shadow.";
								} else {
									if (roomT.hasMonster()) {
										result += " something shuffling about within the room, it makes you feel uneasy";
									} else if (!roomT.isEmpty()) {
										result += " something on the floor, though you can't discern what it is from here.";
									} else {
										result += " an exact copy of the room you're currently in, perhaps the dungeon is getting to your mind.";
									}
								}
							} else {
								result = "You look east and see a wall.";
							}
						} else if (secondW.compareTo("west") == 0) {
							if (map.getRoom(pModel.getUp(), pModel.getSide() - 1).getEnter()) {
								Room roomT = map.getRoom(pModel.getUp(), pModel.getSide() - 1);
								result = "You look into the room to your west and see ";
								if (roomT.isDark()) {
									result += " nothing, the room is encased in shadow.";
								} else {
									if (roomT.hasMonster()) {
										result += " something shuffling about within the room, it makes you feel uneasy";
									} else if (!roomT.isEmpty()) {
										result += " something on the floor, though you can't discern what it is from here.";
									} else {
										result += " an exact copy of the room you're currently in, perhaps the dungeon is getting to your mind.";
									}
								}
							} else {
								result = "You look west and see a wall.";
							}
						} else if (secondW.compareTo("down") == 0) {
							result = "You look downwards and see a stone-brick flooring, it's got patches of grasses growing through the cracks.";
							// we COULD add a String in each room describing it's floor (like different
							// sections have similar flooring or whatever)
							// this also applies to the ceiling as shown below
						} else if (secondW.compareTo("up") == 0) {
							result = "You look upwards and see a tile-stone ceiling, it's got signs of aging that lead you to believe this dungeon is very old.";
						} else {
							result = "That is not a valid direction.";
						}
					}
				} else if (firstW.compareTo("light") == 0) {
					if (secondW.compareTo("torch") == 0) {
						if (pController.contains(secondW)) {
							if (pModel.getMatches() > 0) {
								pModel.setMatches(pModel.getMatches() - 1);
								pModel.setLit(true);
								result = "You take out your pack of matches and strike one, lighting the torch and throwing out the now-burnt match.";
								// maybe add a "burnt match" item on the floor of the room TBD
							} else {
								result = "You are out of matches, uh oh.";
							}
						} else {
							result = "You've lost your torch, that's not good.";
						}
					} else {
						result = "You are unable to light the " + secondW + " on fire";
					}
				} else if (firstW.compareTo("open") == 0) {
					if (secondW.compareTo("chest") == 0) {
						Boolean tempr = false;
						for (int i = 0; i < pModel.getInvenFULL().length; i++) {
							if (pModel.getInventory(i) != null) {
								if (pModel.getInventory(i).getKey()) {
									tempr = true;
								}
							}
						}
						if (tempr) {
							if (currentR.hasChest()) {
								// !!!!!!!!!!!!!!!!!!
								//
								// IMPLEMENT RE-ROUTE TO WIN SCREEN & RESET MODELS
								//
								//
								// !!!!!!!!!!!!!!!!!1
							} else {
								result = "There's no chest in this room, you should get your eyes checked.";
							}
						} else {
							result = "You don't have a key, how did you plan on opening it- brute strength?";
						}
					} else {
						result = "You fail to open " + secondW;
					}
				} else {
					result = "That's not a verb I recognize, click 'Stuck?' for a list of the commands.";
				}
			} // connected to firstW!= null dont touch

		} catch (

		NumberFormatException e) {
			errorMessage = "Invalid double";
		}

		// Add parameters as request attributes
		// this creates attributes named "first" and "second for the response, and grabs
		// the
		// values that were originally assigned to the request attributes, also named
		// "first" and "second"
		// they don't have to be named the same, but in this case, since we are passing
		// them back
		// and forth, it's a good idea
		req.setAttribute("action", req.getParameter("action"));

		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("result", result);

		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/main.jsp").forward(req, resp);
	}

	// gets double from the request with attribute named s
	private Double getDoubleFromParameter(String s) {
		if (s == null || s.equals("")) {
			return null;
		} else {
			return Double.parseDouble(s);
		}
	}

	private String getStringFromParameter(String s) {
		if (s == null || s.equals("")) {
			return null;
		} else {
			return s;
		}
	}
}
