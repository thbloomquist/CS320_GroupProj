package edu.ycp.cs320.groupProj.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.groupProj.controller.SystemController;
import edu.ycp.cs320.groupProj.model.SystemModel;
import edu.ycp.cs320.groupProj.model.DirectionsModel;
import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.controller.ObjectController;

public class ConsoleServlet extends HttpServlet {
	Boolean movement = false;
	private static final long serialVersionUID = 1L;
	SystemModel model = new SystemModel();
	DirectionsModel dModel = new DirectionsModel();
	PlayerModel pModel = new PlayerModel();
	ObjectModel oModel = new ObjectModel();
	ObjectModel currentObj = null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("AddNumbers Servlet: doGet");

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

		//
		String errorMessage = null;
		//
		String result = null;
		try {
			String action = getStringFromParameter(req.getParameter("action"));

			// find a way to take the webpage to http://localhost:8081/TextQuest/dead when
			// their hp reaches 0 or lower
			if (action == null) {
				errorMessage = "Please specify an action or type 'help' for a list of commands.";
			}
			else if(action.toLowerCase().compareTo("make banana") == 0) {
				currentObj = oController.banana();
			}
			else if(action.toLowerCase().compareTo("inventory") == 0) {
				boolean rug = false;
				ObjectModel[] thingy = pModel.getInvenFULL();
				for(int i = 0; i < 10; i++) {
					if(thingy[i] != null) {
						rug = true;
					}
				}
				if(rug) {
					result = pModel.getiNum() + "x " + pModel.getInventory(0).getDesc();
				} else {
					result = "Your inventory is empty";
				}
			}
			else if(action.toLowerCase().compareTo("inspect") == 0) {
				if(currentObj != null) {
					result = "There is a banana in the room.";
				} else {
					result = "The room is empty.";
				}
			}
			else if(model.getUse()) {
				if(action.toLowerCase().compareTo("banana") == 0) {
					ObjectModel[] temp = pModel.getInvenFULL();
					boolean bla = false;
					for(int i = 0; i < 10; i++) {
						if(temp[i] != null) {
							bla = true;
						}
					}
					if(bla) {
						pModel.setiNum(pModel.getiNum()-1);
						ObjectModel tempp = pModel.getInventory(pModel.getiNum());
						pModel.removeItem(pModel.getiNum());
						pModel.setHP(pModel.getHP() + tempp.getThing());
						result = "You eat the banana, yum.";
					} else {
						result = "You don't have any bananas.";
					}
				}
				model.indicateUse(false);
			}
			else if (action.toLowerCase().compareTo("reset") == 0) {
				pModel.reset();
				dModel.reset();
				result = "Everything's back to normal.";
			} else if (action.toLowerCase().compareTo("direction") == 0) {
				result = "North == " + dModel.getNorth() + " South == " + dModel.getSouth() + " East == "
						+ dModel.getEast() + " West == " + dModel.getWest();
			} else if (action.toLowerCase().compareTo("totals") == 0) {
				result = "UpTotal== " + dModel.getUp() + " SideTotal== " + dModel.getSide();
			} else if (action.toLowerCase().compareTo("help") == 0) {
				result = "Use - Uses an item within your inventory. Grab - grabs an item within the room. Move - Moves"
						+ " location.  !!!!The rest is Work In Progress.";
			} else if (model.getMovement()) {
				if (action.toLowerCase().compareTo("north") == 0 || action.toLowerCase().compareTo("west") == 0
						|| action.toLowerCase().compareTo("south") == 0
						|| action.toLowerCase().compareTo("east") == 0) {

					if (action.toLowerCase().compareTo("north") == 0) {
						dModel.moveNorth();
						result = "You moved " + action;
						controller.setMovement(false);
						movement = model.getMovement();
					} else if (action.toLowerCase().compareTo("south") == 0) {
						dModel.moveSouth();
						result = "You moved " + action;
						controller.setMovement(false);
						movement = model.getMovement();
					} else if (action.toLowerCase().compareTo("east") == 0) {
						dModel.moveEast();
						result = "You moved " + action;
						controller.setMovement(false);
						movement = model.getMovement();
					} else if (action.toLowerCase().compareTo("west") == 0) {
						dModel.moveWest();
						result = "You moved " + action;
						controller.setMovement(false);
						movement = model.getMovement();
					}
				} else {
					result = "That is not a valid direction.";
					controller.setMovement(false);
					movement = model.getMovement();
				}
			} else if(model.getGrab()) {
				if(action.toLowerCase().compareTo("banana") == 0) {
					if(currentObj != null) {
						pModel.addInventory(currentObj);
						currentObj = null;
						model.indicateGrab(false);
					} else {
						result = "What banana?";
						model.indicateGrab(false);
					}
				}
				else {
					result = "Invalid item.";
					model.indicateGrab(false);;
				}
			}
			else if (action.toLowerCase().compareTo("move") == 0) {
				controller.setMovement(true);
				movement = model.getMovement();
				result = "Enter a direction you'd like to move.";
			} else if(action.toLowerCase().compareTo("grab") == 0) {
				model.indicateGrab(true);
				result = "What would you like to grab?";
			}
			else if(action.toLowerCase().compareTo("use") == 0) {
				model.indicateUse(true);
				result = "Use what?";
			}
			else if (action.toLowerCase().compareTo("health") == 0) {
				result = " " + pModel.getHP();
			} else if (action.toLowerCase().compareTo("punch") == 0) {
				pModel.setHP(pModel.getHP() - 5);
				result = "There were no monsters in the room so you punched yourself.";
				if(pModel.getHP() <= 0) {
					pModel.reset();
					dModel.reset();
					req.getRequestDispatcher("/_view/dead2.jsp").forward(req, resp);
				}
			} else if (action.toLowerCase().compareTo("kick") == 0) {
				pModel.setHP(pModel.getHP() - 10);
				result = "There were no monsters in the room so you kicked yourself.";
				if(pModel.getHP() <= 0) {
					pModel.reset();
					dModel.reset();
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
