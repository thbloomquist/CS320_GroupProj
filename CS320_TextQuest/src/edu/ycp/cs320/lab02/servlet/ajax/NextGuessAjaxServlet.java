package edu.ycp.cs320.lab02.servlet.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ycp.cs320.groupProj.controller.GuessingGameController;
import edu.ycp.cs320.groupProj.model.SystemModel;

public class NextGuessAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doRequest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doRequest(req, resp);
	}

	private void doRequest(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Read client's model
		Integer min = getInteger(req, "min");
		Integer max = getInteger(req, "max");
		
		if (min == null || max == null) {
			badRequest("Invalid min/max values", resp);
			return;
		}
		
		SystemModel model = new SystemModel();
		// If an action was specified, use a GuessingGameController to carry it out
		String action = req.getParameter("action");

		
		
	}

	private Integer getInteger(HttpServletRequest req, String name) {
		String val = req.getParameter(name);
		if (val == null) {
			return null;
		}
		try {
			return Integer.parseInt(val);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private void badRequest(String message, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		resp.getWriter().println(message);
	}
}
