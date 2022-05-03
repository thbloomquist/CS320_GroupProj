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
import edu.ycp.cs320.groupProj.controller.GameController;
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
//	SystemModel model = new SystemModel();
//	PlayerModel pModel = new PlayerModel();
//	ObjectModel oModel = new ObjectModel();
//	ObjectModel currentObj = null;
//	Map map = new Map();
	String errorMessage = "Current health == "; //FIX LATER


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
		GameController controller = new GameController();
//		SystemController controller = new SystemController();
//		ObjectController oController = new ObjectController();
//		RoomController rController = new RoomController();
//		PlayerController pController = new PlayerController();
//		pController.setModel(pModel);
//		controller.setModel(model);
//		oController.setModel(oModel);
		String userInput = req.getParameter("action").toLowerCase();
		String[] action = null;
		String result = "";
//		Room currentR = map.getRoom(pModel.getUp(), pModel.getSide());\
		Room currentR = controller.getRoom();
//		Room roomNorth = map.getRoom(pModel.getUp()-1, pModel.getSide());
//		Room roomSouth = map.getRoom(pModel.getUp()-1, pModel.getSide());
//		Room roomWest = map.getRoom(pModel.getUp(), pModel.getSide()-1);
//		Room roomEast = map.getRoom(pModel.getUp(), pModel.getSide()+1);
		
		
		HttpSession session = req.getSession(true);
		if(session == null) {
			req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
		}
		
		//prints map - for testing purposes
//		 for (int i = 0; i < 10; i++) { 
//			 for (int j = 0; j < 10; j++) {
//			 if(map.getRoom(j, i).getEnter()) { 
//				 	System.out.print(" [O] "); 
//				 } else
//					 System.out.print(" [X] "); 
//			 } System.out.println(); 
//		 }
		
		
//		//////// Move for DB ////////
//		DBController DBController = new DBController(); // Database controller
//		HttpSession session = req.getSession(false); // get current session
//		session.setAttribute("playerModel", pModel);
//		Player player = (Player) session.getAttribute("player"); // get player from session
//		DBController.getPlayerByUsernameAndPassword(player.getUsername(), player.getPassword()); // get current player model
//		DBController.InsertNewMove(player.getPlayerId(), 1, action); //deal with later
//		System.out.println("Player entered : " + action);
//		//////// Move for DB ////////
		
		 for(int i = 0; i <= 2; i++) //why can't I just do <2
		 {
			 action = userInput.split(" ");
		 }
		 System.out.println(action[0]);
		 System.out.println(action[1]);
		 
		 if(action[0].equals("")) {
			 errorMessage = "Please specify an action or click 'Stuck?' for a list of commands.";
			 }
		 
		 //deals with MOVE command - done I think - no DB stuff
		 if (action[0].equals("move")) {
			 controller.setMovement(true);
			 if(action[1].equals("")) {
				 result = "Move where?";
			 }
			 else if(controller.checkMove(action[1])) {
				 result = "You moved " + action[1];
			 } else {
				 result = "That isn't a valid direction";
			 }
		 }
		 
		 if (currentR.hasMonster()) {
			 result = "The " + currentR.getMonster().getNameTag().getName()
						+ " blocks your way. You'll have to kill it to loot this room.";
		 }

		 //deals with LOOK command - done I think - no DB stuff	 
		 if (action[0].equals("look")) {
			 if (action[1].equals("")) {
				 result = "Look where?";
			 } else {
				 result = controller.Look(action[1]);
			 }
		 }
		 
		 //deals with GRAB command - update PLAYER and ROOM INVENTORY DB
		 if (action[0].equals("grab")) {
			 if (action[1].equals("")) {
				 result = "Grab what?";
			 } else {
				 result = controller.grab(action[1]);
			 }
			 //controller.indicateGrab(true);
			//UPDATE INVENTORY DB FOR ROOM + PLAYER
		 }
		 
		 //deals with PLACE command - done I think - update PLAYER and ROOM INVENTORY DB 
		 if (action[0].equals("place")){
			 result = controller.place(action[1]);
			 }
			 //UPDATE INVENTORY FOR ROOM AND PLAYER
			 //check if place is valid + update inventory
			 // probably gonna move all the fat place and grab code into a controller
		 
		 
		 //deals with USE command - done I think - update PLAYER INVENTORY and HEALTH
		 if (action[0].equals("use")) {
			 //model.indicateUse(true);
			 controller.use(action[1]);
			 //check if use is valid + update inventory and health
			 // gonna implement a controller that checks validity
		 }
		 
		 //deals with INSPECT command - done I think - no DB stuff
		 if (action[0].equals("inspect")) {
			// model.indicateInspect(true);
			 if (action[1].equals("")) {
				 result = "Inspect what?";
			 } else {
				 result = controller.inspect(action[1]);
			 }
		 }
			 else {
				 result = "That is not a command I recognize";
			 }
	
		 
		 
		 //deals with LIGHT command - done I think - DB stuff but idk what yet
		 
		 //deals with LIGHT command - done I think
		 if (action[0].equals("light")) {
			 controller.light(action[1]);
		 }
		 
		 //deals with OPEN command - almost done [NEED WIN SCREEN]
		 if (action[0].equals("open")){
			 controller.open(action[1]);
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
