package edu.ycp.cs320.groupProj.controllerTest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.groupProj.model.PlayerModel;
import edu.ycp.cs320.groupProj.model.NameTag;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.controller.ObjectController;

public class ObjectControllerTest {
	private ObjectModel tempO = new ObjectModel();
	private ObjectController TempO = new ObjectController();
	ObjectModel banana = new ObjectModel();
	
	@Before
	public void setUp() {
		TempO.setModel(tempO);
		banana = TempO.banana();
	}
	
	@Test
	public void testTag() {
		NameTag n = banana.getTag();
		assertTrue(n.getName().equals("Banana"));
		assertTrue(n.getDesc().equals("It's a yellow banana"));
	}
	@Test
	public void testInt() {
		assertTrue(banana.getThing() == 5);
	}
	@Test
	public void testisKey() {
		assertFalse(banana.getKey());
	}
	
	
	
}
