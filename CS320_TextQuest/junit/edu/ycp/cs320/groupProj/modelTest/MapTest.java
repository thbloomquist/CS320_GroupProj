package edu.ycp.cs320.groupProj.modelTest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
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
	@Test
	public void testGetDark() {
		assertTrue(m.getRoom(1, 8).isDark());
		assertTrue(m.getRoom(2, 8).isDark());
		assertTrue(m.getRoom(3, 8).isDark());
		assertTrue(m.getRoom(3, 7).isDark());
		assertTrue(m.getRoom(3, 6).isDark());
		assertTrue(m.getRoom(7, 6).isDark());
		assertTrue(m.getRoom(7, 7).isDark());
		assertTrue(m.getRoom(7, 8).isDark());
		assertTrue(m.getRoom(5, 7).isDark());
		assertTrue(m.getRoom(5, 8).isDark());
		assertTrue(m.getRoom(6, 7).isDark());
		assertTrue(m.getRoom(6, 8).isDark());
		assertTrue(m.getRoom(8, 6).isDark());
		assertTrue(m.getRoom(8, 7).isDark());
		assertTrue(m.getRoom(8, 8).isDark());
	}
	@Test
	public void testcantEnter() {
		assertFalse(m.getRoom(0, 0).getEnter());
		assertFalse(m.getRoom(2, 3).getEnter());
		assertFalse(m.getRoom(9, 2).getEnter());
		assertFalse(m.getRoom(2, 7).getEnter());
		
	}
	@Test
	public void testKillMonster() {
		assertTrue(m.getRoom(8,1).hasMonster());
		m.getRoom(8, 1).deadMonster();
		assertFalse(m.getRoom(8, 1).hasMonster());
	}
	@Test
	public void testMakeMonster() {
		assertFalse(m.getRoom(7, 3).hasMonster());
		m.getRoom(7, 3).makeMonster();
		assertTrue(m.getRoom(7, 3).hasMonster());
	}
	@Test
	public void testCreateKey() {
		m.getRoom(8, 8).createKey();
		assertTrue(m.getRoom(8, 8).getInven()[7].getKey()); // 7 is a temp value for testing purposes
	}
	@Test
	public void testGetChest() {
		assertTrue(m.getRoom(1, 6).hasChest());
	}
	
	
}
