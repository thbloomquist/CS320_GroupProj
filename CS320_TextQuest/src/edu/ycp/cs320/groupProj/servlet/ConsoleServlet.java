package edu.ycp.cs320.groupProj.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.groupProj.controller.SystemController;
import edu.ycp.cs320.groupProj.model.SystemModel;
import edu.ycp.cs320.groupProj.model.DirectionsModel;

public class ConsoleServlet extends HttpServlet {
	Boolean movement = false;
	int hp = 100;
	private static final long serialVersionUID = 1L;
	SystemModel model = new SystemModel();
	DirectionsModel dModel = new DirectionsModel();
	Boolean dCheck = false;
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("AddNumbers Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/main.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("AddNumbers Servlet: doPost");
		SystemController controller = new SystemController();
		controller.setModel(model);
		
		//
		String errorMessage = null;
		// 
		String result = null;
		// FIND SOME WAY TO KEEP TRACK OF NORTH, SOUTH, EAST, AND WEST VALUES IN MODEL CLASS!!!!
		try {
			String action = getStringFromParameter(req.getParameter("action"));

			// check for errors in the form data before using is in a calculation
			if (action == null) {
				errorMessage = "Please specify an action or type 'help' for a list of commands.";
			} 
			else if(action.toLowerCase().compareTo("direction") == 0) {
				result = "North == " + dModel.getNorth() + " South == " + dModel.getSouth() + 
						" East == " + dModel.getEast() + " West == " + dModel.getWest();
			}
			else if(action.toLowerCase().compareTo("totals") == 0) {
				result = "UpTotal== " + dModel.getUp() + " SideTotal== " + dModel.getSide();
			}
			else if (action.toLowerCase().compareTo("help") == 0) {
				result = "LOL!";
			} 
			else if(movement) {
				if(action.toLowerCase().compareTo("north") == 0 || action.toLowerCase().compareTo("west") == 0 || 
						action.toLowerCase().compareTo("south") == 0 || action.toLowerCase().compareTo("east") == 0) {
					
					if(action.toLowerCase().compareTo("north") == 0) {
						dModel.moveNorth();
						result = "You moved " + action;
						controller.setMovement(false);
						movement = model.getMovement();
					} else if(action.toLowerCase().compareTo("south") == 0) {
						dModel.moveSouth();
						result = "You moved " + action;
						controller.setMovement(false);
						movement = model.getMovement();
					} else if(action.toLowerCase().compareTo("east") == 0) {
						dModel.moveEast();
						result = "You moved " + action;
						controller.setMovement(false);
						movement = model.getMovement();
					} else if(action.toLowerCase().compareTo("west") == 0) {
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
			} 
			else if(action.toLowerCase().compareTo("move") == 0) {
				controller.setMovement(true);
				movement = model.getMovement();
				result = "Enter a direction you'd like to move.";
			} 
			else {
				result = action;
			}
		} catch (NumberFormatException e) {
			errorMessage = "Invalid double";
		}
		
		// Add parameters as request attributes
		// this creates attributes named "first" and "second for the response, and grabs the
		// values that were originally assigned to the request attributes, also named "first" and "second"
		// they don't have to be named the same, but in this case, since we are passing them back
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
		if(s == null || s.equals("")) {
			return null;
		} else {
			return s;
		}
	}
}
