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


@WebServlet(urlPatterns = "/login") //instead of xml
public class LoginServlet extends HttpServlet {
	String errorMessage = "";
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Login Up Servlet: doGet");	
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Login Up Servlet: doPost");
		DBController controller = new DBController();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		Player player = controller.getPlayerByUsernameAndPassword(username, password);

		if(player != null) {
			System.out.println(username + "'s account logged in!");
			HttpSession session = req.getSession(true);	
			session.setAttribute("player", player);
		}
		else {
			System.out.println("Account not found");
			errorMessage = "Invalid username or password.";
			req.setAttribute("errorMessage", errorMessage);
			doGet(req, resp);
			return;
		}
		
		req.setAttribute("errorMessage", errorMessage);
			
		req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
	}
}
