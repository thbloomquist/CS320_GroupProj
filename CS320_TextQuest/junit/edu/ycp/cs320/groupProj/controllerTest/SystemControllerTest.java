package edu.ycp.cs320.groupProj.controllerTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

<<<<<<< HEAD
//import edu.ycp.cs320.groupProj.controller.GuessingGameController;
=======
import edu.ycp.cs320.groupProj.controller.NotGuessingGameController;
>>>>>>> refs/remotes/origin/emains
import edu.ycp.cs320.groupProj.model.SystemModel;

public class SystemControllerTest {
	private SystemModel model;
<<<<<<< HEAD
//	private GuessingGameController controller;
=======
	private NotGuessingGameController controller;
>>>>>>> refs/remotes/origin/emains
	
	@Before
	public void setUp() {
		model = new SystemModel();
<<<<<<< HEAD
//		controller = new GuessingGameController();
//		
//		//model.setMin(1);
//		//model.setMax(100);
//		
//		controller.setModel(model);
=======
		controller = new NotGuessingGameController();
		
		//model.setMin(1);
		//model.setMax(100);
		
		controller.setModel(model);
>>>>>>> refs/remotes/origin/emains
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
