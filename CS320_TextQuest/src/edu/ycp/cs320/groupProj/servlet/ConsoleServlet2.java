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
		 for(int i = 0; i < 2; i++)
		 {
			 action = userInput.split(" ");
		 }
		 
		 if (currentR.hasMonster()) {
			 result = "The " + currentR.getMonster().getNameTag().getName()
						+ " blocks your way. You'll have to kill it to loot this room.";
		 }
		 
		 if(action[0].contentEquals(null)) {
			 errorMessage = "Please specify an action or click 'Stuck?' for a list of commands.";		 }
		 if (action[0].contentEquals("move")) {
			 if(action[1].contentEquals("north")) {
				 //do north things
			 } else if(action[1].contentEquals("south")) {
				 //do south things
			 } else if(action[1].contentEquals("east")) {
				 //do east things
			 } else if(action[1].contentEquals("west")) {
				 //do west things
			 }
		 }
		 
		 //deals with LOOK command - done I think
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
				 else if (currentR.isEmpty()) {
					 result += "an empty room";
				 }
				 else if (!currentR.isEmpty()) {
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
		 
		 //deals with GRAB command
		 if (action[0].contentEquals("grab")) {
			 model.indicateGrab(true);
			 if (pModel.getiNum() == 9) {
					result = "Your inventory is full.";
				} else if (currentR.isDark() && !pModel.isLit()) {
					result = "It's too dark to try to grab anything";
				}
		 }
		 
		 //deals with PLACE command
		 if (action[0].contentEquals("place")){
			 //check if place is valid + update inventory
		 }
		 
		 //deals with USE command
		 if (action[0].contentEquals("use")) {
			 //check if use is valid + update inventory and health
		 }
		 
		 //deals with INSPECT command
		 if (action[0].contentEquals("inspect")) {
			 if (action[1].contentEquals(null)) {
				 result = "Inspect what?";
			 }
			 else if (action[1].contentEquals("room")) {
				 
			 }
			 else if (action[1].contentEquals("inventory")){
				 
				 
			 }
			 else {
				 result = "That is not a command I recognize";
			 }
		 }
		 
		 //deals with LIGHT command - done I think
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
						 //CREATE WIN SCREEN HERE
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
