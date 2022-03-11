package edu.ycp.cs320.groupProj.modelTest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.groupProj.model.DirectionsModel;

public class DirectionsModelTest {
	private DirectionsModel model;
	
	@Before
	public void setUp() {
		model = new DirectionsModel();
	}
	
	@Test
	public void testMoveNorth() {
		model.moveNorth();
		assertTrue(model.getNorth()-1 == 0);
		model.moveNorth();
		assertTrue(model.getNorth() == 2);
	}
	@Test
	public void testMoveSouth() {
		model.moveSouth();
		assertTrue(model.getSouth()-1 == 0);
		model.moveSouth();
		assertTrue(model.getSouth() == 2);
	}
	
	@Test
	public void testMoveEast() {
		model.moveEast();
		assertTrue(model.getEast()-1==0);
		model.moveEast();
		assertTrue(model.getEast() == 2);
	}
	
	@Test
	public void testMoveWest() {
		model.moveWest();
		assertTrue(model.getWest()-1==0);
		model.moveWest();
		assertTrue(model.getWest() == 2);
	}
	
	@Test
	public void testTotals() {
		model.setMovements(1, 1, 1, 1);
		assertTrue(model.getUp() == 0);
		assertTrue(model.getSide() == 0);
		
		model.setMovements(7, 2, 4, 2);
		assertTrue(model.getUp() == 5); // up = (north-south)
		assertTrue(model.getSide() == 2); // side = (east-west)
	}
}
