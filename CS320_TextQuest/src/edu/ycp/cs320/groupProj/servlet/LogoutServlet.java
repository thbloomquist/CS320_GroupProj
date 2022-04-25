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


@WebServlet(urlPatterns = "/logout") //instead of xml
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("Log Out Servlet: doPost");
		
		HttpSession session = req.getSession(false);
		
		if(session == null) {
			System.out.println("No session active, redirecting to login");
		}else {
			System.out.println("Deleting current session");
			session.invalidate();
		}
		
		req.getRequestDispatcher("/_view/login.jsp").forward(req, resp);
	}
}
