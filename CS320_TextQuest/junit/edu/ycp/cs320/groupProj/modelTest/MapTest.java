package edu.ycp.cs320.groupProj.modelTest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.groupProj.model.Map;
import edu.ycp.cs320.groupProj.model.Room;

public class MapTest {
	Map m;
	
	@Before
	public void setUp() {
		m = new Map();
		m.reset();
	}
	
	@Test
	public void testcanEnter() {
		assertTrue(m.getRoom(7, 3).getEnter());
		assertTrue(m.getRoom(8, 3).getEnter());
		assertTrue(m.getRoom(8, 2).getEnter());
		assertTrue(m.getRoom(8, 1).getEnter());
		assertTrue(m.getRoom(6, 1).getEnter());
		assertTrue(m.getRoom(1, 8).getEnter());
		assertTrue(m.getRoom(1, 4).getEnter());
	}
	@Test
	public void testHasMonster() {
		assertTrue(m.getRoom(1, 8).hasMonster());
		assertTrue(m.getRoom(8, 1).hasMonster());
		assertTrue(m.getRoom(5, 8).hasMonster());
		assertTrue(m.getRoom(6, 7).hasMonster());
	}
}
