package edu.ycp.cs320.groupProj.modelTest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.groupProj.model.Room;
import edu.ycp.cs320.groupProj.model.NameTag;
import edu.ycp.cs320.groupProj.model.ObjectModel;

public class RoomTest {
	Room room1;
	Room room2;
	
	@Before
	public void setUp() {
		NameTag n = new NameTag("test room", "The white paint on the walls is peeling off");
		ObjectModel[] obj = new ObjectModel[10];
//		room1 = new Room(false, n, obj, true, true);
//		room2 = new Room(true, n, obj, false, false);
		
	}
	
	@Test
	public void testisEmpty() {
		assertTrue(!room1.checkEmpty());
		assertTrue(room2.checkEmpty());
	}
	@Test
	public void testHasMonster() {
			assertTrue(room1.hasMonster());
			assertTrue(!room2.hasMonster());
			
			room1.deadMonster();
			assertTrue(!room1.hasMonster());
	}
	@Test
	public void testCanEnter() {
		assertTrue(!room1.getEnter());
		assertTrue(room2.getEnter());
	}
	@Test
	public void testMakeMonster() {
		room2.makeMonster();
		assertTrue(room2.hasMonster());
	}
	@Test
	public void testIsDark() {
		// originally rooms aren't dark unless stated otherwise
		assertFalse(room1.isDark());
		assertFalse(room2.isDark());
	}
	@Test
	public void testSetDark() {
		assertFalse(room1.isDark());
		room1.setDark(true);
		assertTrue(room1.isDark());
	}
	
}
