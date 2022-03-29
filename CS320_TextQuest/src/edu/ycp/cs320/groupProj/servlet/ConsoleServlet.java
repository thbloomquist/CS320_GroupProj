package edu.ycp.cs320.groupProj.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.groupProj.controller.SystemController;
import edu.ycp.cs320.groupProj.model.DirectionsModel;
import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.controller.ObjectController;
import edu.ycp.cs320.groupProj.model.NameTag;
import edu.ycp.cs320.groupProj.model.Map;
import edu.ycp.cs320.groupProj.model.Room;
import edu.ycp.cs320.groupProj.model.SystemModel;

public class ConsoleServlet extends HttpServlet {
	Boolean movement = false;
	private static final long serialVersionUID = 1L;
	SystemModel model = new SystemModel();
	DirectionsModel dModel = new DirectionsModel();
	PlayerModel pModel = new PlayerModel();
	ObjectModel oModel = new ObjectModel();
	ObjectModel currentObj = null;
	Map map = new Map();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


		System.out.println("Console: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/main.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("AddNumbers Servlet: doPost");
		SystemController controller = new SystemController();
		ObjectController oController = new ObjectController();
		controller.setModel(model);
		oController.setModel(oModel);

		// TO DO
		// IMPLEMENT GRABBING AND PLACING ITEMS, ALONG WITH THEIR USES!!!!!!!
		// TO DO
		String errorMessage = null;
		//
		String result = "";
		try {
			String action = getStringFromParameter(req.getParameter("action"));
			Room currentR = map.getRoom(pModel.getUp(), pModel.getSide());
			if (action == null) {
				errorMessage = "Please specify an action or type 'help' for a list of commands.";
			} else if (model.getInspect()) {
				if (action.toLowerCase().compareTo("room") == 0) {
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
				} else if (action.toLowerCase().compareTo("monster") == 0) {
					if (map.getRoom(pModel.getUp(), pModel.getSide()).hasMonster()) {
						result = map.getRoom(pModel.getUp(), pModel.getSide()).getMonster().getNameTag().getDesc();
					} else {
						result = "There's no monster in the room, unless you consider yourself evil.";
					}
				} else if (action.toLowerCase().compareTo("inventory") == 0) {
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
			} else if (action.toLowerCase().compareTo("look") == 0) {
				NameTag n = map.getRoom(pModel.getUp(), pModel.getSide()).getTag();
				String s = n.getName();
				result = s;
			} else if (model.getUse()) {

				model.indicateUse(false);
			} else if (action.toLowerCase().compareTo("direction") == 0) {
				result = "North == " + dModel.getNorth() + " South == " + dModel.getSouth() + " East == "
						+ dModel.getEast() + " West == " + dModel.getWest();
			} else if (action.toLowerCase().compareTo("totals") == 0) {
			} 
			else if(action.toLowerCase().compareTo("direction") == 0) {
				result = "North == " + dModel.getNorth() + " South == " + dModel.getSouth() + 
						" East == " + dModel.getEast() + " West == " + dModel.getWest();
			}
			
			else if(action.toLowerCase().compareTo("totals") == 0) {
				result = "UpTotal== " + dModel.getUp() + " SideTotal== " + dModel.getSide();
			} else if (action.toLowerCase().compareTo("help") == 0) {
				result = "Use - Uses an item within your inventory. Grab - grabs an item within the room. Move - Moves"
						+ " location.  !!!!The rest is Work In Progress.";
			} else if (model.getMovement()) {
				if (action.toLowerCase().compareTo("north") == 0 || action.toLowerCase().compareTo("west") == 0
						|| action.toLowerCase().compareTo("south") == 0
						|| action.toLowerCase().compareTo("east") == 0) {

					if (action.toLowerCase().compareTo("north") == 0) {
						if (map.getRoom(pModel.getUp() - 1, pModel.getSide()).getEnter()) {
							pModel.setUpLoc(pModel.getUp() - 1);
							result = "You moved " + action;
						} else {
							result = "The path is blocked.";
						}
						controller.setMovement(false);
						movement = model.getMovement();
					} else if (action.toLowerCase().compareTo("south") == 0) {
						if (map.getRoom(pModel.getUp() + 1, pModel.getSide()).getEnter()) {
							pModel.setUpLoc(pModel.getUp() + 1);
							result = "You moved " + action;
						} else {
							result = "The path is blocked";
						}
						controller.setMovement(false);
						movement = model.getMovement();
					} else if (action.toLowerCase().compareTo("east") == 0) {
						if (map.getRoom(pModel.getUp(), pModel.getSide() + 1).getEnter()) {
							pModel.setSideLoc(pModel.getSide() + 1);
							result = "You moved " + action;
						} else {
							result = "The path is blocked";
						}
						controller.setMovement(false);
						movement = model.getMovement();
					} else if (action.toLowerCase().compareTo("west") == 0) {
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
				} else {
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
						if (action.toLowerCase().compareTo(temp[i].getTag().getName()) == 0 && only1) {
							pModel.addInventory(temp[i]);
							result = "You grabbed the " + temp[i].getTag().getName();
							temp[i] = null;
							only1 = false;
						}
					}
					for(int i = 0; i < currentR.getInven().length; i++) {
						currentR.getInven()[i] = temp[i];
					}
				}
				model.indicateGrab(false);
			} else if (action.toLowerCase().compareTo("move") == 0) {
				controller.setMovement(true);
				movement = model.getMovement();
				result = "Enter a direction you'd like to move.";
			} else if (action.toLowerCase().compareTo("grab") == 0) {
				model.indicateGrab(true);
				result = "What would you like to grab?";
			} else if (action.toLowerCase().compareTo("use") == 0) {
				model.indicateUse(true);
				result = "Use what?";
			} else if (action.toLowerCase().compareTo("inspect") == 0) {
				model.indicateInspect(true);
				result = "Inspect what?";
			} else if (action.toLowerCase().compareTo("health") == 0) {
				result = " " + pModel.getHP();
			} else if (action.toLowerCase().compareTo("fight") == 0) {
				if (currentR.hasMonster()) {
					result = "The " + currentR.getMonster().getNameTag().getName()
							+ " bites you, but you manage to beat it.";
					pModel.setHP(pModel.getHP() - currentR.getMonster().getDMG());
					currentR.deadMonster();
				} else {
					result = "You fight yourself, you manage to both lose and win.";
					pModel.setHP(pModel.getHP() - 25);
				}
				if (pModel.getHP() <= 0) {
					pModel.reset();
					dModel.reset();
					map.reset();
					req.getRequestDispatcher("/_view/dead2.jsp").forward(req, resp);
				}
			} else {
				result = "Invalid command.";
			}
		} catch (NumberFormatException e) {
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
