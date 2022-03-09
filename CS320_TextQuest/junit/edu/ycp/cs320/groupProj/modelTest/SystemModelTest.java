package edu.ycp.cs320.groupProj.modelTest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.groupProj.model.SystemModel;

public class SystemModelTest {
	private SystemModel model;
	
	@Before
	public void setUp() {
		model = new SystemModel();
	}
	
	@Test
	public void testFlipMove() {
		model.setMovement();
		assertTrue(model.getMovement());
		model.setMovement();
		assertTrue(!model.getMovement());
	}
	@Test
	public void testSetMove() {
		Boolean thing = true;
		Boolean thing2 = false;
		model.indicateMove(thing);
		assertTrue(model.getMovement());
		model.indicateMove(thing2);
		assertTrue(!model.getMovement());
	}
}
