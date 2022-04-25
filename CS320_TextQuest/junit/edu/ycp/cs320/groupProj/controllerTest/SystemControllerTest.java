package edu.ycp.cs320.groupProj.controllerTest;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;


import edu.ycp.cs320.groupProj.model.SystemModel;
import edu.ycp.cs320.groupProj.controller.SystemController;
import edu.ycp.cs320.groupProj.model.NameTag;
import edu.ycp.cs320.groupProj.model.ObjectModel;
import edu.ycp.cs320.groupProj.controller.ObjectController;

public class SystemControllerTest {
	SystemModel model1 = new SystemModel();
	SystemModel model2 = new SystemModel();
	SystemController control1 = new SystemController();
	SystemController control2 = new SystemController();
	
	
	// this is a comprehensive set of tests for the system controller AND the system model
	@Before
	public void setUp() {
		
		control1.setModel(model1);
		control2.setModel(model2);
	}
	
	@Test
	public void testGetGrab() {
		assertFalse(control1.getGrab());
		assertFalse(control2.getGrab());
	}
	@Test
	public void testGetPlace() {
		assertFalse(control1.getPlace());
		assertFalse(control2.getPlace());
		
	}
	@Test
	public void testGetInspect() {
		assertFalse(control1.getInspect());
		assertFalse(control2.getInspect());
	}
	@Test
	public void testAutoSetGrab() {
		control1.setGrab();
		control2.setGrab();
		assertTrue(control1.getGrab() && control2.getGrab());
	}
	@Test
	public void testAutoSetPlace() {
		control1.setPlace();
		assertTrue(control1.getPlace());
		control2.setPlace();
		assertTrue(control2.getPlace());
		control2.setPlace(); // should set Place back to false
		assertFalse(control2.getPlace());
	}
	
	
}
