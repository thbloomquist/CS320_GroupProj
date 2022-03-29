package edu.ycp.cs320.groupProj.modelTest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.groupProj.model.NameTag;
import edu.ycp.cs320.groupProj.model.ObjectModel;

public class PlayerModelTest {
	private PlayerModel model;
	
	@Before
	public void setUp() {
		model = new PlayerModel();
	}
	
	@Test
	public void testInitial() {
		model.reset();
		assertTrue(model.getUp()-1 == 0);
		
		assertTrue(model.getSide()-2 == 0);
		
		assertTrue(model.getiNum() == 0);
	}
	
	public void addItem() {
		NameTag n = new NameTag("Bla", "Bla");
		ObjectModel thing = new ObjectModel(n, 5, false);
		
		model.addInventory(thing);
		
		assertTrue(model.getInventory(0) == thing);
		
	}
	
}
