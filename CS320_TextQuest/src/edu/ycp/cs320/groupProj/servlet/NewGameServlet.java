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


@WebServlet(urlPatterns = "/new.game") //instead of xml
public class NewGameServlet extends HttpServlet {
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
		
		pModel = DBController.LoadPlayerModel(player.getPlayerId());
		
		
		// initialize all DB's to new values
		if(pModel == null) { // if no PlayerModel, then insert one
			DBController.InsertNewPlayerModel(player.getPlayerId(), 100, 3, 7, 0, 10);
		}else {
			DBController.UpdatePlayerModel(player.getPlayerId(), 100, 3, 7, 0, 10);
		}
		// wipe players inventory
		DBController.UpdatePlayerInven(player.getPlayerId(), "torch");
		
		session.setAttribute("playerModel", pModel);
		
		Map map = new Map();
		
		session.setAttribute("map", map);
		
		Boolean mapCreation = false;
		
		// store map in DB
		for (int y = 1; y < 9; y++) {
			for (int x = 1; x < 9; x++) {
				if(DBController.getRoomInventory(player.getPlayerId(), x, y) != null) {
					mapCreation = true;
				}
			}
		}
		
		if(mapCreation) {
			System.out.println("Map Exists, updating table");
			for (int y = 1; y < 9; y++) {
				for (int x = 1; x < 9; x++) {
					String insert = "";
					if (map.getRoom(y, x).getEnter()) {
						for (int i = 0; i < map.getRoom(y, x).getInven().size(); i++) {
							if (map.getRoom(y, x).getInven().get(i) != null) {
								insert += map.getRoom(y, x).getInven().get(i).getTag().getName() + " ";
							}
							
						}
					DBController.UpdateRoomInven(player.getPlayerId(), x, y, insert);
					}
				}
			}
		}else {
		System.out.println("Map DOESNT exists, creating table");
		// store map in DB
		for (int y = 1; y < 9; y++) {
			for (int x = 1; x < 9; x++) {
				String insert = "";
				if (map.getRoom(y, x).getEnter()) {
					for (int i = 0; i < map.getRoom(y, x).getInven().size(); i++) {
						if (map.getRoom(y, x).getInven().get(i) != null) {
							insert += map.getRoom(y, x).getInven().get(i).getTag().getName() + " ";
						}
					}
					DBController.InsertRoomInven(player.getPlayerId(), x, y, insert);
				}
			}
		}
		}
		
		req.setAttribute("welcome", true);
		
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
