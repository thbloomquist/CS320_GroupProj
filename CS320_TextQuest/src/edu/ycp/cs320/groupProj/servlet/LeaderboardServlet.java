package edu.ycp.cs320.groupProj.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.groupProj.controller.DBController;
import edu.ycp.cs320.groupProj.model.PlayerModel;


@WebServlet(urlPatterns = "/leaderboard") //instead of xml
public class LeaderboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Leaderboard Servlet: doPost");
		
		DBController controller = new DBController();
		ArrayList<PlayerModel> players = controller.getLeaderboard();
		
		for(PlayerModel playerss : players) {
			System.out.println(playerss.getScore());
		}
		
		req.setAttribute("allPlayers", players);
		
		req.getRequestDispatcher("/_view/leaderboard.jsp").forward(req, resp);
		
	}
}
