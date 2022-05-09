package edu.ycp.cs320.groupProj.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.groupProj.controller.DBController;
import edu.ycp.cs320.groupProj.model.PlayerModel;

public class DeadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int score = 0;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("Dead Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/dead2.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		System.out.println("Dead Servlet: doPost");
		
		DBController db = new DBController();
		
		HttpSession session = req.getSession(false);
		Player player = (Player) session.getAttribute("player"); 
		player = db.getPlayerByUsernameAndPassword(player.getUsername(), player.getPassword());
		PlayerModel model = db.LoadPlayerModel(player.getPlayerId());
		if(model == null) {
			score = 0;
		} else {
			score = model.getScore();
		}
		
		
		req.setAttribute("score", score);
		
	}
		
}
