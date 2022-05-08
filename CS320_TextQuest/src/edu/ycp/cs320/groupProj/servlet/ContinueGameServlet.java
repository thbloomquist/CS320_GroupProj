package edu.ycp.cs320.groupProj.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.groupProj.controller.DBController;
import edu.ycp.cs320.groupProj.model.Map;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.model.PlayerModel;


@WebServlet(urlPatterns = "/continue.game") //instead of xml
public class ContinueGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("New Game Servlet: doPost");
		
		HttpSession session = req.getSession(false);
		
		if(session == null) {
			System.out.println("No session active, redirecting to login");
			LoginServlet loginServlet = new LoginServlet();
			loginServlet.doGet(req, resp);
			return;
		}
		
		Player player = (Player) session.getAttribute("player"); // get player from session
		
		PlayerModel pModel = null;
		DBController DBController = new DBController(); // Database controller
		
		// initilize the map
		Map map = new Map();
		
		// load the map
		for (int y = 1; y < 9; y++) {
			for (int x = 1; x < 9; x++) {
					map.setRoomInventory(x, y, DBController.getRoomInventory(player.getPlayerId(),x,y));
				
			}
		}
		
		session.setAttribute("map", map);
		
		ConsoleServlet2 console = new ConsoleServlet2();
		console.doGet(req, resp);
		return;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
