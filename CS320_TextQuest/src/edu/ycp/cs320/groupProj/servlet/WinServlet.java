package edu.ycp.cs320.groupProj.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.ycp.cs320.gamedb.model.Player;
import edu.ycp.cs320.groupProj.controller.DBController;
import edu.ycp.cs320.groupProj.controller.SystemController;
import edu.ycp.cs320.groupProj.model.PlayerModel;

public class WinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int score = 0;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("AddNumbers Servlet: doGet");	
		
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/win.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DBController db = new DBController();
		
		System.out.println("AddNumbers Servlet: doPost");
		HttpSession session = req.getSession(false);
		Player player = (Player) session.getAttribute("player"); 
		player = db.getPlayerByUsernameAndPassword(player.getUsername(), player.getPassword());
		PlayerModel model = db.LoadPlayerModel(player.getPlayerId());
		if(model == null) {
			score = 0;
		} else {
			score = model.getScore();
		}
				

		// holds the error message text, if there is any
		String errorMessage = null;

		// result of calculation goes here
		Double result = null;
		
		
		// add result objects as attributes
		// this adds the errorMessage text and the result to the response
		req.setAttribute("errorMessage", errorMessage);
		req.setAttribute("result", result);
		req.setAttribute("score", score);
		
		// Forward to view to render the result HTML document
		req.getRequestDispatcher("/_view/win.jsp").forward(req, resp);
	}

	// gets double from the request with attribute named s
	private Double getDoubleFromParameter(String s) {
		if (s == null || s.equals("")) {
			return null;
		} else {
			return Double.parseDouble(s);
		}
	}
}
