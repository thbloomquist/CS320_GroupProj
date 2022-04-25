package edu.ycp.cs320.groupProj.modelTest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.groupProj.model.Monster;
import edu.ycp.cs320.groupProj.model.NameTag;

public class MonsterTest {
	Monster standard;
	Monster custom;
	Monster corpse;
	
	@Before
	public void setUp() {
		standard = new Monster();
		NameTag c = new NameTag("Dragon", "It's a winged beast");
		custom = new Monster(500, 40, c);
		corpse = new Monster(-1, 5, new NameTag("Corpse", "Gross!"));
	}
	
	@Test
	public void testgetMonsterDesc() {
		assertTrue(custom.getNameTag().getDesc().equals("It's a winged beast"));
		assertTrue(standard.getNameTag().getDesc().equals("It's big, purple, and scary!"));
	}
	@Test
	public void testGetMonsterName() {
		assertTrue(custom.getNameTag().getName().equals("Dragon"));
		assertTrue(standard.getNameTag().getName().equals("Purple People Eater"));
	}
	@Test
	public void testGetDMG() {
		assertTrue(custom.getDMG() == 40);
		assertTrue(standard.getDMG() == 10);
		assertTrue(corpse.getDMG() == 5);
	}
	@Test
	public void testGetHP() {
		assertTrue(custom.getHP() == 500);
		assertTrue(standard.getHP() == 100);
		assertTrue(corpse.getHP() < 0); // this is useful for the nxt test
	}
	@Test
	public void testGetAlive() {
		assertTrue(custom.getAlive() && standard.getAlive());
		assertFalse(corpse.getAlive()); // any custom monster w/ health below 100 should default to not alive
	}
	@Test
	public void testSetAlive() {
		standard.setAlive(false);
		assertFalse(standard.getAlive());
		assertTrue(custom.getAlive()); // custom should still be alive
	}
	@Test
	public void testDifferentMonster() {
		assertTrue(custom.getDMG() != standard.getDMG());
		assertTrue(custom.getHP() != standard.getHP());
	}
}
