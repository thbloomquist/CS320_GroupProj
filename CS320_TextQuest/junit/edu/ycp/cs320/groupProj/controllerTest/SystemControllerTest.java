package edu.ycp.cs320.groupProj.controllerTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.groupProj.controller.GuessingGameController;
import edu.ycp.cs320.groupProj.model.SystemModel;

public class SystemControllerTest {
	private SystemModel model;
	private GuessingGameController controller;
	
	@Before
	public void setUp() {
		model = new SystemModel();
		controller = new GuessingGameController();
		
		//model.setMin(1);
		//model.setMax(100);
		
		controller.setModel(model);
	}
	
	//@Test
/**	public void testNumberIsGreater() {
		int currentGuess = model.getGuess();
		controller.setNumberIsGreaterThanGuess();
		assertTrue(model.getGuess() > currentGuess);
	}
	@Test
	public void testRestart() {
		model.setMin(3);
		model.setMax(6);
		controller.startGame();
		assertEquals(1, model.getMin());
		assertEquals(100, model.getMax());
	} **/
}
