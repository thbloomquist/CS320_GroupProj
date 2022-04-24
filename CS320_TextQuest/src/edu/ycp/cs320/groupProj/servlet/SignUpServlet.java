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


@WebServlet(urlPatterns = "/signup")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Sign Up Servlet: doGet");	
		// call JSP to generate empty form
		req.getRequestDispatcher("/_view/signup.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		DBController controller = new DBController();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		if(controller.InsertNewPlayer(username, password)) {
			System.out.println(username + "'s account created succesfully!");
			HttpSession session = req.getSession(true);			
			Player player = controller.getPlayerByUsernameAndPassword(username, password);
			
			if(player == null) {
				System.out.println("Account not found");
			}
			else {
				session.setAttribute("player", player);
			}
			
			
			req.getRequestDispatcher("/_view/index.jsp").forward(req, resp);
			return;
		}
		
		
		
		System.out.println("Sign Up Servlet: doPost");
		req.getRequestDispatcher("/_view/signup.jsp").forward(req, resp);
	}
}
